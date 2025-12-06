-- 用户地址表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE user_address';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE user_address (
    id VARCHAR2(64) PRIMARY KEY,
    user_id VARCHAR2(64) NOT NULL,
    type NUMBER(2) NOT NULL,
    name VARCHAR2(255),
    phone VARCHAR2(20),
    company_name VARCHAR2(255),
    business_license_number VARCHAR2(100),
    city VARCHAR2(255) NOT NULL,
    detail VARCHAR2(1000) NOT NULL,
    latitude NUMBER(17,14) NOT NULL,
    longitude NUMBER(17,14) NOT NULL,
    is_default NUMBER(1) DEFAULT 0,
    is_enabled NUMBER(1) DEFAULT 1,
    is_invoice_address NUMBER(1) DEFAULT 0,
    create_time NUMBER(13) NOT NULL,
    update_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(1) DEFAULT 0
);

COMMENT ON TABLE user_address IS '用户地址表';
COMMENT ON COLUMN user_address.id IS '主键ID';
COMMENT ON COLUMN user_address.user_id IS '用户ID';
COMMENT ON COLUMN user_address.type IS '地址类型（1：个人地址，2：公司地址）';
COMMENT ON COLUMN user_address.name IS '姓名';
COMMENT ON COLUMN user_address.phone IS '联系电话';
COMMENT ON COLUMN user_address.company_name IS '公司名称';
COMMENT ON COLUMN user_address.business_license_number IS '企业营业号';
COMMENT ON COLUMN user_address.city IS '城市';
COMMENT ON COLUMN user_address.detail IS '详细地址';
COMMENT ON COLUMN user_address.latitude IS '纬度';
COMMENT ON COLUMN user_address.longitude IS '经度';
COMMENT ON COLUMN user_address.is_default IS '是否默认地址（0：否，1：是）';
COMMENT ON COLUMN user_address.is_enabled IS '是否启用（0：否，1：是）';
COMMENT ON COLUMN user_address.is_invoice_address IS '是否发票地址（0：否，1：是）';
COMMENT ON COLUMN user_address.create_time IS '创建时间';
COMMENT ON COLUMN user_address.update_time IS '更新时间';
COMMENT ON COLUMN user_address.version IS '版本号（乐观锁）';
COMMENT ON COLUMN user_address.deleted IS '是否删除（0：否，1：是）';