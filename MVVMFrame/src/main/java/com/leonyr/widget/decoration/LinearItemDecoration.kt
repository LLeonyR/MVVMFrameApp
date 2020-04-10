package com.leonyr.widget.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonyr.widget.decoration.divider.IDivider
import com.leonyr.widget.decoration.provider.DefaultLinearProvider
import com.leonyr.widget.decoration.provider.ILinearProvider

/**
 * Created by dkzwm on 2017/4/13.
 *
 * @author dkzwm
 */
class LinearItemDecoration private constructor(builder: Builder) : BaseItemDecoration<ILinearProvider?>() {
    override fun calculateItemOffsets(parent: RecyclerView?, position: Int, rect: Rect?) {
        val layoutManager = parent!!.layoutManager as? LinearLayoutManager ?: throw UnsupportedOperationException("LinearItemDecoration can only be used in " + "the RecyclerView which use LinearLayoutManager")
        if (mDrawInsideEachOfItem) {
            rect!![0, 0, 0] = 0
            return
        }
        val manager = layoutManager
        if (manager.orientation == RecyclerView.VERTICAL) {
            if (manager.reverseLayout) {
                if (mProvider!!.isDividerNeedDraw(position)) {
                    rect!![0, mProvider!!.createDivider(position)!!.size, 0] = 0
                } else {
                    rect!![0, 0, 0] = 0
                }
            } else {
                if (mProvider!!.isDividerNeedDraw(position)) {
                    rect!![0, 0, 0] = mProvider!!.createDivider(position)!!.size
                } else {
                    rect!![0, 0, 0] = 0
                }
            }
        } else {
            if (manager.reverseLayout) {
                if (mProvider!!.isDividerNeedDraw(position)) {
                    rect!![mProvider!!.createDivider(position)!!.size, 0, 0] = 0
                } else {
                    rect!![0, 0, 0] = 0
                }
            } else {
                if (mProvider!!.isDividerNeedDraw(position)) {
                    rect!![0, 0, mProvider!!.createDivider(position)!!.size] = 0
                } else {
                    rect!![0, 0, 0] = 0
                }
            }
        }
    }

    override fun drawVerticalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?) {
        val manager = layoutManager as LinearLayoutManager?
        val childCount = parent!!.childCount
        var left: Float
        var top: Float
        var right: Float
        var bottom: Float
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val lp = view.layoutParams as RecyclerView.LayoutParams
            val position = lp.viewAdapterPosition
            if (position < 0 || !mProvider!!.isDividerNeedDraw(position)) continue
            val transitionX = view.translationX
            val transitionY = view.translationY
            left = view.left - lp.leftMargin + transitionX + mProvider!!.marginStart(position)
            right = view.right + lp.rightMargin + transitionX - mProvider!!.marginEnd(position)
            val divider = mProvider!!.createDivider(position)
            if (manager!!.reverseLayout) {
                if (divider!!.type === IDivider.TYPE_DRAWABLE) {
                    bottom = view.top - lp.topMargin + transitionY
                    top = bottom - divider!!.size
                } else {
                    top = view.top - lp.topMargin - divider!!.size / 2f + transitionY
                    bottom = top
                }
                if (mDrawInsideEachOfItem) {
                    top += divider.size
                    bottom += divider.size
                }
            } else {
                if (divider!!.type === IDivider.TYPE_DRAWABLE) {
                    top = view.bottom + lp.bottomMargin + transitionY
                    bottom = top + divider!!.size
                } else {
                    top = view.bottom + lp.bottomMargin + divider!!.size / 2f + transitionY
                    bottom = top
                }
                if (mDrawInsideEachOfItem) {
                    top -= divider.size
                    bottom -= divider.size
                }
            }
            divider.draw(c, left, top, right, bottom)
        }
    }

    override fun drawHorizontalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?) {
        val manager = layoutManager as LinearLayoutManager?
        val childCount = parent!!.childCount
        var left: Float
        var top: Float
        var right: Float
        var bottom: Float
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val lp = view.layoutParams as RecyclerView.LayoutParams
            val position = lp.viewAdapterPosition
            if (position < 0 || !mProvider!!.isDividerNeedDraw(position)) continue
            val transitionX = view.translationX
            val transitionY = view.translationY
            top = view.top - lp.topMargin + transitionY + mProvider!!.marginStart(position)
            bottom = view.bottom + lp.bottomMargin + transitionY - mProvider!!.marginEnd(position)
            val divider = mProvider!!.createDivider(position)
            if (manager!!.reverseLayout) {
                if (divider!!.type === IDivider.TYPE_DRAWABLE) {
                    right = view.left - lp.leftMargin + transitionX
                    left = right - divider!!.size
                } else {
                    left = view.left - lp.leftMargin - divider!!.size / 2f + transitionY
                    right = left
                }
                if (mDrawInsideEachOfItem) {
                    left += divider.size
                    right += divider.size
                }
            } else {
                if (divider!!.type === IDivider.TYPE_DRAWABLE) {
                    left = view.right + lp.rightMargin + transitionX
                    right = left + divider!!.size
                } else {
                    left = view.right + lp.rightMargin + divider!!.size / 2f + transitionX
                    right = left
                }
                if (mDrawInsideEachOfItem) {
                    left -= divider.size
                    right -= divider.size
                }
            }
            divider.draw(c, left, top, right, bottom)
        }
    }

    class Builder(var mContext: Context) : IBuilder<Builder?, ILinearProvider?> {
        var mProvider: ILinearProvider? = null
        var mDrawInsideEachOfItem = false
        var mDrawOverTop = false
        override fun drawOverTop(drawOverTop: Boolean): Builder {
            mDrawOverTop = drawOverTop
            return this
        }

        override fun drawInsideEachOfItem(drawInsideEachOfItem: Boolean): Builder {
            mDrawInsideEachOfItem = drawInsideEachOfItem
            return this
        }

        override fun provider(provider: ILinearProvider?): Builder {
            require(mProvider == null) { "You must set up the ILinearProvider before " + "configuring the custom rules" }
            mProvider = provider
            return this
        }

        fun divider(position: Int, divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setDivider(position, divider!!)
            return this
        }

        fun divider(divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setAllDivider(divider!!)
            return this
        }

        fun needDraw(position: Int, need: Boolean): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setDividerNeedDraw(position, need)
            return this
        }

        fun marginStart(margin: Int): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setAllMarginStart(margin)
            return this
        }

        fun marginStart(position: Int, margin: Int): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setMarginStart(position, margin)
            return this
        }

        fun marginEnd(margin: Int): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setAllMarginEnd(margin)
            return this
        }

        fun marginEnd(position: Int, margin: Int): Builder {
            checkProvider()
            (mProvider as DefaultLinearProvider?)!!.setMarginEnd(position, margin)
            return this
        }

        private fun checkProvider() {
            checkProviderIsNull()
            if (mProvider !is DefaultLinearProvider) mProvider = DefaultLinearProvider(mProvider!!)
        }

        private fun checkProviderIsNull() {
            if (mProvider == null) mProvider = DefaultLinearProvider()
        }

        fun build(): LinearItemDecoration {
            checkProviderIsNull()
            return LinearItemDecoration(this)
        }

    }

    init {
        mDrawInsideEachOfItem = builder.mDrawInsideEachOfItem
        mDrawOverTop = builder.mDrawOverTop
        mProvider = builder.mProvider
    }
}