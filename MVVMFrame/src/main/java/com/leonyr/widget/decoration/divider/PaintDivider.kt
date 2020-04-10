package com.leonyr.widget.decoration.divider

import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
class PaintDivider(private val mPaint: Paint, override var type: Int = IDivider.TYPE_PAINT, override var size: Int = Math.round(mPaint.strokeWidth)) : IDivider {
    override fun draw(canvas: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        canvas!!.drawLine(left, top, right, bottom, mPaint)
    }
}