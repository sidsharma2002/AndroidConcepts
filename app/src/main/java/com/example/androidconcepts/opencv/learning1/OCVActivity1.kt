package com.example.androidconcepts.opencv.learning1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.androidconcepts.R
import com.google.android.material.slider.Slider
import com.google.android.material.slider.Slider.OnSliderTouchListener
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class OCVActivity1 : AppCompatActivity() {

    private var cameraView: CameraBridgeViewBase? = null

    private var mValue: Int = 0
    private lateinit var slider: Slider
    private lateinit var faceDetector: CascadeClassifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocvactivity1)
        load()

        slider = findViewById(R.id.slider)

        slider.addOnSliderTouchListener(object : OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                mValue = slider.value.toInt() * 100
            }

            override fun onStopTrackingTouch(slider: Slider) {
            }
        })

        // Permissions for Android 6+
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST
        )


        cameraView = findViewById(R.id.javaCamera)
        cameraView!!.visibility = CameraBridgeViewBase.VISIBLE

        cameraView!!.setCvCameraViewListener(object : CvCameraViewListener {

            override fun onCameraFrame(inputFrame: Mat?): Mat {
                // get current camera frame as OpenCV Mat object
                val mat = inputFrame ?: return Mat()

                // convert to grayscale
                //Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)

                // rotate to portrait mode
                val matRot = Imgproc.getRotationMatrix2D(Point(mat.cols() /2.0 , mat.rows()/2.0), -90.0, 1.0)
                Imgproc.warpAffine(mat, matRot, matRot, mat.size())

                // main algorithm
                // adaptiveThresholdFromJNI(mat.nativeObjAddr)

                val matOfRect = MatOfRect()

                if (::faceDetector.isInitialized) {

                    faceDetector.detectMultiScale(
                        /* image = */ matRot,
                        /* objects = */ matOfRect,
                        /* scaleFactor = */ 1.5,
                        /* minNeighbors = */ 5
                    )

                    if (matOfRect.size(0) > 0)
                        Log.d(TAG, "face detection ${matOfRect.size()}")
                } else {
                    Log.d(TAG, "no detector initialized")
                }

                // return processed frame for live preview
                return matRot
            }

            override fun onCameraViewStarted(width: Int, height: Int) {}
            override fun onCameraViewStopped() {}
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

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    Log.i("opencv", "OpenCV loaded successfully")

                    try {
                        val inputStream: InputStream = resources.openRawResource(R.raw.haarcascade_frontalface_alt)
                        val cascadeDir: File = getDir("cascade", Context.MODE_PRIVATE)
                        val mCascadeFile = File(cascadeDir, "haarcascade_frontalface_alt.xml")
                        val os = FileOutputStream(mCascadeFile)
                        val buffer = ByteArray(4096)
                        var bytesRead: Int

                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            os.write(buffer, 0, bytesRead)
                        }

                        inputStream.close()
                        os.close()

                        faceDetector = CascadeClassifier(mCascadeFile.absolutePath)

                        if (faceDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier")
                        } else Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.absolutePath)

                        cascadeDir.delete()

                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.e(TAG, "Failed to load cascade. Exception thrown: $e")
                    }

                    cameraView?.enableView()
                }

                else -> {
                    super.onManagerConnected(status)
                }
            }
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