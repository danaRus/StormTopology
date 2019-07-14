package edu.ubb.dissertation.storm.model;

import java.io.Serializable;

import static org.apache.commons.lang3.Validate.notNull;

public class MergedData implements Serializable {

    private SensorData sensorData;
    private PatientData patientData;

    public MergedData() {
    }

    private MergedData(final Builder builder) {
        this.sensorData = builder.sensorData;
        this.patientData = builder.patientData;
    }

    public static class Builder {

        private SensorData sensorData;
        private PatientData patientData;

        public Builder withSensorData(final SensorData sensorData) {
            this.sensorData = sensorData;
            return this;
        }

        public Builder withPatientData(final PatientData patientData) {
            this.patientData = patientData;
            return this;
        }

        public MergedData build() {
            notNull(sensorData);
            notNull(patientData);
            return new MergedData(this);
        }
    }

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(final SensorData sensorData) {
        this.sensorData = sensorData;
    }

    public PatientData getPatientData() {
        return patientData;
    }

    public void setPatientData(final PatientData patientData) {
        this.patientData = patientData;
    }
}
