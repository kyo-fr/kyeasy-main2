-- ----------------------------
-- Table structure for pay_party
-- 作者 hugo
-- date 2025-01-07
-- 交易方
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE pay_party';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE pay_party (
   party_id VARCHAR2(32)  ,
   name VARCHAR2(100)  ,
   party_type NUMBER(1)  ,
   tax_id VARCHAR2(50)  ,
   address VARCHAR2(255)  ,
   postal_code VARCHAR2(20)  ,
   phone VARCHAR2(20)  ,
   email VARCHAR2(100)  ,
   is_deleted NUMBER(1) DEFAULT 0 ,
   version NUMBER  ,
CONSTRAINT PK_pay_party PRIMARY KEY (party_id)
);
-- 添加表注释
COMMENT ON TABLE pay_party IS '交易方';
-- 添加列注释
COMMENT ON COLUMN pay_party.party_id IS '交易方ID';
-- 添加列注释
COMMENT ON COLUMN pay_party.name IS '交易方名称';
-- 添加列注释
COMMENT ON COLUMN pay_party.party_type IS '交易方类型(1:商户,2:个人)';
-- 添加列注释
COMMENT ON COLUMN pay_party.tax_id IS '税号';
-- 添加列注释
COMMENT ON COLUMN pay_party.address IS '地址';
-- 添加列注释
COMMENT ON COLUMN pay_party.postal_code IS '邮编';
-- 添加列注释
COMMENT ON COLUMN pay_party.phone IS '联系电话';
-- 添加列注释
COMMENT ON COLUMN pay_party.email IS '电子邮箱';
-- 添加列注释
COMMENT ON COLUMN pay_party.is_deleted IS '是否删除(0-未删除，1-已删除)';
-- 添加列注释
COMMENT ON COLUMN pay_party.version IS '版本号';