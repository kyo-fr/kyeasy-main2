
-- ----------------------------
-- Table structure for merchant_file_upload
-- 作者 hugo
-- date 2024-10-09
-- 商户文件上传
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_file_upload';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_file_upload (
   version NUMBER  ,
   deleted NUMBER  ,
   business_license VARCHAR2(255)  ,
   id VARCHAR2(32)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  ,
   bank_iban VARCHAR2(255)  ,
   bank_bic VARCHAR2(255)  ,
   bank_rib VARCHAR2(255)  ,
   logo VARCHAR2(256)  ,
   comprehensive_classification VARCHAR2(255)  ,
CONSTRAINT PK_merchant_file_upload PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_file_upload IS '商户文件上传';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.business_license IS '营业执照';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.bank_iban IS '银行iban';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.bank_bic IS '银行bic';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.bank_rib IS '银行rib';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.logo IS '商户logo';
-- 添加列注释
COMMENT ON COLUMN merchant_file_upload.comprehensive_classification IS '综合分类';
