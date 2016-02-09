package com.Auriga.PatientMon.utils;

/**
 * Created by maxim on 19.08.15.
 */
public class Log {

    public static void d(String message) {
        if (!Const.IS_DEBUG) return;
        System.out.print(System.currentTimeMillis() + ":" + message + "\n");
    }
}
