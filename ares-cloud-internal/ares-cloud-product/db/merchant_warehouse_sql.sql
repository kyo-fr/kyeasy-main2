
-- ----------------------------
-- Table structure for merchant_warehouse
-- 作者 hugo
-- date 2025-03-22
-- 商户仓库主数据
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_warehouse';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_warehouse (
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  NOT NULL,
   name VARCHAR2(128)  NOT NULL,
CONSTRAINT PK_merchant_warehouse PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_warehouse IS '商户仓库主数据';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.tenant_id IS '租户id';
-- 添加列注释
COMMENT ON COLUMN merchant_warehouse.name IS '仓库名称';
