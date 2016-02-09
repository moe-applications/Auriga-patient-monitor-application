package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.Alarm;
import com.Auriga.PatientMon.logic.AlarmInterface;
import com.Auriga.PatientMon.logic.AlarmLimitsType;
import com.Auriga.PatientMon.logic.AlarmPriority;

import com.Auriga.PatientMon.logic.PatientRealData;
import com.Auriga.PatientMon.logic.PatientRealTimeDataInterface;
import com.Auriga.PatientMon.logic.PatientInfo;
import com.Auriga.PatientMon.logic.QueueDispatcher;

import static com.Auriga.PatientMon.utils.UiColors.*;
import static com.Auriga.PatientMon.utils.UiStrings.*;
import static com.Auriga.PatientMon.utils.Const.*;
import static com.Auriga.PatientMon.logic.AlarmLimitsType.*;
import static com.Auriga.PatientMon.logic.PatientDateType.*;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.SEL;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Property;
import com.intel.inde.moe.natj.objc.ann.Selector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ios.NSObject;
import ios.coregraphics.struct.CGSize;
import ios.foundation.NSTimeZone;
import ios.foundation.NSTimer;
import ios.uikit.UIButton;
import ios.uikit.UIColor;
import ios.uikit.UIImage;
import ios.uikit.UIImageView;
import ios.uikit.UILabel;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UIViewController;
import ios.uikit.enums.UIControlState;
import ios.uikit.enums.UIRectEdge;

/**
 * Created by alexeybologov on 8/2/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("MainMonitorVC")
@RegisterOnStartup
public class MainMonitorVC extends UIViewController implements AlarmInterface, PatientRealTimeDataInterface {
    static {
        NatJ.register();
    }

    @Selector("alloc")
    public static native MainMonitorVC alloc();

    @Selector("init")
    public native MainMonitorVC init();

    @Generated("NatJ")
    protected MainMonitorVC(Pointer peer) {
        super(peer);
    }

    private PatientInfo mPatient;

    private UILabel mPatientName = null;
    private UILabel mPatientAge = null;
    private UILabel mPatientWeigth = null;
    private UILabel mPatientHeigth = null;
    private UILabel mPatientID = null;
    private UILabel mPatientBOD = null;

    private UILabel mPatientBedID = null;
    private UILabel mPatientCurrTime = null;
    private UILabel mPatientCurrDate = null;

    private UILabel mLimitHrHigh = null;
    private UILabel mLimitHrLow = null;

    private UILabel mHrLabel = null;
    private UILabel mArtSLabel = null;
    private UILabel mArtDLabel = null;
    private UILabel mArtMLabel = null;
    private UILabel mSpo2Label = null;
    private UILabel mPlsLabel = null;

    private UILabel mAlarmLabel = null;
    private UILabel mAlarmHR = null;
    private UILabel mAlarmSTI = null;
    private UILabel mAlarmART = null;
    private UILabel mAlarmSpO2 = null;

    private UIButton mAlarmButton;

    private UIImageView mHeartBeat = null;
    private boolean isHeartBeat = true;

    private boolean isAlarmOn = true;
    private QueueDispatcher mQueueDispatcher = null;
    private NSTimer mTimer = null;

    private Map<String, Alarm> mAlarms = new HashMap<>();
    private ArrayList<String> mAlarmsOrder = new ArrayList<>();
    private int mAlarmIterator = 0;
    private boolean isBlink = false;

    @Selector("alarmButton")
    @Property
    public native UIButton getAlarmButton();

    @Selector("patientName")
    @Property
    public native UILabel getPatientName();

    @Selector("patientAge")
    @Property
    public native UILabel getPatientAge();

    @Selector("patientWeigth")
    @Property
    public native UILabel getPatientWeigth();

    @Selector("patientHeigth")
    @Property
    public native UILabel getPatientHeigth();

    @Selector("patientID")
    @Property
    public native UILabel getPatientID();

    @Selector("patientBOD")
    @Property
    public native UILabel getPatientBOD();

    @Selector("patientBedID")
    @Property
    public native UILabel getPatientBedID();

    @Selector("patientCurrTime")
    @Property
    public native UILabel getPatientCurrTime();

    @Selector("patientCurrDate")
    @Property
    public native UILabel getPatientCurrDate();

    @Selector("alarmLabel")
    @Property
    public native UILabel getAlarmLabel();

    @Selector("limitHrHigh")
    @Property
    public native UILabel getLimitHrHigh();

    @Selector("limitHrLow")
    @Property
    public native UILabel getLimitHrLow();

    @Selector("alarmHR")
    @Property
    public native UILabel getAlarmHR();

    @Selector("alarmSTI")
    @Property
    public native UILabel getAlarmSTI();

    @Selector("alarmART")
    @Property
    public native UILabel getAlarmART();

    @Selector("alarmSpO2")
    @Property
    public native UILabel getAlarmSpO2();

    @Selector("heartBeat")
    @Property
    public native UIImageView getHeartBeat();

    @Selector("hrLabel")
    @Property
    public native UILabel getHrLabel();

    @Selector("art_dLabel")
    @Property
    public native UILabel getArtDLabel();

    @Selector("art_mLabel")
    @Property
    public native UILabel getArtMLabel();

    @Selector("art_sLabel")
    @Property
    public native UILabel getArtSLabel();

    @Selector("spo2Label")
    @Property
    public native UILabel getSpo2Label();

    @Selector("plsLabel")
    @Property
    public native UILabel getPlsLabel();

    @Selector("prefersStatusBarHidden")
    @Override
    public boolean prefersStatusBarHidden() {
        return true;
    }

    @Selector("loadView")
    @Override
    public void loadView() {
        super.loadView();
    }

    @Selector("viewWillDisappear:")
    @Override
    public void viewWillDisappear(boolean animated) {
        if (mQueueDispatcher != null) {
            mQueueDispatcher.setAlarmListener(null);
            mQueueDispatcher.removeHeartBeatListener(this);
            mQueueDispatcher.stopDataLoading();
        }
        if (mTimer != null) {
            mTimer.invalidate();
            mTimer = null;
        }
        navigationController().navigationBar().setHidden(false);
    }

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {
        navigationController().navigationBar().setHidden(true);
        mAlarmButton = getAlarmButton();
        mAlarmLabel = getAlarmLabel();
        mPatientName = getPatientName();
        mPatientAge = getPatientAge();
        mPatientWeigth = getPatientWeigth();
        mPatientHeigth = getPatientHeigth();
        mPatientID = getPatientID();
        mPatientBOD = getPatientBOD();
        mPatientBedID = getPatientBedID();
        mPatientCurrTime = getPatientCurrTime();
        mPatientCurrDate = getPatientCurrDate();
        mLimitHrHigh = getLimitHrHigh();
        mLimitHrLow = getLimitHrLow();
        mHeartBeat = getHeartBeat();
        mHrLabel = getHrLabel();
        mArtSLabel = getArtSLabel();
        mArtDLabel = getArtDLabel();
        mArtMLabel = getArtMLabel();
        mSpo2Label = getSpo2Label();
        mPlsLabel = getPlsLabel();

        mAlarmHR = getAlarmHR();
        mAlarmSTI = getAlarmSTI();
        mAlarmART = getAlarmART();
        mAlarmSpO2 = getAlarmSpO2();

        isAlarmOn = false;
        mAlarmLabel.setHidden(true);

        updateAlarmButton();

        mPatientAge.setText(mPatient.age());
        mPatientHeigth.setText(mPatient.getHeight());
        mPatientWeigth.setText(mPatient.getWeight());

        mPatientName.setText(mPatient.fullName());
        mPatientID.setText(String.format(PATIENT_ID, mPatient.getPatientID()));
        mPatientBOD.setText(String.format(PATIENT_BIRTHDAY, mPatient.stringBirthDate()));

        mPatientBedID.setText(BED + mPatient.getBed());

        this.setEdgesForExtendedLayout(UIRectEdge.All);

        updateTimeLabel();
        mTimer = NSTimer.scheduledTimerWithTimeIntervalTargetSelectorUserInfoRepeats(1.0, this, new SEL("updateTimeLabel:"), null, true);

        isHeartBeat = false;
    }

    @Selector("updateTimeLabel:")
    @Generated
    void updateTimeLabel() {
        DateFormat datef = new SimpleDateFormat(DATE_FORMAT);
        DateFormat timef = new SimpleDateFormat(TIME_FORMAT);
        NSTimeZone currentTimeZone = NSTimeZone.localTimeZone();
        Date currTime = new Date(new Date().getTime() + currentTimeZone.secondsFromGMT() * 1000);
        String time = timef.format(currTime);
        mPatientCurrTime.setText(time);
        mPatientCurrDate.setText(datef.format(currTime));
        updateLimitLabels();
    }

    private void updateLimitLabels() {
        if (mLimitHrHigh != null) {
            mLimitHrHigh.setText(String.format("%.0f",
                    mQueueDispatcher.getAlarmLimits(HRLimits).getHighLimit()));
            mLimitHrLow.setText(String.format("%.0f",
                    mQueueDispatcher.getAlarmLimits(HRLimits).getLowLimit()));
        }
    }

    @Selector("alarmClicked:")
    public void alarmClicked(NSObject sender) {
        isAlarmOn = !isAlarmOn;

        updateAlarmButton();
    }

    /*******************************************
     * Navigation
     *******************************************/

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        if (segue.identifier() == null) return;

        if (segue.identifier().equals("showMenu")) {
            MenuVC controller = (MenuVC) segue.destinationViewController();
            controller.parentController = this;
        } else if (isUIWaveFormVC(segue.identifier())) {
            UIWaveFormVC controller = (UIWaveFormVC) (segue.destinationViewController());
            controller.setDataQueue(sharedQueueDispatcher().queueWithID(segue.identifier()));
            controller.setSampleFreq(SAMPLE_FREQ);
            if (segue.identifier().equals(ECG1.toString())) {
                controller.setWaveColor(WAVE_GREEN);
            } else if (segue.identifier().equals(ECG2.toString())) {
                controller.setWaveColor(WAVE_GREEN);
            } else if (segue.identifier().equals(ARR.toString())) {
                controller.setWaveColor(WAVE_RED);
                controller.setYPadding(WAVE_FORM_ART_PADDING);
            } else if (segue.identifier().equals(SpO2.toString())) {
                controller.setWaveColor(WAVE_BLUE);
                controller.setYPadding(WAVE_FORM_SPO2_PADDING);
            }
        } else if (isLimitsVC(segue.identifier())) {
            LimitsVC controller = (LimitsVC) (segue.destinationViewController());
            if (segue.identifier().equals(HRLimits.toString())) {
                controller.setLimitType(HRLimits);
            } else if (segue.identifier().equals(STILimits.toString())) {
                controller.setLimitType(STILimits);
            } else if (segue.identifier().equals(ARTLimits.toString())) {
                controller.setLimitType(ARTLimits);
            } else if (segue.identifier().equals(SpO2Limits.toString())) {
                controller.setLimitType(SpO2Limits);
            }
        } else if (segue.identifier().equals("AllParams")) {
        } else if (segue.identifier().equals("HRTrend")) {
            UIHRTrendFormVC controller = (UIHRTrendFormVC) (segue.destinationViewController());
            controller.setDataQueue(sharedQueueDispatcher().getTrendQueue(HRTrend));
            controller.setWaveColor(WAVE_GREEN);
        } else if (segue.identifier().equals("SpO2Trend")) {
            UISpO2TrendFormVC controller = (UISpO2TrendFormVC) (segue.destinationViewController());
            controller.setDataQueue(sharedQueueDispatcher().getTrendQueue(SpO2Trend));
            controller.setWaveColor(WAVE_BLUE);
        } else if (segue.identifier().equals("ARTTrend")) {
            UIARTTrendFormVC controller = (UIARTTrendFormVC) (segue.destinationViewController());
            controller.setDataQueue(sharedQueueDispatcher().getTrendQueue(ARTSTrend));
            controller.setDataQueue2(sharedQueueDispatcher().getTrendQueue(ARTDTrend));
            controller.setDataQueue3(sharedQueueDispatcher().getTrendQueue(ARTMTrend));
            controller.setWaveColor(WAVE_RED);
        } else if (segue.identifier().equals("TrendDetailsVC")) {
            TrendDetailsVC controller = (TrendDetailsVC) (segue.destinationViewController());
            controller.setPreferredContentSize(new CGSize(600, 340));
        }
    }

    private boolean isUIWaveFormVC(String segue) {
        return (segue.equals(ECG1.toString()) ||
                segue.equals(ECG2.toString()) ||
                segue.equals(ARR.toString()) ||
                segue.equals(SpO2.toString()));
    }

    private boolean isLimitsVC(String segue) {
        return (segue.equals(HRLimits.toString()) ||
                segue.equals(STILimits.toString()) ||
                segue.equals(ARTLimits.toString()) ||
                segue.equals(SpO2Limits.toString()));
    }

    /*******************************************
     * QueueDispatcher
     *******************************************/

    private QueueDispatcher sharedQueueDispatcher() {
        if (mQueueDispatcher == null) {
            mQueueDispatcher = QueueDispatcher.sharedQueueDispatcher();
            mQueueDispatcher.setAlarmListener(this);
            mQueueDispatcher.addHeartBeatListener(this);
            mQueueDispatcher.startDataLoading();
        }
        return mQueueDispatcher;
    }

    /*******************************************
     * Alarms
     *******************************************/
    public void needShowAlarm(Alarm alarm) {
        if (mAlarmLabel != null) {
            boolean needToStartBlink = mAlarms.isEmpty();

            if (!mAlarms.containsKey(alarm.get_dateType().toString())) {
                mAlarmsOrder.add(alarm.get_dateType().toString());
            }
            mAlarms.put(alarm.get_dateType().toString(), alarm);

            if (alarm.get_dateType() == HRLimits) {
                mAlarmHR.setHidden(false);
            } else if (alarm.get_dateType() == STILimits) {
                mAlarmSTI.setHidden(false);
            } else if (alarm.get_dateType() == ARTLimits) {
                mAlarmART.setHidden(false);
            } else if (alarm.get_dateType() == SpO2Limits) {
                mAlarmSpO2.setHidden(false);
            }

            if (needToStartBlink) {
                mAlarmLabel.setHidden(false);
                performSelectorOnMainThreadWithObjectWaitUntilDone(new SEL("alarmBlink"), null, true);
            }
        }
    }

    public void deleteAlarm(AlarmLimitsType type) {
        if (mAlarms.containsKey(type.toString())) {
            if (mAlarmsOrder.indexOf(type.toString()) <= mAlarmIterator) {
                mAlarmIterator--;
            }
            mAlarms.remove(type.toString());
            mAlarmsOrder.remove(type.toString());

            if (type == HRLimits) {
                mAlarmHR.setHidden(true);
            } else if (type == STILimits) {
                mAlarmSTI.setHidden(true);
            } else if (type == ARTLimits) {
                mAlarmART.setHidden(true);
            } else if (type == SpO2Limits) {
                mAlarmSpO2.setHidden(true);
            }

            if (mAlarms.isEmpty()) {
                mAlarmLabel.setHidden(true);
            }
        }
    }

    public void needSetValueForParameter(float value, String param) {
    }

    public void heartBeat() {
        if (mHeartBeat != null && !isHeartBeat) {
            isHeartBeat = true;
            performSelectorOnMainThreadWithObjectWaitUntilDone(new SEL("heartBeatUI"), null, true);
        }
    }

    public void heartRate(PatientRealData data) {
        if (mHrLabel != null) {
            performSelectorOnMainThreadWithObjectWaitUntilDone(new SEL("updatePatientData:"), data, true);
        }
    }

    @Selector("updatePatientData:")
    @Generated
    public void updatePatientData(PatientRealData data) {
        mHrLabel.setText(String.valueOf(data.getHeartRate()));
        mArtSLabel.setText(String.valueOf(data.getArtS()));
        mArtDLabel.setText(String.valueOf(data.getArtD()));
        mArtMLabel.setText(String.valueOf(data.getArtM()));
        mSpo2Label.setText(String.valueOf(data.getSpO2()));

        if (mQueueDispatcher.checkCurrentValues(data) == 0 && mAlarmLabel != null) {
            mAlarmLabel.setHidden(true);
        }
    }

    @Selector("heartBeatUI")
    @Generated
    public void heartBeatUI() {
        mHeartBeat.setHidden(false);
        performSelectorWithObjectAfterDelay(new SEL("heartDeBeat"), null, 0.3f);
    }

    @Selector("heartDeBeat")
    @Generated
    public void heartDeBeat() {
        mHeartBeat.setHidden(true);
        isHeartBeat = false;
    }

    @Selector("alarmBlink")
    @Generated
    public void alarmBlink() {
        if (!mAlarms.isEmpty() && !mAlarmLabel.isHidden()) {
            if (mAlarmIterator >= mAlarmsOrder.size()) {
                mAlarmIterator = 0;
            }
            Alarm currentAlarm = mAlarms.get(mAlarmsOrder.get(mAlarmIterator++));

            UIColor alarmLabelBackground = null;
            AlarmPriority maxPriority = AlarmPriority.AlarmWarning;
            mAlarmLabel.setText(currentAlarm.getMessage());

            for (String alarmType : mAlarmsOrder) {
                Alarm alarm = mAlarms.get(alarmType);
                UIColor background = null;

                if (alarm.getPriority() == AlarmPriority.AlarmWarning) {
                    background = isBlink ? ALARM_WARNING_ON : ALARM_WARNING_OFF;
                } else if (alarm.getPriority() == AlarmPriority.AlarmDanger) {
                    maxPriority = AlarmPriority.AlarmDanger;
                    background = isBlink ? ALARM_DANGER_ON : ALARM_DANGER_OFF;
                }

                if (alarm.get_dateType() == HRLimits) {
                    mAlarmHR.setBackgroundColor(background);
                } else if (alarm.get_dateType() == STILimits) {
                    mAlarmSTI.setBackgroundColor(background);
                } else if (alarm.get_dateType() == ARTLimits) {
                    mAlarmART.setBackgroundColor(background);
                } else if (alarm.get_dateType() == SpO2Limits) {
                    mAlarmSpO2.setBackgroundColor(background);
                }
            }

            if (maxPriority == AlarmPriority.AlarmWarning) {
                alarmLabelBackground = ALARM_WARNING_ON;
                mAlarmLabel.setTextColor(UIColor.grayColor());
            } else if (maxPriority == AlarmPriority.AlarmDanger) {
                alarmLabelBackground = ALARM_DANGER_ON;
                mAlarmLabel.setTextColor(UIColor.whiteColor());
            }
            mAlarmLabel.setBackgroundColor(alarmLabelBackground);

            isBlink = !isBlink;

            performSelectorWithObjectAfterDelay(new SEL("alarmBlink"), null, 1.0f);
        }
    }

    private void updateAlarmButton() {
        if (isAlarmOn == true) {
            mAlarmButton.setImageForState(UIImage.imageNamed("alarm_on"), UIControlState.Normal);
        } else {
            mAlarmButton.setImageForState(UIImage.imageNamed("alarm_off"), UIControlState.Normal);
        }
    }

    public void setPatient(PatientInfo patient) {
        mPatient = patient;
    }
}
