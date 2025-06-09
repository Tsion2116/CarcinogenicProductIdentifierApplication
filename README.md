# Carcinogenic Product Identifier Application

A Java application that helps identify and track potentially carcinogenic products.

## System Requirements

- Java Runtime Environment (JRE) 21 or later
- Minimum 4GB RAM
- 100MB free disk space

## Installation Instructions

### Windows

1. Download and install Java 21 or later:
   - Visit [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=21)
   - Download the Windows installer (x64)
   - Run the installer and follow the installation wizard
   - Make sure to check "Add to PATH" during installation

2. Download the application:
   - Extract the zip file to a location of your choice
   - Make sure to keep all files in the same directory

### Linux/macOS

1. Download and install Java 21 or later:
   - Visit [Eclipse Temurin](https://adoptium.net/temurin/releases/?version=21)
   - Download the appropriate package for your system
   - Follow the installation instructions for your distribution

2. Download the application:
   - Extract the zip file to a location of your choice
   - Make sure to keep all files in the same directory

## Running the Application

### Windows

1. Double-click `run_application.bat`
   - OR
2. Open Command Prompt in the application directory and run:
   ```
   run_application.bat
   ```

### Linux/macOS

1. Open Terminal in the application directory
2. Make the script executable (first time only):
   ```bash
   chmod +x run_application.sh
   ```
3. Run the application:
   ```bash
   ./run_application.sh
   ```

## Troubleshooting

### Common Issues

1. **"Java is not installed or not in PATH"**
   - Make sure Java is installed correctly
   - Verify Java installation by opening a terminal/command prompt and typing:
     ```
     java -version
     ```
   - If not recognized, reinstall Java and ensure "Add to PATH" is selected

2. **"JAR file not found" or "Database file not found"**
   - Ensure all files are in the same directory
   - Check that file names match exactly:
     - `CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar`
     - `carcinogenic_products.db`

3. **Application crashes on startup**
   - Check if you have the correct Java version (21 or later)
   - Ensure you have sufficient system resources
   - Check the error message in the console for specific issues

### Getting Help

If you encounter any issues not covered in this guide:
1. Check the error message displayed in the console
2. Ensure all files are present and in the correct location
3. Verify Java installation and version
4. Contact support with the specific error message

## File Structure

The application consists of the following files:
- `CarcinogenicProductIdentifier-1.0-SNAPSHOT.jar` - The main application
- `carcinogenic_products.db` - The database file
- `run_application.bat` - Windows launcher script
- `run_application.sh` - Linux/macOS launcher script
- `README.md` - This documentation file

## Security Note

- Keep your database file (`carcinogenic_products.db`) secure
- Do not share your database file with unauthorized users
- Regularly backup your database file

## Support

For technical support or to report issues, please contact the development team. 