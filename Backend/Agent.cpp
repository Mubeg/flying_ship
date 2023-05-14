
#include "Agent.h"

Agent::Agent(int posX, unsigned int size, unsigned int unique_id) : Object(posX, AGENT_Y_PADDING, size, unique_id){}

void Agent::change_pos_to(int posX){
    Object::change_pos_to(posX, AGENT_Y_PADDING);
}

bool Agent::enough_space(Object* one, Object* two){
    return one->distance(two) <= 2*(m_size + (unsigned int)(m_size/10));
}