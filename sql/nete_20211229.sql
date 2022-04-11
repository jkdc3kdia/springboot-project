/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.13 : Database - nate
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nate` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `nate`;

/*Table structure for table `nt_employee` */

DROP TABLE IF EXISTS `nt_employee`;

CREATE TABLE `nt_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) NOT NULL COMMENT '登录账号',
  `password` varchar(128) NOT NULL COMMENT '的呢两个密码',
  `real_name` varchar(8) NOT NULL COMMENT '用户姓名',
  `mobile` varchar(11) NOT NULL COMMENT '手机号码',
  `email` varchar(50) NOT NULL COMMENT '公司邮箱',
  `picture` varchar(512) DEFAULT NULL COMMENT '头像',
  `sex` int(1) NOT NULL COMMENT '性别',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(64) NOT NULL COMMENT '创建人',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `is_init_password` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已设置密码',
  `is_dev` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是开发账号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_unique` (`account`) USING BTREE,
  UNIQUE KEY `mobile_unique` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `nt_employee` */

insert  into `nt_employee`(`id`,`account`,`password`,`real_name`,`mobile`,`email`,`picture`,`sex`,`create_time`,`create_user`,`remark`,`is_delete`,`is_init_password`,`is_dev`) values (1,'tp','d28dbc83deb3bf394830158b81c88b35','Janus','15828528434','heaven@163.com',NULL,1,'2021-07-27 14:47:35','admin',NULL,0,0,0),(2,'sm','d28dbc83deb3bf394830158b81c88b35','宋敏','18782000011','sm@163.com',NULL,0,'2021-08-01 11:47:54','admin',NULL,0,0,0);

/*Table structure for table `nt_employee_role_rel` */

DROP TABLE IF EXISTS `nt_employee_role_rel`;

CREATE TABLE `nt_employee_role_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) NOT NULL COMMENT '用户账号',
  `role_code` varchar(32) NOT NULL COMMENT '角色名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_role_code` (`account`,`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色关系表';

/*Data for the table `nt_employee_role_rel` */

insert  into `nt_employee_role_rel`(`id`,`account`,`role_code`,`create_time`) values (1,'tp','admin','2021-08-08 15:02:54');

/*Table structure for table `nt_menu` */

DROP TABLE IF EXISTS `nt_menu`;

CREATE TABLE `nt_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `menu_icon` varchar(64) DEFAULT NULL COMMENT '菜单icon',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '上级菜单ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `nt_menu` */

insert  into `nt_menu`(`id`,`menu_code`,`menu_name`,`menu_url`,`menu_icon`,`parent_id`,`create_time`,`sort`) values (1,'system_managerment','系统管理',NULL,NULL,NULL,NULL,NULL),(2,'system_managerment_menu','菜单管理',NULL,NULL,'1',NULL,1),(3,'system_managerment_permission','权限管理',NULL,NULL,'1',NULL,2);

/*Table structure for table `nt_permission` */

DROP TABLE IF EXISTS `nt_permission`;

CREATE TABLE `nt_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限表ID',
  `permission_code` varchar(30) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `url` varchar(64) DEFAULT NULL COMMENT 'url',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态 0:启用 1:禁用',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `nt_permission` */

insert  into `nt_permission`(`id`,`permission_code`,`permission_name`,`create_time`,`menu_id`,`url`,`status`,`modify_time`,`create_account`) values (1,'dego:access','系统访问权限',NULL,NULL,NULL,0,NULL,NULL),(2,'dego:system:view','系统菜单',NULL,NULL,NULL,0,NULL,NULL),(3,'dego:system:menu','系统菜单-菜单管理',NULL,NULL,NULL,0,NULL,NULL),(4,'dego:system:permission','系统菜单-权限管理',NULL,NULL,NULL,0,NULL,NULL);

/*Table structure for table `nt_role` */

DROP TABLE IF EXISTS `nt_role`;

CREATE TABLE `nt_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL COMMENT '人员角色',
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(128) DEFAULT NULL COMMENT '角色说明',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_internal` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否内置(内置不可删除)',
  `create_account` varchar(50) DEFAULT NULL COMMENT '创建人账号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `require_code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `nt_role` */

insert  into `nt_role`(`id`,`code`,`name`,`remark`,`create_time`,`is_internal`,`create_account`) values (1,'admin','admin','管理员角色','2021-08-08 15:02:14',0,NULL);

/*Table structure for table `nt_role_permission` */

DROP TABLE IF EXISTS `nt_role_permission`;

CREATE TABLE `nt_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限关联表',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `nt_role_permission` */

insert  into `nt_role_permission`(`id`,`role_id`,`permission_id`,`create_time`,`modify_time`) values (1,1,1,NULL,NULL),(2,1,2,NULL,NULL),(3,1,3,NULL,NULL),(4,1,4,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
