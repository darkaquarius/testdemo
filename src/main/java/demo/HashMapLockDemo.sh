#!/usr/bin/env bash

javac -d . HashMapLockDemo.java

for i in `seq 1 100`; do
    java demo.HashMapLockDemo
done

exit 0