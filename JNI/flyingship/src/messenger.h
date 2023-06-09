#include <jni.h>
#include <stdio.h>
#include <vector>
#include <cstring>
#include <mutex>

#define DATA_LEN 100

#include "headers/JNI_flyingship_src_Messenger.h"


typedef struct __attribute__((packed)) Message
{
    /*
    type:
    0 - invalid
    1 - primary mesage
    2 - 9 - user-defined
    */
    char type;

    /*
    0 - invalid
    1 - backend
    2 - frontend
    */
    char receiver;

    /*
    0 - invalid
    1 - backend
    2 - frontend
    */
    char sender;

    char data[DATA_LEN];
} message_t;


class Messenger{

    std::vector<message_t> *messages_q;
    char my_id;
    public:
    Messenger(char);
    message_t get_message();
    void send_message(message_t msg);

};