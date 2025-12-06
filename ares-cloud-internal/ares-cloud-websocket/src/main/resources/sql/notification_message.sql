-- 创建消息通知表
CREATE TABLE ws_notification_message (
    id VARCHAR2(32) PRIMARY KEY,
    type VARCHAR2(50) NOT NULL,
    notification_type VARCHAR2(50),
    content CLOB NOT NULL,
    receiver_id VARCHAR2(100) NOT NULL,
    sender_id VARCHAR2(100) NOT NULL,
    is_read NUMBER(1) DEFAULT 0 NOT NULL,
    create_time TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    timestamp NUMBER,
    extra_data CLOB
);

-- 创建接收者ID索引，用于快速查询用户的消息
CREATE INDEX idx_notification_receiver ON ws_notification_message(receiver_id);

-- 创建消息类型索引，用于按类型查询消息
CREATE INDEX idx_notification_type ON ws_notification_message(type);

-- 创建通知类型索引，用于按通知类型查询消息
CREATE INDEX idx_notification_notification_type ON ws_notification_message(notification_type);

-- 创建未读消息索引，用于快速查询未读消息
CREATE INDEX idx_notification_unread ON ws_notification_message(receiver_id, is_read);

-- 创建时间戳索引，用于按时间戳排序查询
CREATE INDEX idx_notification_timestamp ON ws_notification_message(timestamp);

-- 添加注释
COMMENT ON TABLE ws_notification_message IS '消息通知表';
COMMENT ON COLUMN ws_notification_message.id IS '主键ID';
COMMENT ON COLUMN ws_notification_message.type IS '消息类型';
COMMENT ON COLUMN ws_notification_message.notification_type IS '通知类型';
COMMENT ON COLUMN ws_notification_message.content IS '消息内容';
COMMENT ON COLUMN ws_notification_message.receiver_id IS '接收者用户ID';
COMMENT ON COLUMN ws_notification_message.sender_id IS '发送者用户ID';
COMMENT ON COLUMN ws_notification_message.is_read IS '是否已读 0-未读 1-已读';
COMMENT ON COLUMN ws_notification_message.create_time IS '创建时间';
COMMENT ON COLUMN ws_notification_message.update_time IS '更新时间';
COMMENT ON COLUMN ws_notification_message.timestamp IS '附加数据时间戳（毫秒）';
COMMENT ON COLUMN ws_notification_message.extra_data IS '附加数据JSON字符串';
