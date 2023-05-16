
#include "environment.h"

Environment::Environment(Messenger messenger_e) : messenger(messenger_e), agent(0, DEFAULT_AGENT_SIZE, 0){unique_id[0] = true;}

void Environment::run(){
    while(is_running){
        if(is_paused){
            std::this_thread::sleep_for(ENV_PAUSE_SLEEP);
            continue;
        }
    }
}

void Environment::pause(){
    is_paused = true;
}

void Environment::resume(){
    is_paused = false;
}

void Environment::stop(){
    is_running = false;
}