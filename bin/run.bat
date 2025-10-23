@echo off
chcp 65001 >nul
title Gestion des microservices Banque
setlocal enabledelayedexpansion

echo =============================================================
echo   LANCEMENT DES MICROSERVICES BANQUE
echo =============================================================

set WILDFLY_HOME=C:\wildfly
set "STANDALONE_FILES=standalone-central.xml standalone-compte.xml standalone-prets.xml"
set "PORTS_MANAGER=9990 19990 29990"
set "DOSSIERS_JAVA=centralisateur compte-courant prets"
set "DEPLOYMENTS=%WILDFLY_HOME%\standalone\deployments-central %WILDFLY_HOME%\standalone\deployments-compte %WILDFLY_HOME%\standalone\deployments-prets"
set "DOSSIERS_DOTNET=compte-depot global"
set "PORTS_JAVA=8080 8180 8280"
set "PORTS_DOTNET=5240 5000"

:: Déterminer quels services compiler basé sur les arguments
set "SERVICES_A_COMPILER_JAVA="
set "SERVICES_A_COMPILER_DOTNET="
set "COMPILE_ONLY=false"

if "%~1" neq "" (
    set "args=%*"
    call :contains "!args!" "/c" COMPILE_ONLY
)

:: Si des arguments sont fournis, filtrer les services
if "%~1" neq "" (
    echo Filtrage des services a compiler...
    set "arg_list=%*"
    
    :: Vérifier chaque argument contre les services Java
    set "idx=1"
    for %%D in (%DOSSIERS_JAVA%) do (
        call :contains "!arg_list!" "%%D" result
        if "!result!"=="true" (
            if "!SERVICES_A_COMPILER_JAVA!"=="" (
                set "SERVICES_A_COMPILER_JAVA=%%D"
            ) else (
                set "SERVICES_A_COMPILER_JAVA=!SERVICES_A_COMPILER_JAVA! %%D"
            )
        )
        set /a idx+=1
    )
    
    :: Vérifier chaque argument contre les services .NET
    set "idx=1"
    for %%D in (%DOSSIERS_DOTNET%) do (
        call :contains "!arg_list!" "%%D" result
        if "!result!"=="true" (
            if "!SERVICES_A_COMPILER_DOTNET!"=="" (
                set "SERVICES_A_COMPILER_DOTNET=%%D"
            ) else (
                set "SERVICES_A_COMPILER_DOTNET=!SERVICES_A_COMPILER_DOTNET! %%D"
            )
        )
        set /a idx+=1
    )
) else (
    :: Si aucun argument, compiler tous les services
    set "SERVICES_A_COMPILER_JAVA=%DOSSIERS_JAVA%"
    set "SERVICES_A_COMPILER_DOTNET=%DOSSIERS_DOTNET%"
)


cd %~dp0\..

:: Lancement des services .NET (seulement ceux selectionnes)
if "!SERVICES_A_COMPILER_DOTNET!" neq "" (
    for %%D in (!SERVICES_A_COMPILER_DOTNET!) do (
        :: Trouver l'index du service pour recuperer le port
        set "service_idx=1"
        set "found=0"
        for %%S in (%DOSSIERS_DOTNET%) do (
            if "%%S"=="%%D" (
                set "found=!service_idx!"
            )
            set /a service_idx+=1
        )
        
        if !found! neq 0 (
            call :get_token !found! "%PORTS_DOTNET%" port
            taskkill /FI "WINDOWTITLE eq %%D" /F >nul 2>&1
            taskkill /FI "WINDOWTITLE eq %%D" /F >nul 2>&1
            echo Lancement de %%D [Dotnet/WebService]...
            start "%%D" cmd /c "cd /d %%D && echo [%%D] Demarrage application .NET... && dotnet run --urls=http://localhost:!port!"
        )
    )
)

if "!COMPILE_ONLY!"=="false" (
    :: Lancement SELECTIF des serveurs WildFly
    set i=1
    for %%F in (%STANDALONE_FILES%) do (
        call :get_token !i! "%DOSSIERS_JAVA%" service_name
        call :get_token !i! "%PORTS_MANAGER%" mgmt_port
        
        :: Vérifier si ce service doit être compilé
        call :contains "!SERVICES_A_COMPILER_JAVA!" "!service_name!" should_start
        
        if "!should_start!"=="true" (
            taskkill /FI "WINDOWTITLE eq !service_name!" /F >nul 2>&1
            taskkill /FI "WINDOWTITLE eq !service_name!" /F >nul 2>&1
            echo Lancement de !service_name! [Java/WildFly] sur port !mgmt_port!...%%D
            start "!service_name!" /min cmd /c "%WILDFLY_HOME%\bin\standalone.bat -c %%F"
        )
        set /a i+=1
    )
)

:: Déploiement des services Java (seulement ceux selectionnes)
if "!SERVICES_A_COMPILER_JAVA!" neq "" (
    for %%D in (!SERVICES_A_COMPILER_JAVA!) do (
        :: Trouver l'index du service pour recuperer port et deployment
        set "service_idx=1"
        set "found=0"
        for %%S in (%DOSSIERS_JAVA%) do (
            if "%%S"=="%%D" (
                set "found=!service_idx!"
            )
            set /a service_idx+=1
        )
        
        if !found! neq 0 (
            call :get_token !found! "%PORTS_JAVA%" port
            call :get_token !found! "%PORTS_MANAGER%" manager
            call :get_token !found! "%DEPLOYMENTS%" deployment
            
            echo [%%D] Compilation et déploiement sur port !port!...
            cd %%D
            echo Compilation Maven...
            call mvn clean package
            echo Nettoyage du dossier !deployment!...
            if exist "!deployment!\*" (
                del /Q "!deployment!\*"
            )
            echo Copie du WAR vers !deployment!...
            if exist "target\%%D.war" (
                copy "target\%%D.war" "!deployment!\"
                echo Fichier %%D.war copie avec succes
            ) else if exist "target\%%D-*.war" (
                for %%F in (target\%%D-*.war) do (
                    copy "%%F" "!deployment!\%%D.war"
                    echo Fichier %%~nxF renomme en %%D.war et copie
                )
            ) else (
                echo ERREUR: Aucun fichier WAR trouve dans target\
            )
            cd ..
        )
    )
)
if "!COMPILE_ONLY!"=="false" (
    echo =============================================================
    echo    Systemes en cours de demarrage...
    echo =============================================================

    :: Affichage des URLs seulement pour les services compiles
    set "count=1"
    for %%D in (%DOSSIERS_JAVA%) do (
        call :contains "!SERVICES_A_COMPILER_JAVA!" "%%D" is_compiled
        if "!is_compiled!"=="true" (
            call :get_token !count! "%PORTS_JAVA%" port
            echo [!port!]  %%D : http://localhost:!port!/%%D
        )
        set /a count+=1
    )
    set "count=1"
    for %%D in (%DOSSIERS_DOTNET%) do (
        call :contains "!SERVICES_A_COMPILER_DOTNET!" "%%D" is_compiled
        if "!is_compiled!"=="true" (
            call :get_token !count! "%PORTS_DOTNET%" port
            echo [!port!]  %%D : http://localhost:!port!/
        )
        set /a count+=1
    )

    echo =============================================================
)
pause
goto :eof

:get_token
setlocal enabledelayedexpansion
set /a token_index=%1
set "token_string=%~2"
set "var_name=%~3"

set /a current_index=1
for %%T in (%token_string%) do (
    if !current_index! equ !token_index! (
        endlocal
        set "%var_name%=%%T"
        goto :eof
    )
    set /a current_index+=1
)
endlocal
goto :eof

:contains
setlocal enabledelayedexpansion
set "string=%~1"
set "substring=%~2"
set "result_var=%~3"

set "result=false"
if not "!string!"=="" (
    echo !string! | find /i "!substring!" >nul && set "result=true"
)

endlocal & set "%result_var%=%result%"
goto :eof