-- MySQL dump 10.13  Distrib 5.6.41, for macos10.13 (x86_64)
--
-- Host: localhost    Database: game_data_001
-- ------------------------------------------------------
-- Server version	5.6.41

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
-- Table structure for table `configconstant`
--

DROP TABLE IF EXISTS `configconstant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configconstant` (
  `id` int(11) NOT NULL,
  `intValue` int(255) DEFAULT NULL,
  `StringValue` varchar(512) DEFAULT NULL,
  `descrpition` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configconstant`
--

LOCK TABLES `configconstant` WRITE;
/*!40000 ALTER TABLE `configconstant` DISABLE KEYS */;
INSERT INTO `configconstant` VALUES (1,500,'11;22','玩家最高等级');
/*!40000 ALTER TABLE `configconstant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configfunction`
--

DROP TABLE IF EXISTS `configfunction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configfunction` (
  `id` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `openType` varchar(16) DEFAULT NULL,
  `openTarget` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configfunction`
--

LOCK TABLES `configfunction` WRITE;
/*!40000 ALTER TABLE `configfunction` DISABLE KEYS */;
/*!40000 ALTER TABLE `configfunction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confignotice`
--

DROP TABLE IF EXISTS `confignotice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `confignotice` (
  `id` int(11) NOT NULL,
  `module` varchar(255) DEFAULT NULL,
  `channel` smallint(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confignotice`
--

LOCK TABLES `confignotice` WRITE;
/*!40000 ALTER TABLE `confignotice` DISABLE KEYS */;
INSERT INTO `confignotice` VALUES (1001,'基础',0,'功能暂未开放');
/*!40000 ALTER TABLE `confignotice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configplayerlevel`
--

DROP TABLE IF EXISTS `configplayerlevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configplayerlevel` (
  `level` int(11) DEFAULT NULL,
  `needExp` bigint(20) DEFAULT NULL,
  `vitality` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configplayerlevel`
--

LOCK TABLES `configplayerlevel` WRITE;
/*!40000 ALTER TABLE `configplayerlevel` DISABLE KEYS */;
INSERT INTO `configplayerlevel` VALUES (1,2345,100),(2,23450,105);
/*!40000 ALTER TABLE `configplayerlevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configskill`
--

DROP TABLE IF EXISTS `configskill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configskill` (
  `id` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `effect` varchar(255) DEFAULT NULL COMMENT '作用说明',
  `needLevel` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configskill`
--

LOCK TABLES `configskill` WRITE;
/*!40000 ALTER TABLE `configskill` DISABLE KEYS */;
INSERT INTO `configskill` VALUES (1,'飞龙探云手','偷取敌人东西或金钱',1),(2,'逍遥神剑','李逍遥自创的绝招 敌方全体',1),(3,'泰山压顶','土系高级法术',10);
/*!40000 ALTER TABLE `configskill` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-15 20:18:13
