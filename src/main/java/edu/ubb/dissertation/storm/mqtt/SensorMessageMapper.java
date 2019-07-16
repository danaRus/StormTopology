package edu.ubb.dissertation.storm.mqtt;

import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static edu.ubb.dissertation.util.Constants.*;
import static edu.ubb.dissertation.util.TypeConverterHelper.*;

public class SensorMessageMapper implements MessageMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorMessageMapper.class);

    @Override
    public Fields outputFields() {
        return new Fields(TIMESTAMP_KEY, ARM_ID_KEY, ROTATION_KEY, TEMPERATURE_KEY, FORCE_KEY, PRESSURE_KEY);
    }

    @Override
    public Values extractValues(final JSONObject json) {
        // the timestamp from the sensor is sent as seconds since the Unix Start Time
        return extractTimestampFromEpoch(json, TIMESTAMP_KEY)
                .map(timestamp -> extractValues(json, timestamp))
                .orElseGet(this::createEmptyValues);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    private Values extractValues(final JSONObject json, final LocalDateTime timestamp) {
        final String armId = extractString(json, ARM_ID_KEY);
        final Double rotation = extractDouble(json, ROTATION_KEY);
        final Double temperature = extractDouble(json, TEMPERATURE_KEY);
        final Double force = extractDouble(json, FORCE_KEY);
        final Double pressure = extractDouble(json, PRESSURE_KEY);
        return new Values(timestamp, armId, rotation, temperature, force, pressure);
    }
}
