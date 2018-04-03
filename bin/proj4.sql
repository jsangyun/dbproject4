-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: localhost    Database: musicapp
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contain`
--

DROP TABLE IF EXISTS `contain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contain` (
  `pl_id` smallint(5) unsigned NOT NULL,
  `mu_id` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`pl_id`,`mu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contain`
--

LOCK TABLES `contain` WRITE;
/*!40000 ALTER TABLE `contain` DISABLE KEYS */;
INSERT INTO `contain` VALUES (1,3),(3,2);
/*!40000 ALTER TABLE `contain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `m_email` varchar(30) NOT NULL,
  `m_name` varchar(20) NOT NULL,
  `m_pw` varchar(20) NOT NULL DEFAULT '00000000',
  PRIMARY KEY (`m_email`),
  UNIQUE KEY `m_pw` (`m_pw`),
  UNIQUE KEY `m_pw_2` (`m_pw`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('hello@world.com','Peter','tothsus'),('jsangyun1039@daum.net','정상윤','newboy2017');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `music` (
  `m_title` varchar(30) NOT NULL,
  `m_artist` varchar(20) NOT NULL,
  `m_playtime` varchar(10) NOT NULL,
  `m_lyric` varchar(2000) DEFAULT NULL,
  `manager_mail` varchar(30) NOT NULL,
  `m_pid` smallint(5) unsigned DEFAULT NULL,
  `m_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`m_id`),
  KEY `manager_mail` (`manager_mail`),
  KEY `m_pid` (`m_pid`),
  CONSTRAINT `music_ibfk_1` FOREIGN KEY (`manager_mail`) REFERENCES `manager` (`m_email`),
  CONSTRAINT `music_ibfk_2` FOREIGN KEY (`m_pid`) REFERENCES `contain` (`pl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES ('Back In White','AC/DC','4:16','None','jsangyun1039@daum.net',NULL,1),('AC/DC','AC/DC','29:20','none','jsangyun1039@daum.net',NULL,2),('hi','hello','10:32','none','jsangyun1039@daum.net',NULL,3);
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `number`
--

DROP TABLE IF EXISTS `number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `number` (
  `user_num` smallint(5) unsigned NOT NULL DEFAULT '0',
  `music_num` smallint(5) unsigned NOT NULL DEFAULT '0',
  `n_id` smallint(1) unsigned NOT NULL DEFAULT '0',
  `playlist_id` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`n_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `number`
--

LOCK TABLES `number` WRITE;
/*!40000 ALTER TABLE `number` DISABLE KEYS */;
INSERT INTO `number` VALUES (2,1,0,2);
/*!40000 ALTER TABLE `number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist`
--

DROP TABLE IF EXISTS `playlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `playlist` (
  `p_name` varchar(30) NOT NULL,
  `uid` varchar(20) NOT NULL,
  `p_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`p_id`),
  KEY `uid` (`uid`),
  CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist`
--

LOCK TABLES `playlist` WRITE;
/*!40000 ALTER TABLE `playlist` DISABLE KEYS */;
INSERT INTO `playlist` VALUES ('Good','PeterPan',1),('Performance','PeterPan',2),('Wow','PeterPan',4),('her','PeterPan',5);
/*!40000 ALTER TABLE `playlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `u_hpnumber` varchar(20) NOT NULL,
  `u_email` varchar(30) NOT NULL,
  `u_birth` varchar(10) NOT NULL,
  `u_name` varchar(20) NOT NULL,
  `u_id` varchar(20) NOT NULL,
  `u_pw` varchar(20) NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_hpnumber` (`u_hpnumber`),
  UNIQUE KEY `u_email` (`u_email`),
  UNIQUE KEY `u_pw` (`u_pw`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('010-0123-1231','tothsus@naver.com','1998.02.04','Patrick','patrick1023','tothsus'),('192-1231-5123','hippo@gmail.com','1029.2.30','Peter','PeterPan','wonderland');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-06 23:16:10
