
-- ----------------------------
-- Table structure for merchant_banner
-- 作者 hugo
-- date 2025-03-18
-- 轮播图
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_banner';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_banner (
   version NUMBER  ,
   deleted NUMBER  ,
   pic_url VARCHAR2(10000)  NOT NULL,
   pic_desc char(128)  ,
   jump_url char(255)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_merchant_banner PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE merchant_banner IS '轮播图';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.pic_url IS '图片链接';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.pic_desc IS '图片描述';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.jump_url IS '跳转链接';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN merchant_banner.tenant_id IS '租户';
