# Universidade Federal do Rio Grande do Norte
### Instituto Metrópole Digital - IMD
### Bacharelado em Tecnologia da Informação – BTI

## Simulação de um Pipeline MIPS

- Aluno: João Vítor Venceslau Coelho
- Professor: Gustavo Girão Barreto da Silva

### Resumo

Este programa tem como objetivo simular a execução de um Pipeline de um processador MIPS (apenas algumas de suas instruções), resolvendo os conflitos gerados pelas dependências encontradas nas instruções.

## Como Executar

Utilizando o Eclipse e configurando os args a serem utilizados, criar um .jar pelo Eclipse e executá-lo no terminal ou utilizar o .jar já criado e disponível no repósitório.

Exemplo de uso configurando os args no Eclipse:
- Após clonar o repo e ter carregado o projeto no Eclipse, ir até **_Run->Run Configurations_**
- Entrar na aba de **_Arguments_** e no campo **_Program arguments_** escrever os argumentos a serem utilizados
- Exemplo: M:1 PATH:instructions/test4.txt
- Com isso já é possivel rodar o programa, clique em **_Run_**

Exemplo de uso pelo .jar pelo terminal:
- java -jar MIPS.jar M:1 PATH:instructions/test10.txt

##### Modo de usar

- 'M:0' - indica o modo sem Redirecionamento ou Reordenamento
- 'M:1' - indica o modo com Redirecionamento e sem Reordenamento
- 'M:2' - indica o modo sem Redirecionamento e com Reordenamento (Não Implementado)
- 'M:3' - indica o modo com Redirecionamento e com Reordenamento (Não Implementado)

##### Instruções Suportadas
 - add 
 - sub
 - beq
 - bne
 - lw
 - sw
 - j 
