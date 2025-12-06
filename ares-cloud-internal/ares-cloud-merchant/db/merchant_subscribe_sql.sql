
-- ----------------------------
-- Table structure for merchant_subscribe
-- 作者 hugo
-- date 2024-10-11
-- 商户订阅
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_subscribe';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/-- auto-generated definition
create table MERCHANT_SUBSCRIBE
(
    VERSION               NUMBER,
    DELETED               NUMBER,
    ID                    VARCHAR2(32) not null
        constraint PK_MERCHANT_SUBSCRIBE
            primary key,
    CREATOR               VARCHAR2(30),
    CREATE_TIME           NUMBER(13),
    UPDATER               VARCHAR2(30),
    UPDATE_TIME           NUMBER(13),
    TENANT_ID             VARCHAR2(32) not null,
    SUBSCRIBE_ID          VARCHAR2(32),
    SUBSCRIBE_TYPE        NUMBER(2),
    INVOICE_ID            VARCHAR2(32),
    START_TIME            VARCHAR2(32) not null,
    END_TIME              VARCHAR2(32),
    MEMORY                NUMBER(8, 2),
    TYPE                  VARCHAR2(16),
    AMOUNT                NUMBER(8, 2),
    IS_ONLINE             NUMBER       not null,
    PAY_WAY               NUMBER       not null,
    PLATFORM_SUBSCRIBE_ID VARCHAR2(32) not null
)
    /

comment on table MERCHANT_SUBSCRIBE is '商户订阅'

comment on column MERCHANT_SUBSCRIBE.VERSION is '版本号'

comment on column MERCHANT_SUBSCRIBE.DELETED is '删除标记'

comment on column MERCHANT_SUBSCRIBE.ID is '主键'

comment on column MERCHANT_SUBSCRIBE.CREATOR is '创建者'

comment on column MERCHANT_SUBSCRIBE.CREATE_TIME is '创建时间'

comment on column MERCHANT_SUBSCRIBE.UPDATER is '更新者'

comment on column MERCHANT_SUBSCRIBE.UPDATE_TIME is '更新时间'

comment on column MERCHANT_SUBSCRIBE.TENANT_ID is '租户(商户id)'

comment on column MERCHANT_SUBSCRIBE.SUBSCRIBE_ID is '订阅id'

comment on column MERCHANT_SUBSCRIBE.SUBSCRIBE_TYPE is '订阅类型（1：网站订阅；2：存储订阅）'

comment on column MERCHANT_SUBSCRIBE.INVOICE_ID is '发票id'

comment on column MERCHANT_SUBSCRIBE.START_TIME is '使用开始时间'

comment on column MERCHANT_SUBSCRIBE.END_TIME is '使用结束时间'

comment on column MERCHANT_SUBSCRIBE.MEMORY is '存储空间'

comment on column MERCHANT_SUBSCRIBE.TYPE is '子类型(网站、财务软件)'

comment on column MERCHANT_SUBSCRIBE.IS_ONLINE is '是否线上支付 1:线上；2:线下'

comment on column MERCHANT_SUBSCRIBE.PAY_WAY is '支付方式1:支付宝，2:微信，3:信用卡，4:现金'

comment on column MERCHANT_SUBSCRIBE.PLATFORM_SUBSCRIBE_ID is '平台订阅id	'
