-- ----------------------------
-- 添加企业营业号字段到用户地址表
-- 作者 hugo
-- date 2024-11-11
-- ----------------------------

-- 检查字段是否存在，如果不存在则添加
DECLARE
  v_column_exists NUMBER;
BEGIN
  SELECT COUNT(*) INTO v_column_exists
  FROM user_tab_columns
  WHERE table_name = 'USER_ADDRESS'
  AND column_name = 'BUSINESS_LICENSE_NUMBER';
  
  IF v_column_exists = 0 THEN
    EXECUTE IMMEDIATE 'ALTER TABLE user_address ADD business_license_number VARCHAR2(100)';
    EXECUTE IMMEDIATE 'COMMENT ON COLUMN user_address.business_license_number IS ''企业营业号''';
    DBMS_OUTPUT.PUT_LINE('字段 business_license_number 已添加到 user_address 表');
  ELSE
    DBMS_OUTPUT.PUT_LINE('字段 business_license_number 已存在于 user_address 表中');
  END IF;
END;
/