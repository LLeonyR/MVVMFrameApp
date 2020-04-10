package com.leonyr.widget.decoration.provider

import android.util.SparseArray
import android.util.SparseBooleanArray
import com.leonyr.widget.decoration.divider.ColorDivider
import com.leonyr.widget.decoration.divider.IDivider

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
class DefaultGridProvider : IGridProvider {
    private var mRowDividers: SparseArray<IDivider>? = null
    private var mColumnDividers: SparseArray<IDivider>? = null
    private var mRowNeedDrawFlags: SparseBooleanArray? = null
    private var mColumnNeedDrawFlags: SparseBooleanArray? = null
    private val mDefaultDivider: IDivider = ColorDivider()
    private var mAllRowDivider: IDivider? = null
    private var mAllColumnDivider: IDivider? = null
    private var mProvider: IGridProvider? = null

    constructor() {}
    constructor(provider: IGridProvider) {
        mProvider = provider
    }

    fun setAllRowDivider(allRowDivider: IDivider) {
        mAllRowDivider = allRowDivider
    }

    fun setAllColumnDivider(allColumnDivider: IDivider) {
        mAllColumnDivider = allColumnDivider
    }

    fun setRowDivider(row: Int, divider: IDivider) {
        if (mRowDividers == null) mRowDividers = SparseArray()
        mRowDividers!!.put(row, divider)
    }

    override fun createRowDivider(row: Int): IDivider {
        val rowDivider = if (mRowDividers == null) null else mRowDividers!![row]
        return rowDivider ?: (if (mAllRowDivider == null) if (mProvider == null) mDefaultDivider else mProvider!!.createRowDivider(row) else mAllRowDivider!!)!!
    }

    fun setColumnDivider(row: Int, divider: IDivider) {
        if (mColumnDividers == null) mColumnDividers = SparseArray()
        mColumnDividers!!.put(row, divider)
    }

    override fun createColumnDivider(column: Int): IDivider {
        val columnDivider = if (mColumnDividers == null) null else mColumnDividers!![column]
        return columnDivider ?: (if (mAllColumnDivider == null) if (mProvider == null) mDefaultDivider else mProvider!!.createColumnDivider(column) else mAllColumnDivider!!)!!
    }

    fun setRowNeedDraw(row: Int, need: Boolean) {
        if (mRowNeedDrawFlags == null) mRowNeedDrawFlags = SparseBooleanArray()
        mRowNeedDrawFlags!!.put(row, need)
    }

    override fun isRowNeedDraw(row: Int): Boolean {
        return if (mRowNeedDrawFlags == null) mProvider == null || mProvider!!.isRowNeedDraw(row) else mRowNeedDrawFlags!![row, true]
    }

    fun setColumnNeedDraw(column: Int, need: Boolean) {
        if (mColumnNeedDrawFlags == null) mColumnNeedDrawFlags = SparseBooleanArray()
        mColumnNeedDrawFlags!!.put(column, need)
    }

    override fun isColumnNeedDraw(column: Int): Boolean {
        return if (mColumnNeedDrawFlags == null) mProvider == null || mProvider!!.isColumnNeedDraw(column) else mColumnNeedDrawFlags!![column, true]
    }

    override fun release() {
        if (mRowDividers != null) mRowDividers!!.clear()
        if (mColumnDividers != null) mColumnDividers!!.clear()
        if (mRowNeedDrawFlags != null) mRowNeedDrawFlags!!.clear()
        if (mColumnNeedDrawFlags != null) mColumnNeedDrawFlags!!.clear()
        mProvider = null
        mAllColumnDivider = null
        mAllRowDivider = null
    }
}