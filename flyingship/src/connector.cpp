#include <jni.h>
#include <stdio.h>

#include "headers/flyingship_src_FlyingShip.h"

JNIEXPORT void JNICALL Java_flyingship_src_FlyingShip_nativePrint
        (JNIEnv *env, jobject obj)
{

    printf("\nПогладь КОТА из C\n");

}