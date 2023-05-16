#include "constants.h"
#pragma once

class Object{

    protected:
    unsigned int id;
    int m_posX = 0;
    int m_posY = 0;
    unsigned int m_size;

    public:
    Object(int posX, int posY, unsigned int size, unsigned int unique_id);
    void change_pos_to(int posX, int posY);
    void change_size(unsigned int size);
    bool check_collision(Object *other);
    unsigned int distance(Object *other);
};