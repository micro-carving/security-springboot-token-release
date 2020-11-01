/*
 Navicat Premium Data Transfer

 Source Server         : spring-boot-token
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : web_token_db

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 02/11/2020 00:59:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `full_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全称',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `authorities` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限',
  `create_user_id` bigint(64) NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user_id` bigint(64) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int(2) NULL DEFAULT NULL COMMENT '是否删除（0正常，1已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_id`(`id`) USING BTREE COMMENT '主键唯一索引',
  UNIQUE INDEX `idx_user_name`(`user_name`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1322828903957520386, 'zhangsan', 'zhangsan', 'zhangsan', '110', 'p1', 111111111111111, '2020-11-01 16:23:30', NULL, NULL, 0);
INSERT INTO `user` VALUES (1322829236012179458, 'lisi', 'lisi', 'lisi', '120', 'p2', 111111111111111, '2020-11-01 16:25:30', NULL, NULL, 0);
INSERT INTO `user` VALUES (1322832356901392385, 'wangwu', 'wangwu', 'wangwu', '119', 'p3', 111111111111111, '2020-11-01 17:00:30', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
