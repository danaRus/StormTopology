package edu.ubb.dissertation.storm.mqtt;

import org.apache.storm.mqtt.MqttMessage;
import org.apache.storm.mqtt.MqttMessageMapper;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;

import static edu.ubb.dissertation.util.TypeConverterHelper.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public interface MessageMapper extends MqttMessageMapper {

    @Override
    default Values toValues(final MqttMessage mqttMessage) {
        return extractValuesFromMessage(convert(mqttMessage.getMessage(), UTF_8));
    }

    default Values extractValuesFromMessage(final String message) {
        final Object json = convertToJsonObject(message,
                e -> getLogger().error("Error occurred while converting sensor message to JSON, {}", e.getMessage()));
        return cast(json, JSONObject.class)
                .map(this::extractValues)
                .orElseGet(this::createEmptyValues);
    }

    Values extractValues(final JSONObject json);

    default Values createEmptyValues() {
        return new Values();
    }

    Logger getLogger();
}
