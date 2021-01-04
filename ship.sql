/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.31-log : Database - ship
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ship` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `ship`;

/*Table structure for table `t_app` */

DROP TABLE IF EXISTS `t_app`;

CREATE TABLE `t_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(32) NOT NULL DEFAULT '' COMMENT '应用名称',
  `description` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `context_path` varchar(32) NOT NULL DEFAULT '' COMMENT '路径上下文',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启，1开启0未开启',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_app` */

/*Table structure for table `t_app_instance` */

DROP TABLE IF EXISTS `t_app_instance`;

CREATE TABLE `t_app_instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `version` varchar(32) NOT NULL DEFAULT '' COMMENT '版本号',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `port` int(11) NOT NULL DEFAULT '0' COMMENT '端口号',
  `weight` int(11) NOT NULL DEFAULT '0' COMMENT '权重',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_app_instance` */

/*Table structure for table `t_app_plugin` */

DROP TABLE IF EXISTS `t_app_plugin`;

CREATE TABLE `t_app_plugin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用id',
  `plugin_id` int(11) NOT NULL COMMENT '插件id',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启，1开启0未开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_app_plugin` */

/*Table structure for table `t_plugin` */

DROP TABLE IF EXISTS `t_plugin`;

CREATE TABLE `t_plugin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '插件名称',
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT 'eg:DynamicRoute',
  `description` varchar(50) NOT NULL DEFAULT '' COMMENT '描述',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_plugin` */

insert  into `t_plugin`(`id`,`name`,`code`,`description`,`created_time`) values (1,'动态路由','DynamicRoute','动态路由插件','2020-12-29 09:48:20');
INSERT  INTO `t_plugin`(`id`,`name`,`code`,`description`,`created_time`) VALUES (2,'鉴权','Auth','鉴权插件','2020-12-29 09:48:20');

/*Table structure for table `t_route_rule` */

DROP TABLE IF EXISTS `t_route_rule`;

CREATE TABLE `t_route_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `version` varchar(32) NOT NULL DEFAULT '' COMMENT '路由版本',
  `match_object` varchar(32) NOT NULL DEFAULT '' COMMENT '匹配对象，DEFAULT，QUERY，HEADER',
  `match_key` varchar(50) NOT NULL DEFAULT '' COMMENT '匹配key',
  `match_method` tinyint(1) NOT NULL DEFAULT '0' COMMENT '匹配方式,1:=,2:regex,3:like',
  `match_rule` varchar(50) NOT NULL DEFAULT '' COMMENT '匹配规则',
  `priority` int(11) NOT NULL DEFAULT '0' COMMENT '优先级，值越大优先级越高',
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否启用，1=开启0=禁用',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_route_rule` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` char(32) NOT NULL DEFAULT '' COMMENT '密码',
  `created_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`user_name`,`password`,`created_time`) values (1,'admin','6b249962c7fa2a4b2ba26474dd164c3c','2021-01-04 11:36:54');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
