DROP DATABASE IF EXISTS `Acme-Antenna`;
CREATE DATABASE `Acme-Antenna`;

-- This creates the user, if it does not exist, which is required to
-- portably implement "DROP USER IF EXISTS".
SET @mode = @@SESSION.sql_mode;
SET SESSION sql_mode = REPLACE(REPLACE(@mode, 'NO_AUTO_CREATE_USER', ''), ',,', ',');
GRANT USAGE ON `Acme-Antenna`.* TO 'acme-user'@'%' IDENTIFIED BY 'a';
GRANT USAGE ON `Acme-Antenna`.* TO 'acme-manager'@'%' IDENTIFIED BY 'a';
SET SESSION sql_mode = @mode;

DROP USER 'acme-user'@'%';
DROP USER 'acme-manager'@'%';

CREATE USER 'acme-user'@'%' IDENTIFIED BY 'ACME-Us3r-P@ssw0rd';
CREATE USER 'acme-manager'@'%' IDENTIFIED BY 'ACME-M@n@ger-6874';

GRANT SELECT, INSERT, UPDATE, DELETE
	ON `Acme-Antenna`.* TO 'acme-user'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER,
        CREATE TEMPORARY TABLES, LOCK TABLES, CREATE VIEW, CREATE ROUTINE,
        ALTER ROUTINE, EXECUTE, TRIGGER, SHOW VIEW
    ON `Acme-Antenna`.* TO 'acme-manager'@'%';
