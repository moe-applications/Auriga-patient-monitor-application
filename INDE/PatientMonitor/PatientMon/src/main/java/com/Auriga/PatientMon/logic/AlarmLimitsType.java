package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/11/15.
 */
public enum AlarmLimitsType {
    HRLimits("HRLimits"),
    STILimits("STILimits"),
    ARTLimits("ARTLimits"),
    SpO2Limits("SpO2Limits");

    private final String mText;

    /**
     * @param text
     */
    private AlarmLimitsType(final String text) {
        this.mText = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return mText;
    }

    public String getName() {
        switch (this) {
            case HRLimits:
                return "HR";
            case STILimits:
                return "STaVL";
            case ARTLimits:
                return "ARTs";
            case SpO2Limits:
                return "SpO2";
        }
        return "";
    }
}
