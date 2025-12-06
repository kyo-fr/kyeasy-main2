
-- ----------------------------
-- Table structure for pay_merchant
-- 作者 hugo
-- date 2024-10-23
-- 支付商户
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE pay_merchant';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE pay_merchant (
   version NUMBER  ,
   deleted NUMBER  ,
   name VARCHAR2(255)  ,
   email VARCHAR2(20)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
CONSTRAINT PK_pay_merchant PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE pay_merchant IS '支付商户';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.name IS '商户名称';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.email IS '邮箱';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN pay_merchant.tenant_id IS '租户';
