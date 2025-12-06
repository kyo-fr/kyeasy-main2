
-- ----------------------------
-- Table structure for hardware
-- 作者 hugo
-- date 2024-10-12
-- 硬件
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE hardware';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE hardware (
   version NUMBER  ,
   deleted NUMBER  ,
   hardware_id VARCHAR2(32)  ,
   type_id NUMBER  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   name VARCHAR2(128)  NOT NULL,
   status NUMBER  DEFAULT 1 NOT NULL,
CONSTRAINT PK_hardware PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE hardware IS '硬件';
-- 添加列注释
COMMENT ON COLUMN hardware.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN hardware.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN hardware.hardware_id IS '硬件id';
-- 添加列注释
COMMENT ON COLUMN hardware.type_id IS '类型id';
-- 添加列注释
COMMENT ON COLUMN hardware.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN hardware.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN hardware.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN hardware.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN hardware.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN hardware.name IS '类型名称';
-- 添加列注释
COMMENT ON COLUMN hardware.status IS '状态(1:可用；2不可用)';
