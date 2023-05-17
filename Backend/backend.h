#pragma once

#include <jni.h>
#include <stdio.h>
#include <messenger.h>
#include "environment.h"
#include <thread>

#include <headers/JNI_flyingship_src_Backend.h>

class Backend{

    Messenger messenger;
    Environment env;
    std::thread env_thread;

    public:
    Backend() : messenger(msg::Backend), env(messenger), env_thread(&Environment::run, env){};
    void run();
};