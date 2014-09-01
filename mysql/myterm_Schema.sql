CREATE SCHEMA `myterm`;
CREATE USER `adminmyterm`@`localhost` IDENTIFIED BY 'x';
GRANT ALL PRIVILEGES ON myterm.* to `adminmyterm`@`localhost` WITH GRANT OPTION;