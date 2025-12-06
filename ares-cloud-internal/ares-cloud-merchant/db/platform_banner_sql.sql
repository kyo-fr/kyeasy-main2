
-- ----------------------------
-- Table structure for platform_banner
-- 作者 hugo
-- date 2025-03-13
-- 轮播图
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_banner';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_banner (
   version NUMBER  ,
   deleted NUMBER  ,
   pic_url VARCHAR2(1024)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   pic_desc char(128)  ,
   jump_url char(255)  ,
CONSTRAINT PK_platform_banner PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_banner IS '轮播图';
-- 添加列注释
COMMENT ON COLUMN platform_banner.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_banner.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_banner.pic_url IS '图片链接';
-- 添加列注释
COMMENT ON COLUMN platform_banner.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_banner.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_banner.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_banner.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_banner.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_banner.pic_desc IS '图片描述';
-- 添加列注释
COMMENT ON COLUMN platform_banner.jump_url IS '跳转链接';
