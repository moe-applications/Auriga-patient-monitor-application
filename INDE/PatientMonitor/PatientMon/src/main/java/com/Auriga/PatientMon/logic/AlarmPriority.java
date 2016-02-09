package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/13/15.
 */
public enum AlarmPriority {
    AlarmWarning("Warning"),
    AlarmDanger("Danger");

    private final String mText;

    /**
     * @param text
     */
    private AlarmPriority(final String text) {
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
            case AlarmWarning:
                return AlarmWarning.toString();
            case AlarmDanger:
                return AlarmDanger.toString();
        }
        return "";
    }
}
