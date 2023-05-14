
#include "constants.h"
#include "object.h"
#include "Agent.h"
#include <messenger.h>
#include <vector>
#include <thread>


class Environment{

    bool is_paused = false;
    bool is_running = true;
    unsigned int timer = 0;
    std::chrono::milliseconds time_tick_speed = DEFAULT_TIME_TICK_SPEED;
    int width = DEFAULT_SCREEN_WIDTH;
    int height = DEFAULT_SCREEN_HEIGHT;
    bool unique_id[UNIQUE_IDS_N] = {};
    std::vector<Object> objects;
    Messenger messenger;
    Agent agent;
    
    public:
    Environment(Messenger);
    void run();
    void process_message();
    void update_width_and_height(int new_width, int new_height);
    void update_tick_speed(std::chrono::milliseconds new_tick_speed);
    void pause();
    void resume();
    void stop();

};