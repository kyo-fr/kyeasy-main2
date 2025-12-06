
-- ----------------------------
-- Table structure for merchant_freight
-- 作者 hugo
-- date 2024-11-05
-- 货运配送费用
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_freight';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_freight (
   version NUMBER  ,
   deleted NUMBER  ,
   type integer   NOT NULL,
   kilograms NUMBER(2,2)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
   expenses NUMBER(2,2)  NOT NULL,
CONSTRAINT PK_merchant_freight PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_freight IS '货运配送费用';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.type IS '货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.kilograms IS '每公斤费用';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_freight.expenses IS '每体积费用';
