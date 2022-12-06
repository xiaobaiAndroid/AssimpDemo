package com.bzf.assimpdemo

import android.content.res.AssetManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bzf.assimpdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private external fun CreateObjectNative(assetManager: AssetManager, pathToInternalDir: String)
    private external fun DeleteObjectNative()
    lateinit var mGestureObject: GestureClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assetManager = assets
        val pathToInternalDir = filesDir.absolutePath

        CreateObjectNative(assetManager, pathToInternalDir)

        mGestureObject = GestureClass(this)

        binding.glSurfaceView.setOnTouchListener(mGestureObject.TwoFingerGestureListener)
    }


    override fun onResume() {
        super.onResume()

        binding.glSurfaceView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.glSurfaceView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        // We are exiting the activity, let's delete the native objects
        DeleteObjectNative()
    }


    companion object {
        // Used to load the 'assimpdemo' library on application startup.
        init {
            System.loadLibrary("assimpdemo")
        }
    }
}