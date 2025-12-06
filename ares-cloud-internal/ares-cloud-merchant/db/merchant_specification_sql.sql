
-- ----------------------------
-- Table structure for merchant_specification
-- 作者 hugo
-- date 2025-03-18
-- 主规格主数据
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_specification';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_specification (
   version NUMBER  ,
   deleted NUMBER  ,
   type char(32)  ,
   id VARCHAR2(32)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
   name VARCHAR2(128)  NOT NULL,
CONSTRAINT PK_merchant_specification PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_specification IS '主规格主数据';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.type IS '规格类型(单选：radio，多选：choices)';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_specification.name IS '主规格名称';
