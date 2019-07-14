package edu.ubb.dissertation;

import edu.ubb.dissertation.storm.bolt.CassandraWriterBolt;
import edu.ubb.dissertation.storm.bolt.DataProcessorBolt;
import edu.ubb.dissertation.storm.model.MergedData;
import edu.ubb.dissertation.storm.model.PatientData;
import edu.ubb.dissertation.storm.model.SensorData;
import edu.ubb.dissertation.storm.mqtt.PatientDataMessageMapper;
import edu.ubb.dissertation.storm.mqtt.SensorMessageMapper;
import edu.ubb.dissertation.util.MqttUtils;
import io.vavr.control.Try;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.mqtt.spout.MqttSpout;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static final String SENSOR_DATA_TOPIC_NAME = "dissertation/sensor_data";
    private static final String PATIENT_DATA_TOPIC_NAME = "dissertation/patient_data";

    public static void main(final String[] args) {
        final Config stormConfig = new Config();
        stormConfig.registerSerialization(MergedData.class);
        stormConfig.registerSerialization(PatientData.class);
        stormConfig.registerSerialization(SensorData.class);

        final TopologyBuilder builder = new TopologyBuilder();
        final MqttSpout sensorSpout = new MqttSpout(new SensorMessageMapper(), MqttUtils.createMqttOptions(SENSOR_DATA_TOPIC_NAME));
        final MqttSpout patientDataSpout = new MqttSpout(new PatientDataMessageMapper(), MqttUtils.createMqttOptions(PATIENT_DATA_TOPIC_NAME));
        builder.setSpout("sensorSpout", sensorSpout);
        builder.setSpout("patientDataSpout", patientDataSpout);

        final DataProcessorBolt dataProcessorBolt = new DataProcessorBolt();
        builder.setBolt("dataProcessorBolt", dataProcessorBolt)
                .shuffleGrouping("sensorSpout")
                .shuffleGrouping("patientDataSpout");

        final CassandraWriterBolt cassandraWriterBolt = new CassandraWriterBolt();
        builder.setBolt("cassandraWriterBolt", cassandraWriterBolt)
                .shuffleGrouping("dataProcessorBolt");

        Try.run(() -> StormSubmitter.submitTopology("PatientAndSensorDataProcessor", stormConfig, builder.createTopology()))
                .onSuccess(v -> LOGGER.error("Storm topology started successfully."))
                .onFailure(t -> LOGGER.error("Could not start topology. Stack Trace: {}", t.getMessage()));
    }
}
