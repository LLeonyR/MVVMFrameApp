package com.leonyr.widget.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.leonyr.widget.decoration.provider.IProvider

/**
 * The base ItemDecoration
 * Created by dkzwm on 2017/4/11.
 *
 * @author dkzwm
 */
abstract class BaseItemDecoration<T : IProvider?> : ItemDecoration() {
    @JvmField
    var mProvider: T? = null
    @JvmField
    var mDrawInsideEachOfItem = false
    @JvmField
    var mDrawOverTop = false
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (!mDrawOverTop) {
            draw(c, parent)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mDrawOverTop) {
            draw(c, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return
        val position = parent.getChildAdapterPosition(view)
        if (position < 0 || position >= adapter.itemCount) {
            return
        }
        calculateItemOffsets(parent, position, outRect)
    }

    abstract fun calculateItemOffsets(view: RecyclerView?, position: Int, rect: Rect?)
    /**
     * Draw the dividers of vertical orientation
     *
     * @param c             Need draw the Canvas
     * @param parent        Need draw The RecyclerView
     * @param layoutManager The LayoutManager of RecyclerView
     */
    abstract fun drawVerticalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?)

    /**
     * Draw the dividers of horizontal orientation
     *
     * @param c             Need draw the Canvas
     * @param parent        Need draw The RecyclerView
     * @param layoutManager The LayoutManager of RecyclerView
     */
    abstract fun drawHorizontalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?)

    private fun draw(c: Canvas, parent: RecyclerView) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val manager = layoutManager
            if (manager.orientation == RecyclerView.VERTICAL) drawVerticalOrientationDividers(c, parent, manager) else drawHorizontalOrientationDividers(c, parent, manager)
        } else if (layoutManager is StaggeredGridLayoutManager) {
            Log.e(javaClass.simpleName, "Will soon support this feature !!")
        } else {
            throw UnsupportedOperationException(javaClass.simpleName + " can only be used in the RecyclerView which use GridLayoutManager" + " or LinearLayoutManager or StaggeredGridLayoutManager")
        }
    }

    fun release() {
        mProvider!!.release()
    }

    internal interface IBuilder<R, S> {
        /**
         * Draw the divider over the top
         *
         * @param overTop True of false
         * @return Builder
         */
        fun drawOverTop(overTop: Boolean): R

        /**
         * Draw the divider inside each of item
         *
         * @param drawInsideEachOfItem true or false
         * @return Builder
         */
        fun drawInsideEachOfItem(drawInsideEachOfItem: Boolean): R

        /**
         * Set the divider provider
         *
         * @param provider Custom divider provider , can not be null
         * @return Builder
         */
        fun provider(provider: S): R
    }
}