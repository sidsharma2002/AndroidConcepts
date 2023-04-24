package com.example.androidconcepts.ui.retroDesign

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
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
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        return super.performClick()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (contentContainer.childCount > 0) return // assuming the target view is already added.

        // detach user's target view from current pos in hierarchy and attach to content container.
        // child at 0th pos is "root".
        val view = getChildAt(1)
        removeView(view)

        (contentContainer).addView(view)

        val isWidthMatchParent =
            layoutParams.width == android.view.ViewGroup.LayoutParams.MATCH_PARENT

        if (isWidthMatchParent) {
            // if match parent then we need to modify some attributes.
            getChildAt(0).layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT

            (contentContainer.layoutParams as ConstraintLayout.LayoutParams).setMargins(
                /* left = */ 0,
                /* top = */ 0,
                /* right = */ resources.getDimension(R.dimen.neopop_def_space).toInt() - 5,
                /* bottom = */ 0
            )

            contentContainer.layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
            invalidate()
        }

    }

    private var animUpUnconsumed = false

    enum class STATE {
        PRESSING, PRESSED, RELEASING, RELEASED
    }

    private var currState: STATE = STATE.RELEASED

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {

                if (currState != STATE.RELEASED) return false

                if (System.currentTimeMillis() - lastClicked < (duration * 2) + /* offset */ 100L) return false
                lastClicked = System.currentTimeMillis()

                animateDown()
                return true
            }

            MotionEvent.ACTION_UP -> {

                if (currState == STATE.PRESSING) {
                    animUpUnconsumed = true
                    performClick()
                } else if (currState == STATE.PRESSED) {
                    animateUp()
                    performClick()
                }

                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.x !in 0f..width.toFloat() || event.y !in 0f..height.toFloat()) {

                    if (currState == STATE.PRESSING) {
                        animUpUnconsumed = true
                    } else if (currState == STATE.PRESSED) {
                        animateUp()
                    }

                    return true
                }
            }
        }

        return false
    }

    private val space = context.resources.getDimension(R.dimen.neopop_def_space)
    private val contentDisplacement = 2 * space / 3
    private val shadowDisplacement = space / 3

    private fun animateDown() {
        contentContainer.animate().translationXBy(contentDisplacement)
            .translationYBy(contentDisplacement)
            .setDuration(duration)
            .withStartAction {
                currState = STATE.PRESSING
            }
            .withEndAction {
                currState = STATE.PRESSED

                if (animUpUnconsumed)
                    animateUp()
            }.start()

        sideShadow.animate().translationXBy(-shadowDisplacement).translationYBy(-shadowDisplacement)
            .setDuration(duration)
            .start()

        bottomShadow.animate().translationYBy(-shadowDisplacement)
            .translationXBy(-shadowDisplacement)
            .setDuration(duration).start()
    }

    private fun animateUp() {
        contentContainer.animate().translationYBy(-(contentDisplacement))
            .translationXBy(-(contentDisplacement))
            .setDuration(duration)
            .withStartAction {
                currState = STATE.RELEASING
            }
            .withEndAction {
                currState = STATE.RELEASED
                animUpUnconsumed = false
            }
            .start()

        sideShadow.animate().translationXBy(shadowDisplacement)
            .translationYBy(shadowDisplacement).setDuration(duration)
            .start()

        bottomShadow.animate().translationYBy(shadowDisplacement)
            .translationXBy(shadowDisplacement)
            .setDuration(duration).start()
    }

    private val Number.toPx
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

}