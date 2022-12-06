package com.bzf.assimpdemo


import android.app.Activity
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.MotionEvent.INVALID_POINTER_ID
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.View.OnTouchListener
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.MotionEventCompat

/**
 *@author: baizf
 *@date: 2022/12/6
 */
class GestureClass(activity: Activity) {

    private val mTapScrollDetector: GestureDetectorCompat by lazy {
        GestureDetectorCompat(activity, MyTapScrollListener())
    }
    private var mScaleDetector: ScaleGestureDetector

    init {
        mScaleDetector = ScaleGestureDetector(
            activity.applicationContext,
            ScaleListener()
        )
    }


    external fun DoubleTapNative()
    external fun ScrollNative(
        distanceX: Float,
        distanceY: Float,
        positionX: Float,
        positionY: Float
    )

    external fun ScaleNative(scaleFactor: Float)
    external fun MoveNative(distanceX: Float, distanceY: Float)


    var INVALID_POINTER_ID = -100
    private var mTwoFingerPointerId = INVALID_POINTER_ID

    // this listener detects gesture of dragging with two fingers
    var TwoFingerGestureListener: OnTouchListener = object : OnTouchListener {
        private var mLastTouchX = 0f
        private var mLastTouchY = 0f
        override fun onTouch(v: View, event: MotionEvent): Boolean {

            // let the other detectors also consume the event
            mTapScrollDetector.onTouchEvent(event)
            mScaleDetector.onTouchEvent(event)
            val action: Int = MotionEventCompat.getActionMasked(event)
            when (action) {
                MotionEvent.ACTION_DOWN -> {}
                MotionEvent.ACTION_MOVE -> {

                    // track the drag only if two fingers are placed on screen
                    if (mTwoFingerPointerId != INVALID_POINTER_ID) {
                        val x: Float = MotionEventCompat.getX(event, mTwoFingerPointerId)
                        val y: Float = MotionEventCompat.getY(event, mTwoFingerPointerId)

                        // Calculate the distance moved
                        val dx = x - mLastTouchX
                        val dy = y - mLastTouchY

                        // Remember this touch position for the next move event
                        mLastTouchX = x
                        mLastTouchY = y
                        MoveNative(dx, dy)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    mTwoFingerPointerId = INVALID_POINTER_ID
                }
                MotionEvent.ACTION_CANCEL -> {
                    mTwoFingerPointerId = INVALID_POINTER_ID
                }
                MotionEvent.ACTION_POINTER_DOWN -> {

                    // detected two fingers, start the drag
                    mTwoFingerPointerId = MotionEventCompat.getActionIndex(event)
                    val x: Float = MotionEventCompat.getX(event, mTwoFingerPointerId)
                    val y: Float = MotionEventCompat.getY(event, mTwoFingerPointerId)

                    // Remember where we started (for dragging)
                    mLastTouchX = x
                    mLastTouchY = y
                }
                MotionEvent.ACTION_POINTER_UP -> {

                    // two fingers are not placed on screen anymore
                    mTwoFingerPointerId = INVALID_POINTER_ID
                }
            }
            return true
        }
    }

    // this class detects double-tap gesture and tracks the drag gesture by single finger
    inner class MyTapScrollListener : SimpleOnGestureListener() {
        override fun onDoubleTap(event: MotionEvent): Boolean {
            DoubleTapNative()
            return true
        }

        // function is called if user scrolls with one/two fingers
        // we ignore the call if two fingers are placed on screen
        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            if (mTwoFingerPointerId == INVALID_POINTER_ID) {
                ScrollNative(distanceX, distanceY, e2.x, e2.y)
            }
            return true
        }
    }

    // this class detects pinch and zoom gestures
    inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            ScaleNative(detector.scaleFactor)
            return true
        }
    }

}