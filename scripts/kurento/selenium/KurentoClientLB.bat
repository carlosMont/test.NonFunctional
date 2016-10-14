@echo off
rem setlocal enabledelayedexpansion
SET logFile=c:\KurentoSE\Kurento_LB.log
echo %date% %time% Inicio Escenario > %logFile%
SET contador=0
:bucle
if %contador% LSS 5 (
	set /a contador=%contador%+1
	echo %date% %time% %contador% >> %logFile%
		java -jar c:\KurentoSE\KurentoClientLB.jar
	TIMEOUT /T 240
	goto :bucle
)
TIMEOUT /T 14400
TASKKILL /T /F /IM Chrome.exe
echo %date% %time% Fin Escenario >> %logFile%
EXIT