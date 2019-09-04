/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2019 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.webfolder.cdp.session;

import static io.webfolder.cdp.session.ContextLockType.LockInvocation;
import static java.lang.System.currentTimeMillis;
import static java.util.Base64.getDecoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.webfolder.cdp.annotation.Domain;
import io.webfolder.cdp.annotation.Returns;
import io.webfolder.cdp.channel.Channel;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.exception.CdpReadTimeoutException;
import io.webfolder.cdp.logger.CdpLogger;

class SessionInvocationHandler implements InvocationHandler {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Gson gson;

    private final Channel channel;

    private final Map<Integer, Context> contexts;

    private final List<String> enabledDomains = new CopyOnWriteArrayList<>();

    private final CdpLogger log;

    private final Session session;

    private final String sessionId;

    private final int readTimeout;

    private ContextLockType contextLockType;

    SessionInvocationHandler(
                    final Gson gson,
                    final Channel channel,
                    final Map<Integer, Context> contexts,
                    final Session session,
                    final CdpLogger log,
                    final String sessionId,
                    final int readTimeOut,
                    final ContextLockType contextLockType) {
        this.gson            = gson;
        this.channel         = channel;
        this.contexts        = contexts;
        this.session         = session;
        this.log             = log;
        this.sessionId       = sessionId;
        this.readTimeout     = readTimeOut;
        this.contextLockType = contextLockType;
    }

    @Override
    public Object invoke(
                final Object proxy,
                final Method method,
                final Object[] args) throws Throwable {

        final Class<?> klass = method.getDeclaringClass();
        final String  domain = klass.getAnnotation(Domain.class).value();
        final String command = method.getName();

        final boolean hasArgs = args != null && args.length > 0;
        final boolean isVoid  = void.class.equals(method.getReturnType());

        // it's unnecessary to call enable command more than once.
        boolean enable = isVoid && command.equals("enable");
        if (enable && enabledDomains.contains(domain)) {
            return null;
        }

        boolean disable = isVoid && "disable".equals(command);

        Map<String, Object> params = hasArgs ? new HashMap<>(args.length) : null;

        if (hasArgs) {
            int argIndex = 0;
            Parameter[] parameters = method.getParameters();
            for (Object argValue : args) {
                String argName = parameters[argIndex++].getName();
                params.put(argName, argValue);
            }
        }

        int id = counter.incrementAndGet();
        Map<String, Object> map = new HashMap<>(3);
        map.put("id"    , id);
        map.put("sessionId", sessionId);
        map.put("method", domain + "." + command);
        if (hasArgs) {
            map.put("params", params);
        }

        String json = gson.toJson(map);

        log.debug("--> {}", json);

        Context context = null;

        final long start = currentTimeMillis();

        if (session.isConnected()) {
            context = LockInvocation.equals(contextLockType) ? new SemaphoreContext() : new ThreadContext();
            contexts.put(id, context);
            channel.sendText(json);
            context.await(readTimeout);
        } else {
            throw new CdpException("WebSocket connection is not alive. id: " + id);
        }

        if ( (context.getData() == null && context.getError() == null) &&
                   (currentTimeMillis() - start) >= readTimeout ) {
            throw new CdpReadTimeoutException(readTimeout + "ms");
        }

        if ( context.getError() != null ) {
            throw context.getError();
        }

        if (enable) {
            enabledDomains.add(domain);
        } else if (disable) {
            enabledDomains.remove(domain);
        }

        if (isVoid) {
            return null;
        }

        JsonElement data = context.getData();
        if (data == null) {
            return null;
        }

        String returns = method.isAnnotationPresent(Returns.class) ?
                             method.getAnnotation(Returns.class).value() : null;

        if ( ! data.isJsonObject() ) {
            throw new CdpException("invalid response");
        }

        JsonObject object = data.getAsJsonObject();
        JsonElement result = object.get("result");

        if ( result == null || ! result.isJsonObject() ) {
            throw new CdpException("invalid result");   
        }

        JsonObject resultObject = result.getAsJsonObject();

        Object ret = null;
        Type genericReturnType = method.getGenericReturnType();

        if (returns != null) {

            JsonElement jsonElement = resultObject.get(returns);

            Class<?> retType = method.getReturnType();

            if ( jsonElement != null && jsonElement.isJsonPrimitive() ) {
                if (String.class.equals(retType)) {
                    return resultObject.get(returns).getAsString();
                } else if (Boolean.class.equals(retType)) {
                    return resultObject.get(returns).getAsBoolean() ? Boolean.TRUE : Boolean.FALSE;
                } else if (Integer.class.equals(retType)) {
                    return resultObject.get(returns).getAsInt();
                } else if (Double.class.equals(retType)) {
                    return resultObject.get(returns).getAsDouble();
                }
            }

            if ( jsonElement != null && byte[].class.equals(genericReturnType) ) {
                String encoded = gson.fromJson(jsonElement, String.class);
                if (encoded == null || encoded.trim().isEmpty()) {
                    return null;
                } else {
                    return getDecoder().decode(encoded);
                }
            }

            if (List.class.equals(retType)) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                ret = gson.fromJson(jsonArray, genericReturnType);
            } else {
                ret = gson.fromJson(jsonElement, genericReturnType);
            }
        } else {
            ret = gson.fromJson(resultObject, genericReturnType);
        }

        return ret;
    }

    void dispose() {
        enabledDomains.clear();
        for (Context context : contexts.values()) {
            try {
                context.release();
            } catch (Throwable t) {
                // ignore
            }
        }
    }
}
