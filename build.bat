@echo off
setlocal

REM --- User Configuration ---
set "TOMCAT_HOME=%~1"
if "%TOMCAT_HOME%"=="" set /p "TOMCAT_HOME=Enter Path to Apache Tomcat (e.g., C:\apache-tomcat-9.0.x): "

if not exist "%TOMCAT_HOME%" (
    echo Error: Tomcat directory not found at "%TOMCAT_HOME%"
    pause
    exit /b 1
)

echo Using Tomcat at: %TOMCAT_HOME%

REM Clean previous build
if exist "WebContent\WEB-INF\classes" rmdir /s /q "WebContent\WEB-INF\classes"
mkdir "WebContent\WEB-INF\classes"

echo Compiling Java sources...

REM Check for libs
if not exist "WebContent\WEB-INF\lib\*.jar" (
    echo Warning: No JARs found in WebContent\WEB-INF\lib. 
    echo Please ensure mysql-connector-j.jar is present if DB access is needed.
)

REM Compile
javac -sourcepath src -cp "WebContent\WEB-INF\lib\*;%TOMCAT_HOME%\lib\servlet-api.jar" -d "WebContent\WEB-INF\classes" src\com\attendance\util\*.java src\com\attendance\model\*.java src\com\attendance\controller\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!

REM Deploy
set "DEPLOY_DIR=%TOMCAT_HOME%\webapps\AttendanceTrackingSystem"
echo Deploying to %DEPLOY_DIR%...

if exist "%DEPLOY_DIR%" rmdir /s /q "%DEPLOY_DIR%"
mkdir "%DEPLOY_DIR%"

xcopy /s /e /y "WebContent\*.*" "%DEPLOY_DIR%\"

echo.
echo Deployment complete!
echo 1. Start Tomcat (%TOMCAT_HOME%\bin\startup.bat)
echo 2. Access: http://localhost:8080/AttendanceTrackingSystem
echo.
pause
