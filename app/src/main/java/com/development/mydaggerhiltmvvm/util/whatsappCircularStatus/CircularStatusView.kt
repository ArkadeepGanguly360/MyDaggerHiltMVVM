package com.development.mydaggerhiltmvvm.util.whatsappCircularStatus

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.util.SparseIntArray
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.development.mydaggerhiltmvvm.R


class CircularStatusView : View {
    private var radius = 0f
    private var portionWidth = DEFAULT_PORTION_WIDTH
    private var portionSpacing = DEFAULT_PORTION_SPACING
    private var portionColor = DEFAULT_COLOR
    private var portionsCount = DEFAULT_PORTIONS_COUNT
    private val mBorderRect = RectF()
    private var paint: Paint? = null
    private val portionToUpdateMap = SparseIntArray()

    constructor(context: Context) : super(context) {
        init(context, null, -1)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircularStatusView, defStyle, 0)
        if (a != null) {
            portionColor = a.getColor(R.styleable.CircularStatusView_portion_color, DEFAULT_COLOR)
            portionWidth = a.getDimensionPixelSize(
                R.styleable.CircularStatusView_portion_width,
                DEFAULT_PORTION_WIDTH.toInt()
            ).toFloat()
            portionSpacing = a.getDimensionPixelSize(
                R.styleable.CircularStatusView_portion_spacing,
                DEFAULT_PORTION_SPACING
            )
            portionsCount = a.getInteger(
                R.styleable.CircularStatusView_portions_count,
                DEFAULT_PORTIONS_COUNT.toInt()
            ).toFloat()
            a.recycle()
        }
        paint = getPaint()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, -1)
    }

    constructor(
        context: Context,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBorderRect.set(calculateBounds())
        radius = Math.min(
            (mBorderRect.height() - portionWidth) / 2.0f,
            (mBorderRect.width() - portionWidth) / 2.0f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = radius
        val center_x = mBorderRect.centerX()
        val center_y = mBorderRect.centerY()
        val oval = getOval(radius, center_x, center_y)
        val degree = 360 / portionsCount
        val percent = 100 / portionsCount
        var i = 0
        while (i < portionsCount) {
            paint!!.color = getPaintColorForIndex(i)
            val startAngle = START_DEGREE + degree * i
            canvas.drawArc(
                oval, spacing / 2 + startAngle, getProgressAngle(percent) - spacing, false,
                paint!!
            )
            i++
        }
    }

    private fun getPaintColorForIndex(i: Int): Int {
        return if (portionToUpdateMap.indexOfKey(i) >= 0) { //if key is exists
            portionToUpdateMap[i]
        } else {
            portionColor
        }
    }

    @NonNull
    private fun getOval(radius: Float, center_x: Float, center_y: Float): RectF {
        val oval = RectF()
        oval[center_x - radius, center_y - radius, center_x + radius] = center_y + radius
        return oval
    }

    @NonNull
    private fun getPaint(): Paint {
        val paint = Paint()
        paint.color = portionColor
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true
        paint.strokeWidth = portionWidth
        paint.strokeCap = Paint.Cap.ROUND
        return paint
    }

    private val spacing: Int
        private get() = if (portionsCount == 1f) 0 else portionSpacing

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = Math.min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f
        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun getProgressAngle(percent: Float): Float {
        return percent / 100.toFloat() * 360
    }

    fun setPortionsCount(portionsCount: Int) {
        this.portionsCount = portionsCount.toFloat()
    }

    fun setPortionSpacing(spacing: Int) {
        portionSpacing = spacing
    }

    fun setPortionWidth(portionWidth: Float) {
        this.portionWidth = portionWidth
    }

    fun setCustomPaint(paint: Paint?) {
        this.paint = paint
    }

    fun setPortionsColor(color: Int) {
        portionColor = color
        portionToUpdateMap.clear()
        invalidate()
    }

    fun setPortionColorForIndex(index: Int, color: Int) {
        try {
            require(index <= portionsCount - 1) { "Index is Bigger than the count!" }
            Log.d("3llomi", "adding index to map $index")
            portionToUpdateMap.put(index, color)
            invalidate()
        }
        catch (e: Exception){
            e.printStackTrace()
        }

    }

    companion object {
        private const val DEFAULT_PORTION_WIDTH = 10f
        private const val DEFAULT_PORTION_SPACING = 5
        private val DEFAULT_COLOR = Color.parseColor("#D81B60")
        private const val DEFAULT_PORTIONS_COUNT = 1f
        private const val START_DEGREE = -90f
    }
}
