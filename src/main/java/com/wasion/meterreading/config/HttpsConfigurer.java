package com.wasion.meterreading.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Tomcat 提供的重定向默认是302的，302的重定向请求会将Post请求转化为Get请求，与我们的实际需求不符，如果实在要使用https，使用Nginx吧
 * 
 * @author w24882 xieningjie
 * @date 2020年1月17日
 * @see
 */
/*@Configuration*/
public class HttpsConfigurer {

    @Value("${server.port}")
    private int serverPort;

    @Value("${server.http.port}")
    private int serverHttpPort;

    @Bean
    public Connector connector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(serverHttpPort);
        connector.setSecure(false);
        connector.setRedirectPort(serverPort);
        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
            
        };
        tomcat.addAdditionalTomcatConnectors(connector());
        return tomcat;
    }
    
}
