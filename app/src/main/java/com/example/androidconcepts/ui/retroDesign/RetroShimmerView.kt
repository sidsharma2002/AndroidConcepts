package com.example.androidconcepts.ui.retroDesign

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd

class RetroShimmerView constructor(
    context: Context
) : View(context) {

    private var dx = 0f
    private var shimmerWidth = 0f
    private var isAnimInProgress = false

    fun startAnim(widthProvided: Float) {

        if (isAnimInProgress) return

        isAnimInProgress = true
        shimmerWidth = widthProvided / 10 // 10 % of view

        dx = 0f

        ValueAnimator.ofFloat(0f - shimmerWidth, widthProvided).setDuration(1000L).apply {

            this.interpolator = AccelerateDecelerateInterpolator()

            this.addUpdateListener {
                dx = it.animatedValue as Float
                invalidate()
            }

            this.doOnEnd {
                isAnimInProgress = false
            }

            this.start()
        }
    }

    private val rectF = RectF(0f, 0f, 0f, 0f)
    private val paint = Paint().apply {
        color = Color.WHITE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        rectF.left = left + dx
        rectF.top = top.toFloat()
        rectF.right = left + dx + shimmerWidth
        rectF.bottom = top + height.toFloat()

        canvas?.drawRect(rectF, paint)
    }

}