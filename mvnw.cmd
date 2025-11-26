@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup batch script, version 3.3.2
@REM
@REM Optional ENV vars
@REM   MVNW_REPOURL - repo url base for downloading maven distribution
@REM   MVNW_USERNAME/MVNW_PASSWORD - user and password for downloading maven
@REM   MVNW_VERBOSE - true: enable verbose log; others: silence the output
@REM ----------------------------------------------------------------------------

@IF "%__MVNW_ARG0_NAME__%"=="" (SET __MVNW_ARG0_NAME__=%~nx0)
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE__=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0telerik/WebUI.Shared'; $env:__MVNW_SCRIPT_PATH__=$scriptDir; exit 0; }"`) DO @(
  IF "%%A"=="__MVNW_CMD__" SET __MVNW_CMD__=%%B
)
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE__%

@SETLOCAL
@SET MVNW_JAVA_EXE=java.exe

@SET WRAPPER_JAR="%~dp0\.mvn\wrapper\maven-wrapper.jar"

@REM Maven Wrapper uses MAVEN_BASEDIR for multimodule projects
@IF NOT DEFINED MAVEN_BASEDIR (
  @SET MAVEN_BASEDIR=%~dp0
  @SET MAVEN_BASEDIR=!MAVEN_BASEDIR:~0,-1!
)

@REM Download maven-wrapper.jar if not present
@IF NOT EXIST %WRAPPER_JAR% (
  @ECHO Downloading Maven Wrapper...
  @powershell -Command "& { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar' -OutFile '%~dp0\.mvn\wrapper\maven-wrapper.jar' }"
)

@IF EXIST %WRAPPER_JAR% (
  @SET WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain
  @"%MVNW_JAVA_EXE%" -cp %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
) ELSE (
  @ECHO Error: maven-wrapper.jar not found and download failed
  @EXIT /B 1
)
