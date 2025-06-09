@echo off
echo Starting Carcinogenic Product Identifier Application...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 21 or later from: https://adoptium.net/temurin/releases/?version=21
    pause
    exit /b 1
)

REM Check if required files exist
if not exist "target\CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar" (
    echo Error: JAR file not found
    echo Please make sure CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar is in the target directory
    pause
    exit /b 1
)

if not exist "target\carcinogenic_products.db" (
    echo Error: Database file not found
    echo Please make sure carcinogenic_products.db is in the target directory
    pause
    exit /b 1
)

REM Run the application
echo Running application...
java -jar target\CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar

REM If the application crashes, pause to see the error
if %errorlevel% neq 0 (
    echo.
    echo Application exited with an error
    pause
) 