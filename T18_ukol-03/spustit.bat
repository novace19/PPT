@echo off
cd /d "%~dp0"
if not exist target\classes mkdir target\classes
javac -encoding UTF-8 -d target\classes -sourcepath src src\cz\vspj\t18\Main.java
if errorlevel 1 (pause & exit /b 1)
java -cp target\classes cz.vspj.t18.Main
pause
