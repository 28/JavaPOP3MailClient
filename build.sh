#!/bin/bash

rm -rf ./out/
mkdir ./out/
javac -d ./out -verbose src/javapop3mailclient/controller/*.java \
	src/javapop3mailclient/domain/*.java \
	src/javapop3mailclient/gui/*.java \
	src/javapop3mailclient/gui/models/*.java \
	src/javapop3mailclient/systemoperations/*.java
jar cvfm ./out/javapop3mailclient.jar ./src/resources/META-INF/MANIFEST.mf -C ./out/ .
cp ./src/resources/hosts.properties ./out/hosts.properties
