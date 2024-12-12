package com.scienjus.spring.cloud.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.maintenance.StatusResponse;
import com.scienjus.spring.cloud.etcd.properties.EtcdProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * {@link Endpoint} to report etcd cluster information.
 *
 * @author ScienJus
 */
@Endpoint(id = "etcd")
public class EtcdEndpoint {

    private final Client etcdClient;

    private final EtcdProperties etcdProperties;

    public EtcdEndpoint(Client etcdClient, EtcdProperties etcdProperties) {
        this.etcdClient = etcdClient;
        this.etcdProperties = etcdProperties;
    }
    @ReadOperation
    public EtcdStatus invoke() {
        List<EtcdMemberStatus> memberStatuses = etcdProperties.getEndpoints()
                .stream()
                .map(endpoint -> {
                    try {
                        StatusResponse response = etcdClient.getMaintenanceClient().statusMember(endpoint).get();
                        return new EtcdMemberStatus(endpoint, response.getVersion());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .collect(Collectors.toList());
        return new EtcdStatus(memberStatuses);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EtcdStatus {

        private List<EtcdMemberStatus> members;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EtcdMemberStatus {

        private String endpoint;

        private String version;
    }
}
