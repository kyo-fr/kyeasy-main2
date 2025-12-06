
-- ----------------------------
-- Table structure for platform_service
-- 作者 hugo
-- date 2024-10-30
-- 服务和帮助
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_service';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_service (
   version NUMBER  ,
   deleted NUMBER  ,
   content VARCHAR2(2000)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   type integer(2)  NOT NULL,
   title VARCHAR2(1000)  NOT NULL,
CONSTRAINT PK_platform_service PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_service IS '服务和帮助';
-- 添加列注释
COMMENT ON COLUMN platform_service.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_service.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_service.content IS '内容';
-- 添加列注释
COMMENT ON COLUMN platform_service.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_service.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_service.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_service.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_service.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_service.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN platform_service.type IS '类型:1-帮助；2-服务条款';
-- 添加列注释
COMMENT ON COLUMN platform_service.title IS '标题';
