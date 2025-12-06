
-- ----------------------------
-- Table structure for sys_payment_channel
-- 作者 hugo
-- date 2025-05-13
-- 支付渠道
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE sys_payment_channel';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE sys_payment_channel (
   logo VARCHAR2(255)  ,
   status NUMBER(1) DEFAULT 0 ,
   channel_key VARCHAR2(100)  NOT NULL,
   version NUMBER  ,
   deleted NUMBER  ,
   channel_type NUMBER(1) DEFAULT 2 NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   payment_merchant VARCHAR2(20)  ,
CONSTRAINT PK_sys_payment_channel PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE sys_payment_channel IS '支付渠道';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.logo IS 'Logo';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.status IS '状态 0:启用；1:关闭';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.channel_key IS '渠道唯一key';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.channel_type IS '支付渠道;1:线上；2:线下';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN sys_payment_channel.payment_merchant IS '支付商家针对线上支付(braintree、alipay、wechat)';
