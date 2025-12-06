
-- ----------------------------
-- Table structure for pay_merchant_payment_channel
-- 作者 hugo
-- date 2025-01-06
-- 商户支付渠道配置
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE pay_merchant_payment_channel';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE pay_merchant_payment_channel (
   version NUMBER  ,
   deleted NUMBER  ,
   channel_name VARCHAR2(100)  ,
   encryption_algorithm VARCHAR2(50)  ,
   callback_url VARCHAR2(255)  ,
   app_id VARCHAR2(500)  ,
   mch_id VARCHAR2(500)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   merchant_id VARCHAR2(32)  ,
   private_key VARCHAR2(2000)  ,
   public_key VARCHAR2(2000)  ,
CONSTRAINT PK_pay_merchant_payment_channel PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE pay_merchant_payment_channel IS '商户支付渠道配置';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.channel_name IS '通道名称';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.encryption_algorithm IS '加密方式';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.callback_url IS '支付成功后的回调地址';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.app_id IS 'AppId';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.mch_id IS '支付商户号';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.merchant_id IS '商户ID';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.private_key IS '商户的支付私钥，用于签名';
-- 添加列注释
COMMENT ON COLUMN pay_merchant_payment_channel.public_key IS '商户的支付公钥，用于回调签名验证';
