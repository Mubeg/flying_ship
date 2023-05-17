#include "backend.h"

int X_Agent = DEFAULT_SCREEN_WIDTH / 2;

JNIEXPORT void JNICALL Java_JNI_flyingship_src_Backend_run
    (JNIEnv *env, jobject obj)
{
    Backend * backend = new Backend();
    // Agent agent(0, DEFAULT_AGENT_SIZE, 7777);

    // Object *last[N_Elem_In_Layer];
    // Object *elem[N_Elem_In_Layer];
    // unsigned int id_last[N_Elem_In_Layer];
    // unsigned int id[N_Elem_In_Layer];

    // for (int i = 0; i < N_Elem_In_Layer; ++i) {
    //     id_last[i] = i;
    //     id[i] = i + N_Elem_In_Layer;
    // }

    // std::cout << "LAST" << std::endl;
    // create_layer(0, last, nullptr, id, &agent);
    // print_objects(last);
    // std::cout << std::endl;

    // std::cout << "NEW" << std::endl;
    // create_layer(LAYER_HEIGHT, elem, last, id_last, &agent);
    // print_objects(elem);
    // std::cout << std::endl;

    backend->run();

    delete backend;
}

void Backend::run(){

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
            case msg::Cursor:                                   //Нужен один int координата курсора по оси x
                X_Agent = (reinterpret_cast<int *> (msg.data))[0];
                break;
            default:
                break;

        }
        std::this_thread::sleep_for(BACKEND_SLEEP_TIME);
    } while(running);
}