/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : ship

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 23/12/2020 23:18:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名称',
  `context_path` varchar(32) NOT NULL DEFAULT '' COMMENT '路径上下文',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启，1开启0未开启',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_app_instance
-- ----------------------------
DROP TABLE IF EXISTS `t_app_instance`;
CREATE TABLE `t_app_instance` (
  `id` int(11) NOT NULL,
  `version` varchar(32) NOT NULL DEFAULT '' COMMENT '版本号',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `port` int(11) NOT NULL DEFAULT '0' COMMENT '端口号',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `app_id` int(11) NOT NULL COMMENT '应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_app_plugin
-- ----------------------------
DROP TABLE IF EXISTS `t_app_plugin`;
CREATE TABLE `t_app_plugin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `plugin_id` int(11) NOT NULL COMMENT '插件id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
