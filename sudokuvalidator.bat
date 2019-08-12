@ECHO OFF

SET JAR_DIR=target
SET VERSION=1.0-SNAPSHOT

java -jar %JAR_DIR%\sudokuvalidator-%VERSION%.jar %*
EXIT /B %ERRORLEVEL%
