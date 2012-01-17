#!/bin/sh -x
CLASSPATH=../craftbukkit-1.0.1-R1.jar javac *.java
rm -rf me
mkdir -p me/exphc/Sublimation
mv *.class me/exphc/Sublimation
jar cf Sublimation.jar me/ *.yml *.java README
cp Sublimation.jar ../plugins/
