#!/usr/bin/env bash

rm -rf bin/*
javac -sourcepath src -d bin src/ir/ramtung/coolserver/*.java src/*.java

