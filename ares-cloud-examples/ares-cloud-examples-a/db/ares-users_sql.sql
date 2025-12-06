-- ----------------------------
-- Table structure for ares-users
-- 作者 hugo
-- date 2024-09-27
-- 用户
-- ----------------------------
DROP TABLE IF EXISTS `ares-users`;
CREATE TABLE `ares-users`  (
    `version` int   COMMENT '版本号',
    `deleted` int   COMMENT '删除标记',
    `account` varchar(20)   COMMENT '账号',
    `nickname` varchar(20)   COMMENT '昵称',
    `id` varchar(30)   COMMENT '主键',
    `creator` varchar(30)   COMMENT '创建者',
    `create_time` bigint(13)   COMMENT '创建时间',
    `updater` varchar(30)   COMMENT '更新者',
    `update_time` bigint(13)   COMMENT '更新时间',
    `tenant_id` varchar(30)   COMMENT '租户',
    primary key (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC  COMMENT '用户';