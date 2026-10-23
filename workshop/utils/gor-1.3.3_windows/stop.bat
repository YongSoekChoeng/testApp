rem Ctrl+C 또는 실행 종료 시 프로세스 완전 강제 종료
echo.
echo [안내] GoReplay 프로세스를 종료합니다...
taskkill /f /im gor.exe > nul 2>&1
echo [완료] 안전하게 종료되었습니다.
pause