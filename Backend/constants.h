#pragma once

#include <chrono>

using namespace std::literals::chrono_literals;

#define UNIQUE_IDS_N 4000
#define AGENT_Y_PADDING 20
#define DEFAULT_AGENT_SIZE 30
#define BACKEND_SLEEP_TIME 200ms
#define DEFAULT_SCREEN_WIDTH 800
#define DEFAULT_SCREEN_HEIGHT 0//600
#define DEFAULT_TIME_TICK_SPEED 200ms
#define ENV_PAUSE_SLEEP 1000ms
#define AGENT_SIZE 10
#define LAYER_HEIGHT 20
#define MIN_SIZE 10
#define MAX_SIZE 40
#define MAX_TRIES 100
#define SHIFT 2

const int N_Elem_In_Layer = DEFAULT_SCREEN_WIDTH / (2 * (MAX_SIZE + AGENT_SIZE)) - 1;