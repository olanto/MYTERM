CREATE TABLE `ch_admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_full_name` varchar(255) DEFAULT NULL,
  `admin_name` varchar(255) NOT NULL DEFAULT '',
  `admin_email` varchar(255) NOT NULL DEFAULT '',
  `admin_profile` int(11) NOT NULL DEFAULT '0',
  `admin_pass` varchar(255) NOT NULL DEFAULT '',
  `pwd_last_change_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_modified` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_login_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_login_ip` varchar(255) NOT NULL DEFAULT '',
  `failed_logins` smallint(4) unsigned NOT NULL DEFAULT '0',
  `last_failed_attempt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_failed_ip` varchar(255) NOT NULL DEFAULT '',
  `status` enum('Y','N') NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`admin_id`),
  KEY `idx_admin_name_zen` (`admin_name`),
  KEY `idx_admin_email_zen` (`admin_email`),
  KEY `idx_admin_profile_zen` (`admin_profile`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

INSERT INTO `ch_admin` VALUES (1,'Demo Demo','admin','demo@gmail.com',1,'2c10306d4ab7e34f1a9b94c6b77e6a3d:b5','2013-03-30 22:22:21','2013-03-30 22:22:21','2013-02-21 18:27:27','::1',0,'2012-11-13 21:56:59','127.0.0.1','Y'),(14,'Brian Lara','brian','brian@gmail.com',1,'2667872896d766932f53a33e2296bf05:a8','2013-05-16 20:19:40','2013-05-16 20:19:40','0000-00-00 00:00:00','',0,'0000-00-00 00:00:00','','Y'),(15,'Vikash Kumar','vikash','kumar.vikash8961@gmail.com',0,'63582b7efcbd0806e2385f0c7a64ce1b:e8','0000-00-00 00:00:00','2013-05-16 23:53:29','0000-00-00 00:00:00','',0,'0000-00-00 00:00:00','','Y'),(16,'philip Darville','philip','philip@gmail.com',0,'56b312135cbaab63b3e6c70fca3b134e:a4','0000-00-00 00:00:00','2013-05-17 01:44:02','0000-00-00 00:00:00','',0,'0000-00-00 00:00:00','','Y');
