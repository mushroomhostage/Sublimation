#!/bin/sh -x
CLASSPATH=../craftbukkit-1.0.1-R1.jar javac *.java
rm -rf com
mkdir -p com/exphc/Sublimation
mv *.class com/exphc/Sublimation
jar cf Sublimation.jar com/ *.yml
cp Sublimation.jar ../plugins/
