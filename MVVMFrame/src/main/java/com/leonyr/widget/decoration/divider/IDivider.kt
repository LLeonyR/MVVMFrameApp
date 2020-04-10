package com.leonyr.widget.decoration.divider

import android.graphics.Canvas
import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
interface IDivider {
    fun draw(canvas: Canvas?, left: Float, top: Float, right: Float, bottom: Float)

    var type: Int
    var size: Int

    @IntDef(TYPE_COLOR, TYPE_PAINT, TYPE_DRAWABLE) @Retention(RetentionPolicy.SOURCE) annotation class DividerType
    companion object {
        const val TYPE_COLOR = 0
        const val TYPE_PAINT = 1
        const val TYPE_DRAWABLE = 2
    }
}