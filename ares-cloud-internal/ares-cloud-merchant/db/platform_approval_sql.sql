
-- ----------------------------
-- Table structure for platform_approval
-- 作者 hugo
-- date 2024-10-31
-- 平台审批
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE platform_approval';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
-- auto-generated definition
create table PLATFORM_APPROVAL
(
    VERSION               NUMBER,
    DELETED               NUMBER,
    STATUS                NUMBER,
    APPROVAL_ID           VARCHAR2(32),
    ID                    VARCHAR2(30) not null
        constraint PK_PLATFORM_APPROVAL
            primary key,
    CREATOR               VARCHAR2(30),
    CREATE_TIME           NUMBER(13),
    UPDATER               VARCHAR2(30),
    UPDATE_TIME           NUMBER(13),
    TENANT_ID             VARCHAR2(32),
    MERCHANT_SUBSCRIBE_ID VARCHAR2(32),
    PAY_ID                VARCHAR2(32) not null,
    NAME                  VARCHAR2(64) not null,
    TAX_NUM               VARCHAR2(64) not null,
    ORDER_ID              VARCHAR2(32),
    USER_ID               VARCHAR2(32) not null
)


comment on table PLATFORM_APPROVAL is '平台审批';


comment on column PLATFORM_APPROVAL.VERSION is '版本号';


comment on column PLATFORM_APPROVAL.DELETED is '删除标记';


comment on column PLATFORM_APPROVAL.STATUS is '审批状态';


comment on column PLATFORM_APPROVAL.APPROVAL_ID is '审批编号';


comment on column PLATFORM_APPROVAL.ID is '主键';


comment on column PLATFORM_APPROVAL.CREATOR is '创建者';


comment on column PLATFORM_APPROVAL.CREATE_TIME is '创建时间';


comment on column PLATFORM_APPROVAL.UPDATER is '更新者';
;

comment on column PLATFORM_APPROVAL.UPDATE_TIME is '更新时间';


comment on column PLATFORM_APPROVAL.TENANT_ID is '租户id';


comment on column PLATFORM_APPROVAL.MERCHANT_SUBSCRIBE_ID is '租户订阅id';


comment on column PLATFORM_APPROVAL.PAY_ID is '支付类型id 1:线下支付；2:银联；3:现金';


comment on column PLATFORM_APPROVAL.TAX_NUM is '税务号(企业编号)';


comment on column PLATFORM_APPROVAL.ORDER_ID is '订单id';


comment on column PLATFORM_APPROVAL.USER_ID is '用户id';


