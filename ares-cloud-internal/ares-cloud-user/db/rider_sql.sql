
-- ----------------------------
-- Table structure for rider
-- 作者 hugo
-- date 2025-08-26
-- 骑手
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE rider';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE rider (
   version NUMBER  ,
   deleted NUMBER  ,
   user_id VARCHAR2(50)  ,
   email VARCHAR2(50)  ,
   country_code VARCHAR2(10)  ,
   phone VARCHAR2(50)  ,
   name VARCHAR2(50)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(50)  ,
   status NUMBER  ,
   CONSTRAINT PK_rider PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE rider IS '骑手';
-- 添加列注释
COMMENT ON COLUMN rider.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN rider.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN rider.user_id IS '关联的用户id';
-- 添加列注释
COMMENT ON COLUMN rider.email IS '邮箱';
-- 添加列注释
COMMENT ON COLUMN rider.country_code IS '国家代码';
-- 添加列注释
COMMENT ON COLUMN rider.phone IS '手机号';
-- 添加列注释
COMMENT ON COLUMN rider.name IS '名称';
-- 添加列注释
COMMENT ON COLUMN rider.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN rider.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN rider.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN rider.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN rider.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN rider.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN rider.status IS '状态(1:正常,2:停用)';
