package com.leonyr.widget.decoration.provider

import com.leonyr.widget.decoration.divider.IDivider

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
interface IGridProvider : IProvider {
    fun createRowDivider(row: Int): IDivider?
    fun createColumnDivider(column: Int): IDivider?
    fun isRowNeedDraw(row: Int): Boolean
    fun isColumnNeedDraw(column: Int): Boolean
}