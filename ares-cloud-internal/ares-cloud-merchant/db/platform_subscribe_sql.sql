
-- ----------------------------
-- Table structure for platform_subscribe
-- 作者 hugo
-- date 2024-11-01
-- 订阅基础信息
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_subscribe';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
-- auto-generated definition
create table PLATFORM_SUBSCRIBE
(
    VERSION        NUMBER,
    DELETED        NUMBER,
    SUBSCRIBE_TYPE NUMBER        not null,
    PRICE          NUMBER(12, 2) not null,
    TYPE           NUMBER,
    MEMORY         NUMBER(10, 2),
    ID             VARCHAR2(30)  not null
        constraint PK_PLATFORM_SUBSCRIBE
            primary key,
    CREATOR        VARCHAR2(30),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(30),
    UPDATE_TIME    NUMBER(13)
)
    /

comment on table PLATFORM_SUBSCRIBE is '订阅基础信息'
/

comment on column PLATFORM_SUBSCRIBE.VERSION is '版本号'
/

comment on column PLATFORM_SUBSCRIBE.DELETED is '删除标记'
/

comment on column PLATFORM_SUBSCRIBE.SUBSCRIBE_TYPE is '订阅类型（1：服务订阅；2：存储订阅）'
/

comment on column PLATFORM_SUBSCRIBE.PRICE is '金额'
/

comment on column PLATFORM_SUBSCRIBE.TYPE is '子类型:网站、财务软件'
/

comment on column PLATFORM_SUBSCRIBE.MEMORY is '存储空间'
/

comment on column PLATFORM_SUBSCRIBE.ID is '主键'
/

comment on column PLATFORM_SUBSCRIBE.CREATOR is '创建者'
/

comment on column PLATFORM_SUBSCRIBE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_SUBSCRIBE.UPDATER is '更新者'
/

comment on column PLATFORM_SUBSCRIBE.UPDATE_TIME is '更新时间'
/


