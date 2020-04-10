package com.leonyr.widget.decoration.provider

/**
 * Created by dkzwm on 2017/4/13.
 *
 * @author dkzwm
 */
abstract class GridProvider : IGridProvider {
    override fun isRowNeedDraw(row: Int): Boolean {
        return true
    }

    override fun isColumnNeedDraw(column: Int): Boolean {
        return true
    }
}