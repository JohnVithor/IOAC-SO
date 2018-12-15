// Copyright 2018 JV

/**
 * @file        LeitorConfig.c
 * @brief       ...
 *
 * @author      João Vítor (jv.venceslau.c@gmail.com)
 * @since       25/11/2018
 * @date        25/11/2018
 * @version     0.1
 */

#include "LeitorConfig.h"

Config *read_config_file(char *file_name) {
    FILE *file = (FILE *)fopen(file_name, "r");
    if (file == NULL) {
        return NULL;
    }
    Config *config = (Config *)malloc(sizeof(Config));

    fscanf(file, "%i\n", &config->initial_position);
    fscanf(file, "%i\n", &config->queue_size);
    config->queue = (int *)malloc(config->queue_size * sizeof(int));
    for (int i = 0; i < config->queue_size - 1; ++i) {
        fscanf(file, "%i ", &config->queue[i]);
    }
    fscanf(file, "%i", &config->queue[config->queue_size - 1]);

    fclose(file);

    return config;
}