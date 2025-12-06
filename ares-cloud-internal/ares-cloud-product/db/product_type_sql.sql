
-- ----------------------------
-- Table structure for product_type
-- 作者 hugo
-- date 2024-10-28
-- 商品分类管理
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE product_type';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE product_type (
    version NUMBER(2)  ,
    deleted NUMBER(2)  ,
    name VARCHAR2(32)  NOT NULL,
    picture VARCHAR2(2000)  NOT NULL,
    parent_id VARCHAR2(32)  ,
    levels NUMBER(2)  NOT NULL,
    id VARCHAR2(30)  ,
    creator VARCHAR2(30)  ,
    create_time NUMBER(13)  ,
    updater VARCHAR2(30)  ,
    update_time NUMBER(13)  ,
    tenant_id VARCHAR2(32)  NOT NULL,
    CONSTRAINT PK_product_type PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE product_type IS '商品分类管理';
-- 添加列注释
COMMENT ON COLUMN product_type.version IS '版本号';
-- 添加列注释
COMMENT ON COLUMN product_type.deleted IS '删除标记';
-- 添加列注释
COMMENT ON COLUMN product_type.name IS '分类名称';
-- 添加列注释
COMMENT ON COLUMN product_type.picture IS '图片';
-- 添加列注释
COMMENT ON COLUMN product_type.parent_id IS '父级id';
-- 添加列注释
COMMENT ON COLUMN product_type.levels IS '分类级别';
-- 添加列注释
COMMENT ON COLUMN product_type.id IS '主键';
-- 添加列注释
COMMENT ON COLUMN product_type.creator IS '创建者';
-- 添加列注释
COMMENT ON COLUMN product_type.create_time IS '创建时间';
-- 添加列注释
COMMENT ON COLUMN product_type.updater IS '更新者';
-- 添加列注释
COMMENT ON COLUMN product_type.update_time IS '更新时间';
-- 添加列注释
COMMENT ON COLUMN product_type.tenant_id IS '租户ID';

-- 插入测试数据
INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT001', '餐饮美食', 'https://example.com/food.jpg', '', 1, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT002', '中式料理', 'https://example.com/chinese.jpg', 'PT001', 2, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT003', '西式料理', 'https://example.com/western.jpg', 'PT001', 2, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT004', '川菜', 'https://example.com/sichuan.jpg', 'PT002', 3, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT005', '粤菜', 'https://example.com/cantonese.jpg', 'PT002', 3, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT006', '意大利菜', 'https://example.com/italian.jpg', 'PT003', 3, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant001');

-- 为另一个租户添加数据
INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT007', '饮品', 'https://example.com/drinks.jpg', '', 1, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant002');

INSERT INTO product_type (id, name, picture, parent_id, levels, version, deleted, creator, create_time, updater, update_time, tenant_id)
VALUES ('PT008', '咖啡', 'https://example.com/coffee.jpg', 'PT007', 2, 0, 0, 'system', 1704067200000, 'system', 1704067200000, 'tenant002');

COMMIT;
