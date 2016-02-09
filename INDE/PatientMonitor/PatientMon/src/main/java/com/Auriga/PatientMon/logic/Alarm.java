package com.Auriga.PatientMon.logic;

import com.Auriga.PatientMon.utils.UiStrings;

/**
 * Created by alexeybologov on 8/13/15.
 */
public class Alarm {
    private AlarmLimitsType mType;
    private AlarmPriority mPriority;
    private AlarmReason mReason;

    public Alarm() {
    }

    public Alarm dateType(AlarmLimitsType dateType) {
        this.mType = dateType;
        return this;
    }

    public Alarm priority(AlarmPriority priority) {
        this.mPriority = priority;
        return this;
    }

    public Alarm reason(AlarmReason reason) {
        this.mReason = reason;
        return this;
    }

    public AlarmPriority getPriority() {
        return mPriority;
    }

    public AlarmReason getReason() {
        return mReason;
    }

    public AlarmLimitsType get_dateType() {
        return mType;
    }

    public String getMessage() {
        return mType.toString().replace(UiStrings.LIMITS, "") + UiStrings.OUT_OF_RANGE + mReason.toString();
    }
}
