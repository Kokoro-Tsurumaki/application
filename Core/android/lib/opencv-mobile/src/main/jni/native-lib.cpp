#include <jni.h>
#include <string>

#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <android/log.h>
#include <android/bitmap.h>


extern "C" JNIEXPORT jstring JNICALL

Java_kokoro_mobile_opencv_NativeLib_helloWorld(JNIEnv *env, jobject) {
    std::string hello = "C++Text,调用测试，图片选择";
    return env->NewStringUTF(hello.c_str());
}


