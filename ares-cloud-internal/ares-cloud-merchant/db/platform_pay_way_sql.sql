
-- ----------------------------
-- Table structure for platform_pay_way
-- 作者 hugo
-- date 2024-10-15
-- 平台支付类型
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_pay_way';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_pay_way (
   version NUMBER  ,
   deleted NUMBER  ,
   channel_type NUMBER(2)  NOT NULL,
   url VARCHAR2(255)  NOT NULL,
   status NUMBER(2)  NOT NULL,
   front_key VARCHAR2(255)  ,
   back_key VARCHAR2(255)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   PAY_NAME     VARCHAR2(32)  not null
CONSTRAINT PK_platform_pay_way PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_pay_way IS '平台支付类型';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.channel_type IS '支付渠道1:线上；2:线下';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.url IS '图片';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.status IS '状态 1:启用；2:关闭';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.front_key IS '前端验证key';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.back_key IS '后端验证key';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_pay_way.update_time IS '更新时间';

comment on column PLATFORM_PAY_WAY.PAY_NAME is '支付名称'
