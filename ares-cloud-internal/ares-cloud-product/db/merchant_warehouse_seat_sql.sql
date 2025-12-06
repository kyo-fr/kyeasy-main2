
-- ----------------------------
-- Table structure for merchant_warehouse_seat
-- 作者 hugo
-- date 2025-03-22
-- 商户仓库位子主数据
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_warehouse_seat';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_warehouse_seat (
   version NUMBER  ,
   deleted NUMBER  ,
   warehouse_id VARCHAR2(32)  NOT NULL,
   seat_name VARCHAR2(128)  NOT NULL,
   id VARCHAR2(30)  ,
   creator VARCHAR2(30)  ,
   create_time NUMBER(13)  ,
   updater VARCHAR2(30)  ,
   update_time NUMBER(13)  ,
   tenant_id VARCHAR2(32)  NOT NULL,
CONSTRAINT PK_merchant_warehouse_seat PRIMARY KEY (id)
    );
    -- 添加表注释
    COMMENT ON TABLE merchant_warehouse_seat IS '商户仓库位子主数据';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.version IS '版本号';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.deleted IS '删除标记';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.warehouse_id IS '仓库id';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.seat_name IS '位子名称';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.id IS '主键';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.creator IS '创建者';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.create_time IS '创建时间';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.updater IS '更新者';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.update_time IS '更新时间';
    -- 添加列注释
    COMMENT ON COLUMN merchant_warehouse_seat.tenant_id IS '租户';
