package com.example.moviesuniverse.presentation.screens.tabs.main

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MarginItemDecorator(
    @DimenRes private val verticalMargin: Int,
    @DimenRes private val horizontalMargin: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val vertical = parent.resources.getDimension(verticalMargin).toInt()
        val horizontal = parent.resources.getDimension(horizontalMargin).toInt()

        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    top = vertical
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    left = horizontal
                }
            } else {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    left = horizontal
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    top = vertical
                }
            }

            right = horizontal
            bottom = vertical
        }
    }
}