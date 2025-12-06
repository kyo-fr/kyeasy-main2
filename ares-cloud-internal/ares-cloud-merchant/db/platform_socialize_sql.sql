
-- ----------------------------
-- Table structure for platform_socialize
-- 作者 hugo
-- date 2024-10-15
-- 平台海外社交
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_socialize';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_socialize (
   type VARCHAR2(32)  NOT NULL,
   link VARCHAR2(255)  NOT NULL,
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
CONSTRAINT PK_platform_socialize PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_socialize IS '平台海外社交';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.type IS '社交类型';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.link IS '社交链接';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_socialize.update_time IS '更新时间';
