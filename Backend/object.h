#pragma once

#include "constants.h"
#include <math.h>
#include <iostream>
#include <assert.h>

class Object{

    protected:
    unsigned int id;
    int m_posX = 0;
    int m_posY = 0;
    unsigned int m_size;

    public:
<<<<<<< HEAD
    Object(int posX, int posY, unsigned int size, unsigned int unique_id): m_posX(posX), m_posY(posY), m_size(size), id(unique_id){
        #ifdef DEBUG
        //printf("Creating object:\nx: %d\ny: %d\nsize: %d\nid: %d\n", m_posX, m_posY, m_size, id);
        #endif
    };
=======
    Object(int posX, int posY, unsigned int size, unsigned int unique_id): m_posX(posX), m_posY(posY), m_size(size), id(unique_id){};
>>>>>>> 925ed569b385d4f09827f96205dfac7806386214
    void change_pos_to(int posX, int posY);
    void change_pos_x(int posX);
    void change_pos_y(int posY);
    void change_size(unsigned int size);
    int check_collision(Object *other);
    int distance(Object *other);
    void print_obj();
    void sum_pos_y(int posY);
    int return_y();
    int return_x();
    int return_size();
};
