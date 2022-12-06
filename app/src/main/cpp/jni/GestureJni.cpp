/**
 *@author: baizf
 *@date: 2022/12/6
*/
//


#include <jni.h>
#include "modelAssimp.h"
#include "myJNIHelper.h"

extern ModelAssimp *gAssimpObject;

extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_GestureClass_DoubleTapNative(JNIEnv *env, jobject thiz) {
    if (gAssimpObject == nullptr) {
        return;
    }
    gAssimpObject->DoubleTapAction();

}
extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_GestureClass_ScrollNative(JNIEnv *env, jobject instance,
                                                  jfloat distanceX, jfloat distanceY,
                                                  jfloat positionX, jfloat positionY) {
    if (gAssimpObject == NULL) {
        return;
    }
    // normalize movements on the screen wrt GL surface dimensions
    // invert dY to be consistent with GLES conventions
    float dX = (float) distanceX / gAssimpObject->GetScreenWidth();
    float dY = -(float) distanceY / gAssimpObject->GetScreenHeight();
    float posX = 2 * positionX / gAssimpObject->GetScreenWidth() - 1.;
    float posY = -2 * positionY / gAssimpObject->GetScreenHeight() + 1.;
    posX = fmax(-1., fmin(1., posX));
    posY = fmax(-1., fmin(1., posY));
    gAssimpObject->ScrollAction(dX, dY, posX, posY);

}
extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_GestureClass_ScaleNative(JNIEnv *env, jobject instance,
                                                 jfloat scaleFactor) {

    if (gAssimpObject == nullptr) {
        return;
    }
    gAssimpObject->ScaleAction((float) scaleFactor);

}
extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_GestureClass_MoveNative(JNIEnv *env, jobject instance,
                                                jfloat distanceX, jfloat distanceY) {

    if (gAssimpObject == NULL) {
        return;
    }

    // normalize movements on the screen wrt GL surface dimensions
    // invert dY to be consistent with GLES conventions
    float dX = distanceX / gAssimpObject->GetScreenWidth();
    float dY = -distanceY / gAssimpObject->GetScreenHeight();
    gAssimpObject->MoveAction(dX, dY);

}
