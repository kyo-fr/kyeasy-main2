
-- ----------------------------
-- Table structure for product_sub_specification
-- 作者 hugo
-- date 2025-03-24
-- 商品子规格
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_sub_specification';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_sub_specification (
   version NUMBER  ,
   deleted NUMBER  ,
   product_id VARCHAR2(32)  ,
   sub_specification_id VARCHAR2(32)  NOT NULL,
   specification_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
CONSTRAINT PK_product_sub_specification PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_sub_specification IS '商品子规格';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.sub_specification_id IS '子规格id';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.specification_id IS '主规格id';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_sub_specification.tenant_id IS '租户';
