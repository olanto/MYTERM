@echo off
cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysql --host=localhost --user=root --password -myterm -e "source C:\MYTERM\mysql\dump\myTermDBDump071315.sql;"
echo Done!
pause
exit