#!/bin/bash

cd ..

# Reset/clear the dist folder
rm -rf dist/*
mkdir -p dist
mkdir dist/windows

# Compile the jars
if [[ $@ != *"nocompile"* ]]; then
    # Compile the maven project
    mvn clean package
fi

if [[ $@ != *"nodist"* ]]; then
    cp target/FollowMouse.jar dist/FollowMouse.jar
    
    echo ""
    echo "Completing packaging of application."
    echo ""
    
    sh build/windows/build.sh
    echo ""
    
fi
