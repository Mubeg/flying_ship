#pragma once

#include "constants.h"
#include "object.h"
#include "obstacle_generation.h"

#include "agent.h"
#include <messenger.h>
#include <vector>
#include <thread>
#include <ctime>
class Environment{

    bool is_paused = false;
    bool is_running = true;
    unsigned int timer = 0;
    std::chrono::milliseconds time_tick_speed = DEFAULT_TIME_TICK_SPEED;
    int width = DEFAULT_SCREEN_WIDTH;
    int height = DEFAULT_SCREEN_HEIGHT;
    unsigned int unique_id[UNIQUE_IDS_N] = {};
    Messenger messenger;
    Agent agent;
    std::vector<std::vector<Object *>> all_objects;

    
    public:

    Environment(Messenger);
    void run();
    void process_message();
    void update_width_and_height(int new_width, int new_height);
    void update_tick_speed(std::chrono::milliseconds new_tick_speed);
    void pause();
    void resume();
    void stop();
    void print_all_objects();
    void update_agent_pos(int new_posX);
    void create_array_to_send(int (&array_to_send)[DATA_LEN]);

    ~Environment() {
        for (int i = 0; i < all_objects.size(); ++i) {
            if (all_objects[i].empty() == true) {
                continue;
            }
            for (int j = 0; j < N_Elem_In_Layer; ++j) {
                //printf("Delete %p\n",all_objects[i][j]);
                delete all_objects[i][j];
            }
        }
    }

};