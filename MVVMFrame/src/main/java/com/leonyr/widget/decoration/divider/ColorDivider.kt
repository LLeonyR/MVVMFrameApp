package com.leonyr.widget.decoration.divider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.annotation.ColorInt

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
open class ColorDivider @JvmOverloads constructor(@field:ColorInt @param:ColorInt var color: Int = DEFAULT_COLOR, override var type: Int = IDivider.TYPE_PAINT, override var size: Int = DEFAULT_SIZE) : IDivider {

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        canvas?.drawLine(left, top, right, bottom, mPaint)
    }

    companion object {
        private const val DEFAULT_COLOR = Color.GRAY
        private const val DEFAULT_SIZE = 2
    }

    init {
        mPaint.strokeWidth = size.toFloat()
        mPaint.color = color
    }
}