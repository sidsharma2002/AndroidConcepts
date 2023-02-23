package com.example.androidconcepts.opencv.learning1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.androidconcepts.R
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import kotlin.concurrent.thread


class OCVActivity1 : AppCompatActivity() {

    private var cameraView: CameraBridgeViewBase? = null

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    Log.i("opencv", "OpenCV loaded successfully")
                    cameraView?.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    @Volatile
    private var blurR: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocvactivity1)
        load()

        // Permissions for Android 6+
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )


        cameraView = findViewById(R.id.javaCamera)
        cameraView!!.visibility = CameraBridgeViewBase.VISIBLE

        cameraView!!.setCvCameraViewListener(object : CvCameraViewListener {
            override fun onCameraViewStarted(width: Int, height: Int) {

            }

            override fun onCameraViewStopped() {

            }

            override fun onCameraFrame(inputFrame: Mat?): Mat {
                // get current camera frame as OpenCV Mat object
                val mat = inputFrame

                // native call to process current camera frame
                try {
                    if (mat != null)
                        Imgproc.blur(mat, mat, Size((blurR).toDouble(), (blurR).toDouble()))
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // return processed frame for live preview
                return mat ?: Mat()
            }
        })
    }

    private external fun adaptiveThresholdFromJNI(matAddr: Long)

    private var mOpenCvCameraView: CameraBridgeViewBase? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraView!!.setCameraPermissionGranted()
                    cameraView!!.enableView()
                } else {
                    val message = "Camera permission was not granted"
                    Log.e(TAG, message)
                }
            }
            else -> {
                Log.e(TAG, "Unexpected permission request")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mOpenCvCameraView != null)
            mOpenCvCameraView!!.disableView()
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOpenCvCameraView != null)
            mOpenCvCameraView!!.disableView()
    }

    companion object {
        fun load() {
            System.loadLibrary("native-lib")
        }

        private const val TAG = "MainActivity"
        private const val CAMERA_PERMISSION_REQUEST = 1
    }
}