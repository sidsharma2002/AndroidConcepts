package com.example.androidconcepts.ui.viewGroup

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.androidconcepts.R


class NeoPopLayout constructor(
    context: Context,
    attributeSet: AttributeSet?
) : ConstraintLayout(context, attributeSet) {

    private val root: View
    private val contentContainer: CardView
    private val sideShadow: View
    private val bottomShadow: View

    init {
        root = LayoutInflater.from(context).inflate(R.layout.item_neopop_vg, this, true)
        contentContainer = root.findViewById(R.id.cv_content)
        sideShadow = root.findViewById(R.id.view_rightShadow)
        bottomShadow = root.findViewById(R.id.view_bottomShadow)

        isClickable = true
    }

    private var lastClicked = System.currentTimeMillis()

    override fun performClick(): Boolean {
        if (System.currentTimeMillis() - lastClicked < 300L) return false
        lastClicked = System.currentTimeMillis()

        animateOnClick()

        return super.performClick()
    }

    private fun animateOnClick() {


        val space = context.resources.getDimension(R.dimen.neopop_def_space)

        contentContainer.animate().translationXBy(space / 2).translationYBy(space / 2)
            .setDuration(100L)
            .withEndAction {
                // reset to initial pos
                contentContainer.animate().translationYBy(-(space / 2)).translationXBy(-(space / 2))
                    .setDuration(100L)
                    .start()
            }.start()

        sideShadow.animate().translationXBy(-space / 2).setDuration(100L).withEndAction {
            // reset to initial pos
            sideShadow.animate().translationXBy(space / 2).setDuration(100L).start()
        }.start()

        bottomShadow.animate().translationYBy(-space / 2).translationXBy(-space / 2)
            .setDuration(100L).withEndAction {
                // reset to initial pos
                bottomShadow.animate().translationYBy(space / 2).translationXBy(space / 2)
                    .setDuration(100L).start()
            }.start()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (contentContainer.childCount > 0) return // assuming the target view is already added.

        // detach user's target view from current pos in hierarchy and attach to content container.
        // child at 0th pos is "root".
        val view = getChildAt(1)
        removeView(view)

        (root.findViewById<CardView>(R.id.cv_content)).addView(view)
    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

}