/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : game_user_001

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-03-04 14:54:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('123', 'null');

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` bigint(20) DEFAULT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `level` int(11) DEFAULT '1',
  `name` varchar(128) DEFAULT NULL,
  `job` tinyint(4) DEFAULT '0',
  `exp` bigint(20) DEFAULT '0',
  `lastDailyReset` bigint(255) DEFAULT NULL,
  `vipRightJson` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES ('10000', null, '99', 'kingston', '1', '12345', null, null, null);
INSERT INTO `player` VALUES ('2815132638074568705', null, '0', 'robot_1000', '0', '0', '1547125647521', 'null', 'null');
INSERT INTO `player` VALUES ('2815132638084726786', null, '0', 'robot_1000', '0', '0', '1547125647521', 'null', 'null');
INSERT INTO `player` VALUES ('2815132639352520706', '0', '0', 'robot_1000', '0', '0', '0', 'null', 'null');
INSERT INTO `player` VALUES ('2815132639356715010', '0', '0', 'robot_1000', '0', '0', '0', 'null', 'null');

-- ----------------------------
-- Table structure for playerent
-- ----------------------------
DROP TABLE IF EXISTS `playerent`;
CREATE TABLE `playerent` (
  `playerId` bigint(20) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of playerent
-- ----------------------------

-- ----------------------------
-- Table structure for systemrecord
-- ----------------------------
DROP TABLE IF EXISTS `systemrecord`;
CREATE TABLE `systemrecord` (
  `key` varchar(255) NOT NULL,
  `value` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemrecord
-- ----------------------------
INSERT INTO `systemrecord` VALUES ('dailyResetTimestamp', '1551414213880');
