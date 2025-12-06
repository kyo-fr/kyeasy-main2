
-- ----------------------------
-- Table structure for merchant_type
-- 作者 hugo
-- date 2024-10-15
-- 商户类型
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_merchant_type';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_type (
   version NUMBER  ,
   deleted NUMBER  ,
   type VARCHAR2(16)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_merchant_type PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_merchant_type IS '商户类型';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.type IS '类型名称';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_merchant_type.update_time IS '更新时间';
