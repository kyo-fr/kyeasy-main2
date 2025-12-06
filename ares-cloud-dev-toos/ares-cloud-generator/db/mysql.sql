-- init-databases.sql
CREATE DATABASE IF NOT EXISTS nacos;
CREATE DATABASE IF NOT EXISTS ares_generator;

DROP TABLE IF EXISTS gen_datasource;
CREATE TABLE gen_datasource
(
    id          bigint       NOT NULL AUTO_INCREMENT COMMENT 'id',
    db_type     varchar(200) COMMENT '数据库类型',
    conn_name   varchar(200) NOT NULL COMMENT '连接名',
    conn_url    varchar(500) COMMENT 'URL',
    username    varchar(200) COMMENT '用户名',
    password    varchar(200) COMMENT '密码',
    create_time datetime COMMENT '创建时间',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='数据源管理';


DROP TABLE IF EXISTS gen_field_type;
CREATE TABLE gen_field_type
(
    id           varchar(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    column_type  varchar(200) COMMENT '字段类型',
    attr_type    varchar(200) COMMENT '属性类型',
    package_name varchar(200) COMMENT '属性包名',
    create_time  datetime COMMENT '创建时间',
    primary key (id),
    unique key (column_type)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='字段类型管理';


DROP TABLE IF EXISTS gen_base_class;
CREATE TABLE gen_base_class
(
    id           varchar(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    package_name varchar(200) COMMENT '基类包名',
    code         varchar(200) COMMENT '基类编码',
    fields       varchar(500) COMMENT '基类字段，多个用英文逗号分隔',
    remark       varchar(200) COMMENT '备注',
    create_time  datetime COMMENT '创建时间',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='基类管理';


DROP TABLE IF EXISTS gen_models;
CREATE TABLE gen_models
(
    id             varchar(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    table_name     varchar(200) COMMENT '表名',
    class_name     varchar(200) COMMENT '类名',
    table_comment  varchar(200) COMMENT '说明',
    author         varchar(200) COMMENT '作者',
    email          varchar(200) COMMENT '邮箱',
    package_name   varchar(200) COMMENT '项目包名',
    version        varchar(200) COMMENT '项目版本号',
    model_type tinyint COMMENT '模型类型 1->基础摩西;2-》业务模型',
    generator_type tinyint COMMENT '生成方式  0：zip压缩包   1：自定义目录',
    backend_path   varchar(500) COMMENT '后端生成路径',
    frontend_path  varchar(500) COMMENT '前端生成路径',
    module_name    varchar(200) COMMENT '模块名',
    function_name  varchar(200) COMMENT '功能名',
    form_layout    tinyint COMMENT '表单布局  1：一列   2：两列',
    source_type    tinyint DEFAULT 0  COMMENT  '来源类型；0创建，1导入',
    datasource_id  bigint COMMENT '数据源ID',
    baseclass_id   bigint COMMENT '基类ID',
    create_time    datetime COMMENT '创建时间',
    primary key (id),
    unique key (table_name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='代码模型';

DROP TABLE IF EXISTS gen_model_field;
CREATE TABLE gen_model_field
(
    id              varchar(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    model_id        varchar(20) COMMENT '模型id',
    field_name      varchar(200) COMMENT '字段名称',
    field_type      varchar(200) COMMENT '字段类型',
    field_comment   varchar(200) COMMENT '字段说明',
    attr_name       varchar(200) COMMENT '属性名',
    attr_type       varchar(200) COMMENT '属性类型',
    package_name    varchar(200) COMMENT '属性包名',
    sort            int COMMENT '排序',
    auto_fill       varchar(20) COMMENT '自动填充  DEFAULT、INSERT、UPDATE、INSERT_UPDATE',
    primary_pk      tinyint COMMENT '主键 0：否  1：是',
    base_field      tinyint COMMENT '基类字段 0：否  1：是',
    form_item       tinyint COMMENT '表单项 0：否  1：是',
    form_required   tinyint COMMENT '表单必填 0：否  1：是',
    form_type       varchar(200) COMMENT '表单类型',
    form_dict       varchar(200) COMMENT '表单字典类型',
    form_validator  varchar(200) COMMENT '表单效验',
    grid_item       tinyint COMMENT '列表项 0：否  1：是',
    grid_sort       tinyint COMMENT '列表排序 0：否  1：是',
    query_item      tinyint COMMENT '查询项 0：否  1：是',
    length       int COMMENT '长度',
    point       int COMMENT '小数点位数',
    is_null       tinyint COMMENT '列表排序 2：否  1：是',
    def_value    varchar(200) COMMENT '默认值',
    query_type      varchar(200) COMMENT '查询方式',
    query_form_type varchar(200) COMMENT '查询表单类型',
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='代码模型字段';

DROP TABLE IF EXISTS gen_project;
CREATE TABLE gen_project
(
    id                     varchar(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    project_name           varchar(100) COMMENT '项目名',
    project_code           varchar(100) COMMENT '项目标识',
    project_package        varchar(100) COMMENT '项目包名',
    project_path           varchar(200) COMMENT '项目路径',
    modify_project_name    varchar(100) COMMENT '变更项目名',
    modify_project_code    varchar(100) COMMENT '变更标识',
    modify_project_package varchar(100) COMMENT '变更包名',
    exclusions             varchar(200) COMMENT '排除文件',
    modify_suffix          varchar(200) COMMENT '变更文件',
    modify_tmp_path        varchar(100) COMMENT '变更临时路径',
    create_time            datetime COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='项目';


-- 用于测试代码生成器的表结构 --
DROP TABLE IF EXISTS gen_test_student;
CREATE TABLE gen_test_student
(
    id          varchar(20) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
    name        varchar(50) COMMENT '姓名',
    gender      tinyint COMMENT '性别',
    age         int COMMENT '年龄',
    class_name  varchar(50) COMMENT '班级',
    version     int COMMENT '版本号',
    deleted     tinyint COMMENT '删除标识',
    creator     bigint COMMENT '创建者',
    create_time datetime COMMENT '创建时间',
    updater     bigint COMMENT '更新者',
    update_time datetime COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='测试2';


INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('datetime', 'Date', 'java.util.Date', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('date', 'Date', 'java.util.Date', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinyint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('smallint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumint', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('integer', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bigint', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('float', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('double', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('decimal', 'BigDecimal', 'java.math.BigDecimal', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('bit', 'Boolean', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('char', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('varchar', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('tinytext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('text', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('mediumtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('longtext', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('timestamp', 'Date', 'java.util.Date', now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NUMBER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_INTEGER', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_FLOAT', 'Float', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('BINARY_DOUBLE', 'Double', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('VARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('NVARCHAR2', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('CLOB', 'String', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int8', 'Long', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int4', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('int2', 'Integer', NULL, now());
INSERT INTO gen_field_type (column_type, attr_type, package_name, create_time) VALUES ('numeric', 'BigDecimal', 'java.math.BigDecimal', now());

INSERT INTO gen_base_class (id, package_name, code, fields, remark, create_time) VALUES (1, 'org.ares.cloud.database.entity', 'BaseEntity', 'id,creator,create_time,updater,update_time,version,deleted', '使用该基类，则需要表里有这些字段', '2024-09-26 16:38:24');
INSERT INTO gen_base_class (id, package_name, code, fields, remark, create_time) VALUES (2, 'org.ares.cloud.database.entity', 'TenantEntity', 'id,creator,create_time,updater,update_time,version,deleted,tenant_id', '使用该基类，则需要表里有这些字段', '2024-09-26 16:38:24');

INSERT INTO gen_models (id, table_name, class_name, table_comment, author, email, package_name, version, model_type, generator_type, backend_path, frontend_path, module_name, function_name, form_layout, source_type, datasource_id, baseclass_id, create_time) VALUES (1839272128944779265, 'ares_base_entity', 'BaseEntity', 'Entity基基础模型', 'hugo', 'tangxkwork@163.com', 'org.ares.cloud', '1.0.0', 1, null, '/Users/hg/workspace/java', '', 'message', 'ares_base_entity', null, 0, null, null, '2024-09-26 11:53:42');


INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839272432482365441, 1839272128944779265, 'id', 'varchar', '主键', 'id', 'String', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 30, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839274018470354945, 1839272128944779265, 'creator', 'varchar', '创建者', 'creator', 'String', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 30, 0, 0, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839275681813880834, 1839272128944779265, 'create_time', 'bigint', '创建时间', 'createTime', 'Long', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 13, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839275994864148482, 1839272128944779265, 'updater', 'varchar', '更新者', 'updater', 'String', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 30, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839276144529498114, 1839272128944779265, 'update_time', 'bigint', '更新时间', 'updateTime', 'Long', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 13, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839276384103948289, 1839272128944779265, 'version', 'int', '版本号', 'version', 'Integer', null, null, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 0, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839276520544657410, 1839272128944779265, 'deleted', 'int', '删除标记', 'deleted', 'Integer', null, null, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 0, 0, 1, null, null, null);
INSERT INTO gen_model_field (id, model_id, field_name, field_type, field_comment, attr_name, attr_type, package_name, sort, auto_fill, primary_pk, base_field, form_item, form_required, form_type, form_dict, form_validator, grid_item, grid_sort, query_item, length, point, is_null, def_value, query_type, query_form_type) VALUES (1839276661112561666, 1839272128944779265, 'tenant_id', 'varchar', '租户', 'tenantId', 'String', null, 0, 'DEFAULT', 0, 0, null, null, null, null, null, null, null, null, 30, 0, 1, null, null, null);
