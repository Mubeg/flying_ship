
#include "object.h"

class Agent : public Object{

    public:
    Agent(int posX, unsigned int size, unsigned int unique_id);
    void change_pos_to(int PosX);
    bool enough_space(Object* one, Object* two);
};