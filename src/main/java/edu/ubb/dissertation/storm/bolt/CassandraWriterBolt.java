package edu.ubb.dissertation.storm.bolt;

import edu.ubb.dissertation.cassandra.connector.CassandraConnector;
import edu.ubb.dissertation.cassandra.repository.MergedDataRepository;
import edu.ubb.dissertation.storm.model.MergedData;
import io.vavr.control.Try;
import org.apache.storm.task.IBolt;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CassandraWriterBolt extends BaseRichBolt {

    private static Logger LOGGER = LoggerFactory.getLogger(CassandraWriterBolt.class);

    private static final String ADDRESS = "localhost";
    private static final int PORT_NUMBER = 9160;

    private CassandraConnector cassandraConnector;

    private MergedDataRepository mergedDataRepository;

    @Override
    public void prepare(final Map map, final TopologyContext topologyContext, final OutputCollector outputCollector) {
        initializeCassandraConnection();
    }

    @Override
    public void execute(final Tuple input) {
        Try.of(() -> (MergedData) input.getValueByField("mergedData"))
                .onSuccess(this::saveMergedDataToCassandra)
                .onFailure(t -> LOGGER.error("Could not get data. Message: {}", t.getMessage()));
    }

    /**
     * Since it will only save the data to a Cassandra table, no output field has to be declared.
     */
    @Override
    public void declareOutputFields(final OutputFieldsDeclarer declarer) {
    }

    /**
     * Although it is not always guaranteed that this method will be called by storm (see {@link IBolt#cleanup}),
     * the connection was closed here, in order to not have the overhead of connecting & disconnecting from Cassandra
     * for every insert.
     */
    @Override
    public void cleanup() {
        cassandraConnector.close();
    }

    private void initializeCassandraConnection() {
        cassandraConnector = new CassandraConnector();
        cassandraConnector.connect(ADDRESS, PORT_NUMBER);
        mergedDataRepository = new MergedDataRepository(cassandraConnector.getSession());
    }

    private void saveMergedDataToCassandra(final MergedData mergedData) {
        mergedDataRepository.insertEntry(mergedData);
    }
}
