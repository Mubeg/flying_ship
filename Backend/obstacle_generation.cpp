#include "obstacle_generation.h"

extern int X_Agent;

std::random_device rd;
std::mt19937 gen(rd());
 
int random(int low, int high) {
    
    std::uniform_int_distribution<> dist(low, high);
    return dist(gen);
}

void create_layer(int y_upper, std::vector<Object*> &new_layer, std::vector<Object*> &last, unsigned int id_array[], Agent *agent) {
    
    int number_of_tries = 0;
    int k = 0;
    int id = 0;

    for(; id < UNIQUE_IDS_N; ++id) {
        if (id_array[id] == 0) {
            for(int i = 0; i < N_Elem_In_Layer; ++i) {
                id_array[id + i] = 1;
            }
            break;
        }
    }

    for(int i = 0; i < N_Elem_In_Layer; ++i) {

        if (k != i) {
            number_of_tries = 0;
        }
        
        k = i;

        int x = random(MAX_SIZE, DEFAULT_SCREEN_WIDTH - MAX_SIZE);
        int y = random(y_upper, y_upper + MAX_SIZE);
        int size = random(MIN_SIZE, MAX_SIZE);

        Object *obj = new Object(x, y, size, id);

        if (last.empty() != true) {

            if (enough_space_last_layer(last, obj, agent) != 0) {
                
                number_of_tries += 1;
                i -= 1;
                delete obj;
                continue;
            }
        }


        int flag = 0;

        for (int j = 0; j < i; ++j) {

            if (new_layer[j] == nullptr) {
                continue;
            }
            flag += agent->not_enough_space(obj, new_layer[j]);
        }

        if (flag != 0) {
            
            number_of_tries += 1;
            i -= 1;
            delete obj;
            continue;
        }
        
        if (number_of_tries > MAX_TRIES) {
            new_layer[i] = nullptr;
        }
        else {
            new_layer[i] = obj;
        }
        ++id;
    }
}

int check_collisions_last_layer(std::vector<Object*> last, Object obj) {

    int flag = 0;

    for(int i = 0; i < N_Elem_In_Layer; ++i) {
        
        if (last[i] == nullptr) {
            continue;
        }

        flag += obj.check_collision(last[i]);
    }

    return flag;
}

int enough_space_last_layer(std::vector<Object*> last, Object *obj, Agent *agent) {

    int flag = 0;

    for(int i = 0; i < N_Elem_In_Layer; ++i) {
        
        if (last[i] == nullptr) {
            continue;
        }
        flag += agent->not_enough_space(obj, last[i]);
    }

    return flag;
}

void print_objects(std::vector<Object*> obj) {
    
    for (int i = 0; i < obj.size(); ++i) {

        if (obj[i] == nullptr) {
            
            std::cout << "NULPTR" << std::endl;
            continue;
        }

        obj[i]->print_obj();
    }
}