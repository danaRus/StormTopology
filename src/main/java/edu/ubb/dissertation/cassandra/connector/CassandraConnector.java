package edu.ubb.dissertation.cassandra.connector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraConnector.class);
    private static final int DEFAULT_PORT = 9160; // 9042

    private Cluster cluster;

    private Session session;

    public void connect(final String address, final Integer port) {
        cluster = createCluster(address, port);
        // logging added in order to identify the data of the hosts from the cluster
        Try.of(() -> cluster.getMetadata())
                .onSuccess(metadata -> metadata.getAllHosts()
                        .forEach(host -> LOGGER.info("Data center: {}, Host: {}", host.getDatacenter(), host.getAddress())));
        session = cluster.connect();
    }

    public Session getSession() {
        return this.session;
    }

    public void close() {
        session.close();
        cluster.close();
    }

    private Cluster createCluster(final String address, final Integer port) {
        return Cluster.builder()
                .withoutJMXReporting()
                .withoutMetrics()
                .addContactPoint(address)
                .withPort(port != null ? port : DEFAULT_PORT)
                .withLoadBalancingPolicy(new TokenAwarePolicy(new RoundRobinPolicy()))
                .build();
    }
}
