
-- ----------------------------
-- Table structure for platform_approval_record
-- 作者 hugo
-- date 2025-06-16
-- 存储变更明细表
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_approval_record';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_approval_record (
   change_memory NUMBER(12,2)  ,
   version NUMBER  ,
   approval_id VARCHAR2(32)  ,
   record_type VARCHAR2(16)  ,
   deleted NUMBER  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   id VARCHAR2(30)  ,
CONSTRAINT PK_platform_approval_record PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_approval_record IS '存储变更明细表';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.change_memory IS '变更的存储';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.approval_id IS '审批id';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.record_type IS '变更类型';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_approval_record.id IS '主键';
