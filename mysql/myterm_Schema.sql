SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=`ANSI`;
USE mysql;
DROP PROCEDURE IF EXISTS mysql.drop_user_if_exists;
DELIMITER $$
CREATE PROCEDURE mysql.drop_user_if_exists()
BEGIN
  DECLARE foo BIGINT DEFAULT 0 ;
  SELECT COUNT(*)
  INTO foo
    FROM mysql.user
      WHERE User = `adminmyterm` and  Host = `localhost`;
   IF foo > 0 THEN
   		REVOKE ALL PRIVILEGES, GRANT OPTION FROM `adminmyterm`@`localhost`;
        DROP USER `adminmyterm`@`localhost`;
  END IF;
END ;$$
DELIMITER ;
CALL mysql.drop_user_if_exists();

DROP PROCEDURE IF EXISTS mysql.drop_users_if_exists;
SET SQL_MODE=@OLD_SQL_MODE ;

DROP SCHEMA IF EXISTS `myterm`;
CREATE SCHEMA `myterm`;
CREATE USER `adminmyterm`@`localhost` IDENTIFIED BY `x`;
GRANT ALL PRIVILEGES ON myterm.* to `adminmyterm`@`localhost` WITH GRANT OPTION;