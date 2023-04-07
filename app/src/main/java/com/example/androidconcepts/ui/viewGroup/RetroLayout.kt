package com.example.androidconcepts.ui.viewGroup

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.androidconcepts.R


class RetroLayout constructor(
    context: Context,
    attributeSet: AttributeSet?
) : CardView(context, attributeSet) {

    private val root: View
    private val contentContainer: CardView
    private val sideShadow: View
    private val bottomShadow: View

    init {
        root = LayoutInflater.from(context).inflate(R.layout.layout_retro_default, this, true)
        contentContainer = root.findViewById(R.id.cv_content)
        sideShadow = root.findViewById(R.id.view_rightShadow)
        bottomShadow = root.findViewById(R.id.view_bottomShadow)

        cardElevation = 0f
        isClickable = true
    }

    private var lastClicked = System.currentTimeMillis()

    private val duration = 90L

    override fun performClick(): Boolean {
        if (System.currentTimeMillis() - lastClicked < (duration * 2) + 100L) return false
        lastClicked = System.currentTimeMillis()

        animateOnClick()

        return super.performClick()
    }

    private fun animateOnClick() {

        val space = context.resources.getDimension(R.dimen.neopop_def_space)
        val contentDisplacement = 2 * space / 3
        val shadowDisplacement = space / 3

        contentContainer.animate().translationXBy(contentDisplacement)
            .translationYBy(contentDisplacement)
            .setDuration(duration)
            .withEndAction {

                // reset to initial pos
                contentContainer.animate().translationYBy(-(contentDisplacement))
                    .translationXBy(-(contentDisplacement))
                    .setDuration(duration)
                    .start()

            }.start()


        sideShadow.animate().translationXBy(-shadowDisplacement).translationYBy(-shadowDisplacement)
            .setDuration(duration)
            .withEndAction {

                // reset to initial pos
                sideShadow.animate().translationXBy(shadowDisplacement)
                    .translationYBy(shadowDisplacement).setDuration(duration)
                    .start()

            }.start()

        bottomShadow.animate().translationYBy(-shadowDisplacement)
            .translationXBy(-shadowDisplacement)
            .setDuration(duration).withEndAction {

                // reset to initial pos
                bottomShadow.animate().translationYBy(shadowDisplacement)
                    .translationXBy(shadowDisplacement)
                    .setDuration(duration).start()

            }.start()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (contentContainer.childCount > 0) return // assuming the target view is already added.

        // detach user's target view from current pos in hierarchy and attach to content container.
        // child at 0th pos is "root".
        val view = getChildAt(1)
        removeView(view)

        (contentContainer).addView(view)

        val isMatchParentWidth =
            layoutParams.width == android.view.ViewGroup.LayoutParams.MATCH_PARENT

        if (isMatchParentWidth) {
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