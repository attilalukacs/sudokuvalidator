SET JAR_DIR=target

java -jar %JAR_DIR%\sudokuvalidator-${VERSION}.jar $*
EXIT /B %ERRORLEVEL%