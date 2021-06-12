package com.example.grocerylist.utility

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class ItemDragSwipeCallback(
    context: Context,
    backgroundColor: Int,
    drawable: Int,
    dragDirs: Int,
    swipeDirs: Int,
    private val mOnTouchListener: OnTouchListener
) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    private val mIcon: Drawable?
    private val mBackground: ColorDrawable

    interface OnTouchListener {
        fun onMove(
            recyclerView: RecyclerView?,
            viewHolder: RecyclerView.ViewHolder?,
            target: RecyclerView.ViewHolder?
        ): Boolean

        fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return mOnTouchListener.onMove(recyclerView, viewHolder, target)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mOnTouchListener.onSwiped(viewHolder, direction)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 25 //so mBackground is behind the rounded corners of itemView
        val iconMargin: Int = (itemView.getHeight() - mIcon!!.intrinsicHeight) / 2
        val iconTop: Int = itemView.getTop() + (itemView.getHeight() - mIcon.intrinsicHeight) / 2
        val iconBottom = iconTop + mIcon.intrinsicHeight
        if (dX > 0) { // Swiping to the right
            val iconLeft: Int = itemView.getLeft() + iconMargin
            val iconRight = iconLeft + mIcon.intrinsicWidth
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.getLeft(), itemView.getTop(),
                itemView.getLeft() + dX.toInt() + backgroundCornerOffset, itemView.getBottom()
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft: Int = itemView.getRight() - iconMargin - mIcon.intrinsicWidth
            val iconRight: Int = itemView.getRight() - iconMargin
            mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.getRight() + dX.toInt() - backgroundCornerOffset,
                itemView.getTop(), itemView.getRight(), itemView.getBottom()
            )
        } else { // view is unSwiped
            mIcon.setBounds(0, 0, 0, 0)
            mBackground.setBounds(0, 0, 0, 0)
        }
        mBackground.draw(c)
        mIcon.draw(c)
    }

    init {
        mIcon = ContextCompat.getDrawable(context, drawable)
        mBackground = ColorDrawable(context.getResources().getColor(backgroundColor))
    }
}
