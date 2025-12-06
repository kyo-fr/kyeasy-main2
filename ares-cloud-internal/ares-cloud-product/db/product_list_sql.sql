
-- ----------------------------
-- Table structure for product_list
-- 作者 hugo
-- date 2025-04-03
-- 商品清单
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_list';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_list (
   version NUMBER  ,
   deleted NUMBER  ,
   sub_product_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   product_id VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_product_list PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_list IS '商品清单';
-- 添加列注释
COMMENT ON COLUMN product_list.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_list.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_list.sub_product_id IS '商品清单id';
-- 添加列注释
COMMENT ON COLUMN product_list.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_list.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_list.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_list.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_list.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_list.product_id IS '商品id';
