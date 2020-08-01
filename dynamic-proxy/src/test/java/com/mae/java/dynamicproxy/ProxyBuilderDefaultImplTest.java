package com.mae.java.dynamicproxy;

import com.mae.java.dynamicproxy.sample.Echo;
import com.mae.java.dynamicproxy.sample.EchoImpl;
import com.mae.java.dynamicproxy.util.ProxyUtil;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class ProxyBuilderDefaultImplTest {

    @Test
    public void testCanProxy() {
        IProxyBuilder proxyBuilder = new ProxyBuilderDefaultImpl();
        assertThat(proxyBuilder.supportProxy(Echo.class), Matchers.is(true));
    }

   /* @Test
    public void testDelegator() {
        //IProxyBuilder proxyBuilder = ProxyUtil.getInstance();
        Echo proxy = proxyBuilder.createDelegatorProxy(new IObjectProvider<Object>() {
            @Override
            public Object getObject() {
                return new EchoImpl();
            }
        }, new Class<?> [] {Echo.class});
        assertEquals(proxy.echoBack("hello"), "hello");
    }*/
}