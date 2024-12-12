package com.scienjus.spring.cloud.etcd.serviceregistry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.client.serviceregistry.Registration;

@Data
@AllArgsConstructor
public class EtcdRegistration implements Registration {

    private String serviceName;
    private String address;
    private int port;
    private Map<String, String> metadata;

    @Override
    public String getServiceId() {
        return serviceName;
    }

    public String etcdKey(String prefix) {
        return String.format("%s/%s/%s:%d",
                prefix,
                this.getServiceName(),
                this.getAddress(),
                this.getPort());
    }

    @Override
    public String getHost() {
        return address;
    }

    @Override
    public boolean isSecure() {
        return port == 443;
    }

    @Override
    public URI getUri() {
        try {
            return new URI("http", null, address, port, null, null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI", e);
        }
    }

    @Override
    public Map<String, String> getMetadata() {
        return metadata != null ? metadata : new HashMap<>();
    }
}