package com.scienjus.spring.cloud.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.ClientBuilder;
import com.coreos.jetcd.data.ByteSequence;
import com.scienjus.spring.cloud.etcd.properties.EtcdProperties;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
// import org.springframework.boot.actuate.autoconfigure.ConditionalOnEnabledHealthIndicator;
// import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
// import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration} for {@link Client}.
 *
 * @author ScienJus
 */
@Configuration
@ConditionalOnEtcdEnabled
@EnableConfigurationProperties(EtcdProperties.class)
public class EtcdAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Client.class)
    public Client etcdClient(EtcdProperties properties) {
        ClientBuilder clientBuilder = Client.builder()
                .endpoints(properties.getEndpoints());

        if (properties.getUsername() != null) {
            clientBuilder.user(ByteSequence.fromBytes(properties.getUsername().getBytes()));
        }

        if (properties.getPassword() != null) {
            clientBuilder.password(ByteSequence.fromBytes(properties.getPassword().getBytes()));
        }

        return clientBuilder.build();
    }

    @Configuration
    @ConditionalOnClass(Endpoint.class)
    protected static class EtcdHealthConfig {

        @Bean
        @ConditionalOnMissingBean
        // @ConditionalOnEnabledEndpoint
        public EtcdEndpoint etcdEndpoint(Client etcdClient, EtcdProperties properties) {
            return new EtcdEndpoint(etcdClient, properties);
        }

        @Bean
        @ConditionalOnMissingBean
        // @ConditionalOnEnabledHealthIndicator("etcd")
        public EtcdHealthIndicator etcdHealthIndicator(Client etcdClient) {
            return new EtcdHealthIndicator(etcdClient);
        }
    }
}
