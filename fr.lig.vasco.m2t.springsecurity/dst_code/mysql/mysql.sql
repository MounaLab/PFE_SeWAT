
-- Dumping database structure for the web apllication
CREATE DATABASE IF NOT EXISTS `RootElement` 
USE `RootElement`;

-- Dumping structure for table users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping structure for table authorities
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping data for table users: 
INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
	('Marie', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 1),
	('Martin', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 1),
	('Peter', '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 1),
;


-- Dumping data for table authorities:
INSERT INTO `authorities` (`username`, `authority`) VALUES
;

-- Dumping data for table authorities:
INSERT INTO `authorities` (`username`, `authority`) VALUES
;

-- Dumping data for table authorities:
INSERT INTO `authorities` (`username`, `authority`) VALUES
	('Marie', 'ROLE_SECRETARY'),
	('Martin', 'ROLE_NURSE'),
	('Peter', 'ROLE_DOCTOR'),
;

-- Dumping data for table authorities:
INSERT INTO `authorities` (`username`, `authority`) VALUES
;

-- Dumping data for table authorities:
INSERT INTO `authorities` (`username`, `authority`) VALUES
;
