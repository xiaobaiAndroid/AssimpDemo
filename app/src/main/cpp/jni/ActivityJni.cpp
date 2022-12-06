/**
 *@author: baizf
 *@date: 2022/12/6
*/
//

#include <jni.h>
#include <string>
#include "modelAssimp.h"
#include "myJNIHelper.h"

// global pointer is used in JNI calls to reference to same object of type Cube
ModelAssimp *gAssimpObject =NULL;

// global pointer to instance of MyJNIHelper that is used to read from assets
MyJNIHelper * gHelperObject=NULL;


extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_MainActivity_CreateObjectNative(JNIEnv *env, jobject thiz,
                                                        jobject asset_manager,
                                                        jstring path_to_internal_dir) {
    gHelperObject = new MyJNIHelper(env, thiz, asset_manager, path_to_internal_dir);
    gAssimpObject = new ModelAssimp();

}
extern "C"
JNIEXPORT void JNICALL
Java_com_bzf_assimpdemo_MainActivity_DeleteObjectNative(JNIEnv *env, jobject thiz) {

    if (gAssimpObject != nullptr) {
        delete gAssimpObject;
    }
    gAssimpObject = nullptr;

    if (gHelperObject != nullptr) {
        delete gHelperObject;
    }
    gHelperObject = nullptr;

}