#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_dyy_newtest_jni_JniTest_getJniString(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_dyy_newtest_jni_JniTest_add(JNIEnv *env, jclass type, jint i, jint j) {

    return i+j;

}