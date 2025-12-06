
-- ----------------------------
-- Table structure for merchant_key_words
-- 作者 hugo
-- date 2024-10-11
-- 关键字管理
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_key_words';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/-- auto-generated definition
create table MERCHANT_KEY_WORDS
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    KEY_NAME    VARCHAR2(32)     not null,
    KEY_ID      VARCHAR2(32)     not null,
    STATUS      NUMBER default 1 not null,
    ID          VARCHAR2(30)     not null
        constraint PK_MERCHANT_KEY_WORDS
        primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(30)
)

comment on table MERCHANT_KEY_WORDS is '商户关键字'

comment on column MERCHANT_KEY_WORDS.VERSION is '版本号'

comment on column MERCHANT_KEY_WORDS.DELETED is '删除标记'

comment on column MERCHANT_KEY_WORDS.KEY_NAME is '关键字名称'

comment on column MERCHANT_KEY_WORDS.KEY_ID is '关键字id'

comment on column MERCHANT_KEY_WORDS.STATUS is '关键字状态'

comment on column MERCHANT_KEY_WORDS.ID is '主键'

comment on column MERCHANT_KEY_WORDS.CREATOR is '创建者'

comment on column MERCHANT_KEY_WORDS.CREATE_TIME is '创建时间'

comment on column MERCHANT_KEY_WORDS.UPDATER is '更新者'

comment on column MERCHANT_KEY_WORDS.UPDATE_TIME is '更新时间'

comment on column MERCHANT_KEY_WORDS.TENANT_ID is '租户(商户id)'
