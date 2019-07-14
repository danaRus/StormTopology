package edu.ubb.dissertation.storm.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.Validate.notNull;

public class PatientData implements Serializable {

    private LocalDateTime timestamp;
    private Double systolicBloodPressure;
    private Double systolicBloodPressureUpperLimit;
    private Double systolicBloodPressureLowerLimit;
    private Double diastolicBloodPressure;
    private Double diastolicBloodPressureUpperLimit;
    private Double diastolicBloodPressureLowerLimit;
    private Double heartRate;
    private Double heartRateUpperLimit;
    private Double heartRateLowerLimit;
    private Double oxygenSaturationLevel;
    private Double oxygenSaturationLevelUpperLimit;
    private Double oxygenSaturationLevelLowerLimit;
    private Double bloodLossRate;
    private Integer systolicBloodPressureType;
    private Integer diastolicBloodPressureType;
    private Integer heartRateType;
    private Integer oxygenSaturationLevelType;
    private Long patientId;
    private String surgeryId;

    public PatientData() {
    }

    private PatientData(final Builder builder) {
        this.timestamp = builder.timestamp;
        this.systolicBloodPressure = builder.systolicBloodPressure;
        this.systolicBloodPressureUpperLimit = builder.systolicBloodPressureUpperLimit;
        this.systolicBloodPressureLowerLimit = builder.systolicBloodPressureLowerLimit;
        this.diastolicBloodPressure = builder.diastolicBloodPressure;
        this.diastolicBloodPressureUpperLimit = builder.diastolicBloodPressureUpperLimit;
        this.diastolicBloodPressureLowerLimit = builder.diastolicBloodPressureLowerLimit;
        this.heartRate = builder.heartRate;
        this.heartRateUpperLimit = builder.heartRateUpperLimit;
        this.heartRateLowerLimit = builder.heartRateLowerLimit;
        this.oxygenSaturationLevel = builder.oxygenSaturationLevel;
        this.oxygenSaturationLevelUpperLimit = builder.oxygenSaturationLevelUpperLimit;
        this.oxygenSaturationLevelLowerLimit = builder.oxygenSaturationLevelLowerLimit;
        this.bloodLossRate = builder.bloodLossRate;
        this.systolicBloodPressureType = builder.systolicBloodPressureType;
        this.diastolicBloodPressureType = builder.diastolicBloodPressureType;
        this.heartRateType = builder.heartRateType;
        this.oxygenSaturationLevelType = builder.oxygenSaturationLevelType;
        this.patientId = builder.patientId;
        this.surgeryId = builder.surgeryId;
    }

    public static class Builder {

        private LocalDateTime timestamp;
        private Double systolicBloodPressure;
        private Double systolicBloodPressureUpperLimit;
        private Double systolicBloodPressureLowerLimit;
        private Double diastolicBloodPressure;
        private Double diastolicBloodPressureUpperLimit;
        private Double diastolicBloodPressureLowerLimit;
        private Double heartRate;
        private Double heartRateUpperLimit;
        private Double heartRateLowerLimit;
        private Double oxygenSaturationLevel;
        private Double oxygenSaturationLevelUpperLimit;
        private Double oxygenSaturationLevelLowerLimit;
        private Double bloodLossRate;
        private Integer systolicBloodPressureType;
        private Integer diastolicBloodPressureType;
        private Integer heartRateType;
        private Integer oxygenSaturationLevelType;
        private Long patientId;
        private String surgeryId;

        public Builder withTimestamp(final LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withSystolicBloodPressure(final Double systolicBloodPressure) {
            this.systolicBloodPressure = systolicBloodPressure;
            return this;
        }

        public Builder withSystolicBloodPressureUpperLimit(final Double systolicBloodPressureUpperLimit) {
            this.systolicBloodPressureUpperLimit = systolicBloodPressureUpperLimit;
            return this;
        }

        public Builder withSystolicBloodPressureLowerLimit(final Double systolicBloodPressureLowerLimit) {
            this.systolicBloodPressureLowerLimit = systolicBloodPressureLowerLimit;
            return this;
        }

        public Builder withDiastolicBloodPressure(final Double diastolicBloodPressure) {
            this.diastolicBloodPressure = diastolicBloodPressure;
            return this;
        }

        public Builder withDiastolicBloodPressureUpperLimit(final Double diastolicBloodPressureUpperLimit) {
            this.diastolicBloodPressureUpperLimit = diastolicBloodPressureUpperLimit;
            return this;
        }

        public Builder withDiastolicBloodPressureLowerLimit(final Double diastolicBloodPressureLowerLimit) {
            this.diastolicBloodPressureLowerLimit = diastolicBloodPressureLowerLimit;
            return this;
        }

        public Builder withHeartRate(final Double heartRate) {
            this.heartRate = heartRate;
            return this;
        }

        public Builder withHeartRateUpperLimit(final Double heartRateUpperLimit) {
            this.heartRateUpperLimit = heartRateUpperLimit;
            return this;
        }

        public Builder withHeartRateLowerLimit(final Double heartRateLowerLimit) {
            this.heartRateLowerLimit = heartRateLowerLimit;
            return this;
        }

        public Builder withOxygenSaturationLevel(final Double oxygenSaturationLevel) {
            this.oxygenSaturationLevel = oxygenSaturationLevel;
            return this;
        }

        public Builder withOxygenSaturationLevelUpperLimit(final Double oxygenSaturationLevelUpperLimit) {
            this.oxygenSaturationLevelUpperLimit = oxygenSaturationLevelUpperLimit;
            return this;
        }

        public Builder withOxygenSaturationLevelLowerLimit(final Double oxygenSaturationLevelLowerLimit) {
            this.oxygenSaturationLevelLowerLimit = oxygenSaturationLevelLowerLimit;
            return this;
        }

        public Builder withBloodLossRate(final Double bloodLossRate) {
            this.bloodLossRate = bloodLossRate;
            return this;
        }

        public Builder withSystolicBloodPressureType(final Integer systolicBloodPressureType) {
            this.systolicBloodPressureType = systolicBloodPressureType;
            return this;
        }

        public Builder withDiastolicBloodPressureType(final Integer diastolicBloodPressureType) {
            this.diastolicBloodPressureType = diastolicBloodPressureType;
            return this;
        }

        public Builder withHeartRateType(final Integer heartRateType) {
            this.heartRateType = heartRateType;
            return this;
        }

        public Builder withOxygenSaturationLevelType(final Integer oxygenSaturationLevelType) {
            this.oxygenSaturationLevelType = oxygenSaturationLevelType;
            return this;
        }

        public Builder withPatientId(final Long patientId) {
            this.patientId = patientId;
            return this;
        }

        public Builder withSurgeryId(final String surgeryId) {
            this.surgeryId = surgeryId;
            return this;
        }

        public PatientData build() {
            notNull(timestamp);
            notNull(patientId);
            notNull(surgeryId);
            return new PatientData(this);
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public void setSystolicBloodPressure(final Double systolicBloodPressure) {
        this.systolicBloodPressure = systolicBloodPressure;
    }

    public Double getSystolicBloodPressureUpperLimit() {
        return systolicBloodPressureUpperLimit;
    }

    public void setSystolicBloodPressureUpperLimit(final Double systolicBloodPressureUpperLimit) {
        this.systolicBloodPressureUpperLimit = systolicBloodPressureUpperLimit;
    }

    public Double getSystolicBloodPressureLowerLimit() {
        return systolicBloodPressureLowerLimit;
    }

    public void setSystolicBloodPressureLowerLimit(final Double systolicBloodPressureLowerLimit) {
        this.systolicBloodPressureLowerLimit = systolicBloodPressureLowerLimit;
    }

    public Double getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public void setDiastolicBloodPressure(final Double diastolicBloodPressure) {
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public Double getDiastolicBloodPressureUpperLimit() {
        return diastolicBloodPressureUpperLimit;
    }

    public void setDiastolicBloodPressureUpperLimit(final Double diastolicBloodPressureUpperLimit) {
        this.diastolicBloodPressureUpperLimit = diastolicBloodPressureUpperLimit;
    }

    public Double getDiastolicBloodPressureLowerLimit() {
        return diastolicBloodPressureLowerLimit;
    }

    public void setDiastolicBloodPressureLowerLimit(final Double diastolicBloodPressureLowerLimit) {
        this.diastolicBloodPressureLowerLimit = diastolicBloodPressureLowerLimit;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(final Double heartRate) {
        this.heartRate = heartRate;
    }

    public Double getHeartRateUpperLimit() {
        return heartRateUpperLimit;
    }

    public void setHeartRateUpperLimit(final Double heartRateUpperLimit) {
        this.heartRateUpperLimit = heartRateUpperLimit;
    }

    public Double getHeartRateLowerLimit() {
        return heartRateLowerLimit;
    }

    public void setHeartRateLowerLimit(final Double heartRateLowerLimit) {
        this.heartRateLowerLimit = heartRateLowerLimit;
    }

    public Double getOxygenSaturationLevel() {
        return oxygenSaturationLevel;
    }

    public void setOxygenSaturationLevel(final Double oxygenSaturationLevel) {
        this.oxygenSaturationLevel = oxygenSaturationLevel;
    }

    public Double getOxygenSaturationLevelUpperLimit() {
        return oxygenSaturationLevelUpperLimit;
    }

    public void setOxygenSaturationLevelUpperLimit(final Double oxygenSaturationLevelUpperLimit) {
        this.oxygenSaturationLevelUpperLimit = oxygenSaturationLevelUpperLimit;
    }

    public Double getOxygenSaturationLevelLowerLimit() {
        return oxygenSaturationLevelLowerLimit;
    }

    public void setOxygenSaturationLevelLowerLimit(final Double oxygenSaturationLevelLowerLimit) {
        this.oxygenSaturationLevelLowerLimit = oxygenSaturationLevelLowerLimit;
    }

    public Double getBloodLossRate() {
        return bloodLossRate;
    }

    public void setBloodLossRate(final Double bloodLossRate) {
        this.bloodLossRate = bloodLossRate;
    }

    public Integer getSystolicBloodPressureType() {
        return systolicBloodPressureType;
    }

    public void setSystolicBloodPressureType(final Integer systolicBloodPressureType) {
        this.systolicBloodPressureType = systolicBloodPressureType;
    }

    public Integer getDiastolicBloodPressureType() {
        return diastolicBloodPressureType;
    }

    public void setDiastolicBloodPressureType(final Integer diastolicBloodPressureType) {
        this.diastolicBloodPressureType = diastolicBloodPressureType;
    }

    public Integer getHeartRateType() {
        return heartRateType;
    }

    public void setHeartRateType(final Integer heartRateType) {
        this.heartRateType = heartRateType;
    }

    public Integer getOxygenSaturationLevelType() {
        return oxygenSaturationLevelType;
    }

    public void setOxygenSaturationLevelType(final Integer oxygenSaturationLevelType) {
        this.oxygenSaturationLevelType = oxygenSaturationLevelType;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(final Long patientId) {
        this.patientId = patientId;
    }

    public String getSurgeryId() {
        return surgeryId;
    }

    public void setSurgeryId(final String surgeryId) {
        this.surgeryId = surgeryId;
    }
}
