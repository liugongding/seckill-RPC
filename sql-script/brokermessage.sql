/*
 Navicat Premium Data Transfer

 Source Server         : mac_for_mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost
 Source Database       : seckill

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : utf-8

 Date: 09/23/2019 18:36:02 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `brokermessage`
-- ----------------------------
DROP TABLE IF EXISTS `brokermessage`;
CREATE TABLE `brokermessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `message_id` bigint(255) DEFAULT NULL COMMENT '消息id',
  `message` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '消息',
  `try_count` int(11) DEFAULT NULL COMMENT '重试次数',
  `status` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '0-发送中， 1-发送成功， 2-发送失败',
  `next_retry` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下一次重试的时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=323 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
