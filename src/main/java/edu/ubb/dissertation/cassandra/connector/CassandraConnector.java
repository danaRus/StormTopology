package edu.ubb.dissertation.cassandra.connector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraConnector {

    private static final int DEFAULT_PORT = 9042;

    private Cluster cluster;

    private Session session;

    public void connect(final String address, final Integer port) {
        cluster = createCluster(address, port);
        session = cluster.connect("medical");
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
                .addContactPoint(address)
                .withPort(port != null ? port : DEFAULT_PORT)
                .build();
    }
}
