#include "object.h"

int Object::distance(Object *other){
    
    assert(other != nullptr);
    return 
        sqrt((m_posX - other->m_posX)*(m_posX - other->m_posX) +
        (m_posY - other->m_posY)*(m_posY - other->m_posY)) -
        (m_size + other->m_size);
}

int Object::check_collision(Object *other){
    
    if (distance(other) < 0) {
        return 1;
    }
    return 0;
}

void Object::change_pos_to(int posX, int posY){
    m_posX = posX;
    m_posY = posY;
}

void Object::change_pos_x(int posX){
    m_posX = posX;
}

void Object::change_pos_y(int posY){
    m_posY = posY;
}

void Object::sum_pos_y(int posY){
    m_posY += posY;
}

void Object::change_size(unsigned int size){
    m_size = size;
}

int Object::return_y(){
    return m_posY;
}

int Object::return_x(){
    return m_posX;
}

int Object::return_size(){
    return m_size;
}

void Object::print_obj(){
    std::cout << "x: " << m_posX << " , y: " << m_posY << " , size: " << m_size << ", id: " << id <<std::endl;
}
