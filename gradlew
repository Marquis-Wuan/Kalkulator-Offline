#!/bin/sh
WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"
WRAPPER_URL="https://raw.githubusercontent.com/gradle/gradle/v8.1.0/gradle/wrapper/gradle-wrapper.jar"
if [ ! -e "$WRAPPER_JAR" ]; then
    mkdir -p gradle/wrapper
    curl -L "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi
JAVACMD="java"
if [ -n "$JAVA_HOME" ] ; then JAVACMD="$JAVA_HOME/bin/java"; fi
exec "$JAVACMD" -Xmx512m -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
