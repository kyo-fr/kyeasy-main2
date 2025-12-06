-- 为表添加逻辑删除字段的SQL模板
-- 注意：order_items表在V1.0.0__init_tables.sql中已经包含了deleted字段，不需要再添加

-- 如果需要为其他表添加逻辑删除字段，可以使用以下SQL语句
-- 替换 table_name 为实际的表名

-- 添加逻辑删除字段
ALTER TABLE table_name ADD deleted NUMBER(10) DEFAULT 0;

-- 添加注释
COMMENT ON COLUMN table_name.deleted IS '删除标记（0：正常，1：已删除）';