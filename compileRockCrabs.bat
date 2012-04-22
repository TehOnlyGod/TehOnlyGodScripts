@ECHO OFF
:: Make from the RSBot 2 Compile.bat
:: Made to work with RSBot 4***
:: If posted elsewhere, Please keep my credits. I WILL come after you.
:: This file, the RSBot jar, and a folder named Scripts should be all in the same directory.
:: Just run this file and scripts will auto-compile and be put into a bin folder (Will create if one is not found.)
TITLE RSBot Scripts WinAwesome

SET cc=javac
:: Add this (below,) to the cflags if you are getting unchecked errors.
:: -Xlint:unchecked
SET cflags=-deprecation
SET scripts=Scripts/us/tehonlygod/rockcrabs
SET jarpath=RSBot-4*.jar

ECHO Looking for JDK

SET KEY_NAME=HKLM\SOFTWARE\JavaSoft\Java Development Kit
FOR /F "tokens=3" %%A IN ('REG QUERY "%KEY_NAME%" /v CurrentVersion 2^>NUL') DO SET jdkv=%%A
SET jdk=

IF DEFINED jdkv (
FOR /F "skip=2 tokens=2*" %%A IN ('REG QUERY "%KEY_NAME%\%jdkv%" /v JavaHome 2^>NUL') DO SET jdk=%%B
) ELSE (
FOR /F "tokens=*" %%G IN ('DIR /B "%ProgramFiles%\Java\jdk*"') DO SET jdk=%%G
)

SET jdk=%jdk%\bin
SET javac="%jdk%\javac.***"

IF NOT EXIST %javac% (
javac -version 2>NUL
IF "%ERRORLEVEL%" NEQ "0" GOTO :notfound
) ELSE (
GOTO setpath
)

:Compile
IF NOT EXIST %scripts%\*.java (
ECHO No .java script source files found.
GOTO end
)

ECHO Compiling scripts
ECHO. > "%scripts%\.class"
DEL /F /Q "%scripts%\*.class" > NUL
"%cc%" %cflags% -cp "%jarpath%" %scripts%\*.java

IF NOT EXIST bin (
mkdir bin
)
IF EXIST bin\*.class (
DEL /F /Q "bin\*.class" > NUL
)
move %scripts%\*.class bin\ > NUL

:end
PAUSE
EXIT

:notfound
ECHO JDK is not installed, please download and install it from:
ECHO http://java.sun.com/javase/downloads
ECHO.
PAUSE
EXIT

:setpath
SET PATH=%jdk%;%PATH%
GOTO :Compile