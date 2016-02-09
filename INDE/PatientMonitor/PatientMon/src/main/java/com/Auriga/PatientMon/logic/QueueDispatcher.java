package com.Auriga.PatientMon.logic;

import com.Auriga.PatientMon.utils.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.Vector;

import ios.foundation.NSBundle;

import static com.Auriga.PatientMon.logic.PatientDateType.*;
import static com.Auriga.PatientMon.logic.AlarmLimitsType.*;
import static com.Auriga.PatientMon.utils.Const.*;

/**
 * Created by alexeybologov on 8/3/15.
 */
public class QueueDispatcher {

    private Map<String, DPSampleQueue> mQueues = new HashMap<>();
    private Map<String, DPSampleQueue> mQueuesFullECGs = new HashMap<>();
    private Map<String, DPSampleQueue> mQueuesDetails = new HashMap<>();
    private Map<String, AlarmLimits> mLimits = new HashMap<>();
    private Map<String, DataInputStream> mStreams = new HashMap<>();
    private Map<String, Integer> mPositions = new HashMap<>();

    private boolean isRunning = false;
    private boolean isFullECGsLoading = false;
    private boolean isDetails = false;

    private ProgressInterface mProgressListener = null;
    private AlarmInterface mAlarmListener = null;
    private Vector<PatientRealTimeDataInterface> mHbListeners = new Vector<>();

    private Vector<Float> mTrendHr = new Vector<>();
    private Vector<Float> mTrendArtD = new Vector<>();
    private Vector<Float> mTrendArtS = new Vector<>();
    private Vector<Float> mTrendArtM = new Vector<>();
    private Vector<Float> mTrendSpO2 = new Vector<>();

    private Vector<Float> mEcg1 = new Vector<>();
    private Vector<Float> mEcg2 = new Vector<>();
    private Vector<Float> mSpO2 = new Vector<>();
    private Vector<Float> mArt = new Vector<>();

    private int mCurrentData = 0;
    private long mLastHeartBeatDebug = 0;
    private long mLastHeartBeat = 0;

    private Thread mDrawThread;
    private Thread mDataThread;

    private static QueueDispatcher sInstance = null;

    public static QueueDispatcher sharedQueueDispatcher() {
        if (sInstance == null) {
            sInstance = new QueueDispatcher();
        }
        return sInstance;
    }

    public void setProgressListener(ProgressInterface progressInterface) {
        mProgressListener = progressInterface;
    }

    private void openFileToRead(PatientDateType type) {
        String fileId = type.toString();
        NSBundle mainBundle = NSBundle.mainBundle();
        String pathToFile = mainBundle.pathForResourceOfType(fileId, "dat");

        File file = new File(pathToFile);
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            mStreams.put(fileId, dis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromVector(PatientDateType type) {
        String fileId = type.toString();
        DPSampleQueue queue = mQueues.get(fileId);

        if (queue != null && queue.size() > MAX_QUEUE_SIZE) {
            return; //SpO2 has lesser points per frame so if we read it each time, we will get overflow
        }

        DPSampleQueue queue2 = mQueuesFullECGs.get(fileId);
        if (queue2 != null && queue2.size() > MAX_QUEUE_SIZE && isFullECGsLoading) {
            return;
        }

        DPSampleQueue queue3 = mQueuesDetails.get(fileId);
        if (queue3 != null && queue3.size() > MAX_QUEUE_SIZE && isDetails) {
            return;
        }

        float sample;
        float lastSample = 0;
        Vector<Float> data = getOriginalVector(type);
        int size = 4;
        int lastPosition = getPosition(fileId);
        if (lastPosition + size > data.size()) {
            size = data.size() - lastPosition;
        }

        for (int i = lastPosition; i < lastPosition + size; i++) {

            sample = data.get(i);

            if (i == 0) {
                lastSample = sample;
            }
            if (queue != null) {
                queue.pushSample(sample);
            }

            if (isDetails) {
                queue3 = mQueuesDetails.get(fileId);
                if (queue3 != null)
                    queue3.pushSample(sample);
            }

            if (isFullECGsLoading) {
                if (type == ECG1) {
                    queue2 = mQueuesFullECGs.get(fileId);
                    queue2.pushSample(sample);
                    queue2 = mQueuesFullECGs.get(ECG4.toString());
                    queue2.pushSample(sample);
                } else if (type == ECG2) {
                    queue2 = mQueuesFullECGs.get(fileId);
                    queue2.pushSample(sample);
                    queue2 = mQueuesFullECGs.get(ECG5.toString());
                    queue2.pushSample(sample);
                    queue2 = mQueuesFullECGs.get(ECG6.toString());
                    queue2.pushSample(sample);
                    queue2 = mQueuesFullECGs.get(ECG3.toString());
                    queue2.pushSample(sample);
                }
            }

            if (type == ECG1 && Math.abs(sample - lastSample) > 1.0f) {
                //printHb();
                if (mLastHeartBeat == 0) {
                    mLastHeartBeat = new Date().getTime();
                } else {
                    long period = new Date().getTime() - mLastHeartBeat;
                    if (period >= 300) {
                        mLastHeartBeat = new Date().getTime();
                        for (PatientRealTimeDataInterface listener : mHbListeners) {
                            listener.heartBeat();
                        }
                    }
                }
            }
            lastSample = sample;
        }
        lastPosition = lastPosition + size;
        if (lastPosition >= data.size()) {
            lastPosition = 0;
        }
        mPositions.put(fileId, lastPosition);
    }

    public void initQueue() {
        Log.d("init Queue Dispatcher : start");
        updateProgress(0.1f);
        mQueues.put(ECG1.toString(), DPSampleQueue.alloc().init());
        mQueues.put(ECG2.toString(), DPSampleQueue.alloc().init());
        mQueues.put(ARR.toString(), DPSampleQueue.alloc().init());
        mQueues.put(SpO2.toString(), DPSampleQueue.alloc().init());

        openFileToRead(ECG1);
        readDataFromFileToVector(ECG1, mEcg1);
        updateProgress(0.2f);
        openFileToRead(ECG2);
        readDataFromFileToVector(ECG2, mEcg2);

        updateProgress(0.3f);
        openFileToRead(ARR);
        readDataFromFileToVector(ARR, mArt);
        updateProgress(0.4f);
        openFileToRead(SpO2);
        readDataFromFileToVector(SpO2, mSpO2);

        openFileToRead(HRTrend);
        readDataFromFileToVector(HRTrend, mTrendHr);
        updateProgress(0.5f);
        openFileToRead(ARTDTrend);
        readDataFromFileToVector(ARTDTrend, mTrendArtD);
        updateProgress(0.6f);
        openFileToRead(ARTSTrend);
        readDataFromFileToVector(ARTSTrend, mTrendArtS);
        updateProgress(0.7f);
        openFileToRead(ARTMTrend);
        readDataFromFileToVector(ARTMTrend, mTrendArtM);
        updateProgress(0.8f);
        openFileToRead(SpO2Trend);
        readDataFromFileToVector(SpO2Trend, mTrendSpO2);

        setAlarmLimits(new AlarmLimits().dateType(HRLimits).
                highLimit(DEFAULT_LIMIT_HR_HIGH).lowLimit(DEFAULT_LIMIT_HR_LOW));
        setAlarmLimits(new AlarmLimits().dateType(ARTLimits).
                highLimit(DEFAULT_LIMIT_ART_HIGH).lowLimit(DEFAULT_LIMIT_ART_LOW));
        setAlarmLimits(new AlarmLimits().dateType(SpO2Limits).
                highLimit(DEFAULT_LIMIT_SPO2_HIGH).lowLimit(DEFAULT_LIMIT_SPO2_LOW));
        setAlarmLimits(new AlarmLimits().dateType(STILimits).
                highLimit(DEFAULT_LIMIT_STI_HIGH).lowLimit(DEFAULT_LIMIT_STI_LOW));
        updateProgress(1f);

        mDataThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        updatePatientData();
                        sleep(PATIENT_DATA_SLEEP_TIME);
                        synchronized (this) {
                            while (!isRunning) {
                                wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        mDrawThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        addPoints();
                        sleep(ADD_POINT_SLEEP_TIME);
                        synchronized (this) {
                            while (!isRunning) {
                                wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
        mDrawThread.start();
        mDataThread.start();

        Log.d("init Queue Dispatcher : end");
    }

    private void updateProgress(float progress) {
        if (mProgressListener != null) {
            mProgressListener.onProgressUpdated(progress);
        }
    }

    public void startDataLoading() {

        if (isRunning) return;
        isRunning = true;

        synchronized (mDrawThread) {
            mDrawThread.notify();
        }
        synchronized (mDataThread) {
            mDataThread.notify();
        }
    }

    public void stopDataLoading() {
        isRunning = false;
    }

    public DPSampleQueue queueWithID(String queueID) {
        return mQueues.get(queueID);
    }

    private void addPoints() {
        readDataFromVector(ECG1);
        readDataFromVector(ECG2);
        readDataFromVector(ARR);
        readDataFromVector(SpO2);
    }

    public void setAlarmListener(AlarmInterface listener) {
        mAlarmListener = listener;
    }

    public void addHeartBeatListener(PatientRealTimeDataInterface listener) {
        mHbListeners.add(listener);
    }

    public void removeHeartBeatListener(PatientRealTimeDataInterface listener) {
        mHbListeners.remove(listener);
    }

    public AlarmLimits getAlarmLimits(AlarmLimitsType dateType) {
        return mLimits.get(dateType.toString());
    }

    public void setAlarmLimits(AlarmLimits limit) {
        AlarmLimits limitExists = getAlarmLimits(limit.getDateType());
        if (limitExists == null) {
            mLimits.put(limit.getDateType().toString(), limit);
        } else {
            limitExists.lowLimit(limit.getLowLimit()).highLimit(limit.getHighLimit());
        }
    }

    private boolean checkAlarms(AlarmLimitsType dateType, float value) {
        AlarmLimits limit = getAlarmLimits(dateType);
        if (limit != null && mAlarmListener != null) {
            limit.current(value);

            Alarm alarm = new Alarm().dateType(dateType).priority(limit.getPriority());
            if (limit.isLowLimitHappened()) {
                mAlarmListener.needShowAlarm(alarm.reason(AlarmReason.low));
                return true;
            } else if (limit.isHighLimitHappened()) {
                mAlarmListener.needShowAlarm(alarm.reason(AlarmReason.high));
                return true;
            } else {
                mAlarmListener.deleteAlarm(dateType);
            }
        }
        return false;
    }

    public void startFullEcgLoading() {
        mQueuesFullECGs.put(ECG1.toString(), DPSampleQueue.alloc().init());
        mQueuesFullECGs.put(ECG2.toString(), DPSampleQueue.alloc().init());
        mQueuesFullECGs.put(ECG3.toString(), DPSampleQueue.alloc().init());
        mQueuesFullECGs.put(ECG4.toString(), DPSampleQueue.alloc().init());
        mQueuesFullECGs.put(ECG5.toString(), DPSampleQueue.alloc().init());
        mQueuesFullECGs.put(ECG6.toString(), DPSampleQueue.alloc().init());
        isFullECGsLoading = true;
    }

    public void stopFullEcgLoading() {
        mQueuesFullECGs.clear();
        isFullECGsLoading = false;
    }

    public void startDetails(PatientDateType patientDateType) {
        mQueuesDetails.put(patientDateType.toString(), DPSampleQueue.alloc().init());
        isDetails = true;
    }

    public void stopDetails() {
        mQueuesDetails.clear();
        isDetails = false;
    }

    public DPSampleQueue queueFullEcgWithID(String queueID) {
        return mQueuesFullECGs.get(queueID);
    }

    public DPSampleQueue queueDetails(String queueID) {
        return mQueuesDetails.get(queueID);
    }

    public int checkCurrentValues(PatientRealData data) {
        int alarmCount = 0;
        if (checkAlarms(HRLimits, data.getHeartRate()))
            alarmCount++;
        if (checkAlarms(STILimits, 0))
            alarmCount++;
        if (checkAlarms(ARTLimits, data.getArtS()))
            alarmCount++;
        if (checkAlarms(SpO2Limits, data.getSpO2()))
            alarmCount++;

        return alarmCount;
    }

    public void readDataFromFileToVector(PatientDateType type, Vector destVector) {
        String fileId = type.toString();
        DataInputStream dis = mStreams.get(fileId);
        try {
            float sample;
            byte[] buffer = new byte[4];

            while (true) {

                dis.readFully(buffer);
                sample = ByteBuffer.wrap(buffer, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();

                destVector.add(new Float(sample));
            }

        } catch (EOFException e) {
            try {
                dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            mStreams.remove(fileId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePatientData() {
        mCurrentData++;
        if (mCurrentData >= mTrendHr.size()) {
            mCurrentData = 0;
        }

        PatientRealData current = new PatientRealData().setHeartRate(mTrendHr.elementAt(mCurrentData).intValue())
                .setArtD(mTrendArtD.elementAt(mCurrentData).intValue())
                .setArtS(mTrendArtS.elementAt(mCurrentData).intValue())
                .setArtM(mTrendArtM.elementAt(mCurrentData).intValue())
                .setSpO2(mTrendSpO2.elementAt(mCurrentData).intValue());

        for (PatientRealTimeDataInterface listener : mHbListeners) {
            listener.heartRate(current);
        }
    }

    public DPSampleQueue getTrendQueue(PatientDateType trend) {
        DPSampleQueue result = DPSampleQueue.alloc().init();
        Vector<Float> originalVector = getOriginalVector(trend);

        for (Float sample : originalVector) {
            result.pushSample(sample.floatValue());
        }

        return result;
    }

    private Vector<Float> getOriginalVector(PatientDateType type) {
        switch (type) {
            case HRTrend:
                return mTrendHr;
            case SpO2Trend:
                return mTrendSpO2;
            case ARTDTrend:
                return mTrendArtD;
            case ARTMTrend:
                return mTrendArtM;
            case ARTSTrend:
                return mTrendArtS;
            case ECG1:
                return mEcg1;
            case ECG2:
                return mEcg2;
            case ARR:
                return mArt;
            case SpO2:
                return mSpO2;
            default:
                return null;
        }
    }

    private int getPosition(String key) {
        if (mPositions.containsKey(key)) {
            return mPositions.get(key);
        } else {
            mPositions.put(key, 0);
            return 0;
        }
    }

    private void printHb() {
        if (mLastHeartBeatDebug == 0) {
            mLastHeartBeatDebug = new Date().getTime();
        } else {
            long period = new Date().getTime() - mLastHeartBeatDebug;
            if (period >= 300) {
                long heartRate = 60000l / period;
                mLastHeartBeatDebug = new Date().getTime();
                Log.d("-- HB -- " + heartRate + "\n");
            }
        }
    }
}
