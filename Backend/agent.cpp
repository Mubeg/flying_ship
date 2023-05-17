
#include "agent.h"

void Agent::change_pos_x(int posX){
    Object::change_pos_x(posX);
}

int Agent::not_enough_space(Object* one, Object* two){

    assert(one != nullptr);
    assert(two != nullptr);

    if (one->distance(two) <= (int)(2*(m_size + (int)(m_size/10)))) {
        return 1;
    }

    return 0;
}