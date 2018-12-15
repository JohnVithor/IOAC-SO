#include <pthread.h>
#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int *vetor1;
int *vetor2;
int vetor_size;

void* sum_i(void *tid){
	printf("Sou a thread %d e o resultado da soma é %d\n", (int)tid, (vetor1[(int)tid] + vetor2[(int)tid]));
	pthread_exit(NULL);
}

int main(int argc, char *argv[]){
    srand(time(NULL));

    vetor_size = atoi(argv[1]);
   
    vetor1 = (int*) malloc(vetor_size * sizeof(int));
    vetor2 = (int*) malloc(vetor_size * sizeof(int));

    for(size_t i = 0; i < vetor_size; ++i) {
        vetor1[i] = rand() % 99;
        vetor2[i] = rand() % 99;
    }
    
	pthread_t threads[vetor_size];
	int status, i;
	void *thread_return;

	for(i = 0; i < vetor_size; ++i) {
		printf("Processo principal criando thread #%d\n", i);
		status = pthread_create(&threads[i], NULL, sum_i, (void *)(int) i);
		if(status != 0) {
			printf("Erro na criação da thread. Codigo de Erro:%d\n", status);
			return 1;
		}
	}
	
	for(i = 0; i < vetor_size; ++i) {
		printf("Esperando Thread %d finalizar....\n", i);
		pthread_join(threads[i], &thread_return);
		printf("Thread %d finalizada\n", i);
	}
	printf("processo vai finalizar\n");

	return 0;
}