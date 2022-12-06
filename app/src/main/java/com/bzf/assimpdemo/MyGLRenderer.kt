package com.bzf.assimpdemo

import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *@author: baizf
 *@date: 2022/12/6
 */
class MyGLRenderer: GLSurfaceView.Renderer {

    external fun DrawFrameNative()
    external fun SurfaceCreatedNative()
    external fun SurfaceChangedNative(width: Int, height: Int)

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {

        SurfaceCreatedNative();
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        SurfaceChangedNative(width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        DrawFrameNative()
    }

}