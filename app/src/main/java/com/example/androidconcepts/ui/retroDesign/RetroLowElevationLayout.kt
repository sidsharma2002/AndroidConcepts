package com.example.androidconcepts.ui.retroDesign

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidconcepts.R

class RetroLowElevationLayout constructor(
    context: Context,
    attributeSet: AttributeSet?
) : CardView(context, attributeSet) {

    private val root: View
    private val contentContainer: CardView

    init {
        root = LayoutInflater.from(context).inflate(R.layout.layout_retro_low_elev, this, true)
        contentContainer = root.findViewById(R.id.cv_content)

        cardElevation = 0f
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (contentContainer.childCount > 0) return // assuming the target view is already added.

        if (getChildAt(1) != null) {

            // detach user's target view from current pos in hierarchy and attach to content container.
            // child at 0th pos is "root".

            val view = getChildAt(1)
            removeView(view)
            (contentContainer).addView(view)
        }

        val isWidthMatchParent =
            layoutParams.width == android.view.ViewGroup.LayoutParams.MATCH_PARENT

        if (isWidthMatchParent) {
            // if match parent then we need to modify some attributes.
            getChildAt(0).layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
            (contentContainer.layoutParams as ConstraintLayout.LayoutParams).setMargins(
                0,
                0,
                resources.getDimension(R.dimen.neopop_def_space).toInt() - 5,
                0
            )
            contentContainer.layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
            invalidate()
        }

    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

}