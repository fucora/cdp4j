package io.webfolder.cdp.session;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

class CdpTypeAdapterFactory implements TypeAdapterFactory {

    private final ClassLoader cl = getClass().getClassLoader();

    private static final String EVENT_PACKAGE  = "io.webfolder.cdp.event";

    private static final String TYPE_PACKAGE   = "io.webfolder.cdp.type";

    private static final String ADAPTER_PREFIX = "$TypeAdapter";

    @SuppressWarnings("rawtypes")
    private Map<Class, TypeAdapter> adapters = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final Class clazz = type.getRawType();
        final String packageName = clazz.getPackageName();
        if ( packageName != null &&
                (packageName.startsWith(EVENT_PACKAGE) ||
                 packageName.startsWith(TYPE_PACKAGE)) ) {
            final TypeAdapter typeAdapter = adapters.get(clazz);
            if ( typeAdapter != null ) {
                return typeAdapter;
            }
            try {
                final Class<?> typeAdapterClass = cl.loadClass(clazz.getName() + ADAPTER_PREFIX);
                final Constructor<?> constructor = typeAdapterClass.getConstructor(Gson.class);
                final TypeAdapter instance = (TypeAdapter) constructor.newInstance(gson);
                TypeAdapter existing = adapters.putIfAbsent(clazz, instance);
                if ( existing != null ) {
                    return existing;
                } else {
                    return instance;
                }
            } catch (ClassNotFoundException    | NoSuchMethodException    |
                     SecurityException         | InstantiationException   |
                     IllegalAccessException    | IllegalArgumentException |
                     InvocationTargetException e) {
                return null;
            }
        }
        return null;
    }

    public void dispose() {
        adapters.clear();
    }
}
