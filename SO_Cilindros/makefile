CC = gcc
CFLAGS = -Wall -ansi -pedantic -std=c11 -g
RM = rm -rf

LIB_DIR=./lib
INC_DIR=./include
SRC_DIR=./src
OBJ_DIR=./build
BIN_DIR=./bin
DOC_DIR=./doc
TEST_DIR=./test

PROG = run

.PHONY: all clean distclean doxy

all: dir $(PROG)

debug: CFLAGS += -g -O0 -pg
debug: dir $(PROG)

$(PROG): $(OBJ_DIR)/Algoritmos.o $(OBJ_DIR)/LeitorConfig.o $(OBJ_DIR)/main.o
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$@ $^
	@echo ">>> [Executavel $(PROG) criado em $(BIN_DIR)]"

$(OBJ_DIR)/Algoritmos.o: $(SRC_DIR)/Algoritmos.c $(INC_DIR)/Algoritmos.h
	$(CC) -c $(CFLAGS) -I$(INC_DIR)/ -o $@ $<

$(OBJ_DIR)/LeitorConfig.o: $(SRC_DIR)/LeitorConfig.c $(INC_DIR)/LeitorConfig.h
	$(CC) -c $(CFLAGS) -I$(INC_DIR)/ -o $@ $<

$(OBJ_DIR)/main.o: $(SRC_DIR)/main.c
	$(CC) -c $(CFLAGS) -I$(INC_DIR)/ -o $@ $<

valgrind:
	valgrind -v --leak-check=full --show-reachable=yes --track-origins=yes $(arg1) $(arg2) $(arg3) $(arg4)

clean: dir
	$(RM) $(BIN_DIR)/*
	$(RM) $(OBJ_DIR)/*
	$(RM) $(DOC_DIR)/*

dir:
	mkdir -p src include bin build doc