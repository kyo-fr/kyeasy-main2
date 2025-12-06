
create table MERCHANT_OPENING_HOURS
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(30) not null
        constraint PK_OPENING_HOURS
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32),
    WEEK_DAY    VARCHAR2(8),
    OPEN_TIME1  VARCHAR2(16),
    CLOSE_TIME1 VARCHAR2(16),
    TIME_TYPE   NUMBER       not null,
    IS_REST     NUMBER,
    CLOSE_TIME2 VARCHAR2(32),
    OPEN_TIME2  VARCHAR2(32)
)
    /

comment on table MERCHANT_OPENING_HOURS is '商户营业时间'
/

comment on column MERCHANT_OPENING_HOURS.VERSION is '版本号'
/

comment on column MERCHANT_OPENING_HOURS.DELETED is '删除标记'
/

comment on column MERCHANT_OPENING_HOURS.ID is '主键'
/

comment on column MERCHANT_OPENING_HOURS.CREATOR is '创建者'
/

comment on column MERCHANT_OPENING_HOURS.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_OPENING_HOURS.UPDATER is '更新者'
/

comment on column MERCHANT_OPENING_HOURS.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_OPENING_HOURS.TENANT_ID is '租户(商户id)'
/

comment on column MERCHANT_OPENING_HOURS.WEEK_DAY is '工作日'
/

comment on column MERCHANT_OPENING_HOURS.OPEN_TIME1 is '上午开店时间'
/

comment on column MERCHANT_OPENING_HOURS.CLOSE_TIME1 is '上午闭店时间'
/

comment on column MERCHANT_OPENING_HOURS.TIME_TYPE is '营业时间类型 1: 24/7全天候 ; 2：设置工作日时间'
/

comment on column MERCHANT_OPENING_HOURS.CLOSE_TIME2 is '下午闭店时间'
/

comment on column MERCHANT_OPENING_HOURS.OPEN_TIME2 is '下午开店时间'
/
