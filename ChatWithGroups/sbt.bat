set SCRIPT_DIR=%~dp0
java -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=512M -Xmx512M -Xss2M -jar "%SCRIPT_DIR%sbt-launch.jar" %*