package edu.ubb.dissertation.storm.mqtt;

import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

import static edu.ubb.dissertation.util.Constants.*;
import static edu.ubb.dissertation.util.TypeConverterHelper.*;

public class PatientDataMessageMapper implements MessageMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDataMessageMapper.class);

    @Override
    public Fields outputFields() {
        return new Fields(TIMESTAMP_PATIENT_DATA_KEY, SYSTOLIC_BLOOD_PRESSURE_KEY, SYSTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY,
                SYSTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY, DIASTOLIC_BLOOD_PRESSURE_KEY, DIASTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY,
                DIASTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY, HEART_RATE_KEY, HEART_RATE_UPPER_LIMIT_KEY, HEART_RATE_LOWER_LIMIT_KEY,
                OXYGEN_SATURATION_LEVEL_KEY, OXYGEN_SATURATION_LEVEL_UPPER_LIMIT_KEY, OXYGEN_SATURATION_LEVEL_LOWER_LIMIT_KEY,
                BLOOD_LOSS_RATE_KEY, ABNORMAL_VITAL_SIGNS_KEY, PATIENT_ID_KEY, SURGERY_ID_KEY);
    }

    @Override
    public Values extractValues(final JSONObject json) {
        // the timestamp from the generator will have the pattern: yyyy-MM-ddTHH:mm:ss
        return extractTimestamp(json, TIMESTAMP_PATIENT_DATA_KEY)
                .map(timestamp -> extractValues(json, timestamp))
                .orElseGet(this::createEmptyValues);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    private Values extractValues(final JSONObject json, final LocalDateTime timestamp) {
        final Double systolicBloodPressure = extractDouble(json, SYSTOLIC_BLOOD_PRESSURE_KEY);
        final Double systolicBloodPressureUpperLimit = extractDouble(json, SYSTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY);
        final Double systolicBloodPressureLowerLimit = extractDouble(json, SYSTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY);
        final Double diastolicBloodPressure = extractDouble(json, DIASTOLIC_BLOOD_PRESSURE_KEY);
        final Double diastolicBloodPressureUpperLimit = extractDouble(json, DIASTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY);
        final Double diastolicBloodPressureLowerLimit = extractDouble(json, DIASTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY);
        final Double heartRate = extractDouble(json, HEART_RATE_KEY);
        final Double heartRateUpperLimit = extractDouble(json, HEART_RATE_UPPER_LIMIT_KEY);
        final Double heartRateLowerLimit = extractDouble(json, HEART_RATE_LOWER_LIMIT_KEY);
        final Double oxygenSaturationLevel = extractDouble(json, OXYGEN_SATURATION_LEVEL_KEY);
        final Double oxygenSaturationLevelUpperLimit = extractDouble(json, OXYGEN_SATURATION_LEVEL_UPPER_LIMIT_KEY);
        final Double oxygenSaturationLevelLowerLimit = extractDouble(json, OXYGEN_SATURATION_LEVEL_LOWER_LIMIT_KEY);
        final Double bloodLossRate = extractDouble(json, BLOOD_LOSS_RATE_KEY);
        final List<String> abnormalVitalSigns = extractList(json, ABNORMAL_VITAL_SIGNS_KEY);
        final Long patientId = extractLong(json, PATIENT_ID_KEY);
        final String surgeryId = extractString(json, SURGERY_ID_KEY);

        return new Values(timestamp, systolicBloodPressure, systolicBloodPressureUpperLimit, systolicBloodPressureLowerLimit,
                diastolicBloodPressure, diastolicBloodPressureUpperLimit, diastolicBloodPressureLowerLimit,
                heartRate, heartRateUpperLimit, heartRateLowerLimit, oxygenSaturationLevel, oxygenSaturationLevelUpperLimit,
                oxygenSaturationLevelLowerLimit, bloodLossRate, abnormalVitalSigns, patientId, surgeryId);
    }
}
