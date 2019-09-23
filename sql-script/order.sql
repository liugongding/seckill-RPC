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

 Date: 09/23/2019 18:35:44 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `commodity_id` int(20) NOT NULL COMMENT '商品ID',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` int(4) NOT NULL DEFAULT '-1' COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_c_u` (`commodity_id`,`user_phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='秒杀成功明细表';

SET FOREIGN_KEY_CHECKS = 1;
