package com.scienjus.spring.cloud.etcd.serviceregistry.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * etcd discovery properties
 *
 * @author ScienJus
 */
@Data
@ConfigurationProperties("spring.cloud.etcd.discovery")
public class EtcdDiscoveryProperties {

    @Value("${spring.cloud.etcd.prefix:/services}")
    private String prefix;

    @Value("${spring.application.name:application}")
    private String name;

    private String address;

    private Integer port;
}
