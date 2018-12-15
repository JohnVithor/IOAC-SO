// Copyright 2018 JV

/**
 * @file        Algoritmos.h
 * @brief       ...
 *
 * @author      João Vítor (jv.venceslau.c@gmail.com)
 * @since       25/11/2018
 * @date        25/11/2018
 * @version     0.1
 */

#ifndef INCLUDE_ALGORITMOS_H_
#define INCLUDE_ALGORITMOS_H_

#include "LeitorConfig.h"

typedef struct Result {
    int *order;
    int cilinders;
} Result;

Result *fcfs(Config *config);

Result *sstf(Config *config);

Result *scan_down(Config *config);

Result *scan_up(Config *config);

#endif  // INCLUDE_ALGORITMOS_H_