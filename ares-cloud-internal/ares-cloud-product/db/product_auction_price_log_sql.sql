
-- ----------------------------
-- Table structure for product_auction_price_log
-- 作者 hugo
-- date 2025-09-23
-- 拍卖商品竞价记录
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_auction_price_log';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_auction_price_log (
   user_id NVARCHAR2(32)  NOT NULL,
   version NUMBER  ,
   price NUMBER(13,2)  NOT NULL,
   product_id VARCHAR2(32)  NOT NULL,
   deleted NUMBER  ,
   updater VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   id VARCHAR2(30)  ,
   tenant_id VARCHAR2(32)  ,
   create_time NUMBER(13)  ,
CONSTRAINT PK_product_auction_price_log PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_auction_price_log IS '拍卖商品竞价记录';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.user_id IS '用户id';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.price IS '竞拍价格';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.product_id IS '商品id';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.tenant_id IS '租户id';
-- 添加列注释
COMMENT ON COLUMN product_auction_price_log.create_time IS '创建时间';

create index PRODUCT_AUCTION_PRICE_LOG_USER_ID_INDEX
    on PRODUCT_AUCTION_PRICE_LOG (USER_ID)
    /

create index PRODUCT_AUCTION_PRICE_LOG_PRODUCT_ID_INDEX
    on PRODUCT_AUCTION_PRICE_LOG (PRODUCT_ID)
    /

create index PRODUCT_AUCTION_PRICE_LOG_TENANT_ID_INDEX
    on PRODUCT_AUCTION_PRICE_LOG (TENANT_ID)
    /