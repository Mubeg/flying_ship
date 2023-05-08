
#include "backend.h"

JNIEXPORT void JNICALL Java_JNI_flyingship_src_Backend_run
    (JNIEnv *env, jobject obj)
{
    Backend backend{};
}


Backend::Backend() : messenger(1) //messenger's id for backend is 1
{
    message_t msg = {};
    msg.type = 1;
    msg.receiver = 2;
    strcpy(msg.data, "Hello, GUI!!!");
    messenger.send_message(msg);
    
    do{
        msg = messenger.get_message();
    } while(msg.type == 0); //while no messages availible

    printf("Received message: %s\n", msg.data);
}