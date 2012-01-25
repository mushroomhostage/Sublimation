#!/bin/sh -x
CLASSPATH=../craftbukkit-1.1-R1.jar javac *.java -Xlint:deprecation
rm -rf me
mkdir -p me/exphc/Sublimation
mv *.class me/exphc/Sublimation
jar cf Sublimation.jar me/ *.yml *.java README.md
cp Sublimation.jar ../plugins/
