
#include "object.h"

Object::Object(int posX, int posY, unsigned int size, unsigned int unique_id) : m_posX(posX), m_posY(posY), m_size(size), id(unique_id){}

unsigned int Object::distance(Object *other){
    return 
        (m_posX - other->m_posX)*(m_posX - other->m_posX) +
        (m_posY - other->m_posY)*(m_posY - other->m_posY) -
        (m_size + other->m_size)*(m_size + other->m_size);
}

bool Object::check_collision(Object *other){
    return distance(other);
}

void Object::change_pos_to(int posX, int posY){
    m_posX = posX;
    m_posY = posY;
}

void Object::change_size(unsigned int size){
    m_size = size;
}
