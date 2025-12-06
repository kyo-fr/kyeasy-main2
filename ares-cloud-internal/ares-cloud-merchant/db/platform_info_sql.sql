
-- ----------------------------
-- Table structure for platform_info
-- 作者 hugo
-- date 2024-10-15
-- 平台信息
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_info';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_info (
   version NUMBER  ,
   deleted NUMBER  ,
   platform_name VARCHAR2(32)  NOT NULL,
   platform_phone VARCHAR2(32)  NOT NULL,
   tax_num VARCHAR2(32)  NOT NULL,
   address VARCHAR2(128)  NOT NULL,
   email VARCHAR2(32)  NOT NULL,
   language VARCHAR2(16)  NOT NULL,
   country VARCHAR2(32)  NOT NULL,
   mobile VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_platform_info PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_info IS '平台信息';
-- 添加列注释
COMMENT ON COLUMN platform_info.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_info.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_info.platform_name IS '平台名称';
-- 添加列注释
COMMENT ON COLUMN platform_info.platform_phone IS '平台联系电话';
-- 添加列注释
COMMENT ON COLUMN platform_info.tax_num IS '税务号';
-- 添加列注释
COMMENT ON COLUMN platform_info.address IS '平台地址';
-- 添加列注释
COMMENT ON COLUMN platform_info.email IS '平台邮箱';
-- 添加列注释
COMMENT ON COLUMN platform_info.language IS '后台语言';
-- 添加列注释
COMMENT ON COLUMN platform_info.country IS '国家';
-- 添加列注释
COMMENT ON COLUMN platform_info.mobile IS '注册手机号';
-- 添加列注释
COMMENT ON COLUMN platform_info.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_info.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_info.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_info.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_info.update_time IS '更新时间';
