// Copyright 2018 JV

/**
 * @file        Algoritmos.c
 * @brief       ...
 *
 * @author      João Vítor (jv.venceslau.c@gmail.com)
 * @since       25/11/2018
 * @date        25/11/2018
 * @version     0.1
 */

#include "Algoritmos.h"

Result *fcfs(Config *config) {
    Result *result = (Result *)malloc(sizeof(Result));
    result->cilinders = 0;
    result->order = (int *)malloc(config->queue_size * sizeof(int));

    int position = config->initial_position;
    for (int i = 0; i < config->queue_size; ++i) {
        result->order[i] = config->queue[i];
        result->cilinders += abs(position - result->order[i]);
        position = result->order[i];
    }
    return result;
}

Result *sstf(Config *config) {
    Result *result = (Result *)malloc(sizeof(Result));
    result->cilinders = 0;
    result->order = (int *)malloc(config->queue_size * sizeof(int));

    for (int i = 0; i < config->queue_size; ++i) {
        result->order[i] = config->queue[i];
    }

    int position = config->initial_position;
    int temp = 0;
    for (int i = 0; i < config->queue_size; ++i) {
        int next = i;
        for (int j = i; j < config->queue_size; ++j) {
            if (abs(position - result->order[j]) <
                abs(position - result->order[next])) {
                next = j;
            }
        }
        temp = result->order[i];
        result->order[i] = result->order[next];
        result->order[next] = temp;

        result->cilinders += abs(position - result->order[i]);
        position = result->order[i];
    }
    return result;
}

Result *scan_up(Config *config) {
    Result *result = (Result *)malloc(sizeof(Result));
    result->cilinders = 0;
    result->order = (int *)malloc(config->queue_size * sizeof(int));

    int *helper = (int *)malloc(config->queue_size * sizeof(int));
    int h_size = 0;
    for (int i = 1; i < config->queue_size; ++i) {
        int key = config->queue[i];
        int j = i - 1;
        while (j >= 0 && config->queue[j] > key) {
            config->queue[j + 1] = config->queue[j];
            --j;
        }
        config->queue[j + 1] = key;
    }

    int position = config->initial_position;

    int next = 0;
    for (int i = 0; i < config->queue_size; ++i) {
        if (config->queue[i] < position) {
            helper[h_size] = config->queue[i];
            ++h_size;
        } else {
            result->order[next] = config->queue[i];
            result->cilinders += abs(position - result->order[next]);
            position = result->order[next];
            ++next;
        }
    }
    for (int i = h_size - 1; i >= 0; --i) {
        result->order[next] = helper[i];
        result->cilinders += abs(position - result->order[next]);
        position = result->order[next];
        ++next;
    }
    free(helper);
    return result;
}

Result *scan_down(Config *config) {
    Result *result = (Result *)malloc(sizeof(Result));
    result->cilinders = 0;
    result->order = (int *)malloc(config->queue_size * sizeof(int));

    int *helper = (int *)malloc(config->queue_size * sizeof(int));
    int h_size = 0;

    for (int i = 1; i < config->queue_size; ++i) {
        int key = config->queue[i];
        int j = i - 1;
        while (j >= 0 && config->queue[j] > key) {
            config->queue[j + 1] = config->queue[j];
            --j;
        }
        config->queue[j + 1] = key;
    }

    int position = config->initial_position;

    int next = 0;
    for (int i = config->queue_size - 1; i >= 0; --i) {
        if (config->queue[i] > position) {
            helper[h_size] = config->queue[i];
            ++h_size;
        } else {
            result->order[next] = config->queue[i];
            result->cilinders += abs(position - result->order[next]);
            position = result->order[next];
            ++next;
        }
    }
    for (int i = h_size - 1; i >= 0; --i) {
        result->order[next] = helper[i];
        result->cilinders += abs(position - result->order[next]);
        position = result->order[next];
        ++next;
    }
    free(helper);
    return result;
}
