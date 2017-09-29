package com.colin.plana.constants;

/**
 * Created by colin on 2017/9/27.
 */

public final class TaskType {

    public static final int TYPE_DOING = 1; //正在做
    public static final int TYPE_FILE = 2;  //归档
    public static final int TYPE_DELETE = 3;//删除（放到垃圾桶）
    public static final int TYPE_REMIND = 4;

    public static final boolean checkLegalType(int type) {
        return
                type == TYPE_DOING || type == TYPE_FILE || type == TYPE_DELETE || type == TYPE_REMIND;
    }

}
