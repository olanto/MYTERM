@echo off 
cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysqldump --host=localhost --user=root --password --result-file="C:\MYTERM\mysql\dump\myTermDBDump071315.sql" myterm
echo Done!
pause
exit