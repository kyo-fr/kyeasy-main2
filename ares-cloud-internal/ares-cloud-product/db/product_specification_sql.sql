
-- ----------------------------
-- Table structure for product_specification
-- 作者 hugo
-- date 2025-03-18
-- 商品规格
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_specification';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_specification (
   version NUMBER  ,
   deleted NUMBER  ,
   product_id VARCHAR2(32)  NOT NULL,
   specification_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_product_specification PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_specification IS '商品规格';
-- 添加列注释
COMMENT ON COLUMN product_specification.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_specification.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_specification.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_specification.specification_id IS '主规格id';
-- 添加列注释
COMMENT ON COLUMN product_specification.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_specification.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_specification.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_specification.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_specification.update_time IS '更新时间';
