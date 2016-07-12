--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `mpark`.`carpark` DROP PRIMARY KEY;

ALTER TABLE `mpark`.`space` DROP PRIMARY KEY;

ALTER TABLE `mpark`.`allocation` DROP PRIMARY KEY;

ALTER TABLE `mpark`.`requests` DROP PRIMARY KEY;

ALTER TABLE `mpark`.`updates` DROP PRIMARY KEY;

ALTER TABLE `mpark`.`user` DROP PRIMARY KEY;

DROP TABLE `mpark`.`requests`;

DROP TABLE `mpark`.`user`;

DROP TABLE `mpark`.`updates`;

DROP TABLE `mpark`.`space`;

DROP TABLE `mpark`.`allocation`;

DROP TABLE `mpark`.`carpark`;

CREATE TABLE `mpark`.`requests` (
	`requestid` INT NOT NULL,
	`carparkid` INT,
	`spaceid` INT,
	`requestoruserid` VARCHAR(45),
	`owneruserid` VARCHAR(45),
	`requestdate` DATE,
	PRIMARY KEY (`requestid`)
) ENGINE=InnoDB;

CREATE TABLE `mpark`.`user` (
	`userid` VARCHAR(45) NOT NULL,
	`name` VARCHAR(45),
	`telephone` VARCHAR(45),
	`defaultcarparkid` INT,
	`password` VARCHAR(45),
	`isadmin` INT,
	`persistentsessionid` VARCHAR(100),
	PRIMARY KEY (`userid`)
) ENGINE=InnoDB;

CREATE TABLE `mpark`.`updates` (
	`lastchange` TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	PRIMARY KEY (`lastchange`)
) ENGINE=InnoDB;

CREATE TABLE `mpark`.`space` (
	`spaceid` INT NOT NULL,
	`carparkid` INT,
	`defaultownerid` VARCHAR(45),
	`spacename` VARCHAR(45),
	`spacedescription` VARCHAR(45),
	PRIMARY KEY (`spaceid`)
) ENGINE=InnoDB;

CREATE TABLE `mpark`.`allocation` (
	`allocationid` INT NOT NULL,
	`date` DATE,
	`spaceid` INT,
	`userid` VARCHAR(45),
	PRIMARY KEY (`allocationid`)
) ENGINE=InnoDB;

CREATE TABLE `mpark`.`carpark` (
	`carparkid` INT NOT NULL,
	`carparkname` VARCHAR(45),
	`carparkaddress` VARCHAR(100),
	PRIMARY KEY (`carparkid`)
) ENGINE=InnoDB;

