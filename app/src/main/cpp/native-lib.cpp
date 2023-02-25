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

    Point2f srcTri[3];
    srcTri[0] = Point2f(0.f, 0.f);
    srcTri[1] = Point2f(mat.cols - 1.f, 0.f);
    srcTri[2] = Point2f(0.f, mat.rows - 1.f);

    Point2f dstTri[3];
    dstTri[0] = Point2f(0.f, mat.rows * 0.5f);
    dstTri[1] = Point2f(mat.cols * 0.85f, mat.rows * 0.25f);
    dstTri[2] = Point2f(mat.cols * 0.15f, mat.rows * 0.7f);

    Mat warp_mat = getAffineTransform(srcTri, dstTri);
    Mat warp_dst = Mat::zeros(mat.rows, mat.cols, mat.type());
    warpAffine(mat, mat, warp_mat, mat.size());
}
}