#include <jni.h>
#include <stdio.h>
#include <thread>
#include <chrono>
#include "messenger.h"

#include "headers/flyingship_src_Backend.h"


class Backend{

    Messenger messenger;

    public:
    Backend();
};