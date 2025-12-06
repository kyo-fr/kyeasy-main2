
-- ----------------------------
-- Table structure for product_marking
-- 作者 hugo
-- date 2025-03-18
-- 商品标注
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_marking';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_marking (
   product_id VARCHAR2(32)  NOT NULL,
   marking_id VARCHAR2(32)  NOT NULL,
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(32)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
CONSTRAINT PK_product_marking PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_marking IS '商品标注';
-- 添加列注释
COMMENT ON COLUMN product_marking.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_marking.marking_id IS '标注id';
-- 添加列注释
COMMENT ON COLUMN product_marking.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_marking.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_marking.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_marking.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_marking.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_marking.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_marking.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_marking.tenant_id IS '租户';
