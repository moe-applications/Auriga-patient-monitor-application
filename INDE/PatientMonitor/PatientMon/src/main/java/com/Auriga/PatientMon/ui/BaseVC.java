package com.Auriga.PatientMon.ui;

import com.Auriga.PatientMon.logic.QueueDispatcher;
import com.intel.inde.moe.natj.general.NatJ;
import com.intel.inde.moe.natj.general.Pointer;
import com.intel.inde.moe.natj.objc.ObjCRuntime;
import com.intel.inde.moe.natj.objc.SEL;
import com.intel.inde.moe.natj.objc.ann.Selector;

import ios.coregraphics.struct.CGPoint;
import ios.uikit.UIGestureRecognizer;
import ios.uikit.UITapGestureRecognizer;
import ios.uikit.UITouch;
import ios.uikit.UIViewController;
import ios.uikit.enums.UIGestureRecognizerState;
import ios.uikit.protocol.UIGestureRecognizerDelegate;


/**
 * Created by alexeybologov on 8/11/15.
 */
@com.intel.inde.moe.natj.general.ann.Runtime(ObjCRuntime.class)
abstract public class BaseVC extends UIViewController implements UIGestureRecognizerDelegate {
    static {
        NatJ.register();
    }

    public BaseVC(Pointer peer) {
        super(peer);
    }

    private UITapGestureRecognizer mTapBehindGesture = null;

    private QueueDispatcher mQueueDispatcher = QueueDispatcher.sharedQueueDispatcher();

    @Selector("viewDidAppear:")
    @Override
    public void viewDidAppear(boolean animated) {
        super.viewDidAppear(animated);

        if (mTapBehindGesture == null) {
            mTapBehindGesture = UITapGestureRecognizer.alloc().initWithTargetAction(this, new SEL("tapBehindDetected:"));
            mTapBehindGesture.setNumberOfTapsRequired(1);
            mTapBehindGesture.setCancelsTouchesInView(false);
        }

        view().window().addGestureRecognizer(mTapBehindGesture);
        mTapBehindGesture.setDelegate(this);
    }

    @Selector("tapBehindDetected:")
    public void tapBehindDetected(UITapGestureRecognizer sender) {
        if (sender.state() == UIGestureRecognizerState.Ended) {
            CGPoint location = sender.locationInView(null); //Passing nil gives us coordinates in the window

            // swap (x,y) on iOS 8 in landscape
            location = new CGPoint(location.y(), location.x());

            // convert the tap's location into the local view's coordinate system, and test to see if it's in or outside.
            // If outside, dismiss the view.
            if (!(view().pointInsideWithEvent(view().convertPointFromView(location, view().window()), null))) {

                // remove the recognizer first so it's view.window is valid
                view().window().removeGestureRecognizer(sender);
                mTapBehindGesture = null;
                onUserTapOutside();
            }
        }
    }

    @Selector("gestureRecognizerShouldBegin:")
    @Override
    public boolean gestureRecognizerShouldBegin(UIGestureRecognizer gestureRecognizer) {
        return true;
    }

    @Selector("gestureRecognizer:shouldRecognizeSimultaneouslyWithGestureRecognizer:")
    @Override
    public boolean gestureRecognizerShouldRecognizeSimultaneouslyWithGestureRecognizer(UIGestureRecognizer gestureRecognizer, UIGestureRecognizer otherGestureRecognizer) {
        return true;
    }

    @Selector("gestureRecognizer:shouldReceiveTouch:")
    @Override
    public boolean gestureRecognizerShouldReceiveTouch(UIGestureRecognizer gestureRecognizer, UITouch touch) {
        return true;
    }

    public QueueDispatcher getQueueDispatcher() {
        return this.mQueueDispatcher;
    }

    public abstract void onUserTapOutside();
}
