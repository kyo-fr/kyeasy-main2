
-- ----------------------------
-- Table structure for platform_complaints
-- 作者 hugo
-- date 2024-10-17
-- 平台投诉建议
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_complaints';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_complaints (
   version NUMBER  ,
   deleted NUMBER  ,
   user_name VARCHAR2(32)  NOT NULL,
   user_email VARCHAR2(32)  NOT NULL,
   content VARCHAR2(2000)  NOT NULL,
   phone VARCHAR2(32)  NOT NULL,
   user_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
CONSTRAINT PK_platform_complaints PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_complaints IS '平台投诉建议';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.user_name IS '用户名称';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.user_email IS '用户邮件';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.content IS '投诉内容';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.phone IS '用户手机号';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.user_id IS '用户id';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_complaints.update_time IS '更新时间';
