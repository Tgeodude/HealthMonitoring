package com.example.healthmonitoring

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircleProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Int = 0
    private val paintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = ContextCompat.getColor(context, R.color.colorAccent)
        strokeCap = Paint.Cap.ROUND
    }
    private val paintProgress: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = ContextCompat.getColor(context, R.color.black)
        strokeCap = Paint.Cap.ROUND
    }

    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val diameter = Math.min(width, height) - 20
        rect.set(10f, 10f, diameter + 10f, diameter + 10f)

        canvas.drawArc(rect, 0f, 360f, false, paintBackground)

        val sweepAngle = (360 * progress / 100).toFloat()
        canvas.drawArc(rect, -90f, -sweepAngle, false, paintProgress)
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }
}