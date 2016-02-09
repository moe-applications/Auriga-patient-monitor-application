package com.Auriga.PatientMon.ui;

import static com.Auriga.PatientMon.logic.PatientDateType.*;
import com.Auriga.PatientMon.logic.PatientRealData;
import com.Auriga.PatientMon.logic.PatientRealTimeDataInterface;
import com.Auriga.PatientMon.logic.QueueDispatcher;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.SEL;
import com.intel.inde.moe.natj.objc.ann.ObjCClassName;
import com.intel.inde.moe.natj.objc.ann.Property;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.NSObject;
import ios.coregraphics.struct.CGSize;
import ios.uikit.UILabel;
import ios.uikit.UIStoryboardSegue;
import ios.uikit.UIViewController;

/**
 * Created by alexeybologov on 8/11/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
@ObjCClassName("AllParamsVC")
@RegisterOnStartup
public class AllParamsVC extends UIViewController implements PatientRealTimeDataInterface {
    static {
        NatJ.register();
    }

    @Selector("alloc")
    public static native AllParamsVC alloc();

    @Selector("init")
    public native AllParamsVC init();

    @Generated("NatJ")
    protected AllParamsVC(Pointer peer) {
        super(peer);
    }

    private QueueDispatcher mQueueDispatcher = QueueDispatcher.sharedQueueDispatcher();

    private UILabel mHrLabel = null;
    private UILabel mArtSLabel = null;
    private UILabel mArtDLabel = null;
    private UILabel mArtMLabel = null;
    private UILabel mSpo2Label = null;

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

    @Selector("viewDidLoad")
    @Override
    public void viewDidLoad() {

        if (mQueueDispatcher != null) {
            mQueueDispatcher.addHeartBeatListener(this);
        }

        mHrLabel = getHrLabel();
        mArtSLabel = getArtSLabel();
        mArtDLabel = getArtDLabel();
        mArtMLabel = getArtMLabel();
        mSpo2Label = getSpo2Label();
    }

    @Selector("viewDidAppear:")
    @Override
    public void viewDidAppear(boolean animated) {
        super.viewDidAppear(animated);
    }

    @Selector("viewWillDisappear:")
    @Override
    public void viewWillDisappear(boolean animated) {
        if (mQueueDispatcher != null) {
            mQueueDispatcher.removeHeartBeatListener(this);
        }
    }

    @Selector("prepareForSegue:sender:")
    @Generated
    public void prepareForSegueSender(UIStoryboardSegue segue, NSObject sender) {
        if (segue.identifier() != null) {
            if (segue.identifier().equals("FullECGVC")) {
                if (mQueueDispatcher != null) {
                    mQueueDispatcher.startFullEcgLoading();
                }
                FullECGVC controller = (FullECGVC) segue.destinationViewController();
                controller.setPreferredContentSize(new CGSize(600, 600));
            } else if (!segue.identifier().equals("sensorDisabled")) {
                ParamDetailsVC controller = (ParamDetailsVC) segue.destinationViewController();
                controller.setIdentifier(segue.identifier());
                controller.setPreferredContentSize(new CGSize(600, 340));
                if (mQueueDispatcher != null) {
                    if (segue.identifier().equals(ARR.toString()) ||
                            segue.identifier().equals(PVC.toString())) {
                        mQueueDispatcher.startDetails(ECG1);
                    } else if (segue.identifier().equals(SpO2.toString()) ||
                            segue.identifier().equals(PLS.toString())) {
                        mQueueDispatcher.startDetails(SpO2);
                    } else if (segue.identifier().equals(ARTS.toString()) ||
                            segue.identifier().equals(ARTD.toString()) ||
                            segue.identifier().equals(ARTM.toString())) {
                        mQueueDispatcher.startDetails(ARR);
                    }
                }
            }
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
    }

    public void heartBeat() {
    }

    public void heartRate(PatientRealData data) {
        if (mHrLabel != null) {
            performSelectorOnMainThreadWithObjectWaitUntilDone(new SEL("updatePatientData:"), data, true);
        }
    }
}
