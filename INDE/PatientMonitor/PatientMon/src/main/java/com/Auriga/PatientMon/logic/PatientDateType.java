package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/9/15.
 */
public enum PatientDateType {
    ECG1("ECG1"),
    ECG2("ECG2"),
    ECG3("ECG3"),
    ECG4("ECG4"),
    ECG5("ECG5"),
    ECG6("ECG6"),
    ARR("ARR"),
    SpO2("SpO2"),
    HRTrend("tHR"),
    STITrend("STITrend"),
    ARTDTrend("tART_D"),
    ARTSTrend("tART_S"),
    ARTMTrend("tART_M"),
    SpO2Trend("tSpO2"),
    PVC("PVC"),
    PLS("PLS"),
    ARTS("ARTS"),
    ARTD("ARTD"),
    ARTM("ARTM");

    private final String mText;

    /**
     * @param text
     */
    private PatientDateType(final String text) {
        this.mText = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return mText;
    }
}
