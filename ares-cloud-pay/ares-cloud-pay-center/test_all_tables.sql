-- 测试所有表的创建语法
-- 这是一个完整的测试文件，用于验证所有表的SQL语法是否正确

-- 测试普通表创建语法
CREATE TABLE test_merchants (
    id VARCHAR2(50) PRIMARY KEY,
    merchant_no VARCHAR2(50) NOT NULL,
    merchant_name VARCHAR2(100) NOT NULL,
    merchant_type VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) NOT NULL,
    pay_secret VARCHAR2(100) NOT NULL,
    pay_password VARCHAR2(100),
    supported_regions CLOB,
    contact_person VARCHAR2(50),
    contact_phone VARCHAR2(20),
    contact_email VARCHAR2(100),
    address VARCHAR2(500),
    business_license VARCHAR2(100),
    legal_representative VARCHAR2(50),
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_test_merchant_no UNIQUE (merchant_no)
);

-- 测试钱包表创建语法
CREATE TABLE test_wallets (
    id VARCHAR2(50) PRIMARY KEY,
    owner_id VARCHAR2(50) NOT NULL,
    owner_type VARCHAR2(20) NOT NULL,
    payment_region VARCHAR2(10) NOT NULL,
    balance NUMBER(20) DEFAULT 0 NOT NULL,
    frozen_amount NUMBER(20) DEFAULT 0 NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    create_time NUMBER(20) NOT NULL,
    update_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_test_wallet_owner_region UNIQUE (owner_id, payment_region)
);

-- 测试账户表创建语法
CREATE TABLE test_accounts (
    id VARCHAR2(50) PRIMARY KEY,
    user_id VARCHAR2(50) NOT NULL,
    password VARCHAR2(100),
    country_code VARCHAR2(10),
    phone VARCHAR2(20),
    account VARCHAR2(50),
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_test_account_user_id UNIQUE (user_id),
    CONSTRAINT uk_test_account_phone UNIQUE (phone),
    CONSTRAINT uk_test_account_account UNIQUE (account)
);

-- 测试支付订单表创建语法
CREATE TABLE test_payment_order (
    id VARCHAR2(50) PRIMARY KEY,
    order_no VARCHAR2(50) NOT NULL,
    from_user_id VARCHAR2(50) NOT NULL,
    to_user_id VARCHAR2(50) NOT NULL,
    amount NUMBER(20) NOT NULL,
    currency VARCHAR2(10) NOT NULL,
    scale NUMBER(2) NOT NULL,
    subject VARCHAR2(255),
    body VARCHAR2(4000),
    status NUMBER(1) DEFAULT 0 NOT NULL,
    channel NUMBER(1) NOT NULL,
    expire_time NUMBER(20),
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_test_payment_order_no UNIQUE (order_no)
);

-- 测试区块链表创建语法
CREATE BLOCKCHAIN TABLE test_blockchain_transactions (
    id VARCHAR2(50) PRIMARY KEY,
    from_account_id VARCHAR2(50),
    to_account_id VARCHAR2(50),
    order_id VARCHAR2(50),
    amount NUMBER(20) NOT NULL,
    payment_region VARCHAR2(10) NOT NULL,
    fee_amount NUMBER(20) NOT NULL,
    type VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) NOT NULL,
    description VARCHAR2(200),
    create_time NUMBER(20) NOT NULL,
    update_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    version NUMBER(10) NOT NULL,
    deleted NUMBER(1) NOT NULL
) NO DROP UNTIL 3650 DAYS IDLE
NO DELETE UNTIL 16 DAYS AFTER INSERT
HASHING USING "SHA2_512" VERSION "v1";

-- 测试账户流水区块链表创建语法
CREATE BLOCKCHAIN TABLE test_account_flow (
    id VARCHAR2(50) PRIMARY KEY,
    account_id VARCHAR2(50) NOT NULL,
    transaction_id VARCHAR2(50) NOT NULL,
    flow_type VARCHAR2(20) NOT NULL,
    amount NUMBER(20) NOT NULL,
    currency VARCHAR2(10) NOT NULL,
    scale NUMBER(2) NOT NULL,
    balance_before NUMBER(20) NOT NULL,
    balance_after NUMBER(20) NOT NULL,
    create_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) NOT NULL,
    deleted NUMBER(1) NOT NULL
) NO DROP UNTIL 3650 DAYS IDLE
NO DELETE UNTIL 16 DAYS AFTER INSERT
HASHING USING "SHA2_512" VERSION "v1";

-- 清理测试表
DROP TABLE test_merchants;
DROP TABLE test_wallets;
DROP TABLE test_accounts;
DROP TABLE test_payment_order;
DROP TABLE test_blockchain_transactions;
DROP TABLE test_account_flow;

-- 输出成功消息
SELECT 'All table creation syntax tests passed!' AS result FROM dual; 