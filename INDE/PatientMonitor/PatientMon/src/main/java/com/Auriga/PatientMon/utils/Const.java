package com.Auriga.PatientMon.utils;

/**
 * Created by maxim on 17.08.15.
 */
public class Const {
    public final static float SAMPLE_FREQ = 120;
    public final static float WAVE_FORM_SPO2_PADDING = 0.06f;
    public final static float WAVE_FORM_ART_PADDING = -0.06f;
    public final static float DETAILS_SAMPLE_FREQ = 120;
    public final static float MAX_QUEUE_SIZE = 100;
    public final static int ADD_POINT_SLEEP_TIME = 20;
    public final static int PATIENT_DATA_SLEEP_TIME = 1000;
    public final static boolean IS_DEBUG = true;
    public final static long MILLISECONDS_IN_YEAR = 365l * 24 * 60 * 60 * 1000;
    public final static String DATE_FORMAT = "dd-MM-yyyy";
    public final static String TIME_FORMAT = "HH:mm";

    public final static int DEFAULT_LIMIT_HR_HIGH = 95;
    public final static int DEFAULT_LIMIT_HR_LOW = 45;

    public final static int DEFAULT_LIMIT_ART_HIGH = 140;
    public final static int DEFAULT_LIMIT_ART_LOW = 85;

    public final static int DEFAULT_LIMIT_SPO2_HIGH = 99;
    public final static int DEFAULT_LIMIT_SPO2_LOW = 85;

    public final static int DEFAULT_LIMIT_STI_HIGH = 2;
    public final static int DEFAULT_LIMIT_STI_LOW = -2;

}
