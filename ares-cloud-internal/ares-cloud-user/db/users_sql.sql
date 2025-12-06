
-- ----------------------------
-- Table structure for users
-- 作者 hugo
-- date 2024-11-11
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
   email VARCHAR2(32)  ,
   version NUMBER  ,
   deleted NUMBER  ,
   password VARCHAR2(255)  NOT NULL,
   status NUMBER(1)(1) DEFAULT 1 NOT NULL,
   enterprise _number VARCHAR2(32)  ,
   contract_id VARCHAR2(32)  ,
   merchant_id VARCHAR2(32)  ,
   tenant_id VARCHAR2(30)  ,
   identity NUMBER(2) DEFAULT 1 NOT NULL,
   country_code VARCHAR2(10)  NOT NULL,
   phone VARCHAR2(20)  NOT NULL,
   account VARCHAR2(20)  ,
   update_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   creator VARCHAR2(30)  ,
   id VARCHAR2(30)  ,
   nickname VARCHAR2(20)  ,
CONSTRAINT PK_users PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE users IS '用户';
-- 添加列注释
COMMENT ON COLUMN users.email IS '邮箱';
-- 添加列注释
COMMENT ON COLUMN users.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN users.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN users.password IS '密码';
-- 添加列注释
COMMENT ON COLUMN users.status IS '状态(1:正常,2:停用)';
-- 添加列注释
COMMENT ON COLUMN users.enterprise _number IS '企业编号';
-- 添加列注释
COMMENT ON COLUMN users.contract_id IS '合同id';
-- 添加列注释
COMMENT ON COLUMN users.merchant_id IS '商户id';
-- 添加列注释
COMMENT ON COLUMN users.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN users.identity IS '用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)';
-- 添加列注释
COMMENT ON COLUMN users.country_code IS '国家代码';
-- 添加列注释
COMMENT ON COLUMN users.phone IS '手机号';
-- 添加列注释
COMMENT ON COLUMN users.account IS '账号';
-- 添加列注释
COMMENT ON COLUMN users.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN users.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN users.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN users.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN users.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN users.nickname IS '昵称';
