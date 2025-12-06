
-- ----------------------------
-- Table structure for sys_s3_storage
-- 作者 hugo
-- date 2024-10-12
-- s3存储
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE sys_s3_storage';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE sys_s3_storage (
   version NUMBER  ,
   deleted NUMBER  ,
   original_file_name VARCHAR2(255)  ,
   file_type VARCHAR2(100)  ,
   container VARCHAR2(50)  NOT NULL,
   model VARCHAR2(50)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   name VARCHAR2(255)  NOT NULL,
   file_size NUMBER  ,
CONSTRAINT PK_sys_s3_storage PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE sys_s3_storage IS 's3存储';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.original_file_name IS '原始文件名';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.file_type IS '文件类型（MIME 类型）';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.container IS '存储容器名';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.model IS '所属模块';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.name IS '生成的文件名';
-- 添加列注释
COMMENT ON COLUMN sys_s3_storage.file_size IS '文件大小（字节）';
