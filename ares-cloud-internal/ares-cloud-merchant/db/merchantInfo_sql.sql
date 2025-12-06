-- ----------------------------
-- Table structure for merchant_info
-- 作者 hugo
-- date 2024-10-08
-- 商户信息
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_info';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
END IF;
END;

-- auto-generated definition
create table MERCHANT_INFO
(
    TAX_NUM          VARCHAR2(64)  not null,
    ENTERPRISE_EMAIL VARCHAR2(32)  not null,
    COUNTRY_CODE     VARCHAR2(64)  not null,
    LANGUAGE         VARCHAR2(64)  not null,
    REGISTER_PHONE   VARCHAR2(32)  not null,
    ID               VARCHAR2(32)  not null
        constraint PK_MERCHANT_INFO
            primary key,
    CREATOR          VARCHAR2(30),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(30),
    UPDATE_TIME      NUMBER(13),
    NAME             VARCHAR2(64)  not null,
    DOMAIN_NAME      VARCHAR2(256),
    PHONE            VARCHAR2(16)  not null,
    ADDRESS          VARCHAR2(256) not null,
    DELETED          NUMBER,
    VERSION          NUMBER,
    USER_ID          VARCHAR2(32)  not null,
    STATUS           NUMBER default 2,
    I_BAN            VARCHAR2(32),
    BIC              VARCHAR2(64),
    CURRENCY         VARCHAR2(8)   not null,
    PAGE_DISPLAY     NUMBER(2),
    CONTRACT_ID      VARCHAR2(32),
    IS_OPEN_GIFT     NUMBER,
    USED_MEMORY      NUMBER default 0,
    SUM_MEMORY       NUMBER default 0,
    OVERDUE_DATE     NUMBER(13),
    SIREN            VARCHAR2(64),
    NAF              VARCHAR2(64),
    COUNTRY          VARCHAR2(64),
    CITY             VARCHAR2(64),
    POSTAL_CODE      VARCHAR2(32)
)
comment on table MERCHANT_INFO is '商户信息'

comment on column MERCHANT_INFO.TAX_NUM is '税务号'

comment on column MERCHANT_INFO.ENTERPRISE_EMAIL is '企业邮箱'

comment on column MERCHANT_INFO.COUNTRY_CODE is '国家'

comment on column MERCHANT_INFO.LANGUAGE is '网站语言'

comment on column MERCHANT_INFO.REGISTER_PHONE is '注册手机号'

comment on column MERCHANT_INFO.ID is '主键(商户id)'

comment on column MERCHANT_INFO.CREATOR is '创建者'

comment on column MERCHANT_INFO.CREATE_TIME is '创建时间'

comment on column MERCHANT_INFO.UPDATER is '更新者'

comment on column MERCHANT_INFO.UPDATE_TIME is '更新时间'

comment on column MERCHANT_INFO.NAME is '企业名称'

comment on column MERCHANT_INFO.DOMAIN_NAME is '企业域名'

comment on column MERCHANT_INFO.PHONE is '企业电话'

comment on column MERCHANT_INFO.ADDRESS is '企业地址'

comment on column MERCHANT_INFO.DELETED is '删除标记'

comment on column MERCHANT_INFO.VERSION is '版本号'

comment on column MERCHANT_INFO.USER_ID is '用户id'

comment on column MERCHANT_INFO.STATUS is '商户状态 1-注册中；2-正常；3-冻结'

comment on column MERCHANT_INFO.I_BAN is '企业IBank'

comment on column MERCHANT_INFO.BIC is '企业BIC'

comment on column MERCHANT_INFO.CURRENCY is '收款货币'

comment on column MERCHANT_INFO.PAGE_DISPLAY is '首页展示 1：正常商品 ；2：拍卖商品'

comment on column MERCHANT_INFO.CONTRACT_ID is '合同编号'

comment on column MERCHANT_INFO.IS_OPEN_GIFT is '是否开启礼物点1是 2否'

comment on column MERCHANT_INFO.USED_MEMORY is '已使用存储量'

comment on column MERCHANT_INFO.SUM_MEMORY is '总存储量'

comment on column MERCHANT_INFO.OVERDUE_DATE is '存储过期时间'

comment on column MERCHANT_INFO.SIREN is '企业SIREN'

comment on column MERCHANT_INFO.NAF is 'CODE NAF'

comment on column MERCHANT_INFO.COUNTRY is '国家(地址)'

comment on column MERCHANT_INFO.CITY is '城市'

comment on column MERCHANT_INFO.POSTAL_CODE is '邮编'

create index MERCHANT_INFO_USER_ID_INDEX
    on MERCHANT_INFO (USER_ID)
    

