package com.colin.plana.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by colin on 2017/9/28.
 */

public final class PixelUtil {
    private static final String TAG = "PixelUtil";

    public static int dpToPixel(@NonNull Context context, @NonNull float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Log.e(TAG, "dpToPixel: "+displayMetrics.density );
        return Math.round(displayMetrics.density * dp);
    }


}
