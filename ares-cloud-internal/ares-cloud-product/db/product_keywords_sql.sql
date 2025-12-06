
-- ----------------------------
-- Table structure for product_keywords
-- 作者 hugo
-- date 2025-03-18
-- 商品关键字
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_keywords';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_keywords (
   version NUMBER  ,
   deleted NUMBER  ,
   product_id VARCHAR2(32)  NOT NULL,
   key_words_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_product_keywords PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_keywords IS '商品关键字';
-- 添加列注释
COMMENT ON COLUMN product_keywords.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_keywords.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_keywords.product_id IS '商品id`';
-- 添加列注释
COMMENT ON COLUMN product_keywords.key_words_id IS '关键字id';
-- 添加列注释
COMMENT ON COLUMN product_keywords.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_keywords.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_keywords.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_keywords.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_keywords.update_time IS '更新时间';
