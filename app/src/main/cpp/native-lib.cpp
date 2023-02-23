#include <jni.h>
#include <android/log.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#define TAG "NativeLib"

using namespace std;
using namespace cv;

extern "C" {
void JNICALL
JNIEXPORT
Java_com_example_androidconcepts_opencv_learning1_OCVActivity1_adaptiveThresholdFromJNI(JNIEnv *env,
                                                                                        jobject thiz,
                                                                                        jlong matAddr) {
    // get Mat from raw address
    Mat &mat = *(Mat *) matAddr;
    cv::cvtColor(mat, mat, cv::COLORMAP_JET);
}
}