// Copyright 2018 JV

/**
 * @file        main.c
 * @brief       ...
 *
 * @author      João Vítor (jv.venceslau.c@gmail.com)
 * @since       25/11/2018
 * @date        25/11/2018
 * @version     0.1
 */

#include <stdio.h>
#include "Algoritmos.h"
#include "LeitorConfig.h"

int main(int argc, char **argv) {
    if (argc != 2) {
        printf("Por favor informe apenas o arquivo de configuração.\n");
        return -1;
    }
    Config *config = read_config_file(argv[1]);
    if (config == NULL) {
        fprintf(stderr, "Erro na abertura do arquivo %s\n", argv[1]);
        return -1;
    }
    Result *results[4];

    results[0] = fcfs(config);
    results[1] = sstf(config);
    results[3] = scan_down(config);
    results[2] = scan_up(config);

    char *algs[4] = {"FCFS", "SSTF", "SCAN SOBE", "SCAN DESCE"};
    for (int i = 0; i < 4; ++i) {
        printf("%s\n\tOrdem: ", algs[i]);
        printf("%d, ", config->initial_position);
        for (int j = 0; j < config->queue_size - 1; ++j) {
            printf("%d, ", results[i]->order[j]);
        }
        printf("%d\n", results[i]->order[config->queue_size - 1]);
        printf("\tCilindros: %d\n", results[i]->cilinders);
    }

    for (int i = 0; i < 4; ++i) {
        free(results[i]->order);
        free(results[i]);
    }
    free(config->queue);
    free(config);

    return 0;
}
