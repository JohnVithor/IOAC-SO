#include <sys/types.h>
#include <sys/wait.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define READ 0
#define WRITE 1

void escrever(int *vetor_pipe, int num) {
    close(vetor_pipe[READ]);
    write(vetor_pipe[WRITE], &num, sizeof(num));
    close(vetor_pipe[WRITE]);
}

int ler(int *vetor_pipe) {
    int buffer = 0;
    close(vetor_pipe[WRITE]);
    while (read(vetor_pipe[READ], &buffer, sizeof(int)) > 0)
    close(vetor_pipe[READ]);
    return buffer;
}

int somar(int soma, int count, int num) {
    int vetor_pipe[2];
    pipe(vetor_pipe);
    pid_t pid = fork();

    if (pid < 0) {
        printf("(PID = %d | PPID = %d): Erro durante o fork!\n", getpid(), getppid());
        exit(1);
    }
    if (pid == 0) {
        printf("(PID = %d | PPID = %d): Somou: %d + %d\n",getpid(), getppid(), soma, count);
        soma = soma + count;
        escrever(vetor_pipe, soma);
        //printf("(%d) Escreveu: %d\n",getpid(), soma);
        exit(0);
    }
    int resultado = ler(vetor_pipe);
    //printf("(%d) Leu: %d\n",getpid(), resultado);
    return resultado;
}

int main(int argc, char * argv[]) {
    int num = atoi(argv[1]);
    int resultado = 0;
    for (int i = 1; i <= num; ++i) {
        resultado = somar(resultado, i, num);
    }
       
    printf("(PID = %d | PPID = %d): Resultado Final: %d\n", getpid(), getppid(), resultado);
    return 0;
}
