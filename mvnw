#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup batch script, version 3.3.2
#
# Optional ENV vars
#   MVNW_REPOURL - repo url base for downloading maven distribution
#   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
#   MVNW_VERBOSE - true: enable verbose log; others: silence the output
# ----------------------------------------------------------------------------

set -euf
[ "${MVNW_VERBOSE-}" != true ] || set -x

# OS specific support.
native_path() { printf %s\\n "$1"; }
case "$(uname)" in
  (CYGWIN* | MINGW* | MSYS*) natoive_path() { cygpath --path --windows "$1"; } ;;
esac

# set JAVA_HOME from sdkman if present
if [ -z "${JAVA_HOME-}" ] && [ -n "${SDKMAN_CANDIDATES_DIR-}" ]; then
  JAVA_HOME="${SDKMAN_CANDIDATES_DIR}/java/current"
fi

# Determine the Java command to use to start the JVM.
if [ -n "${JAVA_HOME-}" ]; then
  if [ -x "$JAVA_HOME/jre/sh/java" ]; then
    # IBM's JDK on AIX uses strange locations for the executables
    JAVACMD=$JAVA_HOME/jre/sh/java
  else
    JAVACMD=$JAVA_HOME/bin/java
  fi
  if [ ! -x "$JAVACMD" ]; then
    echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
    echo "Please set the JAVA_HOME variable in your environment to match the location of your Java installation." >&2
    exit 1
  fi
else
  JAVACMD=java
  if ! command -v java > /dev/null 2>&1; then
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
    echo "Please set the JAVA_HOME variable in your environment to match the location of your Java installation." >&2
    exit 1
  fi
fi

# resolve symlinks to the script itself
SELF="$0"
while [ -h "$SELF" ]; do
  ls=$(ls -ld "$SELF")
  link=${ls#*' -> '}
  case "$link" in
    /*) SELF="$link" ;;
    *) SELF="$(dirname "$SELF")/$link" ;;
  esac
done
MAVEN_PROJECTBASEDIR=$(dirname "$SELF")
MAVEN_PROJECTBASEDIR=$(cd "$MAVEN_PROJECTBASEDIR" && pwd -P)

MAVEN_WRAPPERJAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

# Download maven-wrapper.jar if not present
if [ ! -f "$MAVEN_WRAPPERJAR" ]; then
  echo "Downloading Maven Wrapper..."
  if command -v curl > /dev/null 2>&1; then
    curl -sL -o "$MAVEN_WRAPPERJAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  elif command -v wget > /dev/null 2>&1; then
    wget -q -O "$MAVEN_WRAPPERJAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  else
    echo "Error: curl or wget required to download maven-wrapper.jar" >&2
    exit 1
  fi
fi

exec "$JAVACMD" \
  -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" \
  -cp "$MAVEN_WRAPPERJAR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
