package io.webfolder.cdp.sample;

import static java.lang.String.format;
import static java.net.Proxy.Type.SOCKS;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.github.markusbernhardt.proxy.ProxySearch;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class ProxyConnection {

    public static void main(String[] args) throws Exception {
        ProxySearch proxySearch = ProxySearch.getDefaultProxySearch();
        ProxySelector proxySelector = proxySearch.getProxySelector();

        SessionFactory factory = new SessionFactory();
        Proxy cdp4jProxy = getProxy(proxySelector,
                                        new URI(format("http://%s:%d", factory.getHost(), factory.getPort())),
                                        false);

        if (cdp4jProxy != null) {
            factory.setHttpClientProxy(cdp4jProxy);
            InetSocketAddress cdp4jProxyAddress = (InetSocketAddress) cdp4jProxy.address();
            factory.getWebSocketProxySettings().setHost(cdp4jProxyAddress.getHostName());
            factory.getWebSocketProxySettings().setPort(cdp4jProxyAddress.getPort());
        }

        String url = "https://google.com";

        Proxy chromeProxy = getProxy(proxySelector, new URI(url), true);

        List<String> arguments = new ArrayList<>();

        if (chromeProxy != null) {
            String protocol = SOCKS.equals(chromeProxy.type()) ? "socks5" : "http";
            InetSocketAddress chromeProxyAddress = (InetSocketAddress) chromeProxy.address();
            arguments.add(format("--proxy-server=%s://%s:%d",
                                protocol,
                                chromeProxyAddress.getHostName(),
                                chromeProxyAddress.getPort()));
        }

        Launcher launcher = new Launcher(factory);
        launcher.launch(arguments);

        try (Session session = factory.create()) {
            session.navigate(url);
            session.waitDocumentReady();
            String content = session.getContent();
            System.out.println(content);
        } finally {
            factory.close();
        }
    }

    protected static Proxy getProxy(ProxySelector selector, URI uri, boolean supportSocks) {
        if (selector == null) {
            return null;
        }
        List<Proxy> proxies = selector.select(uri);
        Proxy found = null;
        for (Proxy proxy : proxies) {
            switch (proxy.type()) {
                case HTTP:
                    found = proxy;
                break;
                case SOCKS:
                    if (supportSocks) {
                        found = proxy;
                    }
                break;
                default:
                    found = null;
                break;
            }
        }
        return found;
    }
}
