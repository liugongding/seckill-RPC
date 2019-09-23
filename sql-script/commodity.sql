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

 Date: 09/23/2019 18:35:54 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `commodity`
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `commodity_id` bigint(20) NOT NULL COMMENT '商品id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '商品名称',
  `number` int(20) NOT NULL COMMENT '库存数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀开始时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`),
  KEY `index_time` (`start_time`,`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='秒杀库存表';

-- ----------------------------
--  Records of `commodity`
-- ----------------------------
BEGIN;
INSERT INTO `commodity` VALUES ('1', '1000', '5000元秒杀iPhone11pro', '96', '2019-09-16 16:08:09', '2019-09-04 16:05:08', '2019-10-01 16:05:11'), ('2', '1001', '10000元秒杀macbookpro', '91', '2019-09-16 16:08:32', '2019-09-04 16:06:02', '2019-10-01 16:06:04'), ('3', '1002', '3000元秒杀ipad', '97', '2019-09-16 16:09:09', '2019-09-04 16:06:53', '2019-10-01 16:06:55'), ('4', '1003', '0元秒杀充气娃娃', '0', '2019-09-12 17:11:33', '2019-09-04 16:07:47', '2019-10-01 16:07:50');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
