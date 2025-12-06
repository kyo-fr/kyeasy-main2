
-- ----------------------------
-- Table structure for merchant_side_bar
-- 作者 hugo
-- date 2024-10-09
-- 商户侧栏
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_side_bar';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
-- auto-generated definition
create table MERCHANT_SIDE_BAR
(
    VERSION     NUMBER(2),
    DELETED     NUMBER(2),
    NAME        VARCHAR2(16) not null,
    ID          VARCHAR2(30) not null
        constraint PK_MERCHANT_SIDE_BAR
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32) not null,
    STATUS      NUMBER(2)    not null,
    CODE        NUMBER       not null
)

comment on table MERCHANT_SIDE_BAR is '商户侧栏'

comment on column MERCHANT_SIDE_BAR.VERSION is '版本号'

comment on column MERCHANT_SIDE_BAR.DELETED is '删除标记'

comment on column MERCHANT_SIDE_BAR.NAME is '勾选侧栏服务'

comment on column MERCHANT_SIDE_BAR.ID is '主键'

comment on column MERCHANT_SIDE_BAR.CREATOR is '创建者'

comment on column MERCHANT_SIDE_BAR.CREATE_TIME is '创建时间'

comment on column MERCHANT_SIDE_BAR.UPDATER is '更新者'

comment on column MERCHANT_SIDE_BAR.UPDATE_TIME is '更新时间'

comment on column MERCHANT_SIDE_BAR.TENANT_ID is '租户'

comment on column MERCHANT_SIDE_BAR.STATUS is '是否勾选(1:是;2:否)'

comment on column MERCHANT_SIDE_BAR.STATUS is '侧栏服务code：1:网上购物; 2:保险; 3:免费配送; 4:货到付款; 5:退款; 6:到店取货'
