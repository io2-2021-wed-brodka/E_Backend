@echo off
set TYPE=%1
set COMMAND=%2
set CORRECT=TRUE

if "%TYPE%" == "" set CORRECT=FALSE
if "%COMMAND%" == "" set CORRECT=FALSE

if %CORRECT% == FALSE (
    echo --------------------------------------------------------------
    echo   Executes Flyway's COMMAND on the database of type TYPE
    echo --------------------------------------------------------------
    echo   USAGE: flyway.cmd TYPE COMMAND
    echo --------------------------------------------------------------
    echo   TYPE - check config catalogue
    echo   COMMAND - migrate, clean, info, validate, baseline, repair
    echo --------------------------------------------------------------
) else (
    mvnw.cmd flyway:%COMMAND% -Dflyway.configFiles=config\flyway-%TYPE%.properties
)

