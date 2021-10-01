package com.development.heavenplusone.util.image_utils

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.sqrt


object OnTouchZoomListener {

    @SuppressLint("ClickableViewAccessibility")
    fun AppCompatImageView.zoomOnTouch() {


// These matrices will be used to move and zoom image

// These matrices will be used to move and zoom image
        val matrix = Matrix()
        val savedMatrix = Matrix()

// We can be in one of these 3 states

// We can be in one of these 3 states
        val NONE = 0
        val DRAG = 1
        val ZOOM = 2
        var mode = NONE

// Remember some things for zooming

// Remember some things for zooming
        val start = PointF()
        val mid = PointF()
        var oldDist = 1f

        this.setOnTouchListener { v, event ->

            val view = v as AppCompatImageView
            view.scaleType = ImageView.ScaleType.MATRIX
            val scale: Float
            when (event.action and MotionEvent.ACTION_MASK) {

                MotionEvent.ACTION_DOWN -> { //first finger down only
                    savedMatrix.set(matrix)
                    start.set(event.x, event.y)
                    mode = DRAG
                }
                MotionEvent.ACTION_UP -> {  //first finger lifted
                }
                MotionEvent.ACTION_POINTER_UP -> {  //second finger lifted
                    mode = NONE
                }
                MotionEvent.ACTION_POINTER_DOWN -> {        // //second finger down
                    oldDist = spacing(event)
                    if (oldDist > 5f) {
                        savedMatrix.set(matrix)
                        midPoint(mid, event)
                        mode = ZOOM
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (mode == DRAG) { //movement of first finger
                        matrix.set(savedMatrix)
//                        if (view.left >= -392) {
                        matrix.postTranslate(event.x - start.x, event.y - start.y)
//                        }
                    } else if (mode == ZOOM) { //pinch zooming
                        val newDist = spacing(event)
                        if (newDist > 5f) {
                            matrix.set(savedMatrix)
                            scale =
                                newDist / oldDist       //thinking i need to play around with this value to limit it**
                            matrix.postScale(scale, scale, mid.x, mid.y)
                        }
                    }
                }

            }
            view.imageMatrix = matrix
            return@setOnTouchListener true
        }

    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }
}