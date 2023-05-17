#include "backend.h"

int X_Agent = DEFAULT_SCREEN_WIDTH / 2;

JNIEXPORT void JNICALL Java_JNI_flyingship_src_Backend_run
    (JNIEnv *env, jobject obj)
{
    Backend * backend = new Backend();
    backend->run();
    delete backend;
}

void Backend::run(){

    message_t msg = {};
    bool running = true;
    do{
        msg = messenger.get_message();
        switch(msg.type){
            case msg::ReCheckin:
                msg.type = msg::Checkin;
                msg.receiver = msg.sender;
                messenger.send_message(msg);
                break;
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
            case msg::UpdateCursor:                                   //Нужен один int координата курсора по оси x
                env.update_agent_pos(msg.data[0]);
                break;
            default:
                break;

        }
        std::this_thread::sleep_for(BACKEND_SLEEP_TIME);
    } while(running);
}