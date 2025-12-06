
-- ----------------------------
-- Table structure for merchant_advertised
-- 作者 hugo
-- date 2025-01-03
-- 商户广告
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_advertised';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_advertised (
   version NUMBER  ,
   deleted NUMBER  ,
   contents VARCHAR2(3000)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
CONSTRAINT PK_merchant_advertised PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_advertised IS '商户广告';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.contents IS '广告描述';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_advertised.tenant_id IS '租户';
