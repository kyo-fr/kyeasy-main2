-- ----------------------------
-- Table structure for ares_base_entity
-- 作者 hugo
-- date 2024-09-26
-- Entity基基础模型
-- ----------------------------
DROP TABLE IF EXISTS `ares_base_entity`;
CREATE TABLE `ares_base_entity`  (
    `version` int   COMMENT '版本号',
    `deleted` int   COMMENT '删除标记',
    `id` VARCHAR2(30)   COMMENT '主键',
    `creator` VARCHAR2(30)   COMMENT '创建者',
    `create_time` bigint(13)   COMMENT '创建时间',
    `updater` VARCHAR2(30)   COMMENT '更新者',
    `update_time` bigint(13)   COMMENT '更新时间',
    `tenant_id` VARCHAR2(30)   COMMENT '租户',
    primary key (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC  COMMENT 'Entity基基础模型';