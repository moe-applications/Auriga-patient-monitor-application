package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.utils.Const;
import static com.Auriga.PatientMon.logic.PatientDateType.*;
import com.Auriga.PatientMon.utils.UiColors;
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
import ios.uikit.UILabel;
import ios.uikit.UIStoryboardSegue;

/**
 * Created by alexeybologov on 8/11/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("ParamDetailsVC")
@RegisterOnStartup
public class ParamDetailsVC extends BaseVC {
    static {
        NatJ.register();
    }

    private UILabel mParamName = null;
    private UILabel mParamMin = null;
    private UILabel mParamMax = null;
    private String mIdentifier = null;

    @Generated("NatJ")
    protected ParamDetailsVC(Pointer peer) {
        super(peer);
    }

    @Selector("alloc")
    public static native ParamDetailsVC alloc();

    @Selector("init")
    public native ParamDetailsVC init();

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        mParamName = getParamName();
        mParamMin = getParamMin();
        mParamMax = getParamMax();

        mParamName.setText(getIdentifier());
        mParamMin.setText(getMinLable());
        mParamMax.setText(getMaxLable());
    }

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        if (segue.identifier() != null && segue.identifier().equals("ParamView")) {
            UIWaveFormVC controller = (UIWaveFormVC) (segue.destinationViewController());
            if (getIdentifier().equals(ARR.toString()) ||
                    getIdentifier().equals(PVC.toString())) {
                controller.setDataQueue(getQueueDispatcher().queueDetails(ECG1.toString()));
                controller.setWaveColor(UiColors.WAVE_GREEN);
                controller.setSampleFreq(Const.DETAILS_SAMPLE_FREQ);
            } else if (getIdentifier().equals(SpO2.toString()) ||
                    getIdentifier().equals(PLS.toString())) {
                controller.setDataQueue(getQueueDispatcher().queueDetails(SpO2.toString()));
                controller.setWaveColor(UiColors.WAVE_BLUE);
                controller.setSampleFreq(Const.DETAILS_SAMPLE_FREQ);
                controller.setYPadding(Const.WAVE_FORM_SPO2_PADDING);
            } else if (getIdentifier().equals(ARTS.toString()) ||
                    getIdentifier().equals(ARTD.toString()) ||
                    getIdentifier().equals(ARTM.toString())) {
                controller.setDataQueue(getQueueDispatcher().queueDetails(ARR.toString()));
                controller.setWaveColor(UiColors.WAVE_RED);
                controller.setSampleFreq(Const.DETAILS_SAMPLE_FREQ);
                controller.setYPadding(Const.WAVE_FORM_ART_PADDING);
            }
        }
    }

    private String getIdentifier() {
        if (mIdentifier == null) {
            mIdentifier = ARR.toString();
        }
        return mIdentifier;
    }

    private String getMinLable() {
        if (getIdentifier().equals(ARR.toString()) ||
                getIdentifier().equals(PVC.toString())) {
            return UiStrings.PARAMS_MIN_LABEL_ARR;
        } else if (getIdentifier().equals(SpO2.toString()) ||
                getIdentifier().equals(PLS.toString())) {
            return UiStrings.PARAMS_MIN_LABEL_SPO2;
        } else {
            return UiStrings.PARAMS_MIN_LABEL_DEFAULT;
        }
    }

    private String getMaxLable() {
        if (getIdentifier().equals(ARR.toString()) ||
                getIdentifier().equals(PVC.toString())) {
            return UiStrings.PARAMS_MAX_LABEL_ARR;
        } else if (getIdentifier().equals(SpO2.toString()) ||
                getIdentifier().equals(PLS.toString())) {
            return UiStrings.PARAMS_MAX_LABEL_SPO2;
        } else {
            return UiStrings.PARAMS_MAX_LABEL_DEFAULT;
        }
    }

    @Selector("paramName")
    @Property
    public native UILabel getParamName();

    @Selector("paramMin")
    @Property
    public native UILabel getParamMin();

    @Selector("paramMax")
    @Property
    public native UILabel getParamMax();

    public void setIdentifier(String identifier) {
        this.mIdentifier = identifier;
    }

    @Override
    public void onUserTapOutside() {
        dismissViewControllerAnimatedCompletion(true, null);
        if (getQueueDispatcher() != null) {
            getQueueDispatcher().stopDetails();
        }
    }
}