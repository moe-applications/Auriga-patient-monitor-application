package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.utils.Const;
import com.Auriga.PatientMon.logic.PatientDateType;
import com.Auriga.PatientMon.utils.UiColors;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.*;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.NSObject;
import ios.uikit.UIStoryboardSegue;

/**
 * Created by alexeybologov on 8/11/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("FullECGVC")
@RegisterOnStartup
public class FullECGVC extends BaseVC {
    static {
        NatJ.register();
    }

    @Selector("alloc")
    public static native FullECGVC alloc();

    @Selector("init")
    public native FullECGVC init();

    @Generated("NatJ")
    protected FullECGVC(Pointer peer) {
        super(peer);
    }

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        if (segue.identifier() != null && isUIWaveFormVC(segue.identifier())) {
            UIWaveFormVC controller = (UIWaveFormVC) (segue.destinationViewController());
            controller.setDataQueue(getQueueDispatcher().queueFullEcgWithID(segue.identifier()));
            controller.setWaveColor(UiColors.WAVE_GREEN);
            controller.setSampleFreq(Const.SAMPLE_FREQ);
        }
    }

    private boolean isUIWaveFormVC(String segue) {
        return (segue.equals(PatientDateType.ECG1.toString()) ||
                segue.equals(PatientDateType.ECG2.toString()) ||
                segue.equals(PatientDateType.ECG3.toString()) ||
                segue.equals(PatientDateType.ECG4.toString()) ||
                segue.equals(PatientDateType.ECG5.toString()) ||
                segue.equals(PatientDateType.ECG6.toString()));
    }

    @Override
    public void onUserTapOutside() {
        dismissViewControllerAnimatedCompletion(true, null);
        if (getQueueDispatcher() != null) {
            getQueueDispatcher().stopFullEcgLoading();
        }
    }
}
