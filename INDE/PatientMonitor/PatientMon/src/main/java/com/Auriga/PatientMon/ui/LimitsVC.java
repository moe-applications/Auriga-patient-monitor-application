package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.AlarmLimits;
import com.Auriga.PatientMon.logic.AlarmLimitsType;
import com.Auriga.PatientMon.logic.QueueDispatcher;
import com.Auriga.PatientMon.utils.UiStrings;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Property;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.NSObject;
import ios.uikit.UIButton;
import ios.uikit.UIColor;
import ios.uikit.UILabel;
import ios.uikit.UIViewController;
import ios.uikit.enums.UIControlState;

/**
 * Created by alexeybologov on 8/11/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("LimitsVC")
@RegisterOnStartup
public class LimitsVC extends UIViewController {
    static {
        NatJ.register();
    }

    @Selector("alloc")
    public static native LimitsVC alloc();

    @Selector("init")
    public native LimitsVC init();

    @Generated("NatJ")
    protected LimitsVC(Pointer peer) {
        super(peer);
    }

    private QueueDispatcher mQueueDispatcher = QueueDispatcher.sharedQueueDispatcher();
    private AlarmLimitsType mLimitType;

    private UILabel mLimitsType = null;
    private UILabel mLowLimit = null;
    private UILabel mHighLimit = null;

    private UIButton mLowLimitEdit;
    private UIButton mLowLimitUp;
    private UIButton mLowLimitDown;
    private UIButton mHighLimitEdit;
    private UIButton mHighLimitUp;
    private UIButton mHighLimitDown;

    private AlarmLimits mLimit;

    private static final float STEP_HR = 1;
    private static final float STEP_STI = 0.1f;
    private static final float STEP_ART = 1;
    private static final float STEP_SPO2 = 1;

    private boolean isLowEditing = false;
    private boolean isHighEditing = false;
    private String mLabelFormat;

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {
        
        mLimitsType = getLimitsType();
        mLowLimit = getLowLimit();
        mHighLimit = getHighLimit();

        mLowLimitEdit = getLowLimitEdit();
        mLowLimitUp = getLowLimitUp();
        mLowLimitDown = getLowLimitDown();
        mHighLimitEdit = getHighLimitEdit();
        mHighLimitUp = getHighLimitUp();
        mHighLimitDown = getHighLimitDown();

        mLimit = mQueueDispatcher.getAlarmLimits(mLimitType);
        if (mLimit == null) {
            mLimit = new AlarmLimits().dateType(mLimitType);
        }

        mLabelFormat = (mLimitType == AlarmLimitsType.STILimits) ? "%.1f" : "%.0f";

        updateUI();
    }

    private void updateUI() {
        mLimitsType.setText(mLimit.getDateType().getName() + " " + UiStrings.LIMITS);
        mLowLimit.setText(String.format(mLabelFormat, mLimit.getLowLimit()));
        mHighLimit.setText(String.format(mLabelFormat, mLimit.getHighLimit()));
    }

    @Selector("lowLimitEdit")
    @Property
    public native UIButton getLowLimitEdit();

    @Selector("lowLimitUp")
    @Property
    public native UIButton getLowLimitUp();

    @Selector("lowLimitDown")
    @Property
    public native UIButton getLowLimitDown();

    @Selector("highLimitEdit")
    @Property
    public native UIButton getHighLimitEdit();

    @Selector("highLimitUp")
    @Property
    public native UIButton getHighLimitUp();

    @Selector("highLimitDown")
    @Property
    public native UIButton getHighLimitDown();

    @Selector("limitsType")
    @Property
    public native UILabel getLimitsType();

    @Selector("lowLimit")
    @Property
    public native UILabel getLowLimit();

    @Selector("highLimit")
    @Property
    public native UILabel getHighLimit();

    @Selector("buttonPressed:")
    public void buttonPressed(NSObject sender) {
        if (sender == mLowLimitEdit) {
            lowLimitEditPressed(isLowEditing);
            highLimitEditPressed(true);
        } else if (sender == mHighLimitEdit) {
            highLimitEditPressed(isHighEditing);
            lowLimitEditPressed(true);
        } else if (sender == mLowLimitUp) {
            mLimit.lowLimit(mLimit.getLowLimit() + getStepValue());
        } else if (sender == mLowLimitDown) {
            mLimit.lowLimit(mLimit.getLowLimit() - getStepValue());
        } else if (sender == mHighLimitUp) {
            mLimit.highLimit(mLimit.getHighLimit() + getStepValue());
        } else if (sender == mHighLimitDown) {
            mLimit.highLimit(mLimit.getHighLimit() - getStepValue());
        }
        updateUI();
    }

    private void lowLimitEditPressed(boolean isEditing) {
        mLowLimitUp.setHidden(isEditing);
        mLowLimitDown.setHidden(isEditing);
        mLowLimitEdit.setTitleForState(
                (isEditing ? UiStrings.BUTTON_EDIT : UiStrings.BUTTON_OK), UIControlState.Normal);
        mLowLimit.setTextColor((isEditing ? UIColor.blueColor() : UIColor.blackColor()));
        isLowEditing = !isEditing;
    }

    private void highLimitEditPressed(boolean isEditing) {
        mHighLimitUp.setHidden(isEditing);
        mHighLimitDown.setHidden(isEditing);
        mHighLimitEdit.setTitleForState(
                (isEditing ? UiStrings.BUTTON_EDIT : UiStrings.BUTTON_OK), UIControlState.Normal);
        mHighLimit.setTextColor((isEditing ? UIColor.redColor() : UIColor.blackColor()));
        isHighEditing = !isEditing;
    }

    private float getStepValue() {
        switch (mLimitType) {
            case ARTLimits:
                return STEP_ART;
            case HRLimits:
                return STEP_HR;
            case SpO2Limits:
                return STEP_SPO2;
            case STILimits:
                return STEP_STI;
        }
        return 0;
    }

    public void setLimitType(AlarmLimitsType limitType) {
        this.mLimitType = limitType;
    }
}