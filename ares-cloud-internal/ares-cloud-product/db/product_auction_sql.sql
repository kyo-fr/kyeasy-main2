
-- ----------------------------
-- Table structure for product_auction
-- 作者 hugo
-- date 2024-11-08
-- 拍卖商品
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_auction';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_auction (
   version NUMBER  ,
   deleted NUMBER  ,
   fares NUMBER(6,2)  NOT NULL,
   fixed_price NUMBER(6,2)  ,
   start_time VARCHAR2(20)  ,
   end_time VARCHAR2(20)  ,
   product_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
CONSTRAINT PK_product_auction PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_auction IS '拍卖商品';
-- 添加列注释
COMMENT ON COLUMN product_auction.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_auction.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_auction.fares IS '每次加价';
-- 添加列注释
COMMENT ON COLUMN product_auction.fixed_price IS '一口价';
-- 添加列注释
COMMENT ON COLUMN product_auction.start_time IS '开始时间';
-- 添加列注释
COMMENT ON COLUMN product_auction.end_time IS '结束时间';
-- 添加列注释
COMMENT ON COLUMN product_auction.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_auction.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_auction.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_auction.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_auction.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_auction.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_auction.tenant_id IS '租户';
