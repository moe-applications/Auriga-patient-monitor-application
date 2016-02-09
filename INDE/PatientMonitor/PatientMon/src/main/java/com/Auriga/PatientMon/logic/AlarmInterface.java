package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/9/15.
 */
public interface AlarmInterface {
    void needShowAlarm(Alarm alarm);

    void deleteAlarm(AlarmLimitsType type);

    void needSetValueForParameter(float value, String param);
}
