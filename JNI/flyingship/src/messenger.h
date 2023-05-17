#pragma once
#include <jni.h>
#include <stdio.h>
#include <vector>
#include <cstring>
#include <mutex>

#define DATA_LEN 100
typedef int Elem_t;

#include "headers/JNI_flyingship_src_Messenger.h"

namespace msg{

    enum SenderIds{
        Bad,
        Overseer,
        Backend,
        Frontend,
        Database,
        Logger,
        MessengingSystem
    };

    enum MessagesTypes {
        BadType,
        RequestSettings,
        SettingsUpdate,
        Pause,
        Stop,
        Resume,
        UpdateFrame,
        Checkin,
        ReCheckin,
        RequestInfo,
        SendInfo,
        End,
        StartGame,
        PlayerLogIn,
        GameOver,
        UpdateCursor
    };

}

    /*
    Message structure, hold it's 
    char: type
    char: receiver
    char: sender
    char[DATA_LEN]: data
    */
    typedef struct __attribute__((packed)) Message
    {
        /*
        type:
        0 - invalid
        1 - primary mesage
        2 - 9 - user-defined
        */
        Elem_t type;

        /*
        0 - invalid
        1 - backend
        2 - frontend
        */
        Elem_t receiver;

        /*
        0 - invalid
        1 - backend
        2 - frontend
        */
        Elem_t sender;

        Elem_t data[DATA_LEN];
    } message_t;


    class Messenger{

        std::vector<message_t> *messages_q;
        Elem_t my_id;

        public:
        Messenger(Elem_t);
        message_t get_message();
        void send_message(message_t msg);

    };