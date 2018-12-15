# Universidade Federal do Rio Grande do Norte
### Instituto Metrópole Digital - IMD
### Bacharelado em Tecnologia da Informação – BTI

## Simulação de Cache

- Aluno: João Vítor Venceslau Coelho
- Professor: Gustavo Girão Barreto da Silva (IOAC - Introdução a Organização e Arquitetura de Computadores)
- Professor: André Maurício Cunha Campos (BPP - Boas Práticas de Programação)

### Resumo

Este programa tem como objetivo simular a comunicação entre uma Cache com uma Memória, de acordo com os comandos do Processador (papel do Usuário utilizando o comandos abaixo)

Padrão de Programação (Diretrizes Adotadas): 
 - O padrão da [Google](https://google.github.io/styleguide/javaguide.html) levemente modificado (indentação utilizada em vez de 2 é de 4 espaços) e algumas das orientações recomendadas em sala de aula.

(**Atenção:** Não foram utilizadas todas as orientações do link, são muitas diretrizes e o projeto não é complexo o suficiente para adotar todas.)
 
Ferramenta de análise estática utilizada: _CheckStyle_ e _SonarLint_

Testes Unitários feitos utilizando o _JUnit 5_ no _Eclipse Oxygen_

### Comandos Aceitos
 - _Read_   (r)
 - _Write_  (w)
 - _Show_   (s)
 - _Config_ (c)
 - _Hit_    (h)
 - _Miss_   (m)
 - _Exit_   (e)
 
 ### Exemplos de Uso
 - r 10
 - w 2 30
 - Show
 - c
 - hit
 - miss
 - e
 
## Como Executar

Utilizando o Eclipse e configurando os args a serem utilizados, criar um .jar pelo Eclipse e executá-lo no terminal ou utilizar o .jar já criado e disponível no repósitório.

Exemplo de uso configurando os args no Eclipse:
- Após clonar o repo e ter carregado o projeto no Eclipse, ir até **_Run->Run Configurations_**
- Entrar na aba de **_Arguments_** e no campo **_Program arguments_** escrever os argumentos a serem utilizados
- Exemplo: _C:tests/config0.txt D:tests/data0.txt_
- Com isso já é possivel rodar o programa, clique em **_Run_**

Exemplo de uso pelo .jar pelo terminal:
 - _java -jar Cache.jar C:tests/config0.txt D:tests/data0.txt_
 
