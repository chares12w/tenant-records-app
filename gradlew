#!/usr/bin/env sh

##############################################################################
##
##  Gradle start-up script for UNIX
##
##############################################################################

# Attempt to set APP_HOME
APP_HOME=$(cd "$(dirname "$0")" && pwd)

# Add default JVM options here
DEFAULT_JVM_OPTS=""

# Locate Java
if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=$(which java)
fi

# Execute Gradle
exec "$JAVA" $DEFAULT_JVM_OPTS -cp "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
