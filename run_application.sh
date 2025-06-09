#!/bin/bash

echo "Starting Carcinogenic Product Identifier Application..."
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 21 or later from: https://adoptium.net/temurin/releases/?version=21"
    read -p "Press Enter to continue..."
    exit 1
fi

# Check if required files exist
if [ ! -f "CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar" ]; then
    echo "Error: JAR file not found"
    echo "Please make sure CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar is in the same directory"
    read -p "Press Enter to continue..."
    exit 1
fi

if [ ! -f "carcinogenic_products.db" ]; then
    echo "Error: Database file not found"
    echo "Please make sure carcinogenic_products.db is in the same directory"
    read -p "Press Enter to continue..."
    exit 1
fi

# Run the application
echo "Running application..."
java -jar CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar

# If the application crashes, wait to see the error
if [ $? -ne 0 ]; then
    echo
    echo "Application exited with an error"
    read -p "Press Enter to continue..."
fi 