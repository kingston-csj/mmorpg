/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.121
 Source Server Type    : MySQL
 Source Server Version : 80400 (8.4.0)
 Source Host           : 192.168.0.121:3306
 Source Schema         : game_user_001

 Target Server Type    : MySQL
 Target Server Version : 80400 (8.4.0)
 File Encoding         : 65001

 Date: 12/06/2024 20:31:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for accountent
-- ----------------------------
DROP TABLE IF EXISTS `accountent`;
CREATE TABLE `accountent`  (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of accountent
-- ----------------------------
INSERT INTO `accountent` VALUES (123, 'kingston');

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` bigint NULL DEFAULT NULL,
  `accountId` bigint NULL DEFAULT NULL,
  `level` int NULL DEFAULT 1,
  `name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `job` tinyint NULL DEFAULT 0,
  `exp` bigint NULL DEFAULT 0,
  `lastDailyReset` bigint NULL DEFAULT NULL,
  `vipRightJson` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `platform` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES (10000, NULL, 99, 'kingston', 1, 12345, NULL, NULL, NULL);
INSERT INTO `player` VALUES (2815132638074568705, NULL, 0, 'robot_1000', 0, 0, 1547125647521, 'null', 'null');
INSERT INTO `player` VALUES (2815132638084726786, NULL, 0, 'robot_1000', 0, 0, 1547125647521, 'null', 'null');
INSERT INTO `player` VALUES (2815132639352520706, 0, 0, 'robot_1000', 0, 0, 0, 'null', 'null');
INSERT INTO `player` VALUES (2815132639356715010, 0, 0, 'robot_1000', 0, 0, 0, 'null', 'null');

-- ----------------------------
-- Table structure for playerent
-- ----------------------------
DROP TABLE IF EXISTS `playerent`;
CREATE TABLE `playerent`  (
  `playerId` bigint NOT NULL,
  `data` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `accountId` bigint NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `id` bigint NOT NULL,
  `vipRight` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`playerId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of playerent
-- ----------------------------
INSERT INTO `playerent` VALUES (10000, NULL, 123, 99, 'winner', 10000, NULL);

-- ----------------------------
-- Table structure for systemrecord
-- ----------------------------
DROP TABLE IF EXISTS `systemrecord`;
CREATE TABLE `systemrecord`  (
  `key` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `value` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of systemrecord
-- ----------------------------
INSERT INTO `systemrecord` VALUES ('dailyResetTimestamp', '1551414213880');

SET FOREIGN_KEY_CHECKS = 1;
