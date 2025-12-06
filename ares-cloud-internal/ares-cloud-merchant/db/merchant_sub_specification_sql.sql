
-- ----------------------------
-- Table structure for merchant_sub_specification
-- 作者 hugo
-- date 2025-03-18
-- 子规格主数据
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_sub_specification';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_sub_specification (
   version NUMBER  ,
   deleted NUMBER  ,
   sub_name VARCHAR2(32)  ,
   sub_num integer(12)  ,
   sub_price NUMBER(12,2)  ,
   sub_picture VARCHAR2(128)  ,
   specification_id VARCHAR2(32)  ,
   id VARCHAR2(32)  ,
   creator VARCHAR2(32)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(32)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
CONSTRAINT PK_merchant_sub_specification PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_sub_specification IS '子规格主数据';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.sub_name IS '子规格名称';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.sub_num IS '子规格库存';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.sub_price IS '子规格价格';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.sub_picture IS '子规格图片';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.specification_id IS '主规格id';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_sub_specification.tenant_id IS '租户';
