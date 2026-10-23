@echo off
setlocal
title Stop Diffy

echo ==========================================
echo Diffy 프로세스 검색 및 종료
echo ==========================================

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ps = Get-CimInstance Win32_Process | Where-Object { $_.Name -in @('java.exe','javaw.exe') -and $_.CommandLine -match 'diffy\.jar' }; if (-not $ps) { Write-Host 'Diffy 프로세스를 찾지 못했습니다.'; exit 1 }; foreach ($p in $ps) { Write-Host ('Diffy PID: ' + $p.ProcessId); Write-Host ('Command: ' + $p.CommandLine); Stop-Process -Id $p.ProcessId -Force -ErrorAction Stop; Write-Host '종료 명령을 전달했습니다.' }"

echo ==========================================
pause
endlocal
```