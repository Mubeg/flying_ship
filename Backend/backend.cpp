#include "backend.h"

int X_Agent = DEFAULT_SCREEN_WIDTH / 2;
bool is_paused = true;
bool is_running = true;

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
                is_paused = true;
                break;
            case msg::Resume:
                is_paused = false;
                break;
            case msg::Stop:
                is_running = false;
                running = false;
                break;
            case msg::StartGame:
                is_paused = false;
                break;
            case msg::UpdateCursor:                                   //Нужен один int координата курсора по оси x
                X_Agent = msg.data[0];
                break;
            default:
                break;

        }
        std::this_thread::sleep_for(BACKEND_SLEEP_TIME);
    } while(running);
}