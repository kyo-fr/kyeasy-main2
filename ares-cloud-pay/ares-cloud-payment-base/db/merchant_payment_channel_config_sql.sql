
-- ----------------------------
-- Table structure for merchant_payment_channel_config
-- 作者 hugo
-- date 2025-05-13
-- 商户支付渠道配置
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_payment_channel_config';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_payment_channel_config (
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(50)  ,
   merchant_id VARCHAR2(32)  ,
   payment_merchant VARCHAR2(100)  NOT NULL,
   config_data CLOB,
CONSTRAINT PK_merchant_payment_channel_config PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_payment_channel_config IS '商户支付渠道配置';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.merchant_id IS '商户ID';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.payment_merchant IS '支付商户(braintree、alipay、wechat)';
-- 添加列注释
COMMENT ON COLUMN merchant_payment_channel_config.config_data IS '支付配置';
