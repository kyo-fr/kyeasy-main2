
-- ----------------------------
-- Table structure for merchant_pay_channel
-- 作者 hugo
-- date 2025-05-13
-- 商户支付渠道
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_pay_channel';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_pay_channel (
   version NUMBER  ,
   deleted NUMBER  ,
   channel_type NUMBER DEFAULT 2 NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(50)  ,
   channel_key VARCHAR2(255)  ,
   merchant_id VARCHAR2(38)  ,
   status NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT PK_merchant_pay_channel PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_pay_channel IS '商户支付渠道';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.channel_type IS '支付渠道;1:线上；2:线下';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.channel_key IS '渠道唯一key';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.merchant_id IS '商户id';
-- 添加列注释
COMMENT ON COLUMN merchant_pay_channel.status IS '状态 1:启用；2:关闭';
