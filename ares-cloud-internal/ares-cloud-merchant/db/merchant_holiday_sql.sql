
-- ----------------------------
-- Table structure for merchant_holiday
-- 作者 hugo
-- date 2025-01-03
-- 商户休假
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_holiday';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_holiday (
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   contents VARCHAR2(3000)  NOT NULL,
CONSTRAINT PK_merchant_holiday PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_holiday IS '商户休假';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_holiday.contents IS '休假描述';
