delimiter $$

CREATE TABLE `general_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` datetime DEFAULT NULL,
  `log` text,
  `note` text,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=420 DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `permissions` (
  `bot` varchar(255) NOT NULL,
  `user` int(11) NOT NULL,
  `read` binary(1) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `profiles` (
  `uid` varchar(255) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `map` text,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `query_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `input_original` text,
  `input_parsed` text,
  `output` text,
  `uid` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `bot` varchar(255) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=756 DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$


