@echo off
setlocal
chcp 65001 >nul
pushd "%~dp0"
if errorlevel 1 (
    echo Nelze otevrit slozku aplikace. Rozbalte cely ZIP do slozky v pocitaci.
    pause
    exit /b 1
)

rem IntelliJ stahuje JDK do uzivatelske slozky .jdks.
set "CSV_JAVA_EXE="
for /d %%D in ("%USERPROFILE%\.jdks\*") do if exist "%%~fD\bin\java.exe" set "CSV_JAVA_EXE=%%~fD\bin\java.exe"
if not defined CSV_JAVA_EXE if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "CSV_JAVA_EXE=%JAVA_HOME%\bin\java.exe"
if not defined CSV_JAVA_EXE for /f "delims=" %%J in ('where java 2^>nul') do if not defined CSV_JAVA_EXE set "CSV_JAVA_EXE=%%J"

if not defined CSV_JAVA_EXE (
    echo Java nebyla nalezena v IntelliJ JDK, JAVA_HOME ani PATH.
    echo V IntelliJ stahnete JDK 17 nebo novejsi a tento soubor spustte znovu.
    popd
    pause
    exit /b 1
)
if not exist "%~dp0T18_cviceni-CSV.jar" (
    echo Chybi T18_cviceni-CSV.jar. Rozbalte cely ZIP, ne pouze tento spoustec.
    popd
    pause
    exit /b 1
)
echo Pouzita Java: "%CSV_JAVA_EXE%"
echo.
"%CSV_JAVA_EXE%" -Dfile.encoding=UTF-8 -jar "%~dp0T18_cviceni-CSV.jar" %*
set "CSV_APP_EXIT=%ERRORLEVEL%"
if not "%CSV_APP_EXIT%"=="0" echo Aplikace skoncila s kodem %CSV_APP_EXIT%. Viz zprava vyse.
popd
echo.
pause
exit /b %CSV_APP_EXIT%
