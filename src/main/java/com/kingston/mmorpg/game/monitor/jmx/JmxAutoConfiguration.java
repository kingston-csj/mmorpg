//package com.kingston.mmorpg.game.monitor.jmx;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.jmx.support.ConnectorServerFactoryBean;
//import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
//
//@Configuration
//public class JmxAutoConfiguration {
//
//    @Value("${jmx.rmi.host:localhost}")
//    private String rmiHost;
//
//    @Value("${jmx.rmi.port:10086}")
//    private int rmiPort;
//
//    @Value("${jmx.service.domain:jmxrmi}")
//    private String jmxDomain;
//
//    // 指定特定端口可以开放命名服务
//    @Bean
//    public RmiRegistryFactoryBean rmiRegistry() {
//        RmiRegistryFactoryBean factoryBean = new RmiRegistryFactoryBean();
//        factoryBean.setPort(rmiPort);
//        factoryBean.setAlwaysCreate(true);
//
//        return factoryBean;
//    }
//
//    @DependsOn("rmiRegistry")
//    @Bean
//    public ConnectorServerFactoryBean jmxConnector() {
//        ConnectorServerFactoryBean serverFactoryBean = new ConnectorServerFactoryBean();
//
//        serverFactoryBean.setServiceUrl(String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/%s", rmiHost, rmiPort, rmiHost, rmiPort, jmxDomain));
//
//        return serverFactoryBean;
//    }
//}