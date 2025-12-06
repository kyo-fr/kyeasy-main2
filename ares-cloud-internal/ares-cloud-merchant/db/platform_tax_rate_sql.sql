
-- ----------------------------
-- Table structure for platform_tax_rate
-- 作者 hugo
-- date 2024-10-15
-- 税率
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_tax_rate';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_tax_rate (
   type VARCHAR2(32)  NOT NULL,
   version NUMBER  ,
   deleted NUMBER  ,
   tax_rate NUMBER(8,2)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
CONSTRAINT PK_platform_tax_rate PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_tax_rate IS '税率';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.type IS '类型';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.tax_rate IS '税率';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_tax_rate.update_time IS '更新时间';

