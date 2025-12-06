
-- ----------------------------
-- Table structure for sys_business_id
-- 作者 hugo
-- date 2024-10-13
-- 系统业务id
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE sys_business_id';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE sys_business_id (
   module_name VARCHAR2(20)  NOT NULL,
   prefix VARCHAR2(10)  ,
   max_sequence NUMBER  ,
   cycle_type NUMBER(1)  ,
   version NUMBER  ,
   deleted NUMBER  ,
   current_date VARCHAR2(13)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(30)  ,
   date_temp VARCHAR2(15)  ,
CONSTRAINT PK_sys_business_id PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE sys_business_id IS '系统业务id';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.module_name IS '业务模块名';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.prefix IS '业务前缀';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.max_sequence IS '当前最大流水号';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.cycle_type IS '流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.current_date IS '当前时间';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.tenant_id IS '租户';
-- 添加列注释
COMMENT ON COLUMN sys_business_id.date_temp IS '日期模版';
