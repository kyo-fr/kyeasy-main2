
-- ----------------------------
-- Table structure for users
-- 作者 hugo
-- date 2024-10-07
-- 用户
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE users';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE users (
   version NUMBER  ,
   deleted NUMBER  ,
   phone VARCHAR2(20)  ,
   country_code VARCHAR2(10)  ,
   account VARCHAR2(20)  ,
   nickname VARCHAR2(20)  ,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
CONSTRAINT PK_users PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE users IS '用户';
-- 添加列注释
COMMENT ON COLUMN users.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN users.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN users.phone IS '手机号';
-- 添加列注释
COMMENT ON COLUMN users.country_code IS '国家代码';
-- 添加列注释
COMMENT ON COLUMN users.account IS '账号';
-- 添加列注释
COMMENT ON COLUMN users.nickname IS '昵称';
-- 添加列注释
COMMENT ON COLUMN users.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN users.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN users.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN users.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN users.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN users.tenant_id IS '租户';
