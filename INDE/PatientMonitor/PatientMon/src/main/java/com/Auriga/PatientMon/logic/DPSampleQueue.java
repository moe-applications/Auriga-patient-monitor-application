package com.Auriga.PatientMon.logic;

import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.*;
import com.intel.inde.moe.natj.general.ann.Runtime;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.ann.ObjCClassBinding;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.NSObject;

/**
 * Created by alexeybologov on 8/2/15.
 */
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
@Generated
public class DPSampleQueue extends NSObject {

    @Generated
    protected DPSampleQueue(Pointer peer) {
        super(peer);
    }

    @Owned
    @Selector("alloc")
    public static native DPSampleQueue alloc();

    @Selector("init")
    @Generated
    public native DPSampleQueue init();

    @Selector("pushSample:")
    @Generated
    public native void pushSample(float sampleUnit);

    @Selector("popSample")
    @Generated
    public native float popSample();

    @Selector("isEmpty")
    @Generated
    public native boolean isEmpty();

    @Selector("size")
    @Generated
    public native int size();

    static {
        NatJ.register();
    }
}
