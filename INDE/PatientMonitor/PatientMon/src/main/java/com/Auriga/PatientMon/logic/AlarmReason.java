package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/13/15.
 */
public enum AlarmReason {
    low("low"),
    high("high");

    private final String mText;

    /**
     * @param text
     */
    private AlarmReason(final String text) {
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
            case low:
                return low.toString();
            case high:
                return high.toString();
        }
        return "";
    }
}
