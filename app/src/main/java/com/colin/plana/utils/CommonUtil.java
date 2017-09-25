package com.colin.plana.utils;

/**
 * Created by colin on 2017/9/25.
 */

public final class CommonUtil {

    private CommonUtil() {
    }


    public static <T> T checkNotNull(T object) {
        if (object != null) {
            return object;
        } else {
            throw new NullPointerException("### 赋值的时候出现空指针异常");
        }
    }
}
