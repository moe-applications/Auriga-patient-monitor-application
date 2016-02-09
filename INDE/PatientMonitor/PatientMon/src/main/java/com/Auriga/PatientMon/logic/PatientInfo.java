package com.Auriga.PatientMon.logic;

import com.Auriga.PatientMon.utils.Const;
import com.Auriga.PatientMon.utils.UiStrings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexeybologov on 8/2/15.
 */
public class PatientInfo {

    private String mFirstName = "";
    private String mLastName = "";
    private Date mBirthDate = null;
    private String mBed = "";
    private String mPatientID = "";

    // It should be float with stringWeight/stringHeight methods, but we don't actualy need float values now.
    private String mWeight = "";
    private String mHeight = "";

    // Sometimes this class will be abstract, but now I wrote it here
    private String mFileName = "";

    public String stringBirthDate() {
        if (mBirthDate != null) {
            DateFormat df = new SimpleDateFormat(Const.DATE_FORMAT);
            return df.format(mBirthDate);
        }
        return "";
    }

    public String description() {
        return String.format(UiStrings.PATIENT_BED, mBed, mLastName, mFileName);
    }

    public String fullName() {
        return String.format(UiStrings.PATIENT_NAME, mFileName, mLastName);
    }

    public String age() {
        long age = (new Date()).getTime() - mBirthDate.getTime();

        return String.format(UiStrings.PATIENT_AGE, age / Const.MILLISECONDS_IN_YEAR);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public Date getmBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date mBirthDate) {
        this.mBirthDate = mBirthDate;
    }

    public String getBed() {
        return mBed;
    }

    public void setBed(String mBed) {
        this.mBed = mBed;
    }

    public String getPatientID() {
        return mPatientID;
    }

    public void setPatientID(String mPatientID) {
        this.mPatientID = mPatientID;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String mHeight) {
        this.mHeight = mHeight;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }
}
