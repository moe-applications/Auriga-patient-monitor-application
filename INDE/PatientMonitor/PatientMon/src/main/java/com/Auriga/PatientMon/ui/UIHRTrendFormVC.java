package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.DPSampleQueue;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.glkit.GLKViewController;
import ios.uikit.UIColor;

/**
 * Created by alexeybologov on 8/15/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("UIHRTrendFormVC")
@RegisterOnStartup
public class UIHRTrendFormVC extends GLKViewController {

    @Generated("NatJ")
    protected UIHRTrendFormVC(Pointer peer) {
        super(peer);
    }

    @Selector("setDataQueue:")
    @Generated
    public native void setDataQueue(DPSampleQueue dataQueue);

    @Selector("setWaveColor:")
    @Generated
    public native void setWaveColor(UIColor waveColor);

    static {
        NatJ.register();
    }
}
