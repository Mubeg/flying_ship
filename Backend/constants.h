#pragma once

#include <chrono>

using namespace std::literals::chrono_literals;

#define UNIQUE_IDS_N 4000
#define DEFAULT_AGENT_SIZE 30
#define BACKEND_SLEEP_TIME 200ms
#define DEFAULT_SCREEN_WIDTH 1280
#define DEFAULT_SCREEN_HEIGHT 720
#define DEFAULT_TIME_TICK_SPEED 200ms
#define ENV_PAUSE_SLEEP 100ms
#define AGENT_SIZE 40
#define LAYER_HEIGHT 20
#define MIN_SIZE 10
#define MAX_SIZE 50
#define MAX_TRIES 100
#define SHIFT 2
#define AGENT_Y_PADDING (DEFAULT_SCREEN_HEIGHT - 20)

const int N_Elem_In_Layer = DEFAULT_SCREEN_WIDTH / (2 * (MAX_SIZE + AGENT_SIZE)) - 1;