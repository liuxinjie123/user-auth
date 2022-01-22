drop table if exists menu;
CREATE TABLE `menu` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `menu_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单编码',
  `parent_id` varchar(20) DEFAULT NULL COMMENT '父节点',
  `node_type` tinyint NOT NULL DEFAULT '1' COMMENT '节点类型，1文件夹，2页面，3按钮',
  `icon_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '图标地址',
  `sort` int NOT NULL DEFAULT '1' COMMENT '排序号',
  `link_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '页面对应的地址',
  `level` int NOT NULL DEFAULT '0' COMMENT '层次',
  `path` varchar(2500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '树id的路径 整个层次上的路径id，逗号分隔，想要找父节点特别快',
  `del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1：已删除；0：未删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

drop table if exists role;
CREATE TABLE `role` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1：已删除；0：未删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

drop table if exists role_menu;
CREATE TABLE `role_menu` (
   `id` varchar(50) NOT NULL COMMENT '主键id',
   `role_id` varchar(50) NOT NULL COMMENT '角色ID',
   `menu_id` varchar(50) NOT NULL COMMENT '菜单ID',
   PRIMARY KEY (`id`),
   KEY `idx_role_id` (`role_id`) USING BTREE,
   KEY `idx_menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';

drop table if exists user;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `mobile` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '手机号',
  `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录名',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `del` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 1：已删除；0：未删除',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`) USING BTREE,
  KEY `idx_username` (`username`) USING BTREE,
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

drop table if exists user_role;
CREATE TABLE `user_role` (
   `id` varchar(50) NOT NULL COMMENT '主键id',
   `user_id` varchar(50) NOT NULL COMMENT '用户ID',
   `role_id` varchar(50) NOT NULL COMMENT '角色ID',
   PRIMARY KEY (`id`),
   KEY `idx_user_id` (`user_id`) USING BTREE,
   KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';






