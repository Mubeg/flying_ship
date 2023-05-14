
#include "backend.h"

JNIEXPORT void JNICALL Java_JNI_flyingship_src_Backend_run
    (JNIEnv *env, jobject obj)
{
    Backend backend();
}


Backend::Backend() : messenger(msg::Backend), env(messenger), env_thread(env_run)
{
    message_t msg = {};
    bool running = true;
    do{
        msg = messenger.get_message();
        switch(msg.type){
            case msg::Bad:
                break;
            case msg::Pause:
                env.pause();
                break;
            case msg::Resume:
                env.resume();
                break;
            case msg::Stop:
                env.stop();
                running = false;
                break;
            default:
                break;

        }
        std::this_thread::sleep_for(BACKEND_SLEEP_TIME);
    } while(running);
}

void Backend::env_run(){
    env.run();
}