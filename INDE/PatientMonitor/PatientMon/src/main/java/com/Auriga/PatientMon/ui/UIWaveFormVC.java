package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.DPSampleQueue;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.*;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.glkit.GLKViewController;
import ios.uikit.UIColor;

/**
 * Created by alexeybologov on 8/5/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("UIWaveFormVC")
@RegisterOnStartup
public class UIWaveFormVC extends GLKViewController {

    @Generated("NatJ")
    protected UIWaveFormVC(Pointer peer) {
        super(peer);
    }

    @Selector("setDataQueue:")
    @Generated
    public native void setDataQueue(DPSampleQueue dataQueue);

    @Selector("setWaveColor:")
    @Generated
    public native void setWaveColor(UIColor waveColor);


    @Selector("setSampleFreq:")
    @Generated
    public native void setSampleFreq(float sampleFreq);

    @Selector("setYPadding:")
    @Generated
    public native void setYPadding(float yPadding);

    static {
        NatJ.register();
    }
}
