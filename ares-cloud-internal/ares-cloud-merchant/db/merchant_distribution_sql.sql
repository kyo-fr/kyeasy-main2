
-- ----------------------------
-- Table structure for merchant_distribution
-- 作者 hugo
-- date 2024-11-05
-- 商户配送
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_distribution';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_distribution (
   version NUMBER  ,
   deleted NUMBER  ,
   per_kilometer NUMBER(2,2)  ,
   excess_fees NUMBER(2,2)  ,
   is_third_parties integer  ,
   address_type integer  NOT NULL,
   address VARCHAR2(128)  NOT NULL,
   minimum_delivery_amount NUMBER(4)  NOT NULL,
   beyond_kilometer_not_delivered integer  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   type integer ,
   stage  NUMBER(24,2)  ,
   global_third_parties integer  ,
   kilometers_exceeded integer  ,
CONSTRAINT PK_merchant_distribution PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_distribution IS '商户配送';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.per_kilometer IS '每公里收费(收费配送)';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.excess_fees IS '超过公里费用(第三方配送)';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.is_third_parties IS '是否开启第三方配送 1-是；2-否';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.address_type IS '地址类型: 1-默认商户地址；2-仓库地址；3-港口地址';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.address IS '地址';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.minimum_delivery_amount IS '最低配送金额';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.beyond_kilometer_not_delivered IS '超出公里不配送';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.type IS '配送类型 1-免费配送；2-收费配送';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.stage IS '0-2公里收费(收费配送)';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.global_third_parties IS '第三方配送类型 1-全局第三方；2-超出公里';
-- 添加列注释
COMMENT ON COLUMN merchant_distribution.kilometers_exceeded IS '超出公里数(第三方配送)';
