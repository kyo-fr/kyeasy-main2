
-- ----------------------------
-- Table structure for product_base_info
-- 作者 hugo
-- date 2024-11-06
-- 商品基础信息
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_base_info';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_base_info (
   version NUMBER  ,
   deleted NUMBER  ,
   name VARCHAR2(255)  NOT NULL,
   price NUMBER(2)  NOT NULL,
   inventory NUMBER(8)  NOT NULL,
   level_one_id VARCHAR2(32)  NOT NULL,
   level_two_id VARCHAR2(32)  NOT NULL,
   level_three_id VARCHAR2(32)  NOT NULL,
   tax_id VARCHAR2(32)  ,
   briefly VARCHAR2(2000)  ,
   picture_url VARCHAR2(1000)  NOT NULL,
   video_url VARCHAR2(2000)  ,
   weight numeric(6,2)  NOT NULL,
   length numeric(6,2)  NOT NULL,
   is_distribution NUMBER(2)  ,
   is_serve NUMBER(2)  ,
   delivery_fee numeric(8,2)  ,
   pre_serve_fee numeric(8,2)  ,
   serve_fee numeric(6,2)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30) NOT NULL ,
   width NUMBER(6,2)  NOT NULL,
   height numeric(6,2)  NOT NULL,
   per_delivery_fee numeric(10,2)  ,
   type NUMBER(2)  ,
   is_enable NUMBER(2)  ,
CONSTRAINT PK_product_base_info PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_base_info IS '商品基础信息';
-- 添加列注释
COMMENT ON COLUMN product_base_info.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_base_info.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_base_info.name IS '商品名称';
-- 添加列注释
COMMENT ON COLUMN product_base_info.price IS '商品价格';
-- 添加列注释
COMMENT ON COLUMN product_base_info.inventory IS '库存';
-- 添加列注释
COMMENT ON COLUMN product_base_info.level_one_id IS '一级分类id';
-- 添加列注释
COMMENT ON COLUMN product_base_info.level_two_id IS '二级分类id';
-- 添加列注释
COMMENT ON COLUMN product_base_info.level_three_id IS '三级分类id';
-- 添加列注释
COMMENT ON COLUMN product_base_info.tax_id IS '商品税率id';
-- 添加列注释
COMMENT ON COLUMN product_base_info.briefly IS '商品简介';
-- 添加列注释
COMMENT ON COLUMN product_base_info.picture_url IS '商品图片';
-- 添加列注释
COMMENT ON COLUMN product_base_info.video_url IS '视频';
-- 添加列注释
COMMENT ON COLUMN product_base_info.weight IS '重量';
-- 添加列注释
COMMENT ON COLUMN product_base_info.length IS '长度';
-- 添加列注释
COMMENT ON COLUMN product_base_info.is_distribution IS '是否勾选配送费：1-是；2-否';
-- 添加列注释
COMMENT ON COLUMN product_base_info.is_serve IS '是否勾选服务费：1-是；2-否';
-- 添加列注释
COMMENT ON COLUMN product_base_info.delivery_fee IS '配送费金额';
-- 添加列注释
COMMENT ON COLUMN product_base_info.pre_serve_fee IS '服务费百分比';
-- 添加列注释
COMMENT ON COLUMN product_base_info.serve_fee IS '服务费金额';
-- 添加列注释
COMMENT ON COLUMN product_base_info.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_base_info.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_base_info.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_base_info.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_base_info.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_base_info.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN product_base_info.width IS '宽度';
-- 添加列注释
COMMENT ON COLUMN product_base_info.height IS '高度';
-- 添加列注释
COMMENT ON COLUMN product_base_info.per_delivery_fee IS '配送费百分比';
-- 添加列注释
COMMENT ON COLUMN product_base_info.is_enbale IS '是否上下架：1-上架;2-下架 默认值1';
-- 添加列注释
COMMENT ON COLUMN product_base_info.type IS '商品类型：1-普通商品;2-优惠商品;3-拍卖商品;4-批发商品 默认值1';
