/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : gameforum

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 11/06/2023 13:55:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `article_type_id` int NOT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `game_id` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `view` int NOT NULL DEFAULT 0,
  `likes` int NOT NULL DEFAULT 0,
  `user_id` int NOT NULL,
  `is_top` int NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '这是一个标题', 1, NULL, 1, NULL, '2016年3月，AlphaGo在经过长达五个小时的厮杀后，最终以4:1战胜世界冠军、围棋九段选手李世石，这标志着人工智能在这一最具“挑战性”之一的领域，已经达到甚至超越了人类的水平。<br/>\r\n随着人工智能的发展与不断成熟，其开始进入到了更多人的视野中，并逐渐进入我们的生活，从围棋开始，人工智能不断进入到编程、绘画、医药等领域，并以其强大的学习能力以及高超的效率展现出将可能取代相关工作者的势头，那未来是否会真正的取代，而被取代的人类将何去何从，人们又将如何与人工智能相处，这些问题我们都需要认真分析。\r\n', 1, 1, 1, 1, '2023-05-22 18:15:48', '2023-06-09 21:31:18', 0);
INSERT INTO `article` VALUES (2, '约深干无', 1, NULL, 6, NULL, 'dolor sit voluptate laborum ut', 0, 0, 1, 0, '2023-05-23 15:02:47', '2023-05-23 15:02:47', 1);
INSERT INTO `article` VALUES (3, '划按别月且次状', 1, NULL, 1, NULL, 'officia', 0, 0, 1, 0, '2023-05-23 15:03:00', '2023-05-23 15:03:00', 1);
INSERT INTO `article` VALUES (4, '存因究族三', 1, NULL, 1, NULL, 'Excepteur in ut Duis', 0, 0, 1, 0, '2023-05-23 15:03:10', '2023-05-29 19:47:25', 0);
INSERT INTO `article` VALUES (5, '心容定', 1, NULL, 1, NULL, 'dolore', 0, 0, 1, 0, '2023-05-23 15:03:15', '2023-05-23 15:03:15', 1);
INSERT INTO `article` VALUES (6, '写合员每照', 1, NULL, 2, NULL, 'amet sunt non magna Ut', 0, 0, 1, 0, '2023-05-23 15:03:22', '2023-05-23 15:03:22', 1);
INSERT INTO `article` VALUES (7, '速么术军因前', 1, NULL, 2, NULL, 'enim Excepteur ex culpa id', 0, 0, 1, 0, '2023-05-23 15:03:32', '2023-05-23 15:03:32', 0);
INSERT INTO `article` VALUES (8, '测试', 1, 'http://localhost:8080//images/d7d29d8c-8cb9-48a5-9b45-68ca7d9db1de.jpeg', 1, '1231312313', '<p data-v-md-line=\"1\">阿达啊啊啊啊啊啊<br>\n<img src=\"http://localhost:8080//images/d70ed823-0d50-47f2-bf42-22cc6a54a7b7.jpeg\" alt=\"图片\"></p>\n', 2, 1, 1, 1, '2023-05-28 21:47:23', '2023-06-09 21:34:01', 0);

-- ----------------------------
-- Table structure for article_type
-- ----------------------------
DROP TABLE IF EXISTS `article_type`;
CREATE TABLE `article_type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_user` int NOT NULL,
  `update_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_type
-- ----------------------------
INSERT INTO `article_type` VALUES (1, '攻略', 1, 1, '2023-05-23 15:20:15', '2023-05-23 15:20:10', 0);
INSERT INTO `article_type` VALUES (2, '分享', 1, 1, '2023-05-29 19:48:20', '2023-05-29 19:48:23', 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NULL DEFAULT NULL,
  `comment_id` int NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `likes` int NOT NULL DEFAULT 0,
  `user_id` int NOT NULL,
  `reply_user_id` int NOT NULL,
  `is_top` int NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, NULL, 'aaa', 1, 1, 1, 0, '2023-05-23 18:04:21', '2023-05-27 16:37:18', 1);
INSERT INTO `comment` VALUES (2, 1, NULL, '1231311', 0, 1, 1, 0, '2023-05-26 22:05:30', '2023-05-29 21:49:57', 0);
INSERT INTO `comment` VALUES (3, 1, NULL, '12321313', 0, 1, 1, 0, '2023-05-26 22:08:34', '2023-05-29 21:49:35', 0);
INSERT INTO `comment` VALUES (4, 1, NULL, '这是一条评论', 1, 1, 1, 0, '2023-05-27 15:15:59', '2023-05-29 21:55:20', 0);
INSERT INTO `comment` VALUES (5, 1, NULL, '这是修复后的一条评论', 0, 1, 1, 0, '2023-05-27 15:18:18', '2023-05-27 16:37:23', 1);
INSERT INTO `comment` VALUES (6, 1, NULL, '这次真的修复了！', 0, 1, 1, 0, '2023-05-27 15:19:20', '2023-05-27 16:37:22', 0);
INSERT INTO `comment` VALUES (7, 1, NULL, '发送', 1, 1, 1, 1, '2023-05-27 16:05:36', '2023-05-29 21:55:20', 0);
INSERT INTO `comment` VALUES (8, NULL, 3, '发送', 1, 1, 1, 0, '2023-05-27 16:05:45', '2023-05-27 20:02:12', 0);
INSERT INTO `comment` VALUES (9, 1, NULL, '一哈哈哈', 0, 1, 1, 0, '2023-05-27 16:46:38', '2023-05-27 16:46:38', 1);
INSERT INTO `comment` VALUES (10, 1, NULL, '在顶层显示最新评论', 0, 1, 1, 0, '2023-05-27 17:01:28', '2023-05-27 17:01:28', 1);
INSERT INTO `comment` VALUES (11, NULL, 1, '测试回复', 0, 1, 1, 0, '2023-05-27 18:53:17', '2023-05-27 18:53:17', 1);
INSERT INTO `comment` VALUES (12, NULL, 3, '12313123213', 1, 1, 1, 0, '2023-05-27 19:41:17', '2023-05-27 19:55:09', 0);
INSERT INTO `comment` VALUES (13, NULL, 1, '测试赛', 0, 1, 1, 0, '2023-05-27 19:43:42', '2023-05-27 19:43:42', 1);
INSERT INTO `comment` VALUES (14, NULL, 1, '绝对显示', 0, 1, 1, 0, '2023-05-27 19:44:50', '2023-05-27 19:44:50', 1);
INSERT INTO `comment` VALUES (15, 8, NULL, '第一条评论！', 1, 1, 1, 0, '2023-05-29 14:24:58', '2023-05-29 14:25:12', 0);

-- ----------------------------
-- Table structure for forum_setting
-- ----------------------------
DROP TABLE IF EXISTS `forum_setting`;
CREATE TABLE `forum_setting`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `integral` int NOT NULL,
  `article_exp` int NOT NULL,
  `comment_exp` int NOT NULL,
  `day_exp` int NOT NULL DEFAULT 66,
  `create_user` int NOT NULL,
  `update_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_setting
-- ----------------------------
INSERT INTO `forum_setting` VALUES (1, 5, 6, 3, 66, 1, 1, '2023-05-29 23:34:37', '2023-05-29 23:34:56', 0);

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` int NOT NULL,
  `platform` int NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NOT NULL DEFAULT 1,
  `create_user` int NOT NULL,
  `update_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES (1, '测试游戏1', 1, 1, '测试', 1, 1, 1, '2023-05-18 16:25:06', '2023-05-18 16:25:06', 0);
INSERT INTO `game` VALUES (2, '测试游戏2', 2, 1, '测试', 1, 1, 1, '2023-05-18 16:25:21', '2023-05-18 16:25:21', 0);
INSERT INTO `game` VALUES (3, '测试游戏3', 3, 1, '测试', 1, 1, 1, '2023-05-18 16:25:28', '2023-05-18 16:25:28', 0);
INSERT INTO `game` VALUES (4, '测试游戏4', 4, 1, '测试', 1, 1, 1, '2023-05-18 16:25:34', '2023-05-18 16:25:34', 0);
INSERT INTO `game` VALUES (5, '测试游戏5', 5, 1, '测试', 1, 1, 1, '2023-05-18 16:25:43', '2023-05-18 16:25:43', 0);
INSERT INTO `game` VALUES (6, '测试游戏6', 6, 1, '测试', 1, 1, 1, '2023-05-18 16:25:50', '2023-05-18 16:25:50', 0);
INSERT INTO `game` VALUES (7, '测试游戏7', 1, 2, '测试', 1, 1, 1, '2023-05-18 16:26:02', '2023-05-18 16:26:02', 0);
INSERT INTO `game` VALUES (8, '测试游戏8', 2, 2, '测试', 1, 1, 1, '2023-05-18 16:26:13', '2023-05-18 16:26:13', 0);
INSERT INTO `game` VALUES (9, '测试游戏9', 3, 2, '测试', 1, 1, 1, '2023-05-18 16:26:19', '2023-05-18 16:26:19', 0);
INSERT INTO `game` VALUES (10, '测试游戏10', 4, 2, '测试', 1, 1, 1, '2023-05-18 16:26:26', '2023-05-18 16:26:26', 0);
INSERT INTO `game` VALUES (11, '测试游戏11', 5, 2, '测试', 1, 1, 1, '2023-05-18 16:26:34', '2023-05-18 16:26:34', 0);
INSERT INTO `game` VALUES (12, '测试游戏12', 6, 2, '测试', 1, 1, 1, '2023-05-18 16:26:39', '2023-05-18 16:26:39', 0);
INSERT INTO `game` VALUES (13, '测试游戏13', 1, 3, '测试', 1, 1, 1, '2023-05-18 16:26:46', '2023-05-18 16:26:46', 0);
INSERT INTO `game` VALUES (14, '测试游戏14', 2, 3, '测试', 1, 1, 1, '2023-05-18 16:26:51', '2023-05-18 16:26:51', 0);
INSERT INTO `game` VALUES (15, '测试游戏15', 3, 3, '测试', 1, 1, 1, '2023-05-18 16:26:56', '2023-05-18 16:26:56', 0);
INSERT INTO `game` VALUES (16, '测试游戏16', 4, 3, '测试', 1, 1, 1, '2023-05-18 16:27:03', '2023-05-18 16:27:03', 0);
INSERT INTO `game` VALUES (17, '测试游戏17', 5, 3, '测试', 1, 1, 1, '2023-05-18 16:27:09', '2023-05-18 16:27:09', 0);
INSERT INTO `game` VALUES (18, '测试游戏18', 6, 3, '测试', 1, 1, 1, '2023-05-18 16:27:16', '2023-05-18 16:27:16', 0);
INSERT INTO `game` VALUES (19, '测试添加', 1, 3, '测试', 1, 1, 1, '2023-05-20 17:28:51', '2023-05-21 01:42:01', 0);
INSERT INTO `game` VALUES (20, '测试11111', 5, 6, '1111', 1, 1, 1, '2023-05-21 01:41:25', '2023-05-21 01:41:25', 1);
INSERT INTO `game` VALUES (21, '反馈', 4, 4, '1231', 0, 1, 1, '2023-05-30 13:54:44', '2023-05-30 13:54:44', 0);

-- ----------------------------
-- Table structure for game_type
-- ----------------------------
DROP TABLE IF EXISTS `game_type`;
CREATE TABLE `game_type`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int NOT NULL DEFAULT 1,
  `create_user` int NOT NULL,
  `update_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game_type
-- ----------------------------
INSERT INTO `game_type` VALUES (1, '策略', 1, 1, 1, '2023-05-18 16:21:48', '2023-05-18 16:21:48', 0);
INSERT INTO `game_type` VALUES (2, '动作', 1, 1, 1, '2023-05-18 16:23:06', '2023-05-18 16:23:06', 0);
INSERT INTO `game_type` VALUES (3, '冒险', 1, 1, 1, '2023-05-18 16:23:11', '2023-05-18 16:23:11', 0);
INSERT INTO `game_type` VALUES (4, '模拟', 1, 1, 1, '2023-05-18 16:23:16', '2023-05-18 16:23:16', 0);
INSERT INTO `game_type` VALUES (5, '角色扮演', 1, 1, 1, '2023-05-18 16:23:40', '2023-05-18 16:23:40', 0);
INSERT INTO `game_type` VALUES (6, '体育及竞速', 1, 1, 1, '2023-05-18 16:23:51', '2023-05-18 16:23:51', 0);
INSERT INTO `game_type` VALUES (7, '摆烂', 1, 1, 1, '2023-05-20 17:25:16', '2023-05-20 17:25:16', 0);
INSERT INTO `game_type` VALUES (8, '可以摆', 1, 1, 1, '2023-05-21 01:41:03', '2023-05-21 01:41:10', 1);

-- ----------------------------
-- Table structure for like_record
-- ----------------------------
DROP TABLE IF EXISTS `like_record`;
CREATE TABLE `like_record`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NULL DEFAULT NULL,
  `comment_id` int NULL DEFAULT NULL,
  `user_id` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like_record
-- ----------------------------
INSERT INTO `like_record` VALUES (1, 1, NULL, 1, '2023-05-25 16:17:38', '2023-05-25 16:17:41', 1);
INSERT INTO `like_record` VALUES (3, 1, NULL, 1, '2023-05-26 16:17:28', '2023-05-26 16:17:28', 1);
INSERT INTO `like_record` VALUES (4, 1, NULL, 1, '2023-05-26 16:21:32', '2023-05-26 16:21:32', 1);
INSERT INTO `like_record` VALUES (5, 1, NULL, 1, '2023-05-26 16:35:38', '2023-05-26 16:35:38', 1);
INSERT INTO `like_record` VALUES (6, 1, NULL, 1, '2023-05-26 16:35:39', '2023-05-26 16:35:39', 1);
INSERT INTO `like_record` VALUES (7, 1, NULL, 1, '2023-05-26 16:35:41', '2023-05-26 16:35:41', 1);
INSERT INTO `like_record` VALUES (8, 1, NULL, 1, '2023-05-26 16:35:43', '2023-05-26 16:35:43', 0);
INSERT INTO `like_record` VALUES (9, NULL, 3, 1, '2023-05-27 16:24:46', '2023-05-27 16:24:49', 1);
INSERT INTO `like_record` VALUES (11, NULL, 3, 1, '2023-05-27 16:37:17', '2023-05-27 16:37:17', 1);
INSERT INTO `like_record` VALUES (12, NULL, 1, 1, '2023-05-27 16:37:18', '2023-05-27 16:37:18', 0);
INSERT INTO `like_record` VALUES (13, NULL, 2, 1, '2023-05-27 16:37:19', '2023-05-27 16:37:19', 1);
INSERT INTO `like_record` VALUES (14, NULL, 4, 1, '2023-05-27 16:37:19', '2023-05-27 16:37:19', 0);
INSERT INTO `like_record` VALUES (15, NULL, 5, 1, '2023-05-27 16:37:20', '2023-05-27 16:37:20', 1);
INSERT INTO `like_record` VALUES (16, NULL, 6, 1, '2023-05-27 16:37:21', '2023-05-27 16:37:21', 1);
INSERT INTO `like_record` VALUES (17, NULL, 7, 1, '2023-05-27 16:37:22', '2023-05-27 16:37:22', 0);
INSERT INTO `like_record` VALUES (18, NULL, 8, 1, '2023-05-27 18:13:01', '2023-05-27 18:13:01', 1);
INSERT INTO `like_record` VALUES (19, NULL, 12, 1, '2023-05-27 19:55:09', '2023-05-27 19:55:09', 0);
INSERT INTO `like_record` VALUES (20, NULL, 3, 1, '2023-05-27 20:01:48', '2023-05-27 20:01:48', 1);
INSERT INTO `like_record` VALUES (21, NULL, 3, 1, '2023-05-27 20:01:49', '2023-05-27 20:01:49', 1);
INSERT INTO `like_record` VALUES (22, NULL, 3, 1, '2023-05-27 20:02:08', '2023-05-27 20:02:08', 1);
INSERT INTO `like_record` VALUES (23, NULL, 3, 1, '2023-05-27 20:02:09', '2023-05-27 20:02:09', 1);
INSERT INTO `like_record` VALUES (24, NULL, 8, 1, '2023-05-27 20:02:11', '2023-05-27 20:02:11', 1);
INSERT INTO `like_record` VALUES (25, NULL, 8, 1, '2023-05-27 20:02:12', '2023-05-27 20:02:12', 0);
INSERT INTO `like_record` VALUES (26, 8, NULL, 1, '2023-05-29 14:25:10', '2023-05-29 14:25:10', 0);
INSERT INTO `like_record` VALUES (27, NULL, 15, 1, '2023-05-29 14:25:12', '2023-05-29 14:25:12', 0);

-- ----------------------------
-- Table structure for platform
-- ----------------------------
DROP TABLE IF EXISTS `platform`;
CREATE TABLE `platform`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
  `status` int NOT NULL DEFAULT 1,
  `create_user` int NOT NULL,
  `update_user` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platform
-- ----------------------------
INSERT INTO `platform` VALUES (1, 'PC', 1, 1, 1, '2023-05-18 16:18:46', '2023-05-18 16:18:46', 0);
INSERT INTO `platform` VALUES (2, '手机', 1, 1, 1, '2023-05-18 16:19:37', '2023-05-18 16:19:37', 0);
INSERT INTO `platform` VALUES (3, '主机', 1, 1, 1, '2023-05-18 16:19:43', '2023-05-18 16:19:43', 0);
INSERT INTO `platform` VALUES (4, 'PSP', 1, 1, 1, '2023-05-20 04:04:56', '2023-05-20 04:04:56', 0);
INSERT INTO `platform` VALUES (5, '13123', 1, 1, 1, '2023-05-20 04:15:10', '2023-05-20 04:15:10', 0);
INSERT INTO `platform` VALUES (6, '测试1123', 1, 1, 1, '2023-05-20 18:27:15', '2023-05-21 01:40:51', 1);
INSERT INTO `platform` VALUES (7, '测试1', 1, 1, 1, '2023-05-20 18:27:31', '2023-05-20 18:27:31', 1);
INSERT INTO `platform` VALUES (8, '测试2', 1, 1, 1, '2023-05-20 18:32:08', '2023-05-20 18:32:08', 1);
INSERT INTO `platform` VALUES (9, '测试3', 1, 1, 1, '2023-05-20 18:33:08', '2023-05-20 18:33:08', 0);
INSERT INTO `platform` VALUES (10, '测试4', 1, 1, 1, '2023-05-20 18:33:12', '2023-05-20 18:33:12', 0);
INSERT INTO `platform` VALUES (11, '测试5', 1, 1, 1, '2023-05-20 18:35:20', '2023-05-20 18:35:20', 1);
INSERT INTO `platform` VALUES (12, '测试5', 1, 1, 1, '2023-05-21 00:59:14', '2023-05-21 00:59:14', 1);
INSERT INTO `platform` VALUES (13, '测试999', 1, 1, 1, '2023-05-21 01:13:26', '2023-05-30 17:49:36', 0);
INSERT INTO `platform` VALUES (14, 'psp', 1, 1, 1, '2023-05-30 13:57:52', '2023-05-30 17:44:23', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` int NOT NULL DEFAULT 3 COMMENT '0女1男3未知',
  `integral` int NOT NULL DEFAULT 0 COMMENT '积分',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `level` int NOT NULL DEFAULT 0 COMMENT '等级',
  `day_level` int NOT NULL DEFAULT 0,
  `power` int NOT NULL DEFAULT 0 COMMENT '0为普通账号，1为管理员',
  `status` int NOT NULL DEFAULT 0 COMMENT '0正常，1封禁',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'b9d11b3be25f5a1a7dc8ca04cd310b28', 'http://localhost:8080/images/f30adbf0-76ee-4a28-b41b-0c0107803882.jpg', 'iwgyf', '17854355483', '', 1, 666, '这是一段介绍12313211', 666, 0, 1, 0, '2023-05-15 20:39:42', '2023-06-09 21:22:41', 0);
INSERT INTO `user` VALUES (7, '456464', '4d1dfcd9562a281975ab1e7f23285d86', NULL, '123123131', '17878979879', '', 3, 0, NULL, 0, 0, 0, 0, '2023-05-16 18:11:23', '2023-05-16 18:11:23', 0);
INSERT INTO `user` VALUES (8, 'iwgyf', '4e3e809dcc3711664d3a218098860a44', 'http://localhost:8080/images/60095a9d-9bb1-4699-b54f-3fdc832c7b2f.jpg', '啊啊啊啊啊', '17812345646', '2500595148@qq.com', 3, 0, NULL, 0, 0, 0, 0, '2023-05-20 04:16:35', '2023-06-09 21:16:05', 0);

-- ----------------------------
-- Table structure for view_record
-- ----------------------------
DROP TABLE IF EXISTS `view_record`;
CREATE TABLE `view_record`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `user_id` int NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view_record
-- ----------------------------
INSERT INTO `view_record` VALUES (1, 8, 1, '2023-05-30 12:09:03', '2023-06-09 21:34:01', 0);
INSERT INTO `view_record` VALUES (2, 1, 1, '2023-06-09 21:31:18', '2023-06-09 21:55:27', 0);
INSERT INTO `view_record` VALUES (3, 8, 1, '2023-06-09 21:34:01', '2023-06-09 21:34:01', 0);

SET FOREIGN_KEY_CHECKS = 1;
