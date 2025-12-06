
-- ----------------------------
-- Table structure for merchant_marking
-- 作者 hugo
-- date 2025-03-19
-- 标注主数据
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_marking';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_marking (
   version NUMBER  ,
   deleted NUMBER  ,
   tenant_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   name VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_merchant_marking PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_marking IS '标注主数据';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.tenant_id IS '租户id';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_marking.name IS '标注名称';
