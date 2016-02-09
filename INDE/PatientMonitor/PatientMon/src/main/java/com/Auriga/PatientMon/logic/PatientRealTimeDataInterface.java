package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/11/15.
 */
public interface PatientRealTimeDataInterface {
    void heartBeat();

    void heartRate(PatientRealData data);
}
