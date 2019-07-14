package edu.ubb.dissertation.cassandra.repository;

import com.datastax.driver.core.Session;
import edu.ubb.dissertation.storm.model.MergedData;
import edu.ubb.dissertation.storm.model.PatientData;
import edu.ubb.dissertation.storm.model.SensorData;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

import static org.apache.commons.lang3.Validate.notNull;

public class MergedDataRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergedDataRepository.class);

    private static final String TABLE_NAME = "patientmeasurements";

    private Session session;

    public MergedDataRepository(final Session session) {
        this.session = session;
    }

    /**
     * Insert a new entry into the table {@value TABLE_NAME}. The entry will be contain data
     * - from the patient
     * - from the simulator
     * In order to be able to insert the entry, the value of {@param patientData.timestamp} must be equal to
     * {@param sensorData.timestamp}
     */
    public void insertEntry(final MergedData mergedData) {
        final PatientData patientData = mergedData.getPatientData();
        final SensorData sensorData = mergedData.getSensorData();
        notNull(patientData);
        notNull(sensorData);

        if (patientData.getTimestamp().equals(sensorData.getTimestamp())) {
            Try.run(() -> session.execute(createQuery(patientData, sensorData)))
                    .onFailure(t -> LOGGER.error("Error while inserting into table {}. Message: {}", TABLE_NAME, t.getMessage()));
        }
    }

    private String createQuery(final PatientData patientData, final SensorData sensorData) {
        final String columnNames = "(timestamp, systolicBloodPressure, systolicBloodPressureUpperLimit, systolicBloodPressureLowerLimit, " +
                "diastolicBloodPressure, diastolicBloodPressureUpperLimit, diastolicBloodPressureLowerLimit, heartRate, " +
                "heartRateUpperLimit, heartRateLowerLimit, oxygenSaturationLevel, oxygenSaturationLevelUpperLimit, " +
                "oxygenSaturationLevelLowerLimit, bloodLossRate, systolicBloodPressureType, diastolicBloodPressureType, " +
                "heartRateType, oxygenSaturationLevelType, patient_id, surgery_id, rotation, temperature, force, pressure, arm_id)";
        return String.format("INSERT INTO %s%s VALUES ('%s', %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, %.2f, " +
                        "%.2f, %d, %d, %d, %d, %d, '%s', %.2f, %.2f, %.2f, %.2f, '%s') USING TTL 300;",
                TABLE_NAME, columnNames,
                Timestamp.valueOf(patientData.getTimestamp()), patientData.getSystolicBloodPressure(),
                patientData.getSystolicBloodPressureUpperLimit(), patientData.getSystolicBloodPressureLowerLimit(),
                patientData.getDiastolicBloodPressure(), patientData.getDiastolicBloodPressureUpperLimit(),
                patientData.getDiastolicBloodPressureLowerLimit(), patientData.getHeartRate(),
                patientData.getHeartRateUpperLimit(), patientData.getHeartRateLowerLimit(),
                patientData.getOxygenSaturationLevel(), patientData.getOxygenSaturationLevelUpperLimit(),
                patientData.getOxygenSaturationLevelLowerLimit(), patientData.getBloodLossRate(),
                patientData.getSystolicBloodPressureType(), patientData.getDiastolicBloodPressureType(),
                patientData.getHeartRateType(), patientData.getOxygenSaturationLevelType(), patientData.getPatientId(),
                patientData.getSurgeryId(), sensorData.getRotation(), sensorData.getTemperature(),
                sensorData.getForce(), sensorData.getPressure(), sensorData.getArmId());
    }

}
