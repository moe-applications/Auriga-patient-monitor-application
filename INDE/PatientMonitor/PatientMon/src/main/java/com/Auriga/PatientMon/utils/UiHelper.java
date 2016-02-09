package com.Auriga.PatientMon.utils;

import ios.coregraphics.struct.CGPoint;
import ios.coregraphics.struct.CGRect;
import ios.coregraphics.struct.CGSize;
import ios.uikit.UIAlertView;
import ios.uikit.UIColor;
import ios.uikit.UIProgressView;
import ios.uikit.enums.UIProgressViewStyle;

/**
 * UiHelper
 * v.1.0
 * 03.09.15
 * Created by Maxim Sofronov
 * maxim.sofronov@auriga.com
 * Copyright (c) 2015 Auriga Inc. All rights reserved.
 */
public class UiHelper {

    public static UIProgressView getProgressView() {
        UIProgressView progressView = UIProgressView.alloc().
                initWithProgressViewStyle(UIProgressViewStyle.Bar);
        progressView.setFrame(new CGRect(new CGPoint(0, 0), new CGSize(200, 50)));
        progressView.setBounds(new CGRect(new CGPoint(0, 0), new CGSize(200, 50)));
        progressView.setBackgroundColor(UIColor.blackColor());
        progressView.setUserInteractionEnabled(false);
        progressView.setTrackTintColor(UIColor.whiteColor());
        progressView.setProgressTintColor(UIColor.blueColor());
        progressView.setProgress(0);
        return progressView;
    }

    public static UIAlertView getAlertView(UIProgressView progressView, Object target) {
        UIAlertView alertView = UIAlertView.alloc().initWithTitleMessageDelegateCancelButtonTitleOtherButtonTitles(
                UiStrings.LOADING, null, target, null, null);
        alertView.setValueForKey(progressView, "accessoryView");
        return alertView;
    }
}
