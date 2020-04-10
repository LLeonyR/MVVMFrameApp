package com.leonyr.widget.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leonyr.widget.decoration.divider.IDivider
import com.leonyr.widget.decoration.provider.DefaultGridProvider
import com.leonyr.widget.decoration.provider.IGridProvider

/**
 * Created by dkzwm on 2017/4/5.
 *
 * @author dkzwm
 */
class GridItemDecoration private constructor(builder: Builder) : BaseItemDecoration<IGridProvider?>() {
    override fun calculateItemOffsets(parent: RecyclerView?, position: Int, rect: Rect?) {
        val layoutManager = parent!!.layoutManager as? GridLayoutManager ?: throw UnsupportedOperationException("GridItemDecoration can only be used in " + "the RecyclerView which use GridLayoutManager")
        if (mDrawInsideEachOfItem) {
            rect!![0, 0, 0] = 0
            return
        }
        val manager = layoutManager
        val spanCount = manager.spanCount
        val lookup = manager.spanSizeLookup
        var columnSize = 0
        var rowSize = 0
        val row: Int
        val column: Int
        val totalSpanSize: Int
        if (manager.orientation == RecyclerView.VERTICAL) {
            row = lookup.getSpanGroupIndex(position, spanCount)
            column = lookup.getSpanIndex(position, spanCount)
            totalSpanSize = getTotalSpanSizeByPosition(manager, position)
            val drawColumn: Boolean
            val drawRow = isRowNeedDraw(row, totalSpanSize, true, manager.reverseLayout, spanCount)
            if (drawRow) {
                val rowDivider = createRowDivider(row, true, manager.reverseLayout)
                rowSize = rowDivider!!.size
            }
            if (manager.reverseLayout) {
                drawColumn = isColumnNeedDraw(column, totalSpanSize, true, true, spanCount)
                if (drawColumn) {
                    val columnDivider = createColumnDivider(column, true, true)
                    columnSize = columnDivider!!.size
                }
                rect!![0, 0, columnSize] = rowSize
            } else {
                drawColumn = isColumnNeedDraw(column, totalSpanSize, true, false, spanCount)
                if (drawColumn) {
                    val columnDivider = createColumnDivider(column, true, false)
                    columnSize = columnDivider!!.size
                }
                rect!![columnSize, rowSize, 0] = 0
            }
        } else {
            row = lookup.getSpanIndex(position, spanCount)
            column = lookup.getSpanGroupIndex(position, spanCount)
            totalSpanSize = getTotalSpanSizeByPosition(manager, position)
            val drawColumn = isColumnNeedDraw(column, totalSpanSize, false, manager.reverseLayout, spanCount)
            val drawRow: Boolean
            if (drawColumn) {
                val columnDivider = createColumnDivider(column, false, manager.reverseLayout)
                columnSize = columnDivider!!.size
            }
            if (manager.reverseLayout) {
                drawRow = isRowNeedDraw(row, totalSpanSize, false, true, spanCount)
                if (drawRow) {
                    val rowDivider = createRowDivider(row, false, true)
                    rowSize = rowDivider!!.size
                }
                rect!![0, 0, columnSize] = rowSize
            } else {
                drawRow = isRowNeedDraw(row, totalSpanSize, false, false, spanCount)
                if (drawRow) {
                    val rowDivider = createRowDivider(row, false, false)
                    rowSize = rowDivider!!.size
                }
                rect!![columnSize, rowSize, 0] = 0
            }
        }
    }

    override fun drawVerticalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?) {
        val manager = layoutManager as GridLayoutManager?
        val spanCount = manager!!.spanCount
        val childCount = parent!!.childCount
        val lookup = manager.spanSizeLookup
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val lp = view.layoutParams as RecyclerView.LayoutParams
            val position = lp.viewAdapterPosition
            if (position < 0) continue
            val row = lookup.getSpanGroupIndex(position, spanCount)
            val column = lookup.getSpanIndex(position, spanCount)
            val totalSpanSize = getTotalSpanSizeByPosition(manager, position)
            val transitionX = view.translationX
            val transitionY = view.translationY
            val drawColumn = isColumnNeedDraw(column, totalSpanSize, true, manager.reverseLayout, spanCount)
            val drawRow = isRowNeedDraw(row, totalSpanSize, true, manager.reverseLayout, spanCount)
            var left: Float
            var top: Float
            var right: Float
            var bottom: Float
            if (drawColumn) {
                top = view.top - lp.topMargin + transitionY
                bottom = view.bottom + lp.bottomMargin + transitionY
                if (manager.reverseLayout) {
                    val columnDivider = createColumnDivider(column, true, true)
                    if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        left = view.right + lp.rightMargin + transitionX
                        right = left + columnDivider!!.size
                    } else {
                        left = view.right + lp.rightMargin + transitionX + columnDivider!!.size / 2f
                        right = left
                    }
                    if (mDrawInsideEachOfItem) {
                        left -= columnDivider.size
                        right -= columnDivider.size
                    }
                    columnDivider.draw(c, left, top, right, bottom)
                    val needDrawLastRow = !mDrawInsideEachOfItem && totalSpanSize != spanCount && row > 1 && mProvider!!.isRowNeedDraw(row - 1)
                    if (needDrawLastRow) {
                        left = view.right + lp.rightMargin + transitionX
                        right = left + columnDivider.size
                        val rowDivider = mProvider!!.createRowDivider(row - 1)
                        if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                            top = bottom
                            bottom = top + rowDivider!!.size
                        } else {
                            top = bottom + rowDivider!!.size / 2f
                            bottom = top
                        }
                        rowDivider.draw(c, left, top, right, bottom)
                    }
                } else {
                    val columnDivider = createColumnDivider(column, true, false)
                    if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        right = view.left - lp.leftMargin + transitionX
                        left = right - columnDivider!!.size
                    } else {
                        left = view.left - lp.leftMargin + transitionX - columnDivider!!.size / 2f
                        right = left
                    }
                    if (mDrawInsideEachOfItem) {
                        left += columnDivider.size
                        right += columnDivider.size
                    }
                    columnDivider.draw(c, left, top, right, bottom)
                    val needDrawLastRow = !mDrawInsideEachOfItem && column > 0 && row > 1 && mProvider!!.isRowNeedDraw(row - 1)
                    if (needDrawLastRow) {
                        right = view.left - lp.leftMargin + transitionX
                        left = right - columnDivider.size
                        val rowDivider = mProvider!!.createRowDivider(row - 1)
                        if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                            bottom = top
                            top = top - rowDivider!!.size
                        } else {
                            top = top - rowDivider!!.size / 2f
                            bottom = top
                        }
                        rowDivider.draw(c, left, top, right, bottom)
                    }
                }
            }
            if (drawRow) {
                val rowDivider = createRowDivider(row, true, manager.reverseLayout)
                left = view.left - lp.leftMargin + transitionX
                right = view.right + lp.rightMargin + transitionX
                if (manager.reverseLayout) {
                    if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        top = view.bottom + lp.bottomMargin + transitionY
                        bottom = top + rowDivider!!.size
                    } else {
                        bottom = view.bottom + lp.bottomMargin + transitionY + rowDivider!!.size / 2f
                        top = bottom
                    }
                    if (mDrawInsideEachOfItem) {
                        top -= rowDivider.size
                        bottom -= rowDivider.size
                    }
                } else {
                    if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        bottom = view.top - lp.topMargin + transitionY
                        top = bottom - rowDivider!!.size
                    } else {
                        bottom = view.top - lp.topMargin + transitionY - rowDivider!!.size / 2f
                        top = bottom
                    }
                    if (mDrawInsideEachOfItem) {
                        top += rowDivider.size
                        bottom += rowDivider.size
                    }
                }
                rowDivider.draw(c, left, top, right, bottom)
            }
        }
    }

    override fun drawHorizontalOrientationDividers(c: Canvas?, parent: RecyclerView?, layoutManager: RecyclerView.LayoutManager?) {
        val manager = layoutManager as GridLayoutManager?
        val spanCount = manager!!.spanCount
        val childCount = parent!!.childCount
        val lookup = manager.spanSizeLookup
        for (i in 0 until childCount) {
            val view = parent.getChildAt(i)
            val lp = view.layoutParams as RecyclerView.LayoutParams
            val position = lp.viewAdapterPosition
            if (position < 0) continue
            val row = lookup.getSpanIndex(position, spanCount)
            val column = lookup.getSpanGroupIndex(position, spanCount)
            val totalSpanSize = getTotalSpanSizeByPosition(manager, position)
            val transitionX = view.translationX
            val transitionY = view.translationY
            val drawColumn = isColumnNeedDraw(column, totalSpanSize, false, manager.reverseLayout, spanCount)
            val drawRow = isRowNeedDraw(row, totalSpanSize, false, manager.reverseLayout, spanCount)
            var left: Float
            var top: Float
            var right: Float
            var bottom: Float
            if (drawColumn) {
                val columnDivider = createColumnDivider(column, false, manager.reverseLayout)
                top = view.top - lp.topMargin + transitionY
                bottom = view.bottom + lp.bottomMargin + transitionY
                if (manager.reverseLayout) {
                    if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        left = view.right + lp.rightMargin + transitionX
                        right = left + columnDivider!!.size
                    } else {
                        left = view.right + lp.rightMargin + transitionX + columnDivider!!.size / 2f
                        right = left
                    }
                    if (mDrawInsideEachOfItem) {
                        left -= columnDivider.size
                        right -= columnDivider.size
                    }
                } else {
                    if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        left = view.left - lp.leftMargin + transitionX
                        right = left - columnDivider!!.size
                    } else {
                        left = view.left - lp.leftMargin + transitionX - columnDivider!!.size / 2f
                        right = left
                    }
                    if (mDrawInsideEachOfItem) {
                        left += columnDivider.size
                        right += columnDivider.size
                    }
                }
                columnDivider.draw(c, left, top, right, bottom)
            }
            if (drawRow) {
                val rowDivider = createRowDivider(row, false, manager.reverseLayout)
                left = view.left - lp.leftMargin + transitionX
                right = view.right + lp.rightMargin + transitionX
                if (manager.reverseLayout) {
                    if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        top = view.bottom + lp.bottomMargin + transitionY
                        bottom = top + rowDivider!!.size
                    } else {
                        bottom = view.bottom + lp.bottomMargin + transitionY + rowDivider!!.size / 2f
                        top = bottom
                    }
                    if (mDrawInsideEachOfItem) {
                        top -= rowDivider.size
                        bottom -= rowDivider.size
                    }
                    rowDivider.draw(c, left, top, right, bottom)
                    val needDrawLastColumn = !mDrawInsideEachOfItem && column > 1 && totalSpanSize != spanCount && mProvider!!.isColumnNeedDraw(column - 1)
                    if (needDrawLastColumn) {
                        top = view.bottom + lp.bottomMargin + transitionY
                        bottom = top + rowDivider.size
                        val columnDivider = mProvider!!.createColumnDivider(column - 1)
                        if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                            left = right
                            right = left + columnDivider!!.size
                        } else {
                            left = right + columnDivider!!.size / 2f
                            right = left
                        }
                        columnDivider.draw(c, left, top, right, bottom)
                    }
                } else {
                    if (rowDivider!!.type === IDivider.TYPE_DRAWABLE) {
                        top = view.top - lp.topMargin + transitionY
                        bottom = top - rowDivider!!.size
                    } else {
                        bottom = view.top - lp.topMargin + transitionY - rowDivider!!.size / 2f
                        top = bottom
                    }
                    if (mDrawInsideEachOfItem) {
                        top += rowDivider.size
                        bottom += rowDivider.size
                    }
                    rowDivider.draw(c, left, top, right, bottom)
                    val needDrawLastColumn = !mDrawInsideEachOfItem && column > 0 && row != spanCount && mProvider!!.isColumnNeedDraw(column - 1)
                    if (needDrawLastColumn) {
                        top = view.top - lp.topMargin + transitionY
                        bottom = top - rowDivider.size
                        val columnDivider = mProvider!!.createColumnDivider(column - 1)
                        if (columnDivider!!.type === IDivider.TYPE_DRAWABLE) {
                            right = left
                            left = left - columnDivider!!.size
                            columnDivider.draw(c, left, top, right, bottom)
                        } else {
                            left = left - columnDivider!!.size / 2f
                            right = left
                            columnDivider.draw(c, left, top, right, bottom)
                        }
                    }
                }
            }
        }
    }

    private fun createColumnDivider(column: Int, vertical: Boolean, reverseLayout: Boolean): IDivider? {
        return if (vertical) {
            if (reverseLayout) mProvider!!.createColumnDivider(column) else mProvider!!.createColumnDivider(column - 1)
        } else {
            mProvider!!.createColumnDivider(column - 1)
        }
    }

    private fun createRowDivider(row: Int, vertical: Boolean, reverseLayout: Boolean): IDivider? {
        return if (vertical) {
            mProvider!!.createRowDivider(row - 1)
        } else {
            if (reverseLayout) {
                mProvider!!.createRowDivider(row)
            } else {
                mProvider!!.createRowDivider(row - 1)
            }
        }
    }

    private fun isColumnNeedDraw(column: Int, totalSpanSize: Int, vertical: Boolean, reverseLayout: Boolean, spanCount: Int): Boolean {
        return if (vertical) {
            if (reverseLayout) totalSpanSize != spanCount && mProvider!!.isColumnNeedDraw(column) else column > 0 && mProvider!!.isColumnNeedDraw(column - 1)
        } else {
            column > 0 && mProvider!!.isColumnNeedDraw(column - 1)
        }
    }

    private fun isRowNeedDraw(row: Int, totalSpanSize: Int, vertical: Boolean, reverseLayout: Boolean, spanCount: Int): Boolean {
        return if (vertical) {
            row > 0 && mProvider!!.isRowNeedDraw(row - 1)
        } else {
            if (reverseLayout) {
                totalSpanSize != spanCount && mProvider!!.isRowNeedDraw(row)
            } else {
                row > 0 && mProvider!!.isRowNeedDraw(row - 1)
            }
        }
    }

    /**
     * Gets the total number of spans by the position
     *
     * @param manager  The GridLayoutManager
     * @param position The position of Item
     * @return The total span size
     */
    private fun getTotalSpanSizeByPosition(manager: GridLayoutManager?, position: Int): Int {
        var spanTotalSize = 0
        val lookup = manager!!.spanSizeLookup
        val spanCount = manager.spanCount
        for (i in 0..position) {
            val spanLookup = lookup.getSpanSize(i)
            spanTotalSize = spanTotalSize + spanLookup
            if (spanTotalSize >= spanCount) {
                if (spanTotalSize % spanCount != 0) {
                    spanTotalSize = spanLookup
                } else {
                    if (spanTotalSize > spanCount) {
                        spanTotalSize = if (spanTotalSize % spanCount != 0) spanTotalSize % spanCount else spanCount
                    }
                }
            }
        }
        return spanTotalSize
    }

    class Builder(var mContext: Context) : IBuilder<Builder?, IGridProvider?> {
        var mProvider: IGridProvider? = null
        var mDrawInsideEachOfItem = false
        var mDrawOverTop = false
        override fun drawOverTop(overTop: Boolean): Builder {
            mDrawOverTop = overTop
            return this
        }

        override fun drawInsideEachOfItem(drawInsideEachOfItem: Boolean): Builder {
            mDrawInsideEachOfItem = drawInsideEachOfItem
            return this
        }

        override fun provider(provider: IGridProvider?): Builder {
            require(mProvider == null) { "You must set up the IGridProvider before " + "configuring the custom rules" }
            mProvider = provider
            return this
        }

        fun rowDivider(row: Int, divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setRowDivider(row, divider!!)
            return this
        }

        fun rowDivider(divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setAllRowDivider(divider!!)
            return this
        }

        fun columnDivider(column: Int, divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setColumnDivider(column, divider!!)
            return this
        }

        fun columnDivider(divider: IDivider?): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setAllColumnDivider(divider!!)
            return this
        }

        fun rowNeedDraw(row: Int, need: Boolean): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setRowNeedDraw(row, need)
            return this
        }

        fun columnNeedDraw(column: Int, need: Boolean): Builder {
            checkProvider()
            (mProvider as DefaultGridProvider?)!!.setColumnNeedDraw(column, need)
            return this
        }

        private fun checkProvider() {
            checkProviderIsNull()
            if (mProvider !is DefaultGridProvider) mProvider = DefaultGridProvider(mProvider!!)
        }

        private fun checkProviderIsNull() {
            if (mProvider == null) mProvider = DefaultGridProvider()
        }

        fun build(): GridItemDecoration {
            checkProviderIsNull()
            return GridItemDecoration(this)
        }

    }

    init {
        mDrawInsideEachOfItem = builder.mDrawInsideEachOfItem
        mDrawOverTop = builder.mDrawOverTop
        mProvider = builder.mProvider
    }
}