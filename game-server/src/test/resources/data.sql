/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost:3306
 Source Schema         : game_data_001

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 31/10/2020 22:12:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ConfigCommonValue
-- ----------------------------
DROP TABLE IF EXISTS `configcommonvalue`;
CREATE TABLE `configcommonvalue` (
  `id` varchar(32) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of configcommonvalue
-- ----------------------------
BEGIN;
INSERT INTO `configcommonvalue` VALUES ('playerMaxLevel', '999', NULL);
COMMIT;

-- ----------------------------
-- Table structure for configactivity
-- ----------------------------
DROP TABLE IF EXISTS `configactivity`;
CREATE TABLE `configactivity` (
  `id` int(11) NOT NULL,
  `type` int(255) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configactivity
-- ----------------------------
BEGIN;
INSERT INTO `configactivity` VALUES (1, 1, '首充送好礼');
COMMIT;

-- ----------------------------
-- Table structure for configfunction
-- ----------------------------
DROP TABLE IF EXISTS `configfunction`;
CREATE TABLE `configfunction` (
  `id` int(11) NOT NULL,
  `openType` int(11) NOT NULL,
  `openParams` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for confignotice
-- ----------------------------
DROP TABLE IF EXISTS `confignotice`;
CREATE TABLE `confignotice` (
  `id` int(11) NOT NULL,
  `module` varchar(255) DEFAULT NULL,
  `channel` smallint(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of confignotice
-- ----------------------------
BEGIN;
INSERT INTO `confignotice` VALUES (1001, '??', 0, '??????');
COMMIT;

-- ----------------------------
-- Table structure for configplayerlevel
-- ----------------------------
DROP TABLE IF EXISTS `configplayerlevel`;
CREATE TABLE `configplayerlevel` (
  `level` int(11) DEFAULT NULL,
  `needExp` bigint(20) DEFAULT NULL,
  `vitality` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configplayerlevel
-- ----------------------------
BEGIN;
INSERT INTO `configplayerlevel` VALUES (1, 2345, 100);
INSERT INTO `configplayerlevel` VALUES (2, 23450, 105);
COMMIT;

-- ----------------------------
-- Table structure for configskill
-- ----------------------------
DROP TABLE IF EXISTS `configskill`;
CREATE TABLE `configskill` (
  `id` int(11) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `effect` varchar(255) DEFAULT NULL COMMENT '????',
  `needLevel` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of configskill
-- ----------------------------
BEGIN;
INSERT INTO `configskill` VALUES (1, '飞龙探云手', '飞龙探云手', 1);
INSERT INTO `configskill` VALUES (2, '酒神', '酒神', 1);
INSERT INTO `configskill` VALUES (3, '三味真火', '三味真火', 10);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
