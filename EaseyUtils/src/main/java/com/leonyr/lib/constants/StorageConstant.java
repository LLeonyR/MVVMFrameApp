package com.leonyr.lib.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ==============================================================
 * Description:
 * <p>
 * Created by leonyr on 2019.04.14
 * (C) Copyright LeonyR Corporation 2018 All Rights Reserved.
 * ==============================================================
 */
public final class StorageConstant {
    public static final int BYTE = 1;
    public static final int KB   = 1024;
    public static final int MB   = 1048576;
    public static final int GB   = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
