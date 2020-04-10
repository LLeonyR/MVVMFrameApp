package com.leonyr.widget.decoration.provider

import com.leonyr.widget.decoration.divider.IDivider

/**
 * Created by dkzwm on 2017/4/12.
 *
 * @author dkzwm
 */
interface ILinearProvider : IProvider {
    fun createDivider(position: Int): IDivider?
    fun isDividerNeedDraw(position: Int): Boolean
    fun marginStart(position: Int): Int
    fun marginEnd(position: Int): Int
}