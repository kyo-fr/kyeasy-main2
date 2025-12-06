
-- ----------------------------
-- Table structure for platform_work_order
-- 作者 hugo
-- date 2024-10-16
-- 工单
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_work_order';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_work_order (
   version NUMBER  ,
   deleted NUMBER  ,
   user_name VARCHAR2(32)  NOT NULL,
   user_type NUMBER(2)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   work_order_id VARCHAR2(32)  NOT NULL,
   status NUMBER(2)  NOT NULL,
   user_id VARCHAR2(32)  ,
   user_email VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_platform_work_order PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_work_order IS '工单';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.user_name IS '用户名称';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.user_type IS '用户类型（1:商户；2:用户）';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.work_order_id IS '工单id';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.status IS '工单状态(1:未处理；2:处理中；3:已处理)';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.user_id IS '用户id（用户、商户）';
-- 添加列注释
COMMENT ON COLUMN platform_work_order.user_email IS '用户邮件';
