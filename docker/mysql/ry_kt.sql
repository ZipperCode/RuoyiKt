/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : ry_oversea_account

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 19/04/2025 14:01:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_account
-- ----------------------------
DROP TABLE IF EXISTS `app_account`;
CREATE TABLE `app_account`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '城市',
  `age` tinyint(0) NULL DEFAULT 0 COMMENT '年龄',
  `work` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工作',
  `income` float NULL DEFAULT NULL COMMENT '收入',
  `classify` tinyint(0) NULL DEFAULT NULL COMMENT '类型 1 2 3 4',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '数据状态',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `screenshot` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '截图保存地址',
  `link_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '链接备注',
  `modifier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人（存在即为有值）',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1744808861554999950 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_account
-- ----------------------------

-- ----------------------------
-- Table structure for app_account_record
-- ----------------------------
DROP TABLE IF EXISTS `app_account_record`;
CREATE TABLE `app_account_record`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` bigint(0) NULL DEFAULT NULL COMMENT '账号数据id',
  `bind_user_id` bigint(0) NULL DEFAULT NULL COMMENT '绑定用户',
  `classify` tinyint(0) NULL DEFAULT NULL,
  `used` tinyint(0) NULL DEFAULT NULL COMMENT '使用状态',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_account_record
-- ----------------------------

-- ----------------------------
-- Table structure for app_links
-- ----------------------------
DROP TABLE IF EXISTS `app_links`;
CREATE TABLE `app_links`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `link` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `classify` smallint(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 246508 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_links
-- ----------------------------

-- ----------------------------
-- Table structure for app_qr
-- ----------------------------
DROP TABLE IF EXISTS `app_qr`;
CREATE TABLE `app_qr`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `qr_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `qr_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `work` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `income` float NULL DEFAULT NULL,
  `age` tinyint(0) NULL DEFAULT NULL,
  `account_type` tinyint(0) NULL DEFAULT NULL,
  `classify` tinyint(0) NULL DEFAULT NULL,
  `status` tinyint(0) NULL DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `link_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `screenshot` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `modifier` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_qr
-- ----------------------------

-- ----------------------------
-- Table structure for app_qr_record
-- ----------------------------
DROP TABLE IF EXISTS `app_qr_record`;
CREATE TABLE `app_qr_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(0) NULL DEFAULT NULL,
  `bind_user_id` bigint(0) NULL DEFAULT NULL,
  `classify` tinyint(0) NULL DEFAULT NULL,
  `used` tinyint(0) NULL DEFAULT NULL,
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_qr_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_client
-- ----------------------------
DROP TABLE IF EXISTS `sys_client`;
CREATE TABLE `sys_client`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端id',
  `client_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端key',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端秘钥',
  `grant_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权类型',
  `device_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `active_timeout` int(0) NULL DEFAULT 1800 COMMENT 'token活跃超时时间',
  `timeout` int(0) NULL DEFAULT 604800 COMMENT 'token固定超时',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `deleted` smallint(0) NULL DEFAULT 0 COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_client
-- ----------------------------
INSERT INTO `sys_client` VALUES (1, 'e5cd7e4891bf95d1d19206ce24a7b32e', 'pc', 'pc123', 'password,social', 'pc', 6048000, 6048000, '0', 0, 103, 1, '2024-08-11 13:53:20', 1, '2024-08-11 13:53:20');
INSERT INTO `sys_client` VALUES (2, '428a8310cd442757ae699df5d894f051', 'app', 'app123', 'password,sms,social', 'android', 1800, 604800, '0', 0, 103, 1, '2024-08-11 13:53:20', 1, '2024-08-11 13:53:20');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (11, 'OSS预览列表资源开关', 'sys.oss.previewListResource', 'true', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, 'true:开启, false:关闭');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` bigint(0) NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `deleted` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '账号管理系统', 0, NULL, '15888888888', 'xxx@qq.com', '0', '0', 103, 1, '2024-08-11 13:53:18', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(0) NOT NULL COMMENT '字典编码',
  `dict_sort` int(0) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (29, 99, '其他', '0', 'sys_oper_type', '', 'info', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '其他操作');
INSERT INTO `sys_dict_data` VALUES (30, 0, '密码认证', 'password', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '密码认证');
INSERT INTO `sys_dict_data` VALUES (31, 0, '短信认证', 'sms', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '短信认证');
INSERT INTO `sys_dict_data` VALUES (32, 0, '邮件认证', 'email', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '邮件认证');
INSERT INTO `sys_dict_data` VALUES (33, 0, '小程序认证', 'xcx', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '小程序认证');
INSERT INTO `sys_dict_data` VALUES (34, 0, '三方登录认证', 'social', 'sys_grant_type', 'el-check-tag', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '三方登录认证');
INSERT INTO `sys_dict_data` VALUES (35, 0, 'PC', 'pc', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, 'PC');
INSERT INTO `sys_dict_data` VALUES (36, 0, '安卓', 'android', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '安卓');
INSERT INTO `sys_dict_data` VALUES (37, 0, 'iOS', 'ios', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, 'iOS');
INSERT INTO `sys_dict_data` VALUES (38, 0, '小程序', 'xcx', 'sys_device_type', '', 'default', 'N', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '小程序');
INSERT INTO `sys_dict_data` VALUES (1825061004381962242, 0, '数据库存储', '1', 'store_file_storage_type', '', 'default', 'N', 103, 1, '2024-08-18 14:43:46', 1, '2024-08-18 14:43:46', '');
INSERT INTO `sys_dict_data` VALUES (1825061252726702082, 1, '本地存储', '10', 'store_file_storage_type', '', 'default', 'N', 103, 1, '2024-08-18 14:44:45', 1, '2024-08-18 14:44:45', '');
INSERT INTO `sys_dict_data` VALUES (1825061319986561026, 2, 'S3存储', '20', 'store_file_storage_type', '', 'default', 'N', 103, 1, '2024-08-18 14:45:01', 1, '2024-08-18 14:45:01', '');
INSERT INTO `sys_dict_data` VALUES (1825895943289610242, 0, '是', 'true', 'true_or_false', '', 'primary', 'N', 103, 1, '2024-08-20 22:01:31', 1, '2024-08-20 22:01:31', '');
INSERT INTO `sys_dict_data` VALUES (1825896093022068737, 0, '是', 'true', 'sys_yes_no', '', 'primary', 'N', 103, 1, '2024-08-20 22:02:07', 1, '2024-08-20 22:02:37', '');
INSERT INTO `sys_dict_data` VALUES (1825896173066166274, 0, '否', 'false', 'sys_yes_no', '', 'danger', 'N', 103, 1, '2024-08-20 22:02:26', 1, '2024-08-20 22:02:26', '');
INSERT INTO `sys_dict_data` VALUES (1893175474199359489, 0, '一号链接', '1', 'app_links_classify', '', 'default', 'N', NULL, 1, '2025-02-22 21:46:21', 1, '2025-02-22 13:55:41', '');
INSERT INTO `sys_dict_data` VALUES (1893175551777206273, 0, '二号链接', '2', 'app_links_classify', '', 'default', 'N', NULL, 1, '2025-02-22 21:46:40', 1, '2025-02-22 13:56:58', '');
INSERT INTO `sys_dict_data` VALUES (1893258414149230594, 1, 'Line', '1', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-23 03:15:56', 1, '2025-02-22 19:16:28', '');
INSERT INTO `sys_dict_data` VALUES (1893258453634408449, 2, 'Pairs', '2', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-23 03:16:05', 1, '2025-02-22 19:16:32', '');
INSERT INTO `sys_dict_data` VALUES (1893258523964497921, 3, 'WhatsApp1', '3', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-22 19:16:22', 1, '2025-02-22 19:16:22', '');
INSERT INTO `sys_dict_data` VALUES (1893258693242413057, 4, 'WhatsApp2', '4', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-23 03:17:02', 1, '2025-02-22 19:17:20', '');
INSERT INTO `sys_dict_data` VALUES (1893258737450377217, 5, 'WhatsApp3', '5', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-23 03:17:13', 1, '2025-02-22 19:17:30', '');
INSERT INTO `sys_dict_data` VALUES (1893258873907863554, 6, 'WhatsApp4', '6', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-22 19:17:45', 1, '2025-02-22 19:17:45', '');
INSERT INTO `sys_dict_data` VALUES (1893258933274042369, 7, 'WhatsApp5', '7', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-22 19:17:59', 1, '2025-02-22 19:17:59', '');
INSERT INTO `sys_dict_data` VALUES (1893259076664713218, 8, 'WhatsApp6', '8', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-22 19:18:34', 1, '2025-02-22 19:18:34', '');
INSERT INTO `sys_dict_data` VALUES (1893259107912278018, 9, 'WhatsApp7', '9', 'app_account_classify', '', 'default', 'N', NULL, 1, '2025-02-22 19:18:41', 1, '2025-02-22 19:18:41', '');
INSERT INTO `sys_dict_data` VALUES (1893259207745101825, 0, '未使用', '0', 'app_account_status', '', 'primary', 'N', NULL, 1, '2025-02-23 11:19:05', 1, '2025-02-22 19:19:37', '');
INSERT INTO `sys_dict_data` VALUES (1893259397990342657, 1, '已使用', '1', 'app_account_status', '', 'success', 'N', NULL, 1, '2025-02-22 19:19:50', 1, '2025-02-22 19:19:50', '');
INSERT INTO `sys_dict_data` VALUES (1893259438838669313, 2, '无效', '2', 'app_account_status', '', 'danger', 'N', NULL, 1, '2025-02-22 19:20:00', 1, '2025-02-22 19:20:00', '');
INSERT INTO `sys_dict_data` VALUES (1893283225793617921, 1, '日本', '1', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-23 04:54:31', 1, '2025-02-22 20:55:44', '');
INSERT INTO `sys_dict_data` VALUES (1893283270601367554, 2, '意大利', '2', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:54:42', 1, '2025-02-22 20:54:42', '');
INSERT INTO `sys_dict_data` VALUES (1893283302188670978, 3, '英国', '3', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:54:49', 1, '2025-02-22 20:54:49', '');
INSERT INTO `sys_dict_data` VALUES (1893283333352349697, 4, '美国', '4', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:54:57', 1, '2025-02-22 20:54:57', '');
INSERT INTO `sys_dict_data` VALUES (1893283366017589249, 5, '法国', '5', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:05', 1, '2025-02-22 20:55:05', '');
INSERT INTO `sys_dict_data` VALUES (1893283393993596929, 6, '斯洛文尼亚', '6', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:11', 1, '2025-02-22 20:55:11', '');
INSERT INTO `sys_dict_data` VALUES (1893283420572901377, 7, '捷克', '7', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:18', 1, '2025-02-22 20:55:18', '');
INSERT INTO `sys_dict_data` VALUES (1893283452420251649, 8, '克罗地亚', '8', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:25', 1, '2025-02-22 20:55:25', '');
INSERT INTO `sys_dict_data` VALUES (1893283480794718210, 9, '德国', '9', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:32', 1, '2025-02-22 20:55:32', '');
INSERT INTO `sys_dict_data` VALUES (1893283511312474114, 10, '西班牙', '10', 'app_account_country', '', 'default', 'N', NULL, 1, '2025-02-22 20:55:39', 1, '2025-02-22 20:55:39', '');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1825041015272837131 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (11, '授权类型', 'sys_grant_type', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '认证授权类型');
INSERT INTO `sys_dict_type` VALUES (12, '设备类型', 'sys_device_type', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '客户端设备类型');
INSERT INTO `sys_dict_type` VALUES (13, '文件存储类型', 'store_file_storage_type', 103, 1, '2024-08-18 13:14:21', 1, '2024-08-18 13:14:21', 'FileStorageEnum类型');
INSERT INTO `sys_dict_type` VALUES (1825041015272837127, '条件是否', 'true_or_false', 103, 1, '2024-08-20 22:01:02', 1, '2024-08-20 22:01:02', '');
INSERT INTO `sys_dict_type` VALUES (1825041015272837128, '群链接类型', 'app_links_classify', NULL, 1, '2025-02-22 21:44:54', 1, '2025-02-22 13:47:39', '账号模块，群链接的数据类型');
INSERT INTO `sys_dict_type` VALUES (1825041015272837129, '账号数据分类', 'app_account_classify', NULL, 1, '2025-02-23 19:13:16', 1, '2025-02-22 19:14:51', '账号模块，数据分类 （对应枚举 DataClassify）不可动');
INSERT INTO `sys_dict_type` VALUES (1825041015272837130, '数据状态', 'app_account_status', NULL, 1, '2025-02-23 03:15:21', 1, '2025-02-22 19:20:33', '未使用、已使用、无效状态');
INSERT INTO `sys_dict_type` VALUES (1825041015272837131, '账号-城市', 'app_account_country', NULL, 1, '2025-02-22 20:53:55', 1, '2025-02-22 20:53:55', '');

-- ----------------------------
-- Table structure for sys_file_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_config`;
CREATE TABLE `sys_file_config`  (
  `config_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `storage` smallint(0) NOT NULL COMMENT '存储器类型',
  `master` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否主配置',
  `config` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '多态配置 json',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deleted` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_config
-- ----------------------------
INSERT INTO `sys_file_config` VALUES (10, '数据库存储', 1, '0', '{\"@class\":\"org.zipper.modules.storage.client.db.DataBaseFileClientConfig\",\"domain\":\"http://localhost:8080\",\"customDomain\":\"http://localhost:8080\"}', '', 1, '2025-02-09 17:45:07', 1, '2025-02-09 17:45:06.70045', '0');
INSERT INTO `sys_file_config` VALUES (11, '本地存储', 10, '1', '{\"@class\":\"org.zipper.modules.storage.client.local.LocalFileClientConfig\",\"basePath\":\"/upload\",\"domain\":\"http://localhost:8080\",\"customDomain\":\"http://localhost:8080\"}', '', 1, '2025-04-13 12:14:49', 1, '2025-04-13 12:14:49.279666', '0');

-- ----------------------------
-- Table structure for sys_file_content
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_content`;
CREATE TABLE `sys_file_content`  (
  `file_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `config_id` bigint(0) NOT NULL COMMENT '关联配置id',
  `path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '相对路径',
  `content` mediumblob NOT NULL COMMENT '内容',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_content
-- ----------------------------

-- ----------------------------
-- Table structure for sys_file_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_record`;
CREATE TABLE `sys_file_record`  (
  `record_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `config_id` bigint(0) NOT NULL COMMENT '配置id',
  `path` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '相对路径',
  `url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '访问url',
  `mime_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(0) NOT NULL DEFAULT 0 COMMENT '文件大小',
  `hash` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件hash',
  `service` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务商',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上传人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1912480812100493315 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `info_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `client_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '客户端',
  `device_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '设备类型',
  `ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_login_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_login_log_lt`(`login_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1822979398322810881, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2024-08-12 20:52:13');
INSERT INTO `sys_login_log` VALUES (1822979440056135681, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-12 20:52:22');
INSERT INTO `sys_login_log` VALUES (1822994769150955522, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-12 21:53:17');
INSERT INTO `sys_login_log` VALUES (1823727633831731202, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-14 22:25:26');
INSERT INTO `sys_login_log` VALUES (1824071746162663426, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-15 21:12:49');
INSERT INTO `sys_login_log` VALUES (1825022249776324610, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-18 12:09:46');
INSERT INTO `sys_login_log` VALUES (1825038190379728897, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-18 13:13:07');
INSERT INTO `sys_login_log` VALUES (1825076908264177665, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-18 15:46:58');
INSERT INTO `sys_login_log` VALUES (1825098372434542594, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-18 17:12:15');
INSERT INTO `sys_login_log` VALUES (1825144919012589570, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-18 20:17:13');
INSERT INTO `sys_login_log` VALUES (1825868287194157057, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-20 20:11:37');
INSERT INTO `sys_login_log` VALUES (1827160445452693506, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-24 09:46:12');
INSERT INTO `sys_login_log` VALUES (1827202165498376194, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-24 12:31:59');
INSERT INTO `sys_login_log` VALUES (1827632656835858433, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-25 17:02:36');
INSERT INTO `sys_login_log` VALUES (1827647722155069441, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-25 18:02:28');
INSERT INTO `sys_login_log` VALUES (1828072566625599489, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-26 22:10:38');
INSERT INTO `sys_login_log` VALUES (1828423550212173826, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-27 21:25:19');
INSERT INTO `sys_login_log` VALUES (1828428627492663298, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-27 21:45:30');
INSERT INTO `sys_login_log` VALUES (1828780498594004994, 'admin', 'pc', 'pc', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '登录成功', '2024-08-28 21:03:43');
INSERT INTO `sys_login_log` VALUES (1880570501200605186, 'admin', '', '', '0:0:0:0:0:0:0:1', 'unknown', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-01-18 18:58:41');
INSERT INTO `sys_login_log` VALUES (1880571006681243649, 'admin', '', '', '0:0:0:0:0:0:0:1', 'unknown', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-01-18 19:00:42');
INSERT INTO `sys_login_log` VALUES (1880571367777329153, 'admin', '', '', '0:0:0:0:0:0:0:1', 'unknown', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-01-18 19:02:08');
INSERT INTO `sys_login_log` VALUES (1880572317325742081, 'admin', '', '', '0:0:0:0:0:0:0:1', 'unknown', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-01-18 19:05:54');
INSERT INTO `sys_login_log` VALUES (1880609556873302018, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-01-18 21:33:53');
INSERT INTO `sys_login_log` VALUES (1888554526981943298, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-02-09 19:44:22');
INSERT INTO `sys_login_log` VALUES (1893209871514537986, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-02-22 16:03:02');
INSERT INTO `sys_login_log` VALUES (1893215115011100674, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-02-22 16:23:52');
INSERT INTO `sys_login_log` VALUES (1893317330317021186, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-02-22 23:10:02');
INSERT INTO `sys_login_log` VALUES (1913471206384783361, 'admin', '', '', '0:0:0:0:0:0:0:1', '内网IP', 'Chrome', 'Windows 10 or Windows Server 2016', '0', '退出成功', '2025-04-19 13:54:21');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '显示状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1896931183504318467 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 999, 'monitor', NULL, '', 1, 0, 'M', '0', '1', '', 'monitor', 103, 1, '2024-08-12 21:53:18', 1, '2025-03-02 21:07:29', '系统监控目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '1', 'system:post:list', 'post', 103, 1, '2024-08-11 21:53:18', 1, '2025-03-01 11:22:48', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '1', 'system:dict:list', 'dict', 103, 1, '2024-08-11 21:53:18', 1, '2025-03-01 11:23:04', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '1', 'system:notice:list', 'message', 103, 1, '2024-08-11 21:53:19', 1, '2025-03-01 11:17:32', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 103, 1, '2024-08-11 13:53:19', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (1825080322201141250, '文件管理', 1, 10, 'store/file', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'bug', 103, 1, '2024-08-19 08:00:32', 1, '2025-03-01 11:17:16', '');
INSERT INTO `sys_menu` VALUES (1825080790348382210, '配置管理', 1825080322201141250, 1, 'fileConfig', 'store/fileConfig/index', NULL, 1, 0, 'C', '0', '0', 'store:fileConfig:list', 'build', 103, 1, '2024-08-18 16:02:23', 1, '2024-08-18 16:10:49', '');
INSERT INTO `sys_menu` VALUES (1825081011325288449, '配置查询', 1825080790348382210, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:fileConfig:query', '', 103, 1, '2024-08-18 16:03:16', 1, '2024-08-18 16:03:16', '');
INSERT INTO `sys_menu` VALUES (1825081097606316033, '配置新增', 1825080790348382210, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:fileConfig:add', '', 103, 1, '2024-08-18 16:03:37', 1, '2024-08-18 16:03:37', '');
INSERT INTO `sys_menu` VALUES (1825081187867738113, '配置修改', 1825080790348382210, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:fileConfig:edit', '', 103, 1, '2024-08-18 16:03:58', 1, '2024-08-18 16:03:58', '');
INSERT INTO `sys_menu` VALUES (1825081310366580737, '配置删除', 1825080790348382210, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:fileConfig:remove', '', 103, 1, '2024-08-18 16:04:27', 1, '2024-08-18 16:04:37', '');
INSERT INTO `sys_menu` VALUES (1825082069980200962, '文件管理', 1825080322201141250, 1, 'record', 'store/file/index', NULL, 1, 0, 'C', '0', '0', 'store:file:list', '404', 103, 1, '2024-08-18 16:07:29', 1, '2024-08-18 16:11:07', '');
INSERT INTO `sys_menu` VALUES (1825082246145163265, '文件上传', 1825082069980200962, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:file:upload', '', 103, 1, '2024-08-18 16:08:11', 1, '2024-08-18 16:08:11', '');
INSERT INTO `sys_menu` VALUES (1825082399165956098, '文件删除', 1825082069980200962, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:file:remove', '', 103, 1, '2024-08-18 16:08:47', 1, '2024-08-18 16:08:47', '');
INSERT INTO `sys_menu` VALUES (1825082511338422274, '文件下载', 1825082069980200962, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'store:file:download', '', 103, 1, '2024-08-18 16:09:14', 1, '2024-08-18 16:09:14', '');
INSERT INTO `sys_menu` VALUES (1893185148864851970, '链接一', 1893200932576104450, 1, 'index/1', 'account/links/index', '', 1, 0, 'C', '0', '0', 'app:links:list', 'link', NULL, 1, '2025-02-26 06:24:48', 1, '2025-03-02 13:34:09', '');
INSERT INTO `sys_menu` VALUES (1893200932576104450, '群链接管理', 0, 2, 'account/links', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'server', NULL, 1, '2025-02-23 15:27:31', 1, '2025-03-02 21:38:00', '');
INSERT INTO `sys_menu` VALUES (1893293842805972993, 'Line数据管理', 0, 3, 'account/line', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'druid', NULL, 1, '2025-02-23 13:36:42', 1, '2025-02-27 21:32:17', '');
INSERT INTO `sys_menu` VALUES (1893294461746831361, '账号数据', 1893293842805972993, 1, 'ids', 'account/ids/line/index', '', 1, 0, 'C', '0', '0', 'app:account:line:list', 'guide', NULL, 1, '2025-02-24 05:39:10', 1, '2025-03-02 19:22:04', '');
INSERT INTO `sys_menu` VALUES (1893295568250363906, '二维码数据', 1893293842805972993, 3, 'account/qrs/index/1', 'account/qrs/index', NULL, 1, 0, 'C', '0', '1', 'app:qr:list', 'documentation', NULL, 1, '2025-02-23 21:43:34', 1, '2025-03-02 21:02:58', '');
INSERT INTO `sys_menu` VALUES (1893296079296946177, '账号记录', 1893293842805972993, 2, 'records', 'account/ids/line/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:line:record:list', 'education', NULL, 1, '2025-02-24 21:45:36', 1, '2025-03-02 22:16:34', '');
INSERT INTO `sys_menu` VALUES (1895102350161530881, '数据查询', 1893294461746831361, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:list', '', NULL, 1, '2025-02-28 13:23:04', 1, '2025-03-02 18:49:29', '');
INSERT INTO `sys_menu` VALUES (1895102467321024514, '新增数据', 1893294461746831361, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:add', '', NULL, 1, '2025-02-28 21:23:32', 1, '2025-03-02 18:49:37', '');
INSERT INTO `sys_menu` VALUES (1895102629619617793, '修改数据', 1893294461746831361, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:edit', '', NULL, 1, '2025-02-28 13:24:11', 1, '2025-03-02 18:49:58', '');
INSERT INTO `sys_menu` VALUES (1895102759504629761, '删除数据', 1893294461746831361, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:delete', '', NULL, 1, '2025-02-28 21:24:42', 1, '2025-03-02 18:50:12', '');
INSERT INTO `sys_menu` VALUES (1895102923774545921, '批量上传', 1893294461746831361, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:upload', '', NULL, 1, '2025-02-28 13:25:21', 1, '2025-03-02 18:49:45', '');
INSERT INTO `sys_menu` VALUES (1895103145430929410, '查看记录', 1893296079296946177, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:record:list', '', NULL, 1, '2025-02-28 13:26:14', 1, '2025-03-02 18:50:54', '');
INSERT INTO `sys_menu` VALUES (1895103202486046721, '分配数据', 1893294461746831361, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:dispatch', '', NULL, 1, '2025-02-28 13:26:27', 1, '2025-03-02 18:50:20', '');
INSERT INTO `sys_menu` VALUES (1895104328958660609, '二维码记录', 1893293842805972993, 4, 'app/qr/list/1', 'account/qrs/RecordInex', NULL, 1, 0, 'C', '0', '1', 'app:qr:list', 'email', NULL, 1, '2025-02-28 21:30:56', 1, '2025-03-02 21:03:02', '');
INSERT INTO `sys_menu` VALUES (1895659448935018498, '解绑数据', 1893294461746831361, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:line:unbind', '', NULL, 1, '2025-03-01 18:16:47', 1, '2025-03-02 18:50:05', '');
INSERT INTO `sys_menu` VALUES (1895678371239739394, '数据查询', 1893185148864851970, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:list', '', NULL, 1, '2025-03-01 19:31:58', 1, '2025-03-01 11:34:12', '');
INSERT INTO `sys_menu` VALUES (1895678474696441857, '新增数据', 1893185148864851970, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:add', '', NULL, 1, '2025-03-01 19:32:23', 1, '2025-03-01 11:34:06', '');
INSERT INTO `sys_menu` VALUES (1895678595702112258, '修改数据', 1893185148864851970, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:edit', '', NULL, 1, '2025-03-01 19:32:52', 1, '2025-03-01 11:34:00', '');
INSERT INTO `sys_menu` VALUES (1895678705290887170, '检测上传', 1893185148864851970, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:check', '', NULL, 1, '2025-03-01 19:33:18', 1, '2025-03-01 11:33:55', '');
INSERT INTO `sys_menu` VALUES (1895678780712861697, '删除数据', 1893185148864851970, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:delete', '', NULL, 1, '2025-03-01 19:33:36', 1, '2025-03-01 11:33:48', '');
INSERT INTO `sys_menu` VALUES (1896071202978586626, '链接二', 1893200932576104450, 1, 'index/2', 'account/links/index', NULL, 1, 0, 'C', '0', '0', 'app:links:list', 'link', NULL, 1, '2025-03-03 21:32:57', 1, '2025-03-02 13:34:01', '');
INSERT INTO `sys_menu` VALUES (1896071647906160641, '数据查询', 1896071202978586626, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:list', '', NULL, 1, '2025-03-02 13:34:43', 1, '2025-03-02 13:34:43', '');
INSERT INTO `sys_menu` VALUES (1896071699366076417, '新增数据', 1896071202978586626, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:add', '', NULL, 1, '2025-03-02 13:34:55', 1, '2025-03-02 13:34:55', '');
INSERT INTO `sys_menu` VALUES (1896071769671000065, '修改数据', 1896071202978586626, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:edit', '', NULL, 1, '2025-03-02 13:35:12', 1, '2025-03-02 13:35:12', '');
INSERT INTO `sys_menu` VALUES (1896071838205927425, '检测上传', 1896071202978586626, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:check', '', NULL, 1, '2025-03-02 13:35:28', 1, '2025-03-02 13:35:28', '');
INSERT INTO `sys_menu` VALUES (1896071901326008321, '删除数据', 1896071202978586626, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:links:delete', '', NULL, 1, '2025-03-02 13:35:43', 1, '2025-03-02 13:35:43', '');
INSERT INTO `sys_menu` VALUES (1896072295246651394, 'Pairs数据管理', 0, 4, 'account/pairs', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'excel', NULL, 1, '2025-03-02 21:37:17', 1, '2025-03-02 13:37:30', '');
INSERT INTO `sys_menu` VALUES (1896072705625743362, '账号数据', 1896072295246651394, 1, 'ids', 'account/ids/pairs/index', NULL, 1, 0, 'C', '0', '0', 'app:account:pairs:list', 'guide', NULL, 1, '2025-03-03 21:38:55', 1, '2025-03-02 18:56:24', '');
INSERT INTO `sys_menu` VALUES (1896073140503764993, '账号记录', 1896072295246651394, 2, 'records/2', 'account/ids/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:record:list', 'education', NULL, 1, '2025-03-03 13:40:39', 1, '2025-03-02 21:38:28', '');
INSERT INTO `sys_menu` VALUES (1896073642679394306, '数据查询', 1896072705625743362, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:list', '', NULL, 1, '2025-03-02 21:42:38', 1, '2025-03-02 18:52:06', '');
INSERT INTO `sys_menu` VALUES (1896073814855573506, '新增数据', 1896072705625743362, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:add', '', NULL, 1, '2025-03-02 21:43:19', 1, '2025-03-02 18:52:12', '');
INSERT INTO `sys_menu` VALUES (1896151623506518017, '批量上传', 1896072705625743362, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:upload', '', NULL, 1, '2025-03-02 18:52:31', 1, '2025-03-02 18:52:31', '');
INSERT INTO `sys_menu` VALUES (1896151771884216321, '修改数据', 1896072705625743362, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:edit', '', NULL, 1, '2025-03-02 18:53:06', 1, '2025-03-02 18:53:06', '');
INSERT INTO `sys_menu` VALUES (1896151858752446465, '解绑数据', 1896072705625743362, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:unbind', '', NULL, 1, '2025-03-02 18:53:27', 1, '2025-03-02 18:53:27', '');
INSERT INTO `sys_menu` VALUES (1896151938112872449, '删除数据', 1896072705625743362, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:delete', '', NULL, 1, '2025-03-02 18:53:46', 1, '2025-03-02 18:53:46', '');
INSERT INTO `sys_menu` VALUES (1896152022787481602, '分配数据', 1896072705625743362, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:dispatch', '', NULL, 1, '2025-03-02 18:54:06', 1, '2025-03-02 18:54:06', '');
INSERT INTO `sys_menu` VALUES (1896152108967845889, '数据查询', 1896073140503764993, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:pairs:record:list', '', NULL, 1, '2025-03-02 18:54:26', 1, '2025-03-02 18:54:26', '');
INSERT INTO `sys_menu` VALUES (1896173670857105410, 'Ws1数据管理', 0, 5, 'account/ws1', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'tab', NULL, 1, '2025-03-04 12:20:07', 1, '2025-03-03 22:30:27', '');
INSERT INTO `sys_menu` VALUES (1896174171526979586, 'Ws1账号数据', 1896173670857105410, 1, 'ids', 'account/ids/ws1/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws1:list', 'guide', NULL, 1, '2025-03-04 20:22:06', 1, '2025-03-03 22:30:36', '');
INSERT INTO `sys_menu` VALUES (1896175311891128321, 'WS2数据管理', 0, 6, 'account/ws2', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'international', NULL, 1, '2025-03-04 20:26:38', 1, '2025-03-03 22:30:51', '');
INSERT INTO `sys_menu` VALUES (1896175485468205058, 'Ws2账号数据', 1896175311891128321, 1, 'ids', 'account/ids/ws2/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws2:list', 'guide', NULL, 1, '2025-03-03 20:27:20', 1, '2025-03-03 22:30:57', '');
INSERT INTO `sys_menu` VALUES (1896198678643924994, '数据查询', 1896174171526979586, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:list', '', NULL, 1, '2025-03-02 21:59:29', 1, '2025-03-02 21:59:29', '');
INSERT INTO `sys_menu` VALUES (1896198737372569601, '新增数据', 1896174171526979586, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:add', '', NULL, 1, '2025-03-03 05:59:43', 1, '2025-03-02 21:59:51', '');
INSERT INTO `sys_menu` VALUES (1896198853030502402, '批量上传', 1896174171526979586, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:upload', '', NULL, 1, '2025-03-02 22:00:11', 1, '2025-03-02 22:00:11', '');
INSERT INTO `sys_menu` VALUES (1896198947771441153, '修改数据', 1896174171526979586, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:edit', '', NULL, 1, '2025-03-02 22:00:33', 1, '2025-03-02 22:00:33', '');
INSERT INTO `sys_menu` VALUES (1896199016511889409, '解绑数据', 1896174171526979586, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:unbind', '', NULL, 1, '2025-03-02 22:00:50', 1, '2025-03-02 22:00:50', '');
INSERT INTO `sys_menu` VALUES (1896199091074031618, '删除数据', 1896174171526979586, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:delete', '', NULL, 1, '2025-03-02 22:01:08', 1, '2025-03-02 22:01:08', '');
INSERT INTO `sys_menu` VALUES (1896199159344717826, '分配数据', 1896174171526979586, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws1:dispatch', '', NULL, 1, '2025-03-02 22:01:24', 1, '2025-03-02 22:01:24', '');
INSERT INTO `sys_menu` VALUES (1896537443199528962, 'Ws1账号记录', 1896173670857105410, 2, 'records', 'account/ids/ws2/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws2:record:list', 'education', NULL, 1, '2025-03-05 04:25:37', 1, '2025-03-03 22:30:43', '');
INSERT INTO `sys_menu` VALUES (1896537593242365953, '数据查询', 1896537443199528962, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:record:list', '', NULL, 1, '2025-03-03 20:26:13', 1, '2025-03-03 20:26:13', '');
INSERT INTO `sys_menu` VALUES (1896538091181748225, 'Ws2账号记录', 1896175311891128321, 2, 'records', 'account/ids/ws2/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws2:record:list', 'education', NULL, 1, '2025-03-04 12:28:12', 1, '2025-03-03 22:31:03', '');
INSERT INTO `sys_menu` VALUES (1896538226062176257, '数据查询', 1896175485468205058, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:list', '', NULL, 1, '2025-03-03 20:28:44', 1, '2025-03-03 20:28:44', '');
INSERT INTO `sys_menu` VALUES (1896538318571745282, '新增数据', 1896175485468205058, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:add', '', NULL, 1, '2025-03-03 20:29:06', 1, '2025-03-03 20:29:06', '');
INSERT INTO `sys_menu` VALUES (1896538378952945665, '批量上传', 1896175485468205058, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:upload', '', NULL, 1, '2025-03-03 20:29:20', 1, '2025-03-03 20:29:20', '');
INSERT INTO `sys_menu` VALUES (1896538453460561922, '修改数据', 1896175485468205058, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:edit', '', NULL, 1, '2025-03-03 20:29:38', 1, '2025-03-03 20:29:38', '');
INSERT INTO `sys_menu` VALUES (1896538530954522626, '解绑数据', 1896175485468205058, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:unbind', '', NULL, 1, '2025-03-03 20:29:56', 1, '2025-03-03 20:29:56', '');
INSERT INTO `sys_menu` VALUES (1896538593537732610, '删除数据', 1896175485468205058, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:delete', '', NULL, 1, '2025-03-03 20:30:11', 1, '2025-03-03 20:30:11', '');
INSERT INTO `sys_menu` VALUES (1896538645576462337, '分配数据', 1896175485468205058, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:dispatch', '', NULL, 1, '2025-03-03 20:30:24', 1, '2025-03-03 20:30:24', '');
INSERT INTO `sys_menu` VALUES (1896538716162404353, '数据查询', 1896538091181748225, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws2:record:list', '', NULL, 1, '2025-03-03 20:30:41', 1, '2025-03-03 20:30:41', '');
INSERT INTO `sys_menu` VALUES (1896539459510513665, 'Ws3数据管理', 0, 7, 'account/ws3', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'online', NULL, 1, '2025-03-04 04:33:38', 1, '2025-03-03 22:31:09', '');
INSERT INTO `sys_menu` VALUES (1896539629094612994, 'Ws4数据管理', 0, 8, 'account/ws4', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'maxkey', NULL, 1, '2025-03-04 04:34:18', 1, '2025-03-03 22:31:32', '');
INSERT INTO `sys_menu` VALUES (1896539852973977602, 'Ws5数据管理', 0, 9, 'account/ws5', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'job', NULL, 1, '2025-03-04 04:35:12', 1, '2025-03-03 22:31:49', '');
INSERT INTO `sys_menu` VALUES (1896539969005203457, 'Ws6数据管理', 0, 10, 'account/ws6', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'people', NULL, 1, '2025-03-04 04:35:39', 1, '2025-03-03 22:32:09', '');
INSERT INTO `sys_menu` VALUES (1896540209812779009, 'Ws7数据管理', 0, 11, 'account/ws7', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'skill', NULL, 1, '2025-03-04 12:36:37', 1, '2025-03-03 22:32:27', '');
INSERT INTO `sys_menu` VALUES (1896563673001013250, 'Ws3账号数据', 1896539459510513665, 1, 'ids', 'account/ids/ws3/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws3:list', 'guide', NULL, 1, '2025-03-04 06:09:51', 1, '2025-03-03 22:31:18', '');
INSERT INTO `sys_menu` VALUES (1896563879004254210, 'Ws3账号记录', 1896539459510513665, 2, 'records', 'account/ids/ws3/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws3:record:list', 'education', NULL, 1, '2025-03-04 06:10:40', 1, '2025-03-03 22:31:26', '');
INSERT INTO `sys_menu` VALUES (1896564067626299394, 'Ws4账号数据', 1896539629094612994, 1, 'ids', 'account/ids/ws4/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws4:list', '', NULL, 1, '2025-03-04 14:11:25', 1, '2025-04-02 19:33:26', '');
INSERT INTO `sys_menu` VALUES (1896564253241028610, 'Ws4账号记录', 1896539629094612994, 2, 'records', 'account/ids/wd4/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws4:record:list', 'education', NULL, 1, '2025-03-04 06:12:09', 1, '2025-03-03 22:31:44', '');
INSERT INTO `sys_menu` VALUES (1896564306873593857, '数据查询', 1896564253241028610, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:record:list', '', NULL, 1, '2025-03-03 22:12:22', 1, '2025-03-03 22:12:22', '');
INSERT INTO `sys_menu` VALUES (1896564376029278210, '数据查询', 1896563879004254210, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:record:list', '', NULL, 1, '2025-03-03 22:12:38', 1, '2025-03-03 22:12:38', '');
INSERT INTO `sys_menu` VALUES (1896564666455470081, 'Ws5账号数据', 1896539852973977602, 1, 'ids', 'account/ids/ws5/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws5:list', 'guide', NULL, 1, '2025-03-04 14:13:48', 1, '2025-04-02 19:33:39', '');
INSERT INTO `sys_menu` VALUES (1896564869417840641, 'Ws5账号记录', 1896539852973977602, 2, 'records', 'account/ids/ws5/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws5:record:list', '', NULL, 1, '2025-03-04 06:14:36', 1, '2025-03-03 22:32:03', '');
INSERT INTO `sys_menu` VALUES (1896564909339226114, '数据查询', 1896564869417840641, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:record:list', '', NULL, 1, '2025-03-03 22:14:46', 1, '2025-03-03 22:14:46', '');
INSERT INTO `sys_menu` VALUES (1896565135131193345, 'Ws6账号数据', 1896539969005203457, 1, 'ids', 'account/ids/ws6/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws6:list', '', NULL, 1, '2025-03-04 06:15:39', 1, '2025-03-03 22:32:16', '');
INSERT INTO `sys_menu` VALUES (1896565319902867457, 'Ws6账号记录', 1896539969005203457, 1, 'records', 'account/ids/ws6/RecordIndex', NULL, 1, 0, 'C', '0', '0', 'app:account:ws6:record:list', 'education', NULL, 1, '2025-03-04 06:16:23', 1, '2025-03-03 22:32:22', '');
INSERT INTO `sys_menu` VALUES (1896565376211398657, '数据查询', 1896565319902867457, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:record:list', '', NULL, 1, '2025-03-03 22:16:37', 1, '2025-03-03 22:16:37', '');
INSERT INTO `sys_menu` VALUES (1896565539772477441, 'Ws7账号数据', 1896540209812779009, 1, 'ids', 'account/ids/ws7/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws7:list', '', NULL, 1, '2025-03-04 06:17:16', 1, '2025-03-03 22:32:37', '');
INSERT INTO `sys_menu` VALUES (1896565722480553986, 'Ws7账号记录', 1896540209812779009, 2, 'records', 'account/ids/ws7/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws7:record:list', 'education', NULL, 1, '2025-03-04 14:17:59', 1, '2025-03-03 22:32:42', '');
INSERT INTO `sys_menu` VALUES (1896566188832632834, '数据查询', 1896563673001013250, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:list', '', NULL, 1, '2025-03-03 22:19:51', 1, '2025-03-03 22:19:51', '');
INSERT INTO `sys_menu` VALUES (1896566248907649026, '新增数据', 1896563673001013250, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:add', '', NULL, 1, '2025-03-03 22:20:05', 1, '2025-03-03 22:20:05', '');
INSERT INTO `sys_menu` VALUES (1896566311570550786, '批量上传', 1896563673001013250, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:upload', '', NULL, 1, '2025-03-03 22:20:20', 1, '2025-03-03 22:20:20', '');
INSERT INTO `sys_menu` VALUES (1896566357066166274, '修改数据', 1896563673001013250, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:edit', '', NULL, 1, '2025-03-03 22:20:31', 1, '2025-03-03 22:20:31', '');
INSERT INTO `sys_menu` VALUES (1896566411504037890, '解绑数据', 1896563673001013250, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:unbind', '', NULL, 1, '2025-03-03 22:20:44', 1, '2025-03-03 22:20:44', '');
INSERT INTO `sys_menu` VALUES (1896566482131922946, '删除数据', 1896563673001013250, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:delete', '', NULL, 1, '2025-03-03 22:21:01', 1, '2025-03-03 22:21:01', '');
INSERT INTO `sys_menu` VALUES (1896566548766830594, '分配数据', 1896563673001013250, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws3:dispatch', '', NULL, 1, '2025-03-03 22:21:16', 1, '2025-03-03 22:21:16', '');
INSERT INTO `sys_menu` VALUES (1896566613778542594, '数据查询', 1896564067626299394, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:list', '', NULL, 1, '2025-03-04 06:21:32', 1, '2025-03-03 22:21:47', '');
INSERT INTO `sys_menu` VALUES (1896566744926040066, '新增数据', 1896564067626299394, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:add', '', NULL, 1, '2025-03-04 06:22:03', 1, '2025-03-03 22:23:02', '');
INSERT INTO `sys_menu` VALUES (1896566875729604609, '批量上传', 1896564067626299394, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:upload', '', NULL, 1, '2025-03-03 22:22:34', 1, '2025-03-03 22:22:34', '');
INSERT INTO `sys_menu` VALUES (1896567055338090498, '修改数据', 1896564067626299394, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:edit', '', NULL, 1, '2025-03-03 22:23:17', 1, '2025-03-03 22:23:17', '');
INSERT INTO `sys_menu` VALUES (1896567134878871553, '解绑数据', 1896564067626299394, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:edit', '', NULL, 1, '2025-03-03 22:23:36', 1, '2025-03-03 22:23:36', '');
INSERT INTO `sys_menu` VALUES (1896567203015340034, '删除数据', 1896564067626299394, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:delete', '', NULL, 1, '2025-03-03 22:23:52', 1, '2025-03-03 22:23:52', '');
INSERT INTO `sys_menu` VALUES (1896567250750713858, '分配数据', 1896564067626299394, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws4:dispatch', '', NULL, 1, '2025-03-03 22:24:04', 1, '2025-03-03 22:24:04', '');
INSERT INTO `sys_menu` VALUES (1896567382934204418, '数据查询', 1896564666455470081, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:list', '', NULL, 1, '2025-03-03 22:24:35', 1, '2025-03-03 22:24:35', '');
INSERT INTO `sys_menu` VALUES (1896567422721372162, '新增数据', 1896564666455470081, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:add', '', NULL, 1, '2025-03-03 22:24:45', 1, '2025-03-03 22:24:45', '');
INSERT INTO `sys_menu` VALUES (1896567483723329537, '批量上传', 1896564666455470081, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:upload', '', NULL, 1, '2025-03-03 22:24:59', 1, '2025-03-03 22:24:59', '');
INSERT INTO `sys_menu` VALUES (1896567537481723905, '修改数据', 1896564666455470081, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:edit', '', NULL, 1, '2025-03-03 22:25:12', 1, '2025-03-03 22:25:12', '');
INSERT INTO `sys_menu` VALUES (1896567613499289601, '解绑数据', 1896564666455470081, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:unbind', '', NULL, 1, '2025-03-03 22:25:30', 1, '2025-03-03 22:25:30', '');
INSERT INTO `sys_menu` VALUES (1896567657703059458, '删除数据', 1896564666455470081, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:delete', '', NULL, 1, '2025-03-03 22:25:41', 1, '2025-03-03 22:25:41', '');
INSERT INTO `sys_menu` VALUES (1896567719879421954, '分配数据', 1896564666455470081, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws5:dispatch', '', NULL, 1, '2025-03-03 22:25:56', 1, '2025-03-03 22:25:56', '');
INSERT INTO `sys_menu` VALUES (1896567807188054018, '数据查询', 1896565135131193345, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:list', '', NULL, 1, '2025-03-03 22:26:16', 1, '2025-03-03 22:26:16', '');
INSERT INTO `sys_menu` VALUES (1896567850288721921, '新增数据', 1896565135131193345, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:add', '', NULL, 1, '2025-03-03 22:26:27', 1, '2025-03-03 22:26:27', '');
INSERT INTO `sys_menu` VALUES (1896567909927530497, '批量上传', 1896565135131193345, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:upload', '', NULL, 1, '2025-03-03 22:26:41', 1, '2025-03-03 22:26:41', '');
INSERT INTO `sys_menu` VALUES (1896567990286200834, '修改数据', 1896565135131193345, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:edit', '', NULL, 1, '2025-03-03 22:27:00', 1, '2025-03-03 22:27:00', '');
INSERT INTO `sys_menu` VALUES (1896568048553472002, '解绑数据', 1896565135131193345, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:unbind', '', NULL, 1, '2025-03-03 22:27:14', 1, '2025-03-03 22:27:14', '');
INSERT INTO `sys_menu` VALUES (1896568101229735937, '删除数据', 1896565135131193345, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:delete', '', NULL, 1, '2025-03-03 22:27:27', 1, '2025-03-03 22:27:27', '');
INSERT INTO `sys_menu` VALUES (1896568159081771009, '分配数据', 1896565135131193345, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws6:dispatch', '', NULL, 1, '2025-03-03 22:27:40', 1, '2025-03-03 22:27:40', '');
INSERT INTO `sys_menu` VALUES (1896568298764677122, '数据查询', 1896565539772477441, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:list', '', NULL, 1, '2025-03-03 22:28:14', 1, '2025-03-03 22:28:14', '');
INSERT INTO `sys_menu` VALUES (1896568336320475138, '新增数据', 1896565539772477441, 2, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:add', '', NULL, 1, '2025-03-03 22:28:23', 1, '2025-03-03 22:28:23', '');
INSERT INTO `sys_menu` VALUES (1896568384169095169, '批量上传', 1896565539772477441, 3, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:upload', '', NULL, 1, '2025-03-03 22:28:34', 1, '2025-03-03 22:28:34', '');
INSERT INTO `sys_menu` VALUES (1896568467224702977, '修改数据', 1896565539772477441, 4, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:edit', '', NULL, 1, '2025-03-03 22:28:54', 1, '2025-03-03 22:28:54', '');
INSERT INTO `sys_menu` VALUES (1896568534094491649, '解绑数据', 1896565539772477441, 5, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:unbind', '', NULL, 1, '2025-03-03 22:29:10', 1, '2025-03-03 22:29:10', '');
INSERT INTO `sys_menu` VALUES (1896568591392878593, '删除数据', 1896565539772477441, 6, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:delete', '', NULL, 1, '2025-03-03 22:29:23', 1, '2025-03-03 22:29:23', '');
INSERT INTO `sys_menu` VALUES (1896568641783246849, '分配数据', 1896565539772477441, 7, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:dispatch', '', NULL, 1, '2025-03-03 22:29:35', 1, '2025-03-03 22:29:35', '');
INSERT INTO `sys_menu` VALUES (1896568697315831809, '数据查询', 1896565722480553986, 1, '', NULL, NULL, 1, 0, 'F', '0', '0', 'app:account:ws7:record:list', '', NULL, 1, '2025-03-03 22:29:49', 1, '2025-03-03 22:29:49', '');
INSERT INTO `sys_menu` VALUES (1896931049232064513, '数据检索', 0, 4, 'account/search', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'search', NULL, 1, '2025-03-04 22:29:40', 1, '2025-03-04 22:29:40', '');
INSERT INTO `sys_menu` VALUES (1896931183504318466, 'Ws数据检索', 1896931049232064513, 1, 'ws/ids', 'account/ids/ws/index', NULL, 1, 0, 'C', '0', '0', 'app:account:ws:search', 'search', NULL, 1, '2025-03-04 22:30:12', 1, '2025-03-04 22:30:12', '');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` bigint(0) NOT NULL COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '温馨提醒：2018-07-01 新版本发布啦', '2', 0x3C703EE696B0E78988E69CACE58685E5AEB93C2F703E, '0', 103, 1, '2024-08-11 13:53:20', 1, '2024-08-12 20:16:22', '管理员');
INSERT INTO `sys_notice` VALUES (2, '维护通知：2018-07-01 系统凌晨维护', '1', 0xE7BBB4E68AA4E58685E5AEB9, '0', 103, 1, '2024-08-11 13:53:20', NULL, NULL, '管理员');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint(0) NOT NULL COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(0) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(0) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int(0) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint(0) NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type`) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status`) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int(0) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(0) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `deleted` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1891097863029706771 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'superadmin', 1, '1', 1, 1, '0', '0', 103, 1, '2024-08-11 13:53:18', NULL, NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (1891034799911632897, 'Line 粉端', 'uploader_1', 11, '1', 1, 1, '0', '0', NULL, 1, '2025-02-16 16:00:05', 1, '2025-03-02 22:47:06', '');
INSERT INTO `sys_role` VALUES (1891037415005663233, 'Line 业务', 'salesman_1', 11, '1', 1, 1, '0', '0', NULL, 1, '2025-02-16 16:10:28', 1, '2025-03-02 22:47:11', '');
INSERT INTO `sys_role` VALUES (1891097863029706754, '普通管理员', 'admin_account', 1, '1', 1, 1, '0', '0', NULL, 1, '2025-02-16 20:10:40', 1, '2025-04-19 11:02:13', '');
INSERT INTO `sys_role` VALUES (1891097863029706755, 'Pairs 粉端', 'uploader_2', 12, '1', 1, 1, '0', '0', NULL, 1, '2025-03-02 21:52:47', 1, '2025-03-02 22:47:20', '');
INSERT INTO `sys_role` VALUES (1891097863029706756, 'Pairs 业务', 'salesman_2', 12, '1', 1, 1, '0', '0', NULL, 1, '2025-03-02 21:53:39', 1, '2025-03-02 22:47:29', '');
INSERT INTO `sys_role` VALUES (1891097863029706757, 'WS1 粉端', 'uploader_3', 13, '1', 1, 1, '0', '0', NULL, 1, '2025-03-02 21:54:25', 1, '2025-03-02 22:47:35', '');
INSERT INTO `sys_role` VALUES (1891097863029706758, 'WS1 业务', 'salesman_3', 13, '1', 1, 1, '0', '0', NULL, 1, '2025-03-02 21:55:03', 1, '2025-03-02 22:47:52', '');
INSERT INTO `sys_role` VALUES (1891097863029706759, 'Ws2 粉端', 'uploader_4', 14, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 21:16:55', 1, '2025-03-03 22:02:44', '');
INSERT INTO `sys_role` VALUES (1891097863029706760, 'Ws2 业务', 'salesman_4', 14, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:03:17', 1, '2025-03-03 22:04:25', '');
INSERT INTO `sys_role` VALUES (1891097863029706761, 'Ws3 粉端', 'uploader_5', 15, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:03:57', 1, '2025-03-03 22:03:57', '');
INSERT INTO `sys_role` VALUES (1891097863029706762, 'Ws3 业务', 'salesman_5', 15, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:04:52', 1, '2025-03-03 22:04:52', '');
INSERT INTO `sys_role` VALUES (1891097863029706763, 'Ws4 粉端', 'uploader_6', 16, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:05:16', 1, '2025-03-03 22:05:16', '');
INSERT INTO `sys_role` VALUES (1891097863029706764, 'Ws4 业务', 'salesman_6', 16, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:05:42', 1, '2025-03-03 22:05:42', '');
INSERT INTO `sys_role` VALUES (1891097863029706765, 'Ws5 粉端', 'uploader_7', 17, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:06:05', 1, '2025-03-03 22:06:05', '');
INSERT INTO `sys_role` VALUES (1891097863029706766, 'Ws5 业务', 'salesman_7', 17, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:06:25', 1, '2025-03-03 22:06:25', '');
INSERT INTO `sys_role` VALUES (1891097863029706767, 'Ws6 粉端', 'uploader_8', 18, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:06:44', 1, '2025-03-03 22:06:44', '');
INSERT INTO `sys_role` VALUES (1891097863029706768, 'Ws6 业务', 'salesman_8', 18, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:07:04', 1, '2025-03-03 22:07:04', '');
INSERT INTO `sys_role` VALUES (1891097863029706769, 'Ws7 粉端', 'uploader_9', 19, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:07:23', 1, '2025-03-03 22:07:23', '');
INSERT INTO `sys_role` VALUES (1891097863029706770, 'Ws7 业务', 'salesman_9', 19, '1', 1, 1, '0', '0', NULL, 1, '2025-03-03 22:07:50', 1, '2025-03-03 22:08:03', '');
INSERT INTO `sys_role` VALUES (1891097863029706771, 'Line 管理员', 'admin_1', 11, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:03:00', 1, '2025-04-19 11:03:39', '');
INSERT INTO `sys_role` VALUES (1891097863029706772, 'Pairs 管理员', 'admin_2', 12, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:03:32', 1, '2025-04-19 11:03:32', '');
INSERT INTO `sys_role` VALUES (1891097863029706773, 'WS1 管理员', 'admin_3', 13, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:04:24', 1, '2025-04-19 11:04:24', '');
INSERT INTO `sys_role` VALUES (1891097863029706774, 'WS2 管理员', 'admin_4', 14, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:05:11', 1, '2025-04-19 11:05:11', '');
INSERT INTO `sys_role` VALUES (1891097863029706775, 'WS3 管理员', 'admin_5', 15, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:05:44', 1, '2025-04-19 11:05:44', '');
INSERT INTO `sys_role` VALUES (1891097863029706776, 'WS4 管理员', 'admin_6', 16, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:06:32', 1, '2025-04-19 11:06:32', '');
INSERT INTO `sys_role` VALUES (1891097863029706777, 'WS5 管理员', 'admin_7', 17, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:06:59', 1, '2025-04-19 11:06:59', '');
INSERT INTO `sys_role` VALUES (1891097863029706778, 'WS6 管理员', 'admin_8', 18, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:07:22', 1, '2025-04-19 11:07:22', '');
INSERT INTO `sys_role` VALUES (1891097863029706779, 'WS7 管理员', 'admin_9', 19, '1', 1, 1, '0', '0', NULL, 1, '2025-04-19 11:08:07', 1, '2025-04-19 11:08:07', '');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(0) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 100);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1001);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1893293842805972993);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1893294461746831361);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1893296079296946177);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895102350161530881);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895102467321024514);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895102629619617793);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895102923774545921);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895103145430929410);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895103202486046721);
INSERT INTO `sys_role_menu` VALUES (1891034799911632897, 1895659448935018498);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1893293842805972993);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1893294461746831361);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1893296079296946177);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895102350161530881);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895102629619617793);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895103145430929410);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895678371239739394);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895678474696441857);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895678595702112258);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1895678705290887170);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1896071202978586626);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1896071647906160641);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1896071699366076417);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1896071769671000065);
INSERT INTO `sys_role_menu` VALUES (1891037415005663233, 1896071838205927425);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 101);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 102);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 103);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 104);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 105);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 106);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 107);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 108);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 500);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 501);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1004);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1008);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1010);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1011);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1012);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1013);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1014);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1015);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1016);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1017);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1018);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1019);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1020);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1021);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1022);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1023);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1024);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1025);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1026);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1027);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1028);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1029);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1030);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1031);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1032);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1033);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1034);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1035);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1036);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1037);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1038);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1039);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1040);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1041);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1042);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1043);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1044);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1045);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1050);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825080322201141250);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825080790348382210);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825081011325288449);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825081097606316033);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825081187867738113);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825081310366580737);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825082069980200962);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825082246145163265);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825082399165956098);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1825082511338422274);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893293842805972993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893294461746831361);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893295568250363906);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1893296079296946177);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895102350161530881);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895102467321024514);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895102629619617793);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895102759504629761);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895102923774545921);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895103145430929410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895103202486046721);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895104328958660609);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895659448935018498);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895678371239739394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895678474696441857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895678595702112258);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895678705290887170);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1895678780712861697);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071202978586626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071647906160641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071699366076417);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071769671000065);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071838205927425);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896071901326008321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896072295246651394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896072705625743362);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896073140503764993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896073642679394306);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896073814855573506);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896151623506518017);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896151771884216321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896151858752446465);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896151938112872449);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896152022787481602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896152108967845889);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896173670857105410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896174171526979586);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896175311891128321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896175485468205058);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896198678643924994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896198737372569601);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896198853030502402);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896198947771441153);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896199016511889409);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896199091074031618);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896199159344717826);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896537443199528962);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896537593242365953);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538091181748225);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538226062176257);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538318571745282);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538378952945665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538453460561922);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538530954522626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538593537732610);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538645576462337);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896538716162404353);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896539459510513665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896539629094612994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896539852973977602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896539969005203457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896540209812779009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896563673001013250);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896563879004254210);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564067626299394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564253241028610);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564306873593857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564376029278210);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564666455470081);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564869417840641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896564909339226114);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896565135131193345);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896565319902867457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896565376211398657);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896565539772477441);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896565722480553986);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566188832632834);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566248907649026);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566311570550786);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566357066166274);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566411504037890);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566482131922946);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566548766830594);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566613778542594);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566744926040066);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896566875729604609);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567055338090498);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567134878871553);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567203015340034);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567250750713858);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567382934204418);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567422721372162);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567483723329537);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567537481723905);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567613499289601);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567657703059458);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567719879421954);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567807188054018);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567850288721921);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567909927530497);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896567990286200834);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568048553472002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568101229735937);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568159081771009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568298764677122);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568336320475138);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568384169095169);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568467224702977);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568534094491649);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568591392878593);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568641783246849);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896568697315831809);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896931049232064513);
INSERT INTO `sys_role_menu` VALUES (1891097863029706754, 1896931183504318466);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1895678371239739394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1895678474696441857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1895678595702112258);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1895678705290887170);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1895678780712861697);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071202978586626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071647906160641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071699366076417);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071769671000065);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071838205927425);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896071901326008321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896072295246651394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896072705625743362);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896073140503764993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896073642679394306);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896073814855573506);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896151623506518017);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896151771884216321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896151858752446465);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896151938112872449);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896152022787481602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706755, 1896152108967845889);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896072295246651394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896072705625743362);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896073140503764993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896073642679394306);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896073814855573506);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896151623506518017);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896151771884216321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896151858752446465);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896151938112872449);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896152022787481602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706756, 1896152108967845889);
INSERT INTO `sys_role_menu` VALUES (1891097863029706757, 1896173670857105410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706757, 1896174171526979586);
INSERT INTO `sys_role_menu` VALUES (1891097863029706758, 1896173670857105410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706758, 1896174171526979586);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896175311891128321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896175485468205058);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538091181748225);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538226062176257);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538318571745282);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538378952945665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538453460561922);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538530954522626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538593537732610);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538645576462337);
INSERT INTO `sys_role_menu` VALUES (1891097863029706759, 1896538716162404353);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896175311891128321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896175485468205058);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896538091181748225);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896538226062176257);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896538453460561922);
INSERT INTO `sys_role_menu` VALUES (1891097863029706760, 1896538716162404353);
INSERT INTO `sys_role_menu` VALUES (1891097863029706761, 1896539459510513665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706762, 1896539459510513665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706763, 1896539629094612994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706764, 1896539629094612994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706765, 1896539852973977602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706766, 1896539852973977602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706767, 1896539969005203457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706768, 1896539969005203457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706769, 1896540209812779009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706770, 1896540209812779009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893293842805972993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893294461746831361);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893295568250363906);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1893296079296946177);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895102350161530881);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895102467321024514);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895102629619617793);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895102759504629761);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895102923774545921);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895103145430929410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895103202486046721);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895104328958660609);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895659448935018498);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895678371239739394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895678474696441857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895678595702112258);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895678705290887170);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1895678780712861697);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071202978586626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071647906160641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071699366076417);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071769671000065);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071838205927425);
INSERT INTO `sys_role_menu` VALUES (1891097863029706771, 1896071901326008321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1893185148864851970);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1893200932576104450);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1895678371239739394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1895678474696441857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1895678595702112258);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1895678705290887170);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1895678780712861697);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071202978586626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071647906160641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071699366076417);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071769671000065);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071838205927425);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896071901326008321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896072295246651394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896072705625743362);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896073140503764993);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896073642679394306);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896073814855573506);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896151623506518017);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896151771884216321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896151858752446465);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896151938112872449);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896152022787481602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706772, 1896152108967845889);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896173670857105410);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896174171526979586);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896198678643924994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896198737372569601);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896198853030502402);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896198947771441153);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896199016511889409);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896199091074031618);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896199159344717826);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896537443199528962);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896537593242365953);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896931049232064513);
INSERT INTO `sys_role_menu` VALUES (1891097863029706773, 1896931183504318466);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896175311891128321);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896175485468205058);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538091181748225);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538226062176257);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538318571745282);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538378952945665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538453460561922);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538530954522626);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538593537732610);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538645576462337);
INSERT INTO `sys_role_menu` VALUES (1891097863029706774, 1896538716162404353);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896539459510513665);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896563673001013250);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896563879004254210);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896564376029278210);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566188832632834);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566248907649026);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566311570550786);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566357066166274);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566411504037890);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566482131922946);
INSERT INTO `sys_role_menu` VALUES (1891097863029706775, 1896566548766830594);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896539629094612994);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896564067626299394);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896564253241028610);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896564306873593857);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896566613778542594);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896566744926040066);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896566875729604609);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896567055338090498);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896567134878871553);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896567203015340034);
INSERT INTO `sys_role_menu` VALUES (1891097863029706776, 1896567250750713858);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896539852973977602);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896564666455470081);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896564869417840641);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896564909339226114);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567382934204418);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567422721372162);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567483723329537);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567537481723905);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567613499289601);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567657703059458);
INSERT INTO `sys_role_menu` VALUES (1891097863029706777, 1896567719879421954);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896539969005203457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896565135131193345);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896565319902867457);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896565376211398657);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896567807188054018);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896567850288721921);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896567909927530497);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896567990286200834);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896568048553472002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896568101229735937);
INSERT INTO `sys_role_menu` VALUES (1891097863029706778, 1896568159081771009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 100);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1001);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1002);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1003);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1005);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1006);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1007);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896540209812779009);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896565539772477441);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896565722480553986);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568298764677122);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568336320475138);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568384169095169);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568467224702977);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568534094491649);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568591392878593);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568641783246849);
INSERT INTO `sys_role_menu` VALUES (1891097863029706779, 1896568697315831809);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'sys_user' COMMENT '用户类型（sys_user系统用户）',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` bigint(0) NULL DEFAULT NULL COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `deleted` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `dispatch` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否可分配（0可分配，1不可分配）',
  `login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_dept` bigint(0) NULL DEFAULT NULL COMMENT '创建部门',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1895814867141693442 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 100, 'admin', '疯狂的狮子Li', 'sys_user', '', '', '1', 1895703447020408833, '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '0', '0:0:0:0:0:0:0:1', '2024-08-28 21:03:43', 103, 1, '2024-08-11 13:53:18', 1, '2024-08-28 21:03:43', '管理员');
INSERT INTO `sys_user` VALUES (2, NULL, 'saleman', '业务员', 'sys_user', '', '', '0', NULL, '$2a$10$.poXtZE0p5.vKpZHzjkLDehPrFE//SDWaGiuThdMiYd.kOzQji4Ci', '0', '0', '0', '', NULL, NULL, 1, '2025-03-01 18:49:54', 1, '2025-03-01 18:49:54', '');
INSERT INTO `sys_user` VALUES (10, 100, 'riben', 'riben', 'sys_user', '', '', '0', NULL, '$2a$10$.poXtZE0p5.vKpZHzjkLDehPrFE//SDWaGiuThdMiYd.kOzQji4Ci', '0', '0', '1', '', NULL, NULL, 1, '2023-06-26 23:30:03', 1, '2025-03-13 16:13:07', '粉端, type = 2');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `role_id` bigint(0) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
