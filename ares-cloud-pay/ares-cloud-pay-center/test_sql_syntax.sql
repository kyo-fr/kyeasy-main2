-- 测试Oracle区块链表语法
-- 这是一个简化的测试文件，用于验证SQL语法是否正确

-- 测试区块链表创建语法
CREATE BLOCKCHAIN TABLE test_blockchain (
    id VARCHAR2(50) PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    create_time NUMBER(20) NOT NULL,
    version NUMBER(10) NOT NULL,
    deleted NUMBER(1) NOT NULL
) NO DROP UNTIL 3650 DAYS IDLE
NO DELETE UNTIL 16 DAYS AFTER INSERT
HASHING USING "SHA2_512" VERSION "v1";

-- 清理测试表
DROP TABLE test_blockchain; 