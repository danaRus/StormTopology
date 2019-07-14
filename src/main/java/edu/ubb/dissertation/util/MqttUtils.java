package edu.ubb.dissertation.util;

import org.apache.storm.mqtt.common.MqttOptions;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MqttUtils {

    private MqttUtils() {
    }

    public static MqttOptions createMqttOptions(final String topic) {
        final MqttOptions options = new MqttOptions();
        options.setTopics(Stream.of(topic).collect(Collectors.toList()));
        options.setCleanConnection(false);
        options.setUrl("tcp://iot.eclipse.org:1883");
        return options;
    }
}
