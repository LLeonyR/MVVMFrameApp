package com.leonyr.widget.decoration.provider

import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import com.leonyr.widget.decoration.divider.ColorDivider
import com.leonyr.widget.decoration.divider.IDivider

/**
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
class DefaultLinearProvider : ILinearProvider {
    private val mDefaultDivider: IDivider = ColorDivider()
    private var mDividers: SparseArray<IDivider>? = null
    private var mMarginsOfStart: SparseIntArray? = null
    private var mMarginsOfEnd: SparseIntArray? = null
    private var mNeedDrawFlags: SparseBooleanArray? = null
    private var mProvider: ILinearProvider? = null
    private var mAllMarginOfStart = -1
    private var mAllMarginOfEnd = -1
    private var mAllDivider: IDivider? = null

    constructor() {}
    constructor(provider: ILinearProvider) {
        mProvider = provider
    }

    fun setAllDivider(divider: IDivider) {
        mAllDivider = divider
    }

    fun setDivider(position: Int, divider: IDivider) {
        if (mDividers == null) mDividers = SparseArray()
        mDividers!!.put(position, divider)
    }

    override fun createDivider(position: Int): IDivider {
        val divider = if (mDividers == null) null else mDividers!![position]
        return divider ?: (if (mAllDivider == null) if (mProvider == null) mDefaultDivider else mProvider!!.createDivider(position) else mAllDivider!!)!!
    }

    fun setDividerNeedDraw(position: Int, need: Boolean) {
        if (mNeedDrawFlags == null) mNeedDrawFlags = SparseBooleanArray()
        mNeedDrawFlags!!.put(position, need)
    }

    override fun isDividerNeedDraw(position: Int): Boolean {
        return if (mNeedDrawFlags == null) {
            mProvider == null || mProvider!!.isDividerNeedDraw(position)
        } else {
            mNeedDrawFlags!![position, true]
        }
    }

    fun setMarginStart(position: Int, margin: Int) {
        if (mMarginsOfStart == null) mMarginsOfStart = SparseIntArray()
        mMarginsOfStart!!.put(position, margin)
    }

    fun setAllMarginStart(margin: Int) {
        mAllMarginOfStart = margin
    }

    override fun marginStart(position: Int): Int {
        val margin = if (mMarginsOfStart == null) -1 else mMarginsOfStart!![position, -1]
        return if (margin == -1) if (mAllMarginOfStart == -1) if (mProvider == null) 0 else mProvider!!.marginStart(position) else mAllMarginOfStart else margin
    }

    fun setMarginEnd(position: Int, margin: Int) {
        if (mMarginsOfEnd == null) mMarginsOfEnd = SparseIntArray()
        mMarginsOfEnd!!.put(position, margin)
    }

    fun setAllMarginEnd(margin: Int) {
        mAllMarginOfEnd = margin
    }

    override fun marginEnd(position: Int): Int {
        val margin = if (mMarginsOfEnd == null) -1 else mMarginsOfEnd!![position, -1]
        return if (margin == -1) if (mAllMarginOfEnd == -1) if (mProvider == null) 0 else mProvider!!.marginEnd(position) else mAllMarginOfEnd else margin
    }

    override fun release() {
        if (mDividers != null) mDividers!!.clear()
        if (mNeedDrawFlags != null) mNeedDrawFlags!!.clear()
        if (mMarginsOfStart != null) mMarginsOfStart!!.clear()
        if (mMarginsOfEnd != null) mMarginsOfEnd!!.clear()
        mAllDivider = null
        mProvider = null
    }
}