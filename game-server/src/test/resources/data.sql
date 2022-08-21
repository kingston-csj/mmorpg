/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : game_data_001

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 30/6/2022 22:12:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for configactivity
-- ----------------------------
DROP TABLE IF EXISTS `configactivity`;
CREATE TABLE `configactivity`  (
  `id` int(11) NOT NULL,
  `type` int(255) NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configactivity
-- ----------------------------
INSERT INTO `configactivity` VALUES (1, 1, '首充送好礼');

-- ----------------------------
-- Table structure for configcommonvalue
-- ----------------------------
DROP TABLE IF EXISTS `configcommonvalue`;
CREATE TABLE `configcommonvalue`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configcommonvalue
-- ----------------------------
INSERT INTO `configcommonvalue` VALUES ('playerMaxLevel', '999');
INSERT INTO `configcommonvalue` VALUES ('specialLevels', '[100,200,300]');

-- ----------------------------
-- Table structure for configconstant
-- ----------------------------
DROP TABLE IF EXISTS `configconstant`;
CREATE TABLE `configconstant`  (
  `id` int(11) NOT NULL,
  `intValue` int(255) NULL DEFAULT NULL,
  `StringValue` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descrpition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configconstant
-- ----------------------------
INSERT INTO `configconstant` VALUES (1, 500, '11;22', '玩家最高等级');

-- ----------------------------
-- Table structure for configcross
-- ----------------------------
DROP TABLE IF EXISTS `configcross`;
CREATE TABLE `configcross`  (
  `id` int(11) NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gamePort` int(255) NULL DEFAULT NULL,
  `rpcPort` int(255) NULL DEFAULT NULL,
  `crossServer` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configcross
-- ----------------------------
INSERT INTO `configcross` VALUES (10001, '127.0.0.1', 'Game1', 9527, 9627, 80001);
INSERT INTO `configcross` VALUES (80001, '127.0.0.1', 'Center1', 9528, 9628, 0);

-- ----------------------------
-- Table structure for configfunction
-- ----------------------------
DROP TABLE IF EXISTS `configfunction`;
CREATE TABLE `configfunction`  (
  `id` int(11) NOT NULL,
  `openType` int(11) NOT NULL,
  `openParams` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for configmap
-- ----------------------------
DROP TABLE IF EXISTS `configmap`;
CREATE TABLE `configmap`  (
  `id` int(11) NOT NULL,
  `mapType` tinyint(11) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `height` int(11) NOT NULL,
  `width` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for confignotice
-- ----------------------------
DROP TABLE IF EXISTS `confignotice`;
CREATE TABLE `confignotice`  (
  `id` int(11) NOT NULL,
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `channel` smallint(6) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of confignotice
-- ----------------------------
INSERT INTO `confignotice` VALUES (1001, '基础', 0, '功能暂未开放');

-- ----------------------------
-- Table structure for configplayerlevel
-- ----------------------------
DROP TABLE IF EXISTS `configplayerlevel`;
CREATE TABLE `configplayerlevel`  (
  `level` int(11) NULL DEFAULT NULL,
  `needExp` bigint(20) NULL DEFAULT NULL,
  `vitality` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configplayerlevel
-- ----------------------------
INSERT INTO `configplayerlevel` VALUES (1, 2345, 100);
INSERT INTO `configplayerlevel` VALUES (2, 23450, 105);

-- ----------------------------
-- Table structure for configskill
-- ----------------------------
DROP TABLE IF EXISTS `configskill`;
CREATE TABLE `configskill`  (
  `id` int(11) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `effect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作用说明',
  `needLevel` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configskill
-- ----------------------------
INSERT INTO `configskill` VALUES (1, '飞龙探云手', '偷取敌人东西或金钱', 1);
INSERT INTO `configskill` VALUES (2, '逍遥神剑', '李逍遥自创的绝招 敌方全体', 1);
INSERT INTO `configskill` VALUES (3, '泰山压顶', '土系高级法术', 10);

SET FOREIGN_KEY_CHECKS = 1;
