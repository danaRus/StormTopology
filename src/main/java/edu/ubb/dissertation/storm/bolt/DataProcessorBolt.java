package edu.ubb.dissertation.storm.bolt;

import edu.ubb.dissertation.storm.model.MergedData;
import edu.ubb.dissertation.storm.model.PatientData;
import edu.ubb.dissertation.storm.model.SensorData;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import static edu.ubb.dissertation.util.Constants.*;
import static edu.ubb.dissertation.util.TypeConverterHelper.cast;

public class DataProcessorBolt extends BaseRichBolt {

    private static Logger LOGGER = LoggerFactory.getLogger(DataProcessorBolt.class);

    private static final ConcurrentMap<LocalDateTime, SensorData> sensorEntries = new ConcurrentHashMap<>();
    private static final ConcurrentMap<LocalDateTime, PatientData> patientDataEntries = new ConcurrentHashMap<>();

    private OutputCollector collector;

    @Override
    public void prepare(final Map map, final TopologyContext topologyContext, final OutputCollector outputCollector) {
        this.collector = outputCollector;
    }

    @Override
    public void execute(final Tuple input) {
        extractDataBySource(input);

        sensorEntries.keySet()
                .stream()
                .filter(timestamp -> patientDataEntries.keySet().contains(timestamp))
                .forEach(this::emitValue);
    }

    @Override
    public void declareOutputFields(final OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("mergedData"));
    }

    private void emitValue(final LocalDateTime timestamp) {
        final MergedData mergedData = new MergedData.Builder()
                .withSensorData(sensorEntries.remove(timestamp))
                .withPatientData(patientDataEntries.remove(timestamp))
                .build();
        collector.emit(new Values(mergedData));
    }

    private void extractDataBySource(final Tuple input) {
        final Consumer<? super Throwable> extractPatientDataConsumer =
                t -> execute(() -> input.getDoubleByField(SYSTOLIC_BLOOD_PRESSURE_KEY), v -> handlePatientDataEntry(input),
                        e -> LOGGER.error("Failure while extracting patient data. Message: {}", e.getMessage()));
        execute(() -> input.getStringByField(ARM_ID_KEY), v -> handleSensorDataEntry(input), extractPatientDataConsumer);
    }

    private <T> void execute(final CheckedFunction0<? extends T> runnable, final Consumer<? super T> successAction,
                             final Consumer<? super Throwable> failureAction) {
        Try.of(runnable)
                .onSuccess(successAction)
                .onFailure(failureAction);
    }

    private void handleSensorDataEntry(final Tuple input) {
        createSensorEntry(input)
                .ifPresent(sensorData -> sensorEntries.put(sensorData.getTimestamp(), sensorData));
    }

    private void handlePatientDataEntry(final Tuple input) {
        createPatientEntry(input)
                .ifPresent(patientData -> patientDataEntries.put(patientData.getTimestamp(), patientData));
    }

    private Optional<PatientData> createPatientEntry(final Tuple input) {
        return cast(input.getValueByField(TIMESTAMP_PATIENT_DATA_KEY), LocalDateTime.class)
                .flatMap(timestamp -> createPatientEntry(input, timestamp));
    }

    @SuppressWarnings("unchecked")
    private Optional<PatientData> createPatientEntry(final Tuple input, final LocalDateTime timestamp) {
        final Double systolicBloodPressure = input.getDoubleByField(SYSTOLIC_BLOOD_PRESSURE_KEY);
        final Double systolicBloodPressureUpperLimit = input.getDoubleByField(SYSTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY);
        final Double systolicBloodPressureLowerLimit = input.getDoubleByField(SYSTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY);
        final Double diastolicBloodPressure = input.getDoubleByField(DIASTOLIC_BLOOD_PRESSURE_KEY);
        final Double diastolicBloodPressureUpperLimit = input.getDoubleByField(DIASTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY);
        final Double diastolicBloodPressureLowerLimit = input.getDoubleByField(DIASTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY);
        final Double heartRate = input.getDoubleByField(HEART_RATE_KEY);
        final Double heartRateUpperLimit = input.getDoubleByField(HEART_RATE_UPPER_LIMIT_KEY);
        final Double heartRateLowerLimit = input.getDoubleByField(HEART_RATE_LOWER_LIMIT_KEY);
        final Double oxygenSaturationLevel = input.getDoubleByField(OXYGEN_SATURATION_LEVEL_KEY);
        final Double oxygenSaturationLevelUpperLimit = input.getDoubleByField(OXYGEN_SATURATION_LEVEL_UPPER_LIMIT_KEY);
        final Double oxygenSaturationLevelLowerLimit = input.getDoubleByField(OXYGEN_SATURATION_LEVEL_LOWER_LIMIT_KEY);
        final Double bloodLossRate = input.getDoubleByField(BLOOD_LOSS_RATE_KEY);
        final List<String> abnormalVitalSigns = cast(input.getValueByField(ABNORMAL_VITAL_SIGNS_KEY), List.class)
                .orElseGet(ArrayList::new);
        final Long patientId = input.getLongByField(PATIENT_ID_KEY);
        final String surgeryId = input.getStringByField(SURGERY_ID_KEY);

        return Try.of(() -> new PatientData.Builder()
                .withTimestamp(timestamp)
                .withSystolicBloodPressure(systolicBloodPressure)
                .withSystolicBloodPressureUpperLimit(systolicBloodPressureUpperLimit)
                .withSystolicBloodPressureLowerLimit(systolicBloodPressureLowerLimit)
                .withDiastolicBloodPressure(diastolicBloodPressure)
                .withDiastolicBloodPressureUpperLimit(diastolicBloodPressureUpperLimit)
                .withDiastolicBloodPressureLowerLimit(diastolicBloodPressureLowerLimit)
                .withHeartRate(heartRate)
                .withHeartRateUpperLimit(heartRateUpperLimit)
                .withHeartRateLowerLimit(heartRateLowerLimit)
                .withOxygenSaturationLevel(oxygenSaturationLevel)
                .withOxygenSaturationLevelUpperLimit(oxygenSaturationLevelUpperLimit)
                .withOxygenSaturationLevelLowerLimit(oxygenSaturationLevelLowerLimit)
                .withBloodLossRate(bloodLossRate)
                .withSystolicBloodPressureType(extractVitalSignType(abnormalVitalSigns, SYSTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT, SYSTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT))
                .withDiastolicBloodPressureType(extractVitalSignType(abnormalVitalSigns, DIASTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT, DIASTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT))
                .withHeartRateType(extractVitalSignType(abnormalVitalSigns, HEART_RATE_ABOVE_UPPER_LIMIT, HEART_RATE_BELOW_LOWER_LIMIT))
                .withOxygenSaturationLevelType(extractVitalSignType(abnormalVitalSigns, OXYGEN_SATURATION_LEVEL_ABOVE_UPPER_LIMIT, OXYGEN_SATURATION_LEVEL_BELOW_LOWER_LIMIT))
                .withPatientId(patientId)
                .withSurgeryId(surgeryId)
                .build())
                .map(Optional::ofNullable)
                .getOrElseGet(t -> Optional.empty());
    }

    private Integer extractVitalSignType(final List<String> abnormalVitalSigns, final String upperLimitKey, final String lowerLimitKey) {
        return abnormalVitalSigns.contains(upperLimitKey) ? 1 : abnormalVitalSigns.contains(lowerLimitKey) ? -1 : 0;
    }

    private Optional<SensorData> createSensorEntry(final Tuple input) {
        return cast(input.getValueByField(TIMESTAMP_KEY), LocalDateTime.class)
                .flatMap(timestamp -> createSensorEntry(input, timestamp));
    }

    private Optional<SensorData> createSensorEntry(final Tuple input, final LocalDateTime timestamp) {
        final String armId = input.getStringByField(ARM_ID_KEY);
        final Double rotation = input.getDoubleByField(ROTATION_KEY);
        final Double temperature = input.getDoubleByField(TEMPERATURE_KEY);
        final Double force = input.getDoubleByField(FORCE_KEY);
        final Double pressure = input.getDoubleByField(PRESSURE_KEY);

        return Try.of(() -> new SensorData.Builder().withArmId(armId)
                .withTimestamp(timestamp)
                .withRotation(rotation)
                .withTemperature(temperature)
                .withForce(force)
                .withPressure(pressure)
                .build())
                .map(Optional::ofNullable)
                .getOrElseGet(t -> Optional.empty());
    }
}
