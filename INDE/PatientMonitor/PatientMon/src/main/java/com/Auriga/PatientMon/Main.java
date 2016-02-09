package com.Auriga.PatientMon;

import ios.NSObject;
import ios.foundation.NSDictionary;
import ios.uikit.UIAlertView;
import ios.uikit.UIApplication;
import ios.uikit.UIProgressView;
import ios.uikit.UIWindow;
import ios.uikit.c.UIKit;
import ios.uikit.protocol.UIApplicationDelegate;

import com.Auriga.PatientMon.logic.ProgressInterface;
import com.Auriga.PatientMon.logic.QueueDispatcher;
import com.Auriga.PatientMon.utils.UiHelper;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.general.ann.Generated;
import com.intel.inde.moe.natj.general.ann.RegisterOnStartup;
import com.intel.inde.moe.natj.objc.SEL;
import com.intel.inde.moe.natj.objc.ann.Selector;

@RegisterOnStartup
public class Main extends NSObject implements UIApplicationDelegate {

    static {
        NatJ.register();
    }

    public static void main(String[] args) {
        UIKit.UIApplicationMain(0, null, null, Main.class.getName());
    }

    @Generated("NatJ")
    @Selector("alloc")
    public static native Main alloc();

    @Generated("NatJ")
    protected Main(Pointer peer) {
        super(peer);
    }

    private UIWindow mWindow;
    private UIAlertView mAlertView;
    private UIProgressView mProgressView;

    @Override
    @Selector("application:didFinishLaunchingWithOptions:")
    public boolean applicationDidFinishLaunchingWithOptions(UIApplication application, NSDictionary launchOptions) {

        showProgress();
        performSelectorInBackgroundWithObject(new SEL("initQueueDispatcher"), null);

        return true;
    }

    @Selector("initQueueDispatcher")
    @Generated
    public void initQueueDispatcher() {
        QueueDispatcher.sharedQueueDispatcher().initQueue();
        hideProgress();
        QueueDispatcher.sharedQueueDispatcher().setProgressListener(null);
    }

    @Override
    @Selector("setWindow:")
    public void setWindow(UIWindow window) {
        mWindow = window;
    }

    @Override
    @Selector("window")
    public UIWindow window() {
        return mWindow;
    }

    private void showProgress() {

        QueueDispatcher.sharedQueueDispatcher().setProgressListener(new ProgressInterface() {
            @Override
            public void onProgressUpdated(float progress) {
                if (mProgressView != null) {
                    performSelectorOnMainThreadWithObjectWaitUntilDone(new SEL("updateProgress:"),
                            new Float(progress), false);
                }
            }
        });

        mProgressView = UiHelper.getProgressView();
        mAlertView = UiHelper.getAlertView(mProgressView, this);
        mAlertView.show();
    }

    @Selector("updateProgress:")
    @Generated
    private void updateProgress(Float progress) {
        mProgressView.setProgressAnimated(progress, true);
    }

    private void hideProgress() {
        if (mAlertView != null) {
            mAlertView.dismissWithClickedButtonIndexAnimated(-1, true);
        }
    }
}
