cd "C:\Program Files\MySQL\MySQL Server 5.6\bin"
mysql --host=localhost --user=root --password < C:\MYTERM\mysql\myterm_Schema.sql
mysql -h localhost -u adminmyterm -p myterm < C:\MYTERM\mysql\schema_myterm.sql
mysql -h localhost -u adminmyterm -p myterm < C:\MYTERM\mysql\create_view.sql
pause
