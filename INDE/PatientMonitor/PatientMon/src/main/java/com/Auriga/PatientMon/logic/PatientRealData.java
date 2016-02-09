package com.Auriga.PatientMon.logic;

/**
 * Created by alexeybologov on 8/14/15.
 */
public class PatientRealData {
    private int mHeartRate = 0;
    private int mArtS = 0;
    private int mArtD = 0;
    private int mArtM = 0;
    private int mSpo2 = 0;

    public PatientRealData() {
    }

    public PatientRealData setHeartRate(int heartRate) {
        this.mHeartRate = heartRate;
        return this;
    }

    public PatientRealData setArtD(int artD) {
        this.mArtD = artD;
        return this;
    }

    public PatientRealData setArtS(int artS) {
        this.mArtS = artS;
        return this;
    }

    public PatientRealData setArtM(int artM) {
        this.mArtM = artM;
        return this;
    }

    public PatientRealData setSpO2(int spo2) {
        this.mSpo2 = spo2;
        return this;
    }

    public int getHeartRate() {
        return mHeartRate;
    }

    public int getArtS() {
        return mArtS;
    }

    public int getArtD() {
        return mArtD;
    }

    public int getArtM() {
        return mArtM;
    }

    public int getSpO2() {
        return mSpo2;
    }
}
