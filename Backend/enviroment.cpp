#include "environment.h"

Environment::Environment(Messenger messenger_e) : messenger(messenger_e), agent(200, DEFAULT_AGENT_SIZE, 0) {
    unique_id[0] = true;
    std::vector<Object *> elem;
    all_objects.push_back(elem);
}

void Environment::run() {
    
    int delet_elements = 0;
    int stop = 0;
    int num_elem = 0;
    int new_elems = LAYER_HEIGHT;

    while(is_running){

        if(is_paused) {
            std::this_thread::sleep_for(ENV_PAUSE_SLEEP);
            continue;
        }

        std::vector<Object *>elem;
        for(int i = 0; i < N_Elem_In_Layer; i++) {
            elem.push_back(nullptr);
        }
        if (all_objects[0].empty() == true) {
            create_layer(-(MAX_SIZE + LAYER_HEIGHT), elem, all_objects[0], unique_id, &agent);
            all_objects[0] = elem;
            num_elem++;
        }
        else {

            for(int j = 0; j < all_objects.size(); ++j) {

                if (all_objects[j].empty() == true) {
                    continue;
                }

                for(int k = 0; k < N_Elem_In_Layer; ++k) {
                    
                    if (all_objects[j][k] == nullptr) {
                        delet_elements++;
                        continue;
                    }
                    else {

                        if (agent.check_collision(all_objects[j][k]) == 1) {
                            std::cout << "I crashed :(" << std::endl;
                            is_running = false;
                        }

                        all_objects[j][k]->sum_pos_y(SHIFT);
                        if ((all_objects[j][k]->return_y() - MAX_SIZE) > DEFAULT_SCREEN_HEIGHT) {
                            delet_elements++;
                        }
                    }
                }

                if (delet_elements == N_Elem_In_Layer) {
                    all_objects.erase(all_objects.begin() + j);
                    j--;
                }
                delet_elements = 0;
            }

            new_elems -= SHIFT;
            
            std::cout << std::endl << new_elems << std::endl;

            if (new_elems <= 0) {
                
                create_layer(-(MAX_SIZE + LAYER_HEIGHT), elem, all_objects[all_objects.size() - 1], unique_id, &agent);
                all_objects.push_back(elem);
                new_elems = LAYER_HEIGHT;
                num_elem++;
            }
        }

        // std::cout << "THERE ARE " << num_elem << " OF US NOW" << std::endl;
        // print_all_objects();
        // std::cout << std::endl;

        if (stop == 70) {
            break;
        }
        stop += 1;

        message_t msg = {};
        msg.type = msg::UpdateFrame;
        msg.receiver = msg::Frontend;
        create_array_to_send(msg.data);
        // std::cout << "ARRAY!" << std::endl;
        // for (int i = 0; i < 100; ++i) {
        //     std::cout << arr[i] << " ";
        // }
        std::cout << std::endl;
        messenger.send_message(msg);
        std::this_thread::sleep_for(ENV_PAUSE_SLEEP);
        
    }
}

void Environment::update_agent_pos(int new_posX) {
    agent.change_pos_x(new_posX);
}

void Environment::pause() {
    is_paused = true;
}

void Environment::resume() {
    is_paused = false;
}

void Environment::stop() {
    is_running = false;
}

void Environment::print_all_objects() {
    for (int i = 0; i < all_objects.size(); ++i) {
        std::cout << "I'm " << i << std::endl;
        print_objects(all_objects[i]);
        std::cout << std::endl;
    }
}

void Environment::create_array_to_send(int (&array_to_send)[DATA_LEN]) {
    
    int pos = 0;
    int num_elem = 0;

    array_to_send[0] = agent.return_x();
    array_to_send[1] = agent.return_y();
    array_to_send[2] = agent.return_size();

    for (int i = 0; i < all_objects.size(); ++i) {

        if (all_objects[i].empty() == true) {
            continue;
        }
        
        for(int j = 0; j < N_Elem_In_Layer; ++j) {
                    
            if (all_objects[i][j] == nullptr) {
                continue;
            }
            else {
                pos = 4 + (i * N_Elem_In_Layer + j) * 3;
                array_to_send[pos] = all_objects[i][j]->return_x();
                array_to_send[pos + 1] = all_objects[i][j]->return_y();
                array_to_send[pos + 2] = all_objects[i][j]->return_size();
                num_elem++;
            }
        }
    }

    array_to_send[3] = num_elem;
}