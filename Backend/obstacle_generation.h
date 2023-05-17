#pragma once

#include <random>
#include <algorithm>
#include <iostream>

#include "agent.h"
#include "object.h"

void create_layer(int y_upper, std::vector<Object*> &new_layer, std::vector<Object*> &last, unsigned int id_array[], Agent *agent);
void print_objects(std::vector<Object*> obj);
int check_collisions_last_layer(std::vector<Object*> last, Object obj);
int enough_space_last_layer(std::vector<Object*> last, Object *obj, Agent *agent);