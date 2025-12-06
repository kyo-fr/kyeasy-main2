
-- ----------------------------
-- Table structure for platform_work_order_content
-- 作者 hugo
-- date 2024-10-17
-- 工单内容
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_work_order_content';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE platform_work_order_content (
   version NUMBER  ,
   deleted NUMBER  ,
   send_id VARCHAR2(32)  NOT NULL,
   receiver_id VARCHAR2(32)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   work_order_id VARCHAR2(32)  NOT NULL,
   content VARCHAR2(2048)  NOT NULL,
CONSTRAINT PK_platform_work_order_content PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE platform_work_order_content IS '工单内容';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.send_id IS '发送者id';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.receiver_id IS '接收者id';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.work_order_id IS '工单id';
-- 添加列注释
COMMENT ON COLUMN platform_work_order_content.content IS '会话内容';
