@echo off
cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysql --host=localhost --user=root --port=3306 --password --default-character-set=utf8 --comments < "C:\MYTERM\mysql\dump\myTermDBDump071315.sql"
echo Done!
pause
exit