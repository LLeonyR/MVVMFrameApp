package com.leonyr.widget.decoration.provider

/**
 * Created by dkzwm on 2017/4/13.
 *
 * @author dkzwm
 */
abstract class LinearProvider : ILinearProvider {
    override fun isDividerNeedDraw(position: Int): Boolean {
        return true
    }

    override fun marginEnd(position: Int): Int {
        return 0
    }

    override fun marginStart(position: Int): Int {
        return 0
    }
}