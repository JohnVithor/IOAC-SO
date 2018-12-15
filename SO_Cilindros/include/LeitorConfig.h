// Copyright 2018 JV

/**
 * @file        LeitorConfig.h
 * @brief       ...
 *
 * @author      João Vítor (jv.venceslau.c@gmail.com)
 * @since       25/11/2018
 * @date        25/11/2018
 * @version     0.1
 */

#ifndef INCLUDE_LEITORCONFIG_H_
#define INCLUDE_LEITORCONFIG_H_

#include <stdio.h>
#include <stdlib.h>

typedef struct Config {
    int initial_position;
    int queue_size;
    int *queue;
} Config;

Config *read_config_file(char *file_name);

#endif  // INCLUDE_LEITORCONFIG_H_