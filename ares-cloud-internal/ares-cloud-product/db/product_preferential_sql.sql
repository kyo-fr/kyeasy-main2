
-- ----------------------------
-- Table structure for product_preferential
-- 作者 hugo
-- date 2024-11-07
-- 优惠商品
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_preferential';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_preferential (
   version NUMBER  ,
   deleted NUMBER  ,
   product_id VARCHAR2(32)  NOT NULL,
   advertising_words VARCHAR2(1000)  ,
   start_time VARCHAR2(12)  NOT NULL,
   end_time VARCHAR2(12)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   preferential_price NUMBER(6,2)  NOT NULL,
CONSTRAINT PK_product_preferential PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_preferential IS '优惠商品';
-- 添加列注释
COMMENT ON COLUMN product_preferential.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_preferential.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_preferential.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_preferential.advertising_words IS '广告词';
-- 添加列注释
COMMENT ON COLUMN product_preferential.start_time IS '优惠开始时间';
-- 添加列注释
COMMENT ON COLUMN product_preferential.end_time IS '优惠结束时间';
-- 添加列注释
COMMENT ON COLUMN product_preferential.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_preferential.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_preferential.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_preferential.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_preferential.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_preferential.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN product_preferential.preferential_price IS '优惠价格';
