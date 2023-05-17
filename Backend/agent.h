#pragma once

#include "object.h"
#include <assert.h>

class Agent : public Object{

    public:
    Agent(int posX, unsigned int size, unsigned int unique_id) : Object(posX, AGENT_Y_PADDING, AGENT_SIZE, unique_id){};
    void change_pos_x(int PosX);
    int not_enough_space(Object* one, Object* two);
};
