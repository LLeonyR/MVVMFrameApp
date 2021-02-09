package com.leonyr.widget.decoration.divider

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import kotlin.math.roundToInt

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
class DrawableDivider(private val mDrawable: Drawable, private val mUseWidth: Boolean, override var type: Int = IDivider.TYPE_DRAWABLE, override var size: Int = if (mUseWidth) mDrawable.intrinsicWidth else mDrawable.intrinsicHeight) : IDivider {

    override fun draw(canvas: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        canvas?.let {
            mDrawable.setBounds(left.roundToInt(), top.roundToInt(), right.roundToInt(),
                bottom.roundToInt()
            )
            mDrawable.draw(it)
        }
    }
}