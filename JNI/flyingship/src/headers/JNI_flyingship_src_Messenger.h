/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class JNI_flyingship_src_Messenger */

#ifndef _Included_JNI_flyingship_src_Messenger
#define _Included_JNI_flyingship_src_Messenger
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     JNI_flyingship_src_Messenger
 * Method:    getMessageNative
 * Signature: (B[B)B
 */
JNIEXPORT jbyte JNICALL Java_JNI_flyingship_src_Messenger_getMessageNative
  (JNIEnv *, jobject, jbyte, jbyteArray);

/*
 * Class:     JNI_flyingship_src_Messenger
 * Method:    sendMessageNative
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_JNI_flyingship_src_Messenger_sendMessageNative
  (JNIEnv *, jobject, jbyteArray, jint);

#ifdef __cplusplus
}
#endif
#endif
