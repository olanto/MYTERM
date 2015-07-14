@echo off 
cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysqldump --default-character-set=utf8 --add-drop-database --add-drop-table --dump-date --add-locks --host=localhost --port=3306 --user=root --password --result-file="C:\MYTERM\mysql\dump\myTermDBDump071315.sql"  --databases myterm
echo Done!
pause
exit