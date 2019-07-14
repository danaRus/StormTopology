package edu.ubb.dissertation.util;

public final class Constants {

    private Constants() {
    }

    // sensor data keys
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String ARM_ID_KEY = "armId";
    public static final String ROTATION_KEY = "rotation";
    public static final String TEMPERATURE_KEY = "temperature";
    public static final String FORCE_KEY = "force";
    public static final String PRESSURE_KEY = "pressure";

    // patient data keys
    public static final String TIMESTAMP_PATIENT_DATA_KEY = "timestamp";
    public static final String SYSTOLIC_BLOOD_PRESSURE_KEY = "sbp";
    public static final String SYSTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY = "sbpUL";
    public static final String SYSTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY = "sbpLL";
    public static final String DIASTOLIC_BLOOD_PRESSURE_KEY = "dbp";
    public static final String DIASTOLIC_BLOOD_PRESSURE_UPPER_LIMIT_KEY = "dbpUL";
    public static final String DIASTOLIC_BLOOD_PRESSURE_LOWER_LIMIT_KEY = "dbpLL";
    public static final String HEART_RATE_KEY = "hr";
    public static final String HEART_RATE_UPPER_LIMIT_KEY = "hrUL";
    public static final String HEART_RATE_LOWER_LIMIT_KEY = "hrLL";
    public static final String OXYGEN_SATURATION_LEVEL_KEY = "os";
    public static final String OXYGEN_SATURATION_LEVEL_UPPER_LIMIT_KEY = "osUL";
    public static final String OXYGEN_SATURATION_LEVEL_LOWER_LIMIT_KEY = "osLL";
    public static final String BLOOD_LOSS_RATE_KEY = "bloodLossRate";
    public static final String ABNORMAL_VITAL_SIGNS_KEY = "abnormalVitalSigns";
    public static final String PATIENT_ID_KEY = "patientId";
    public static final String SURGERY_ID_KEY = "surgeryId";

    // abnormal vital sign possible values
    public static final String SYSTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT = "SYSTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT";
    public static final String SYSTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT = "SYSTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT";
    public static final String DIASTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT = "DIASTOLIC_BLOOD_PRESSURE_BELOW_LOWER_LIMIT";
    public static final String DIASTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT = "DIASTOLIC_BLOOD_PRESSURE_ABOVE_UPPER_LIMIT";
    public static final String HEART_RATE_BELOW_LOWER_LIMIT = "HEART_RATE_BELOW_LOWER_LIMIT";
    public static final String HEART_RATE_ABOVE_UPPER_LIMIT = "HEART_RATE_ABOVE_UPPER_LIMIT";
    public static final String OXYGEN_SATURATION_LEVEL_BELOW_LOWER_LIMIT = "OXYGEN_SATURATION_LEVEL_BELOW_LOWER_LIMIT";
    public static final String OXYGEN_SATURATION_LEVEL_ABOVE_UPPER_LIMIT = "OXYGEN_SATURATION_LEVEL_ABOVE_UPPER_LIMIT";
}
