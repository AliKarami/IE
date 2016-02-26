#!/usr/bin/env bash

rm -rf bin/*
javac -cp coolserver.jar -sourcepath src -d bin src/*.java

if [ $? -eq 0 ]; then
    cd bin
    jar cf ../CA3.jar *.class
fi