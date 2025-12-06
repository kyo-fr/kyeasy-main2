
-- ----------------------------
-- Table structure for platform_marking
-- 作者 hugo
-- date 2024-11-04
-- 商品标注
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_marking';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_marking (
   version NUMBER  ,
   deleted NUMBER  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   marking_name VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_platform_marking PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_marking IS '商品标注';
-- 添加列注释
COMMENT ON COLUMN platform_marking.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_marking.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_marking.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_marking.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_marking.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_marking.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_marking.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_marking.marking_name IS '标注名称';
