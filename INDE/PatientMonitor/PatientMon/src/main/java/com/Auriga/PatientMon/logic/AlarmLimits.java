package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/9/15.
 */
public class AlarmLimits {
    private AlarmLimitsType mDateType = null;
    private float mLowLimit = -1000.0f;
    private float mHighLimit = 1000.0f;
    private float mCurrent = 0.0f;

    public AlarmLimits() {
    }

    public AlarmLimits dateType(AlarmLimitsType dateType) {
        this.mDateType = dateType;
        return this;
    }

    public AlarmLimits lowLimit(float lowLimit) {
        this.mLowLimit = lowLimit;
        return this;
    }

    public AlarmLimits highLimit(float highLimit) {
        this.mHighLimit = highLimit;
        return this;
    }

    public AlarmLimits current(float current) {
        this.mCurrent = current;
        return this;
    }

    public float getLowLimit() {
        return mLowLimit;
    }

    public float getHighLimit() {
        return mHighLimit;
    }

    public float getCurrent() {
        return mCurrent;
    }

    public AlarmLimitsType getDateType() {
        return mDateType;
    }

    public boolean isLowLimitHappened() {
        return mCurrent <= mLowLimit;
    }

    public boolean isHighLimitHappened() {
        return mCurrent >= mHighLimit;
    }

    public AlarmPriority getPriority() {
        float diff = 0;
        if (isHighLimitHappened()) {
            diff = mCurrent / mHighLimit - 1.0f;
        } else if (isLowLimitHappened()) {
            diff = 1.0f - mCurrent / mLowLimit;
        }
        if (diff <= 0.2f) {
            return AlarmPriority.AlarmWarning;
        }
        return AlarmPriority.AlarmDanger;
    }
}
