package com.Auriga.PatientMon.ui;

import static com.Auriga.PatientMon.logic.PatientDateType.*;
import com.Auriga.PatientMon.utils.UiColors;
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
@ObjCClassName("TrendDetailsVC")
@RegisterOnStartup
public class TrendDetailsVC extends BaseVC {
    static {
        NatJ.register();
    }

    private UILabel mParamName = null;
    private UILabel mParamMin = null;
    private UILabel mParamMax = null;

    @Generated("NatJ")
    protected TrendDetailsVC(Pointer peer) {
        super(peer);
    }

    @Selector("alloc")
    public static native TrendDetailsVC alloc();

    @Selector("init")
    public native TrendDetailsVC init();

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {
        mParamName = getParamName();
        mParamMin = getParamMin();
        mParamMax = getParamMax();
    }

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        if (segue.identifier() != null) {
            if (segue.identifier().equals("HRTrendDetails")) {
                UIHRTrendFormVC controller = (UIHRTrendFormVC) (segue.destinationViewController());
                controller.setDataQueue(getQueueDispatcher().getTrendQueue(HRTrend));
                controller.setWaveColor(UiColors.WAVE_GREEN);
            } else if (segue.identifier().equals("SPO2TrendDetails")) {
                UISpO2TrendFormVC controller = (UISpO2TrendFormVC) (segue.destinationViewController());
                controller.setDataQueue(getQueueDispatcher().getTrendQueue(SpO2Trend));
                controller.setWaveColor(UiColors.WAVE_BLUE);
            } else if (segue.identifier().equals("ARTTrendDetails")) {
                UIARTTrendFormVC controller = (UIARTTrendFormVC) (segue.destinationViewController());
                controller.setDataQueue(getQueueDispatcher().getTrendQueue(ARTSTrend));
                controller.setDataQueue2(getQueueDispatcher().getTrendQueue(ARTDTrend));
                controller.setDataQueue3(getQueueDispatcher().getTrendQueue(ARTMTrend));
                controller.setWaveColor(UiColors.WAVE_RED);
            }
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

    @Override
    public void onUserTapOutside() {
        dismissViewControllerAnimatedCompletion(true, null);
    }
}