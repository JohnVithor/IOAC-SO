#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


pthread_t *threads;
int status, i;
void *thread_return;

int soma = 0;
int somado = 0;

void* mostrar_resultado(void *tid) {
	while(!somado) {
		printf("Thread %d esperando termino da soma...\n");
		sleep(1);
	}
	printf("O Resultado da soma eh: %d\n", soma);
}

void* somar(void *tid){
	printf("Este é a Thread %d. Somando : %d\n", (int)tid+1, (int)tid+1);
	soma = soma + ((int)tid + 1) ;		
	pthread_exit(NULL);
}

int main(int argc, char *argv[]){

	int NTHREADS = atoi(argv[1]);

	threads = malloc(NTHREADS * sizeof(pthread_t));
	
	pthread_t thread0;
	status = pthread_create(&thread0, NULL, mostrar_resultado, (void *)(size_t) 0);

	for(i = 0; i < NTHREADS; i++){
		status = pthread_create(&threads[i], NULL, somar, (void *)(size_t) i);
		if(status!=0){
			printf("Erro na criação da thread. Codigo de Erro:%d\n", status);
			return 1;
		}
	}
	
	for( i = 0; i < NTHREADS; i++){
		pthread_join(threads[i], &thread_return);
	}
	somado = 1;
	pthread_join(thread0, &thread_return);
	//printf("processo pai vai finalizar e o valor final de soma = %d\n", soma);

	return 0;
}
