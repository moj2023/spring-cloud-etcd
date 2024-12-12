package com.scienjus.spring.cloud.etcd.serviceregistry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.util.Map;

import org.springframework.cloud.client.serviceregistry.Registration;

@Data
@AllArgsConstructor
public class EtcdRegistration implements Registration {

    private String serviceName;

    private String address;

    private int port;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHost'");
    }

    @Override
    public boolean isSecure() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSecure'");
    }

    @Override
    public URI getUri() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUri'");
    }

    @Override
    public Map<String, String> getMetadata() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMetadata'");
    }

}
