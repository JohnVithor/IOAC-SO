#define _GNU_SOURCE
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

pthread_mutex_t mutex;
int **matriz1;
int **matriz2;
int **matriz3;
int SIZE;

void inicializa_matriz() {
    matriz1 = (int**) malloc(SIZE*sizeof(int*));
    matriz2 = (int**) malloc(SIZE*sizeof(int*));
    matriz3 = (int**) malloc(SIZE*sizeof(int*));
    for (int i = 0; i < SIZE; i++) {
        matriz1[i] = (int*) malloc(SIZE*sizeof(int));
        matriz2[i] = (int*) malloc(SIZE*sizeof(int));
        matriz3[i] = (int*) malloc(SIZE*sizeof(int));
        for (int j = 0; j < SIZE; j++) {
            matriz1[i][j] = rand() % 10;
            matriz2[i][j] = rand() % 10;
        }
    }
}

void print_matrix(int **matriz) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("%d ", matriz[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}

void *calc_line(void *p) {
    int i;

    pthread_mutex_lock(&mutex);
    for (int colunm = 0; colunm < SIZE; colunm++) {
        int somaprod = 0;
        for (int i = 0; i < SIZE; i++) {
            somaprod += matriz1[(int)(size_t)p][i] * matriz2[i][colunm];
        }
        matriz3[(int)(size_t)p][colunm] = somaprod;
    }
    pthread_mutex_unlock(&mutex);

    printf("THREAD#%d: Finalizou.\n", (int)(size_t)p);
    pthread_exit(NULL);
}

int main(int argc, char **argv) {
    int i;
    SIZE = atoi(argv[1]);
    pthread_t tid[SIZE];

    srand(time(NULL));
    inicializa_matriz();
    pthread_mutex_init(&mutex, NULL);

    for (i = 0; i < SIZE; i++) {
        pthread_create(&tid[i], NULL, calc_line, (void *)(size_t)i);
    }

    for (i = 0; i < SIZE; i++) {
        pthread_join(tid[i], NULL);
    }

    printf("Matriz 1\n");
    print_matrix(matriz1);
    printf("Matriz 2\n");
    print_matrix(matriz2);
    printf("Matriz 3\n");
    print_matrix(matriz3);

    pthread_mutex_destroy(&mutex);

    return 0;
}