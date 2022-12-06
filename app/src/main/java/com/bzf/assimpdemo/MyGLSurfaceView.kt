package com.bzf.assimpdemo

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log

/**
 *@author: baizf
 *@date: 2022/12/6
 */
class MyGLSurfaceView(context: Context, attrs: AttributeSet): GLSurfaceView(context, attrs){

    private val  mRenderer = MyGLRenderer()

    init {

        try {
            setEGLContextClientVersion(3)
            setRenderer(mRenderer)

            // calls onDrawFrame(...) continuously
            renderMode = RENDERMODE_CONTINUOUSLY
        } catch (e: Exception) {
            Log.e("MyGLSurfaceView", "Unable to create GLES context!", e)
        }
    }

}