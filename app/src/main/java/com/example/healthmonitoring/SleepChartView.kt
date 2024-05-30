package com.example.healthmonitoring

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.DashPathEffect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

data class SleepSegment(val duration: Float, val stage: SleepStage)

enum class SleepStage(val value: Int) {
    DEEP(0),
    LIGHT(1),
    REM(2),
    AWAKE(3)
}

class SleepChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.black)
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val paintBackground = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.color_card_sleep)
        style = Paint.Style.FILL
    }
    private val paintDashedLine = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.dashed_line)
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }
    private val path = Path()
    private var segments: List<SleepSegment> = emptyList()

    private val verticalPadding = 16f // Отступы сверху и снизу

    fun setSegments(segments: List<SleepSegment>) {
        this.segments = segments
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPaint(paintBackground)

        if (segments.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat() - verticalPadding * 2
        val totalDuration = segments.sumByDouble { it.duration.toDouble() }.toFloat()
        val segmentWidth = width / totalDuration

        val numberOfZones = 4
        for (i in 0 until numberOfZones) {
            val y = i * (height / (numberOfZones - 1)) + verticalPadding
            canvas.drawLine(0f, y, width, y, paintDashedLine)
        }

        var currentX = 0f

        path.reset()
        var lastY = height - (segments[0].stage.value * (height / (numberOfZones - 1))) + verticalPadding
        path.moveTo(currentX, lastY)

        for (i in 1 until segments.size) {
            val segment = segments[i]
            val nextX = currentX + segmentWidth * segment.duration
            val nextY = height - (segment.stage.value * (height / (numberOfZones - 1))) + verticalPadding
            val midX = (currentX + nextX) / 2

            path.cubicTo(midX, lastY, midX, nextY, nextX, nextY)

            currentX = nextX
            lastY = nextY
        }

        val finalY = if (segments.last().stage == SleepStage.AWAKE) {
            verticalPadding
        } else {
            height + verticalPadding
        }

        path.cubicTo(currentX, lastY, (currentX + width) / 2, finalY, width, finalY)

        canvas.drawPath(path, paintLine)
    }
}