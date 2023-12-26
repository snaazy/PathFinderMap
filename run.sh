#!/bin/bash

PROJECT_DIR="."

BIN_DIR="$PROJECT_DIR/bin"

MAIN_CLASS="MainApp.App"

mkdir -p "$BIN_DIR"

cd "$PROJECT_DIR"

javac -d "$BIN_DIR" $(find . -name "*.java")

java -cp "$BIN_DIR" $MAIN_CLASS
