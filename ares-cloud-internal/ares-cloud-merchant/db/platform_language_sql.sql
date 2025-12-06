
-- ----------------------------
-- Table structure for platform_language
-- 作者 hugo
-- date 2024-10-15
-- 平台设置语言
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_language';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_language (
   version NUMBER  ,
   deleted NUMBER  ,
   name VARCHAR2(32)  NOT NULL,
   status NUMBER(2)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_platform_language PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_language IS '平台设置语言';
-- 添加列注释
COMMENT ON COLUMN platform_language.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_language.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_language.name IS '语言名称';
-- 添加列注释
COMMENT ON COLUMN platform_language.status IS '状态 1:启用；2:关闭';
-- 添加列注释
COMMENT ON COLUMN platform_language.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_language.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_language.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_language.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_language.update_time IS '更新时间';
