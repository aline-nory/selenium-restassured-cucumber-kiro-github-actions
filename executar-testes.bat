@echo off
:: ============================================================
:: Script para executar os testes Selenium + Cucumber + JUnit
:: ============================================================

:: Certificado SSL customizado (necessario por causa do Avast interceptando HTTPS)
set MAVEN_OPTS=-Djavax.net.ssl.trustStore=%USERPROFILE%\.maven-cacerts -Djavax.net.ssl.trustStorePassword=changeit

:: Caminho completo do Maven (caso nao esteja no PATH ainda)
set MVN=C:\maven\apache-maven-3.9.6\bin\mvn.cmd

echo.
echo ============================================================
echo  AUTOMACAO - Selenium + Cucumber + JUnit + REST Assured
echo ============================================================
echo.
echo Opcoes de execucao:
echo   executar-testes.bat             - todos os testes (UI + API)
echo   executar-testes.bat api         - somente testes de API
echo   executar-testes.bat ui          - somente testes de UI (Chrome)
echo   executar-testes.bat @smoke      - cenarios com tag @smoke
echo.

if not exist "%MVN%" (
    echo ERRO: Maven nao encontrado em %MVN%
    pause
    exit /b 1
)

if "%1"=="api" (
    echo Executando testes de API...
    %MVN% test -f pom.xml "-Dcucumber.features=src/test/resources/features/api_posts.feature"
) else if "%1"=="ui" (
    echo Executando testes de UI...
    %MVN% test -f pom.xml "-Dcucumber.features=src/test/resources/features/login.feature"
) else if "%1"=="" (
    echo Executando TODOS os testes...
    %MVN% test -f pom.xml
) else (
    echo Executando cenarios com tag: %1
    %MVN% test -f pom.xml -Dcucumber.filter.tags="%1"
)

echo.
if exist "target\cucumber-reports\cucumber.html" (
    echo Relatorio HTML: %CD%\target\cucumber-reports\cucumber.html
) else (
    echo Relatorio nao gerado - verifique os erros acima.
)
echo.
pause
