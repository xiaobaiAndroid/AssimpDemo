/**
 *@author: baizf
 *@date: 2022/12/6
*/
//


#include <jni.h>

#include "modelAssimp.h"

extern ModelAssimp *gAssimpObject;

extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_MyGLRenderer_DrawFrameNative(JNIEnv *env,
                                                     jobject instance) {

    if (gAssimpObject == nullptr) {
        return;
    }
    gAssimpObject->Render();

}
extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_MyGLRenderer_SurfaceCreatedNative(JNIEnv *env,
                                                          jobject instance) {

    if (gAssimpObject == NULL) {
        return;
    }
    gAssimpObject->PerformGLInits();

}


extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_MyGLRenderer_SurfaceChangedNative(JNIEnv *env,
                                                          jobject instance,
                                                          jint width,
                                                          jint height) {

    if (gAssimpObject == nullptr) {
        return;
    }
    gAssimpObject->SetViewport(width, height);

}