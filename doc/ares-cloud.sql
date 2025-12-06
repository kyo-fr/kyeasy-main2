create sequence PRODUCT_TYPES_SEQ
/

create sequence SEQ_SPECIFICATION_ID
    nocache
/

create sequence SEQ_ORDER_ID
    nocache
/

create sequence SEQ_ORDER_ITEM_ID
    nocache
/

create sequence SEQ_DELIVERY_INFO_ID
    nocache
/

create sequence SEQ_INVOICE_ITEM_ID
    nocache
/

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
    OVERDUE_DATE     NUMBER,
    IS_UPDATED       VARCHAR2(2)
)
/

comment on table MERCHANT_INFO is '商户信息'
/

comment on column MERCHANT_INFO.TAX_NUM is '税务号'
/

comment on column MERCHANT_INFO.ENTERPRISE_EMAIL is '企业邮箱'
/

comment on column MERCHANT_INFO.COUNTRY_CODE is '国家'
/

comment on column MERCHANT_INFO.LANGUAGE is '网站语言'
/

comment on column MERCHANT_INFO.REGISTER_PHONE is '注册手机号'
/

comment on column MERCHANT_INFO.ID is '主键'
/

comment on column MERCHANT_INFO.CREATOR is '创建者'
/

comment on column MERCHANT_INFO.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_INFO.UPDATER is '更新者'
/

comment on column MERCHANT_INFO.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_INFO.NAME is '企业名称'
/

comment on column MERCHANT_INFO.DOMAIN_NAME is '企业域名'
/

comment on column MERCHANT_INFO.PHONE is '企业电话'
/

comment on column MERCHANT_INFO.ADDRESS is '企业地址'
/

comment on column MERCHANT_INFO.DELETED is '删除标记'
/

comment on column MERCHANT_INFO.VERSION is '版本号'
/

comment on column MERCHANT_INFO.USER_ID is '用户id'
/

comment on column MERCHANT_INFO.STATUS is '商户状态 1-注册中；2-正常；3-冻结'
/

comment on column MERCHANT_INFO.I_BAN is '企业IBank'
/

comment on column MERCHANT_INFO.BIC is '企业BIC'
/

comment on column MERCHANT_INFO.CURRENCY is '收款货币'
/

comment on column MERCHANT_INFO.PAGE_DISPLAY is '首页展示 1：正常商品 ；2：拍卖商品'
/

comment on column MERCHANT_INFO.CONTRACT_ID is '合同编号'
/

comment on column MERCHANT_INFO.IS_OPEN_GIFT is '是否开启礼物点1是 2否'
/

comment on column MERCHANT_INFO.USED_MEMORY is '已使用存储量'
/

comment on column MERCHANT_INFO.SUM_MEMORY is '总存储量'
/

comment on column MERCHANT_INFO.OVERDUE_DATE is '存储过期时间'
/

comment on column MERCHANT_INFO.IS_UPDATED is 'Y代表已维护商户信息；N代表没有维护'
/

create index MERCHANT_INFO_USER_ID_INDEX
    on MERCHANT_INFO (USER_ID)
/

create table MERCHANT_OPENING_HOURS
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(32) not null
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
    TIME_TYPE   VARCHAR2(16) not null,
    CLOSE_TIME2 VARCHAR2(32),
    OPEN_TIME2  VARCHAR2(32),
    IS_AM_REST  NUMBER(2),
    IS_PM_REST  NUMBER(2)
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

comment on column MERCHANT_OPENING_HOURS.TIME_TYPE is '营业时间类型 allDay: 24/7全天候 ; time：设置工作日时间; relax：休息'
/

comment on column MERCHANT_OPENING_HOURS.CLOSE_TIME2 is '下午闭店时间'
/

comment on column MERCHANT_OPENING_HOURS.OPEN_TIME2 is '下午开店时间'
/

comment on column MERCHANT_OPENING_HOURS.IS_AM_REST is '上午是否休息 1:是 ; 2：否'
/

comment on column MERCHANT_OPENING_HOURS.IS_PM_REST is '下午是否休息 1:是 ; 2：否'
/

create index MERCHANT_OPENING_HOURS_TENANT_ID_INDEX
    on MERCHANT_OPENING_HOURS (TENANT_ID)
/

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
/

comment on table MERCHANT_SIDE_BAR is '商户侧栏'
/

comment on column MERCHANT_SIDE_BAR.VERSION is '版本号'
/

comment on column MERCHANT_SIDE_BAR.DELETED is '删除标记'
/

comment on column MERCHANT_SIDE_BAR.NAME is '勾选侧栏服务'
/

comment on column MERCHANT_SIDE_BAR.ID is '主键'
/

comment on column MERCHANT_SIDE_BAR.CREATOR is '创建者'
/

comment on column MERCHANT_SIDE_BAR.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_SIDE_BAR.UPDATER is '更新者'
/

comment on column MERCHANT_SIDE_BAR.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_SIDE_BAR.TENANT_ID is '租户'
/

comment on column MERCHANT_SIDE_BAR.STATUS is '是否勾选(1:是;2:否)'
/

comment on column MERCHANT_SIDE_BAR.CODE is '侧栏服务code'
/

create index MERCHANT_SIDE_BAR_TENANT_ID_INDEX
    on MERCHANT_SIDE_BAR (TENANT_ID)
/

create table MERCHANT_SOCIALIZE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    YOUTUBE     VARCHAR2(128),
    FACE_BOOK   VARCHAR2(128),
    BILIBILI    VARCHAR2(128),
    TIKTOK      VARCHAR2(128),
    WHATS_APP   VARCHAR2(128),
    WECHAT      VARCHAR2(32),
    DOU_YIN     VARCHAR2(128),
    ID          VARCHAR2(30) not null
        constraint PK_MERCHANT_SOCIALIZE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32) not null,
    FEAC_BOOK   VARCHAR2(128),
    TWITTER     VARCHAR2(128)
)
/

comment on table MERCHANT_SOCIALIZE is '商户社交'
/

comment on column MERCHANT_SOCIALIZE.VERSION is '版本号'
/

comment on column MERCHANT_SOCIALIZE.DELETED is '删除标记'
/

comment on column MERCHANT_SOCIALIZE.YOUTUBE is 'youtube'
/

comment on column MERCHANT_SOCIALIZE.FACE_BOOK is 'faceBook'
/

comment on column MERCHANT_SOCIALIZE.BILIBILI is 'bilibili'
/

comment on column MERCHANT_SOCIALIZE.TIKTOK is 'tiktok'
/

comment on column MERCHANT_SOCIALIZE.WHATS_APP is 'whatsApp'
/

comment on column MERCHANT_SOCIALIZE.WECHAT is '微信'
/

comment on column MERCHANT_SOCIALIZE.DOU_YIN is '抖音'
/

comment on column MERCHANT_SOCIALIZE.ID is '主键'
/

comment on column MERCHANT_SOCIALIZE.CREATOR is '创建者'
/

comment on column MERCHANT_SOCIALIZE.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_SOCIALIZE.UPDATER is '更新者'
/

comment on column MERCHANT_SOCIALIZE.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_SOCIALIZE.TENANT_ID is '租户'
/

comment on column MERCHANT_SOCIALIZE.FEAC_BOOK is 'feacbook'
/

comment on column MERCHANT_SOCIALIZE.TWITTER is '推特'
/

create index TENANT_ID_INDEX
    on MERCHANT_SOCIALIZE (TENANT_ID)
/

create table MERCHANT_KEY_WORDS
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    KEY_NAME    VARCHAR2(32)     not null,
    STATUS      NUMBER default 1 not null,
    ID          VARCHAR2(30)     not null
        constraint PK_MERCHANT_KEY_WORDS
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)     not null
)
/

comment on table MERCHANT_KEY_WORDS is '商户关键字'
/

comment on column MERCHANT_KEY_WORDS.VERSION is '版本号'
/

comment on column MERCHANT_KEY_WORDS.DELETED is '删除标记'
/

comment on column MERCHANT_KEY_WORDS.KEY_NAME is '关键字名称'
/

comment on column MERCHANT_KEY_WORDS.STATUS is '关键字状态1:启用 2关闭'
/

comment on column MERCHANT_KEY_WORDS.ID is '主键'
/

comment on column MERCHANT_KEY_WORDS.CREATOR is '创建者'
/

comment on column MERCHANT_KEY_WORDS.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_KEY_WORDS.UPDATER is '更新者'
/

comment on column MERCHANT_KEY_WORDS.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_KEY_WORDS.TENANT_ID is '租户(商户id)'
/

create index MERCHANT_KEY_WORDS_TENANT_ID_INDEX
    on MERCHANT_KEY_WORDS (TENANT_ID)
/

create table MERCHANT_SUBSCRIBE
(
    VERSION        NUMBER,
    DELETED        NUMBER,
    ID             VARCHAR2(32) not null
        constraint PK_MERCHANT_SUBSCRIBE
            primary key,
    CREATOR        VARCHAR2(30),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(30),
    UPDATE_TIME    NUMBER(13),
    TENANT_ID      VARCHAR2(32) not null,
    SUBSCRIBE_TYPE VARCHAR2(32),
    INVOICE_ID     VARCHAR2(64),
    START_TIME     VARCHAR2(32),
    END_TIME       VARCHAR2(32),
    MEMORY         NUMBER(8, 2),
    AMOUNT         NUMBER(8, 2),
    APPROVAL_ID    VARCHAR2(32),
    CONTRACT_ID    VARCHAR2(32),
    STATUS         NUMBER
)
/

comment on table MERCHANT_SUBSCRIBE is '商户订阅'
/

comment on column MERCHANT_SUBSCRIBE.VERSION is '版本号'
/

comment on column MERCHANT_SUBSCRIBE.DELETED is '删除标记'
/

comment on column MERCHANT_SUBSCRIBE.ID is '主键'
/

comment on column MERCHANT_SUBSCRIBE.CREATOR is '创建者'
/

comment on column MERCHANT_SUBSCRIBE.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_SUBSCRIBE.UPDATER is '更新者'
/

comment on column MERCHANT_SUBSCRIBE.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_SUBSCRIBE.TENANT_ID is '租户(商户id)'
/

comment on column MERCHANT_SUBSCRIBE.SUBSCRIBE_TYPE is '订阅类型：cash-收银系统订阅；storage-存储空间订阅'
/

comment on column MERCHANT_SUBSCRIBE.INVOICE_ID is '发票id'
/

comment on column MERCHANT_SUBSCRIBE.START_TIME is '使用开始时间'
/

comment on column MERCHANT_SUBSCRIBE.END_TIME is '使用结束时间'
/

comment on column MERCHANT_SUBSCRIBE.MEMORY is '存储空间'
/

comment on column MERCHANT_SUBSCRIBE.APPROVAL_ID is '审批id'
/

comment on column MERCHANT_SUBSCRIBE.CONTRACT_ID is '合同id'
/

comment on column MERCHANT_SUBSCRIBE.STATUS is '1:订阅；2到期'
/

create table HARDWARE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    HARDWARE_ID VARCHAR2(32),
    TYPE_ID     NUMBER           not null,
    ID          VARCHAR2(30)     not null
        constraint PK_HARDWARE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    NAME        VARCHAR2(128)    not null,
    STATUS      NUMBER default 1 not null
)
/

comment on table HARDWARE is '硬件'
/

comment on column HARDWARE.VERSION is '版本号'
/

comment on column HARDWARE.DELETED is '删除标记'
/

comment on column HARDWARE.HARDWARE_ID is '硬件id'
/

comment on column HARDWARE.TYPE_ID is '类型id'
/

comment on column HARDWARE.ID is '主键'
/

comment on column HARDWARE.CREATOR is '创建者'
/

comment on column HARDWARE.CREATE_TIME is '创建时间'
/

comment on column HARDWARE.UPDATER is '更新者'
/

comment on column HARDWARE.UPDATE_TIME is '更新时间'
/

comment on column HARDWARE.NAME is '类型名称'
/

comment on column HARDWARE.STATUS is '状态(1:可用；2不可用)'
/

create table SYS_S3_STORAGE
(
    VERSION            NUMBER,
    DELETED            NUMBER,
    ORIGINAL_FILE_NAME VARCHAR2(255),
    FILE_TYPE          VARCHAR2(100),
    CONTAINER          VARCHAR2(50)  not null,
    MODEL              VARCHAR2(50),
    ID                 VARCHAR2(30)  not null
        constraint PK_SYS_S3_STORAGE
            primary key,
    CREATOR            VARCHAR2(30),
    CREATE_TIME        NUMBER(13),
    UPDATER            VARCHAR2(30),
    UPDATE_TIME        NUMBER(13),
    TENANT_ID          VARCHAR2(30),
    NAME               VARCHAR2(255) not null,
    FILE_SIZE          NUMBER
)
/

comment on table SYS_S3_STORAGE is 's3存储'
/

comment on column SYS_S3_STORAGE.VERSION is '版本号'
/

comment on column SYS_S3_STORAGE.DELETED is '删除标记'
/

comment on column SYS_S3_STORAGE.ORIGINAL_FILE_NAME is '原始文件名'
/

comment on column SYS_S3_STORAGE.FILE_TYPE is '文件类型（MIME 类型）'
/

comment on column SYS_S3_STORAGE.CONTAINER is '存储容器名'
/

comment on column SYS_S3_STORAGE.MODEL is '所属模块'
/

comment on column SYS_S3_STORAGE.ID is '主键'
/

comment on column SYS_S3_STORAGE.CREATOR is '创建者'
/

comment on column SYS_S3_STORAGE.CREATE_TIME is '创建时间'
/

comment on column SYS_S3_STORAGE.UPDATER is '更新者'
/

comment on column SYS_S3_STORAGE.UPDATE_TIME is '更新时间'
/

comment on column SYS_S3_STORAGE.TENANT_ID is '租户'
/

comment on column SYS_S3_STORAGE.NAME is '生成的文件名'
/

comment on column SYS_S3_STORAGE.FILE_SIZE is '文件大小（字节）'
/

create table SYS_BUSINESS_ID
(
    MODULE_NAME  VARCHAR2(128) not null,
    PREFIX       VARCHAR2(10),
    MAX_SEQUENCE NUMBER,
    CYCLE_TYPE   NUMBER(1),
    VERSION      NUMBER,
    DELETED      NUMBER,
    CURRENT_DATE VARCHAR2(13)  not null,
    ID           VARCHAR2(30)  not null
        constraint PK_SYS_BUSINESS_ID
            primary key,
    CREATOR      VARCHAR2(30),
    CREATE_TIME  NUMBER(13),
    UPDATER      VARCHAR2(30),
    UPDATE_TIME  NUMBER(13),
    TENANT_ID    VARCHAR2(30),
    DATE_TEMP    VARCHAR2(15)
)
/

comment on table SYS_BUSINESS_ID is '系统业务id'
/

comment on column SYS_BUSINESS_ID.MODULE_NAME is '业务模块名'
/

comment on column SYS_BUSINESS_ID.PREFIX is '业务前缀'
/

comment on column SYS_BUSINESS_ID.MAX_SEQUENCE is '当前最大流水号'
/

comment on column SYS_BUSINESS_ID.CYCLE_TYPE is '流水号周期(0:从不，1:每天，2:每周，3：每月，4：每年)'
/

comment on column SYS_BUSINESS_ID.VERSION is '版本号'
/

comment on column SYS_BUSINESS_ID.DELETED is '删除标记'
/

comment on column SYS_BUSINESS_ID.CURRENT_DATE is '当前时间'
/

comment on column SYS_BUSINESS_ID.ID is '主键'
/

comment on column SYS_BUSINESS_ID.CREATOR is '创建者'
/

comment on column SYS_BUSINESS_ID.CREATE_TIME is '创建时间'
/

comment on column SYS_BUSINESS_ID.UPDATER is '更新者'
/

comment on column SYS_BUSINESS_ID.UPDATE_TIME is '更新时间'
/

comment on column SYS_BUSINESS_ID.TENANT_ID is '租户'
/

comment on column SYS_BUSINESS_ID.DATE_TEMP is '日期模版'
/

create table PLATFORM_MERCHANT_TYPE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    TYPE        VARCHAR2(16) not null,
    ID          VARCHAR2(30) not null
        constraint PK_MERCHANT_TYPE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13)
)
/

comment on table PLATFORM_MERCHANT_TYPE is '商户类型'
/

comment on column PLATFORM_MERCHANT_TYPE.VERSION is '版本号'
/

comment on column PLATFORM_MERCHANT_TYPE.DELETED is '删除标记'
/

comment on column PLATFORM_MERCHANT_TYPE.TYPE is '类型名称'
/

comment on column PLATFORM_MERCHANT_TYPE.ID is '主键'
/

comment on column PLATFORM_MERCHANT_TYPE.CREATOR is '创建者'
/

comment on column PLATFORM_MERCHANT_TYPE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_MERCHANT_TYPE.UPDATER is '更新者'
/

comment on column PLATFORM_MERCHANT_TYPE.UPDATE_TIME is '更新时间'
/

create table PLATFORM_SOCIALIZE
(
    VERSION       NUMBER,
    DELETED       NUMBER,
    ID            VARCHAR2(32) not null
        constraint PK_PLATFORM_SOCIALIZE
            primary key,
    CREATOR       VARCHAR2(30),
    CREATE_TIME   NUMBER(13),
    UPDATER       VARCHAR2(30),
    UPDATE_TIME   NUMBER(13),
    WHATS_APP     VARCHAR2(255),
    WECHAT        VARCHAR2(255),
    TIKTOK        VARCHAR2(255),
    YOUTUBE       VARCHAR2(255),
    BILIBILI      VARCHAR2(255),
    DOU_YIN       VARCHAR2(255),
    TWITTER       VARCHAR2(255),
    CHINA_VIDEO   VARCHAR2(255),
    FOREIGN_VIDEO VARCHAR2(255),
    FACEBOOK      VARCHAR2(255)
)
/

comment on table PLATFORM_SOCIALIZE is '平台海外社交'
/

comment on column PLATFORM_SOCIALIZE.VERSION is '版本号'
/

comment on column PLATFORM_SOCIALIZE.DELETED is '删除标记'
/

comment on column PLATFORM_SOCIALIZE.ID is '主键'
/

comment on column PLATFORM_SOCIALIZE.CREATOR is '创建者'
/

comment on column PLATFORM_SOCIALIZE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_SOCIALIZE.UPDATER is '更新者'
/

comment on column PLATFORM_SOCIALIZE.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_SOCIALIZE.WHATS_APP is 'WHATSAPP'
/

comment on column PLATFORM_SOCIALIZE.WECHAT is '微信'
/

comment on column PLATFORM_SOCIALIZE.TIKTOK is 'TIKTOK'
/

comment on column PLATFORM_SOCIALIZE.YOUTUBE is 'youtube'
/

comment on column PLATFORM_SOCIALIZE.BILIBILI is 'bilibili'
/

comment on column PLATFORM_SOCIALIZE.DOU_YIN is '抖音中国'
/

comment on column PLATFORM_SOCIALIZE.TWITTER is 'twitter'
/

comment on column PLATFORM_SOCIALIZE.CHINA_VIDEO is '官方介绍视频(中国)'
/

comment on column PLATFORM_SOCIALIZE.FOREIGN_VIDEO is '官方介绍视频(海外)'
/

comment on column PLATFORM_SOCIALIZE.FACEBOOK is 'FACEBOOK'
/

create table PLATFORM_TAX_RATE
(
    TYPE        VARCHAR2(2)  not null,
    VERSION     NUMBER,
    DELETED     NUMBER,
    TAX_RATE    NUMBER(8, 2) not null,
    ID          VARCHAR2(30) not null
        constraint PK_PLATFORM_TAX_RATE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)
)
/

comment on table PLATFORM_TAX_RATE is '税率'
/

comment on column PLATFORM_TAX_RATE.TYPE is '类型:1-商品税率(商户)；2-服务税率(商户)；3:运送税率(商户)；4:订阅税率(平台)；5:礼物点税率(平台)'
/

comment on column PLATFORM_TAX_RATE.VERSION is '版本号'
/

comment on column PLATFORM_TAX_RATE.DELETED is '删除标记'
/

comment on column PLATFORM_TAX_RATE.TAX_RATE is '税率'
/

comment on column PLATFORM_TAX_RATE.ID is '主键'
/

comment on column PLATFORM_TAX_RATE.CREATOR is '创建者'
/

comment on column PLATFORM_TAX_RATE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_TAX_RATE.UPDATER is '更新者'
/

comment on column PLATFORM_TAX_RATE.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_TAX_RATE.TENANT_ID is '商户id'
/

create table PLATFORM_PAY_WAY
(
    VERSION      NUMBER,
    DELETED      NUMBER,
    CHANNEL_TYPE NUMBER(2)     not null,
    URL          VARCHAR2(255) not null,
    STATUS       NUMBER(2)     not null,
    FRONT_KEY    VARCHAR2(255),
    BACK_KEY     VARCHAR2(255) not null,
    ID           VARCHAR2(30)  not null
        constraint PK_PLATFORM_PAY_WAY
            primary key,
    CREATOR      VARCHAR2(30),
    CREATE_TIME  NUMBER(13),
    UPDATER      VARCHAR2(30),
    UPDATE_TIME  NUMBER(13),
    PAY_NAME     VARCHAR2(32)  not null
)
/

comment on table PLATFORM_PAY_WAY is '平台支付类型'
/

comment on column PLATFORM_PAY_WAY.VERSION is '版本号'
/

comment on column PLATFORM_PAY_WAY.DELETED is '删除标记'
/

comment on column PLATFORM_PAY_WAY.CHANNEL_TYPE is '支付渠道1:线上；2:线下'
/

comment on column PLATFORM_PAY_WAY.URL is '图片'
/

comment on column PLATFORM_PAY_WAY.STATUS is '状态 1:启用；2:关闭'
/

comment on column PLATFORM_PAY_WAY.FRONT_KEY is '前端验证key'
/

comment on column PLATFORM_PAY_WAY.BACK_KEY is '后端验证key'
/

comment on column PLATFORM_PAY_WAY.ID is '主键'
/

comment on column PLATFORM_PAY_WAY.CREATOR is '创建者'
/

comment on column PLATFORM_PAY_WAY.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_PAY_WAY.UPDATER is '更新者'
/

comment on column PLATFORM_PAY_WAY.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_PAY_WAY.PAY_NAME is '支付名称'
/

create table PLATFORM_LANGUAGE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    NAME        VARCHAR2(32) not null,
    STATUS      NUMBER(2)    not null,
    ID          VARCHAR2(30) not null
        constraint PK_PLATFORM_LANGUAGE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13)
)
/

comment on table PLATFORM_LANGUAGE is '平台设置语言'
/

comment on column PLATFORM_LANGUAGE.VERSION is '版本号'
/

comment on column PLATFORM_LANGUAGE.DELETED is '删除标记'
/

comment on column PLATFORM_LANGUAGE.NAME is '语言名称'
/

comment on column PLATFORM_LANGUAGE.STATUS is '状态 1:启用；2:关闭'
/

comment on column PLATFORM_LANGUAGE.ID is '主键'
/

comment on column PLATFORM_LANGUAGE.CREATOR is '创建者'
/

comment on column PLATFORM_LANGUAGE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_LANGUAGE.UPDATER is '更新者'
/

comment on column PLATFORM_LANGUAGE.UPDATE_TIME is '更新时间'
/

create table PLATFORM_WORK_ORDER
(
    VERSION         NUMBER,
    DELETED         NUMBER,
    ID              VARCHAR2(32) not null
        constraint PK_PLATFORM_WORK_ORDER
            primary key,
    CREATOR         VARCHAR2(30),
    CREATE_TIME     NUMBER(13),
    UPDATER         VARCHAR2(30),
    UPDATE_TIME     NUMBER(13),
    USER_ID         VARCHAR2(32) not null,
    WORK_ORDER_TYPE VARCHAR2(32) not null,
    STATUS          VARCHAR2(32),
    TENANT_ID       VARCHAR2(32)
)
/

comment on table PLATFORM_WORK_ORDER is '工单'
/

comment on column PLATFORM_WORK_ORDER.VERSION is '版本号'
/

comment on column PLATFORM_WORK_ORDER.DELETED is '删除标记'
/

comment on column PLATFORM_WORK_ORDER.ID is '主键'
/

comment on column PLATFORM_WORK_ORDER.CREATOR is '创建者'
/

comment on column PLATFORM_WORK_ORDER.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_WORK_ORDER.UPDATER is '更新者'
/

comment on column PLATFORM_WORK_ORDER.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_WORK_ORDER.USER_ID is '发起人用户id'
/

comment on column PLATFORM_WORK_ORDER.WORK_ORDER_TYPE is '工单类型'
/

comment on column PLATFORM_WORK_ORDER.STATUS is '工单状态：created-未处理；finished-已处理'
/

create table USERS
(
    VERSION           NUMBER,
    DELETED           NUMBER,
    STATUS            NUMBER(1) default 1 not null,
    ENTERPRISE_NUMBER VARCHAR2(32),
    CONTRACT_ID       VARCHAR2(32),
    MERCHANT_ID       VARCHAR2(32),
    PASSWORD          VARCHAR2(255),
    TENANT_ID         VARCHAR2(32),
    IDENTITY          NUMBER(2) default 1 not null,
    COUNTRY_CODE      VARCHAR2(10)        not null,
    PHONE             VARCHAR2(20)        not null,
    ACCOUNT           VARCHAR2(20),
    UPDATE_TIME       NUMBER(13),
    UPDATER           VARCHAR2(30),
    CREATE_TIME       NUMBER(13),
    CREATOR           VARCHAR2(30),
    ID                VARCHAR2(30)        not null
        constraint PK_USERS
            primary key,
    NICKNAME          VARCHAR2(20),
    EMAIL             VARCHAR2(32),
    IS_TEMPORARY      NUMBER(1) default 0 not null
)
/

comment on table USERS is '用户'
/

comment on column USERS.VERSION is '版本号'
/

comment on column USERS.DELETED is '删除标记'
/

comment on column USERS.STATUS is '状态(1:正常,2:停用)'
/

comment on column USERS.ENTERPRISE_NUMBER is '企业编号'
/

comment on column USERS.CONTRACT_ID is '合同id'
/

comment on column USERS.MERCHANT_ID is '商户id'
/

comment on column USERS.PASSWORD is '密码'
/

comment on column USERS.TENANT_ID is '租户'
/

comment on column USERS.IDENTITY is '用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)'
/

comment on column USERS.COUNTRY_CODE is '国家代码'
/

comment on column USERS.PHONE is '手机号'
/

comment on column USERS.ACCOUNT is '账号'
/

comment on column USERS.UPDATE_TIME is '更新时间'
/

comment on column USERS.UPDATER is '更新者'
/

comment on column USERS.CREATE_TIME is '创建时间'
/

comment on column USERS.CREATOR is '创建者'
/

comment on column USERS.ID is '主键'
/

comment on column USERS.NICKNAME is '昵称'
/

comment on column USERS.IS_TEMPORARY is '是否为临时用户(0:否,1:是)'
/

create table PLATFORM_WORK_ORDER_CONTENT
(
    VERSION       NUMBER,
    DELETED       NUMBER,
    SEND_ID       VARCHAR2(32)   not null,
    RECEIVER_ID   VARCHAR2(32)   not null,
    ID            VARCHAR2(30)   not null
        constraint PK_PLATFORM_WORK_ORDER_CONTENT
            primary key,
    CREATOR       VARCHAR2(30),
    CREATE_TIME   NUMBER(13),
    UPDATER       VARCHAR2(30),
    UPDATE_TIME   NUMBER(13),
    WORK_ORDER_ID VARCHAR2(32)   not null,
    CONTENT       VARCHAR2(2048) not null
)
/

comment on table PLATFORM_WORK_ORDER_CONTENT is '工单内容'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.VERSION is '版本号'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.DELETED is '删除标记'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.SEND_ID is '发送者id'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.RECEIVER_ID is '接收者id'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.ID is '主键'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.CREATOR is '创建者'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.UPDATER is '更新者'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.WORK_ORDER_ID is '工单id'
/

comment on column PLATFORM_WORK_ORDER_CONTENT.CONTENT is '会话内容'
/

create table PLATFORM_COMPLAINTS
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    USER_NAME   VARCHAR2(32)   not null,
    USER_EMAIL  VARCHAR2(32)   not null,
    CONTENT     VARCHAR2(2000) not null,
    PHONE       VARCHAR2(32)   not null,
    USER_ID     VARCHAR2(32)   not null,
    ID          VARCHAR2(30)   not null
        constraint PK_PLATFORM_COMPLAINTS
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13)
)
/

comment on table PLATFORM_COMPLAINTS is '平台投诉建议'
/

comment on column PLATFORM_COMPLAINTS.VERSION is '版本号'
/

comment on column PLATFORM_COMPLAINTS.DELETED is '删除标记'
/

comment on column PLATFORM_COMPLAINTS.USER_NAME is '用户名称'
/

comment on column PLATFORM_COMPLAINTS.USER_EMAIL is '用户邮件'
/

comment on column PLATFORM_COMPLAINTS.CONTENT is '投诉内容'
/

comment on column PLATFORM_COMPLAINTS.PHONE is '用户手机号'
/

comment on column PLATFORM_COMPLAINTS.USER_ID is '用户id'
/

comment on column PLATFORM_COMPLAINTS.ID is '主键'
/

comment on column PLATFORM_COMPLAINTS.CREATOR is '创建者'
/

comment on column PLATFORM_COMPLAINTS.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_COMPLAINTS.UPDATER is '更新者'
/

comment on column PLATFORM_COMPLAINTS.UPDATE_TIME is '更新时间'
/

create table PRODUCT_TYPE
(
    VERSION     NUMBER(2),
    DELETED     NUMBER(2),
    NAME        VARCHAR2(32)   not null,
    PICTURE     VARCHAR2(2000) not null,
    PARENT_ID   VARCHAR2(32),
    LEVELS      NUMBER(2)      not null,
    ID          VARCHAR2(30)   not null
        constraint PK_PRODUCT_TYPE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)   not null
)
/

comment on table PRODUCT_TYPE is '商品分类管理'
/

comment on column PRODUCT_TYPE.VERSION is '版本号'
/

comment on column PRODUCT_TYPE.DELETED is '删除标记'
/

comment on column PRODUCT_TYPE.NAME is '分类名称'
/

comment on column PRODUCT_TYPE.PICTURE is '图片'
/

comment on column PRODUCT_TYPE.PARENT_ID is '父级id'
/

comment on column PRODUCT_TYPE.LEVELS is '分类级别'
/

comment on column PRODUCT_TYPE.ID is '主键'
/

comment on column PRODUCT_TYPE.CREATOR is '创建者'
/

comment on column PRODUCT_TYPE.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_TYPE.UPDATER is '更新者'
/

comment on column PRODUCT_TYPE.UPDATE_TIME is '更新时间'
/

comment on column PRODUCT_TYPE.TENANT_ID is '租户id'
/

create table PLATFORM_SERVICE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    CONTENT     VARCHAR2(2000) not null,
    ID          VARCHAR2(30)   not null
        constraint PK_PLATFORM_SERVICE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32),
    TYPE        NUMBER         not null,
    TITLE       VARCHAR2(1000) not null
)
/

comment on table PLATFORM_SERVICE is '服务和帮助'
/

comment on column PLATFORM_SERVICE.VERSION is '版本号'
/

comment on column PLATFORM_SERVICE.DELETED is '删除标记'
/

comment on column PLATFORM_SERVICE.CONTENT is '内容'
/

comment on column PLATFORM_SERVICE.ID is '主键'
/

comment on column PLATFORM_SERVICE.CREATOR is '创建者'
/

comment on column PLATFORM_SERVICE.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_SERVICE.UPDATER is '更新者'
/

comment on column PLATFORM_SERVICE.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_SERVICE.TENANT_ID is '租户'
/

comment on column PLATFORM_SERVICE.TYPE is '类型:1-帮助；2-服务条款'
/

comment on column PLATFORM_SERVICE.TITLE is '标题'
/

create table PLATFORM_SUBSCRIBE
(
    VERSION        NUMBER,
    DELETED        NUMBER,
    SUBSCRIBE_TYPE VARCHAR2(16)  not null,
    PRICE          NUMBER(12, 2) not null,
    TYPE           NUMBER,
    MEMORY         NUMBER,
    ID             VARCHAR2(30)  not null
        constraint PK_PLATFORM_SUBSCRIBE
            primary key,
    CREATOR        VARCHAR2(30),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(30),
    UPDATE_TIME    NUMBER(13),
    MONTH          NUMBER
)
/

comment on table PLATFORM_SUBSCRIBE is '订阅基础信息'
/

comment on column PLATFORM_SUBSCRIBE.VERSION is '版本号'
/

comment on column PLATFORM_SUBSCRIBE.DELETED is '删除标记'
/

comment on column PLATFORM_SUBSCRIBE.SUBSCRIBE_TYPE is '订阅类型：cash-收银系统订阅；storage-存储空间订阅'
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

comment on column PLATFORM_SUBSCRIBE.MONTH is '月份'
/

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
    NAME                  VARCHAR2(64) not null,
    TAX_NUM               VARCHAR2(64) not null,
    ORDER_ID              VARCHAR2(32),
    USER_ID               VARCHAR2(32) not null,
    MONTH                 NUMBER,
    OVERDUE_DATE          NUMBER(13),
    MEMORY                NUMBER,
    TOTAL_PRICE           NUMBER(12),
    TYPE                  VARCHAR2(32),
    CHANNEL_TYPE          NUMBER(2),
    SUBSCRIBE_TYPE        VARCHAR2(32),
    START_TIME            VARCHAR2(32),
    END_TIME              VARCHAR2(32),
    CONTRACT_ID           VARCHAR2(32),
    TAX_RATE              NUMBER(8),
    SUB_STATUS            VARCHAR2(16),
    USABLE_MEMORY         NUMBER
)
/

comment on table PLATFORM_APPROVAL is '平台审批'
/

comment on column PLATFORM_APPROVAL.VERSION is '版本号'
/

comment on column PLATFORM_APPROVAL.DELETED is '删除标记'
/

comment on column PLATFORM_APPROVAL.STATUS is '审批状态'
/

comment on column PLATFORM_APPROVAL.APPROVAL_ID is '审批编号'
/

comment on column PLATFORM_APPROVAL.ID is '主键'
/

comment on column PLATFORM_APPROVAL.CREATOR is '创建者'
/

comment on column PLATFORM_APPROVAL.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_APPROVAL.UPDATER is '更新者'
/

comment on column PLATFORM_APPROVAL.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_APPROVAL.TENANT_ID is '租户'
/

comment on column PLATFORM_APPROVAL.MERCHANT_SUBSCRIBE_ID is '租户订阅id'
/

comment on column PLATFORM_APPROVAL.TAX_NUM is '税务号(企业编号)'
/

comment on column PLATFORM_APPROVAL.ORDER_ID is '订单id'
/

comment on column PLATFORM_APPROVAL.USER_ID is '用户id'
/

comment on column PLATFORM_APPROVAL.MONTH is '购买月份默认12月'
/

comment on column PLATFORM_APPROVAL.OVERDUE_DATE is '过期时间'
/

comment on column PLATFORM_APPROVAL.MEMORY is '购买总存储'
/

comment on column PLATFORM_APPROVAL.TOTAL_PRICE is '购买总金额'
/

comment on column PLATFORM_APPROVAL.TYPE is '订阅类型:开通商户；增加存储'
/

comment on column PLATFORM_APPROVAL.CHANNEL_TYPE is '支付方式 0:线上,1:线下支付'
/

comment on column PLATFORM_APPROVAL.SUBSCRIBE_TYPE is '订阅类型：cash-收银系统订阅；storage-存储空间订阅'
/

comment on column PLATFORM_APPROVAL.START_TIME is '开始时间'
/

comment on column PLATFORM_APPROVAL.END_TIME is '使用结束时间'
/

comment on column PLATFORM_APPROVAL.CONTRACT_ID is '合同id'
/

comment on column PLATFORM_APPROVAL.TAX_RATE is '税率'
/

comment on column PLATFORM_APPROVAL.SUB_STATUS is '订阅状态 sub:订阅；over:到期'
/

comment on column PLATFORM_APPROVAL.USABLE_MEMORY is '可用存储'
/

create index PLATFORM_APPROVAL_TENANT_ID_APPROVAL_ID_INDEX
    on PLATFORM_APPROVAL (TENANT_ID, APPROVAL_ID)
/

create table MERCHANT_DISTRIBUTION
(
    VERSION                        NUMBER,
    DELETED                        NUMBER,
    PER_KILOMETER                  NUMBER(10, 2),
    EXCESS_FEES                    NUMBER(10, 2),
    IS_THIRD_PARTIES               VARCHAR2(2),
    ADDRESS_TYPE                   VARCHAR2(16)  not null,
    ADDRESS                        VARCHAR2(128) not null,
    MINIMUM_DELIVERY_AMOUNT        NUMBER(4)     not null,
    BEYOND_KILOMETER_NOT_DELIVERED NUMBER        not null,
    ID                             VARCHAR2(32)  not null
        constraint PK_MERCHANT_DISTRIBUTION
            primary key,
    CREATOR                        VARCHAR2(30),
    CREATE_TIME                    NUMBER(13),
    UPDATER                        VARCHAR2(30),
    UPDATE_TIME                    NUMBER(13),
    TENANT_ID                      VARCHAR2(32)  not null,
    TYPE                           VARCHAR2(16),
    STAGE                          NUMBER(24, 2),
    GLOBAL_THIRD_PARTIES           VARCHAR2(16),
    KILOMETERS_EXCEEDED            NUMBER
)
/

comment on table MERCHANT_DISTRIBUTION is '商户配送'
/

comment on column MERCHANT_DISTRIBUTION.VERSION is '版本号'
/

comment on column MERCHANT_DISTRIBUTION.DELETED is '删除标记'
/

comment on column MERCHANT_DISTRIBUTION.PER_KILOMETER is '每公里收费(收费配送)'
/

comment on column MERCHANT_DISTRIBUTION.EXCESS_FEES is '超过公里费用(第三方配送)'
/

comment on column MERCHANT_DISTRIBUTION.IS_THIRD_PARTIES is '是否开启第三方配送 Y-是；N-否'
/

comment on column MERCHANT_DISTRIBUTION.ADDRESS_TYPE is '地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址'
/

comment on column MERCHANT_DISTRIBUTION.ADDRESS is '地址'
/

comment on column MERCHANT_DISTRIBUTION.MINIMUM_DELIVERY_AMOUNT is '最低配送金额'
/

comment on column MERCHANT_DISTRIBUTION.BEYOND_KILOMETER_NOT_DELIVERED is '超出公里不配送'
/

comment on column MERCHANT_DISTRIBUTION.ID is '主键'
/

comment on column MERCHANT_DISTRIBUTION.CREATOR is '创建者'
/

comment on column MERCHANT_DISTRIBUTION.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_DISTRIBUTION.UPDATER is '更新者'
/

comment on column MERCHANT_DISTRIBUTION.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_DISTRIBUTION.TENANT_ID is '租户'
/

comment on column MERCHANT_DISTRIBUTION.TYPE is '配送类型 free-免费配送；charge-收费配送'
/

comment on column MERCHANT_DISTRIBUTION.STAGE is '0-2公里收费(收费配送)'
/

comment on column MERCHANT_DISTRIBUTION.GLOBAL_THIRD_PARTIES is '第三方配送类型 global-全局第三方；exceed-超出公里'
/

comment on column MERCHANT_DISTRIBUTION.KILOMETERS_EXCEEDED is '超出公里数(第三方配送)'
/

create table MERCHANT_FREIGHT
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    TYPE        NUMBER       not null,
    KILOGRAMS   NUMBER(6, 2) not null,
    ID          VARCHAR2(32) not null
        constraint PK_MERCHANT_FREIGHT
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32),
    EXPENSES    NUMBER(6, 2) not null,
    STATUS      VARCHAR2(2)  not null
)
/

comment on table MERCHANT_FREIGHT is '货运配送费用'
/

comment on column MERCHANT_FREIGHT.VERSION is '版本号'
/

comment on column MERCHANT_FREIGHT.DELETED is '删除标记'
/

comment on column MERCHANT_FREIGHT.TYPE is '货运类型: 1-cm3；2-ft3；3-m3；4-托盘；5-20GP；6-40GP；7-40HQ'
/

comment on column MERCHANT_FREIGHT.KILOGRAMS is '每公斤费用'
/

comment on column MERCHANT_FREIGHT.ID is '主键'
/

comment on column MERCHANT_FREIGHT.CREATOR is '创建者'
/

comment on column MERCHANT_FREIGHT.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_FREIGHT.UPDATER is '更新者'
/

comment on column MERCHANT_FREIGHT.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_FREIGHT.TENANT_ID is '租户'
/

comment on column MERCHANT_FREIGHT.EXPENSES is '每体积费用'
/

comment on column MERCHANT_FREIGHT.STATUS is '是否勾选,Y:是;N:否;默认否'
/

create index MERCHANT_FREIGHT_TENANT_ID_INDEX
    on MERCHANT_FREIGHT (TENANT_ID)
/

create table PRODUCT_BASE_INFO
(
    VERSION           NUMBER,
    DELETED           NUMBER,
    NAME              VARCHAR2(255) not null,
    PRICE             NUMBER(8, 2)  not null,
    INVENTORY         NUMBER(8)     not null,
    LEVEL_ONE_ID      VARCHAR2(32)  not null,
    LEVEL_TWO_ID      VARCHAR2(32),
    LEVEL_THREE_ID    VARCHAR2(32),
    TAX_ID            VARCHAR2(32),
    BRIEFLY           VARCHAR2(2000),
    PICTURE_URL       VARCHAR2(10000),
    VIDEO_URL         VARCHAR2(2000),
    WEIGHT            NUMBER(6, 2),
    LENGTH            NUMBER(6, 2)  not null,
    IS_DISTRIBUTION   VARCHAR2(32),
    IS_SERVE          VARCHAR2(32),
    DELIVERY_FEE      NUMBER(8, 2),
    PRE_SERVE_FEE     NUMBER(4, 2),
    SERVE_FEE         NUMBER(8, 2),
    ID                VARCHAR2(30)  not null
        constraint PK_PRODUCT_BASE_INFO
            primary key,
    CREATOR           VARCHAR2(30),
    CREATE_TIME       NUMBER(13),
    UPDATER           VARCHAR2(30),
    UPDATE_TIME       NUMBER(13),
    TENANT_ID         VARCHAR2(32)  not null,
    WIDTH             NUMBER(6, 2)  not null,
    HEIGHT            NUMBER(6, 2)  not null,
    PER_DELIVERY_FEE  NUMBER(4, 2),
    TYPE              VARCHAR2(16) default null,
    IS_ENABLE         VARCHAR2(16) default null,
    WAREHOUSE_SEAT_ID VARCHAR2(32),
    WAREHOUSE_ID      VARCHAR2(32)
)
/

comment on table PRODUCT_BASE_INFO is '商品基础信息'
/

comment on column PRODUCT_BASE_INFO.VERSION is '版本号'
/

comment on column PRODUCT_BASE_INFO.DELETED is '删除标记'
/

comment on column PRODUCT_BASE_INFO.NAME is '商品名称'
/

comment on column PRODUCT_BASE_INFO.PRICE is '商品价格'
/

comment on column PRODUCT_BASE_INFO.INVENTORY is '库存'
/

comment on column PRODUCT_BASE_INFO.LEVEL_ONE_ID is '一级分类id'
/

comment on column PRODUCT_BASE_INFO.LEVEL_TWO_ID is '二级分类id'
/

comment on column PRODUCT_BASE_INFO.LEVEL_THREE_ID is '三级分类id'
/

comment on column PRODUCT_BASE_INFO.TAX_ID is '商品税率id'
/

comment on column PRODUCT_BASE_INFO.BRIEFLY is '商品简介'
/

comment on column PRODUCT_BASE_INFO.PICTURE_URL is '商品图片1'
/

comment on column PRODUCT_BASE_INFO.VIDEO_URL is '视频'
/

comment on column PRODUCT_BASE_INFO.WEIGHT is '重量'
/

comment on column PRODUCT_BASE_INFO.LENGTH is '长度'
/

comment on column PRODUCT_BASE_INFO.IS_DISTRIBUTION is '是否勾选配送费：distribution-是；not_distribution-否'
/

comment on column PRODUCT_BASE_INFO.IS_SERVE is '是否勾选服务费：serve-是；not_serve-否'
/

comment on column PRODUCT_BASE_INFO.DELIVERY_FEE is '配送费金额'
/

comment on column PRODUCT_BASE_INFO.PRE_SERVE_FEE is '服务费百分比'
/

comment on column PRODUCT_BASE_INFO.SERVE_FEE is '服务发金额'
/

comment on column PRODUCT_BASE_INFO.ID is '主键'
/

comment on column PRODUCT_BASE_INFO.CREATOR is '创建者'
/

comment on column PRODUCT_BASE_INFO.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_BASE_INFO.UPDATER is '更新者'
/

comment on column PRODUCT_BASE_INFO.UPDATE_TIME is '更新时间'
/

comment on column PRODUCT_BASE_INFO.TENANT_ID is '租户'
/

comment on column PRODUCT_BASE_INFO.WIDTH is '宽度'
/

comment on column PRODUCT_BASE_INFO.HEIGHT is '高度'
/

comment on column PRODUCT_BASE_INFO.PER_DELIVERY_FEE is '配送费百分比'
/

comment on column PRODUCT_BASE_INFO.TYPE is '商品类型：common-普通商品;preferential-优惠商品;auction-拍卖商品;wholesale-批发商品 默认值=common'
/

comment on column PRODUCT_BASE_INFO.IS_ENABLE is '商品上下架 enable-上架;not_enable-下架 默认值=enable'
/

comment on column PRODUCT_BASE_INFO.WAREHOUSE_SEAT_ID is '位子id'
/

comment on column PRODUCT_BASE_INFO.WAREHOUSE_ID is '仓库id'
/

create table PRODUCT_PREFERENTIAL
(
    VERSION            NUMBER,
    DELETED            NUMBER,
    PRODUCT_ID         VARCHAR2(32) not null,
    ADVERTISING_WORDS  VARCHAR2(1000),
    START_TIME         VARCHAR2(32) not null,
    END_TIME           VARCHAR2(32) not null,
    ID                 VARCHAR2(30) not null
        constraint PK_PRODUCT_PREFERENTIAL
            primary key,
    CREATOR            VARCHAR2(30),
    CREATE_TIME        NUMBER(13),
    UPDATER            VARCHAR2(30),
    UPDATE_TIME        NUMBER(13),
    TENANT_ID          VARCHAR2(32) not null,
    PREFERENTIAL_PRICE NUMBER(6, 2) not null
)
/

comment on table PRODUCT_PREFERENTIAL is '优惠商品'
/

comment on column PRODUCT_PREFERENTIAL.START_TIME is '开始时间'
/

comment on column PRODUCT_PREFERENTIAL.END_TIME is '结束时间'
/

create table PRODUCT_AUCTION
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    FARES       NUMBER(6, 2) not null,
    FIXED_PRICE NUMBER(6, 2),
    START_TIME  VARCHAR2(20),
    END_TIME    VARCHAR2(20),
    PRODUCT_ID  VARCHAR2(32) not null,
    ID          VARCHAR2(30) not null
        constraint PK_PRODUCT_AUCTION
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)
)
/

comment on table PRODUCT_AUCTION is '拍卖商品'
/

comment on column PRODUCT_AUCTION.VERSION is '版本号'
/

comment on column PRODUCT_AUCTION.DELETED is '删除标记'
/

comment on column PRODUCT_AUCTION.FARES is '每次加价'
/

comment on column PRODUCT_AUCTION.FIXED_PRICE is '一口价'
/

comment on column PRODUCT_AUCTION.START_TIME is '开始时间'
/

comment on column PRODUCT_AUCTION.END_TIME is '结束时间'
/

comment on column PRODUCT_AUCTION.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_AUCTION.ID is '主键'
/

comment on column PRODUCT_AUCTION.CREATOR is '创建者'
/

comment on column PRODUCT_AUCTION.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_AUCTION.UPDATER is '更新者'
/

comment on column PRODUCT_AUCTION.UPDATE_TIME is '更新时间'
/

comment on column PRODUCT_AUCTION.TENANT_ID is '租户'
/

create table PRODUCT_WHOLESALE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    NUM_ONE     NUMBER(10),
    NUM_TWO     NUMBER(10),
    PRICE_THREE NUMBER(10, 2),
    NUM_THREE   NUMBER(10),
    PRODUCT_ID  VARCHAR2(32) not null,
    ID          VARCHAR2(30) not null
        constraint PK_PRODUCT_WHOLESALE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32),
    PRICE_ONE   NUMBER(10, 2),
    PRICE_TWO   NUMBER(10, 2)
)
/

comment on table PRODUCT_WHOLESALE is '批发商品'
/

comment on column PRODUCT_WHOLESALE.VERSION is '版本号'
/

comment on column PRODUCT_WHOLESALE.DELETED is '删除标记'
/

comment on column PRODUCT_WHOLESALE.NUM_ONE is '数量1'
/

comment on column PRODUCT_WHOLESALE.NUM_TWO is '数量2'
/

comment on column PRODUCT_WHOLESALE.PRICE_THREE is '单价3'
/

comment on column PRODUCT_WHOLESALE.NUM_THREE is '数量3'
/

comment on column PRODUCT_WHOLESALE.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_WHOLESALE.ID is '主键'
/

comment on column PRODUCT_WHOLESALE.CREATOR is '创建者'
/

comment on column PRODUCT_WHOLESALE.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_WHOLESALE.UPDATER is '更新者'
/

comment on column PRODUCT_WHOLESALE.UPDATE_TIME is '更新时间'
/

comment on column PRODUCT_WHOLESALE.TENANT_ID is '租户'
/

comment on column PRODUCT_WHOLESALE.PRICE_ONE is '单价1'
/

comment on column PRODUCT_WHOLESALE.PRICE_TWO is '单价2'
/

create table PLATFORM_INFO
(
    VERSION        NUMBER,
    DELETED        NUMBER,
    PLATFORM_NAME  VARCHAR2(32)  not null,
    PLATFORM_PHONE VARCHAR2(32)  not null,
    TAX_NUM        VARCHAR2(32)  not null,
    ADDRESS        VARCHAR2(128) not null,
    EMAIL          VARCHAR2(32)  not null,
    LANGUAGE       VARCHAR2(16)  not null,
    COUNTRY        VARCHAR2(32)  not null,
    MOBILE         VARCHAR2(32)  not null,
    ID             VARCHAR2(32)  not null
        constraint PK_PLATFORM_INFO
            primary key,
    CREATOR        VARCHAR2(30),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(30),
    UPDATE_TIME    NUMBER(13),
    CURRENCY       VARCHAR2(32)
)
/

comment on table PLATFORM_INFO is '平台信息'
/

comment on column PLATFORM_INFO.VERSION is '版本号'
/

comment on column PLATFORM_INFO.DELETED is '删除标记'
/

comment on column PLATFORM_INFO.PLATFORM_NAME is '平台名称'
/

comment on column PLATFORM_INFO.PLATFORM_PHONE is '平台联系电话'
/

comment on column PLATFORM_INFO.TAX_NUM is '税务号'
/

comment on column PLATFORM_INFO.ADDRESS is '平台地址'
/

comment on column PLATFORM_INFO.EMAIL is '平台邮箱'
/

comment on column PLATFORM_INFO.LANGUAGE is '后台语言'
/

comment on column PLATFORM_INFO.COUNTRY is '国家'
/

comment on column PLATFORM_INFO.MOBILE is '注册手机号'
/

comment on column PLATFORM_INFO.ID is '主键'
/

comment on column PLATFORM_INFO.CREATOR is '创建者'
/

comment on column PLATFORM_INFO.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_INFO.UPDATER is '更新者'
/

comment on column PLATFORM_INFO.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_INFO.CURRENCY is '平台币种'
/

create table MERCHANT_FILE_UPLOAD
(
    VERSION          NUMBER,
    DELETED          NUMBER,
    BUSINESS_LICENSE VARCHAR2(255),
    ID               VARCHAR2(32) not null
        constraint PK_MERCHANT_FILE_UPLOAD
            primary key,
    CREATOR          VARCHAR2(30),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(30),
    UPDATE_TIME      NUMBER(13),
    TENANT_ID        VARCHAR2(32),
    BANK_RIB         VARCHAR2(255),
    LOGO             VARCHAR2(256)
)
/

comment on column MERCHANT_FILE_UPLOAD.BUSINESS_LICENSE is '营业执照'
/

comment on column MERCHANT_FILE_UPLOAD.BANK_RIB is '银行rib'
/

comment on column MERCHANT_FILE_UPLOAD.LOGO is '商户logo'
/

create index MERCHANT_FILE_UPLOAD_TENANT_ID_INDEX
    on MERCHANT_FILE_UPLOAD (TENANT_ID)
/

create table MERCHANT_HOLIDAY
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(32)   not null
        constraint PK_MERCHANT_HOLIDAY
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32),
    CONTENTS    VARCHAR2(3000) not null
)
/

comment on table MERCHANT_HOLIDAY is '商户休假'
/

comment on column MERCHANT_HOLIDAY.VERSION is '版本号'
/

comment on column MERCHANT_HOLIDAY.DELETED is '删除标记'
/

comment on column MERCHANT_HOLIDAY.ID is '主键'
/

comment on column MERCHANT_HOLIDAY.CREATOR is '创建者'
/

comment on column MERCHANT_HOLIDAY.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_HOLIDAY.UPDATER is '更新者'
/

comment on column MERCHANT_HOLIDAY.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_HOLIDAY.TENANT_ID is '租户'
/

comment on column MERCHANT_HOLIDAY.CONTENTS is '休假描述'
/

create table MERCHANT_ADVERTISED
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    CONTENTS    VARCHAR2(3000),
    ID          VARCHAR2(32) not null
        constraint PK_MERCHANT_ADVERTISED
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)
)
/

comment on table MERCHANT_ADVERTISED is '商户广告'
/

comment on column MERCHANT_ADVERTISED.VERSION is '版本号'
/

comment on column MERCHANT_ADVERTISED.DELETED is '删除标记'
/

comment on column MERCHANT_ADVERTISED.CONTENTS is '广告描述'
/

comment on column MERCHANT_ADVERTISED.ID is '主键'
/

comment on column MERCHANT_ADVERTISED.CREATOR is '创建者'
/

comment on column MERCHANT_ADVERTISED.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_ADVERTISED.UPDATER is '更新者'
/

comment on column MERCHANT_ADVERTISED.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_ADVERTISED.TENANT_ID is '租户'
/

create table PAYMENT_ORDERS
(
    ORDER_NO         VARCHAR2(32)                      not null
        constraint PK_PAYMENT_ORDERS
            primary key,
    MERCHANT_ID      VARCHAR2(32)                      not null,
    CHANNEL          VARCHAR2(20)                      not null,
    AMOUNT           NUMBER(19, 2)                     not null,
    SUBJECT          VARCHAR2(256),
    BODY             VARCHAR2(1024),
    STATUS           VARCHAR2(20)                      not null,
    PLATFORM         VARCHAR2(20)                      not null,
    RETURN_URL       VARCHAR2(512),
    NOTIFY_URL       VARCHAR2(512),
    CHANNEL_TRADE_NO VARCHAR2(64),
    EXPIRE_TIME      TIMESTAMP(6),
    CREATE_TIME      TIMESTAMP(6) default SYSTIMESTAMP not null,
    PAY_TIME         TIMESTAMP(6)
)
/

comment on table PAYMENT_ORDERS is '支付订单表'
/

comment on column PAYMENT_ORDERS.ORDER_NO is '订单号'
/

comment on column PAYMENT_ORDERS.MERCHANT_ID is '商户ID'
/

comment on column PAYMENT_ORDERS.CHANNEL is '支付渠道'
/

comment on column PAYMENT_ORDERS.AMOUNT is '支付金额'
/

comment on column PAYMENT_ORDERS.SUBJECT is '订单标题'
/

comment on column PAYMENT_ORDERS.BODY is '订单内容'
/

comment on column PAYMENT_ORDERS.STATUS is '订单状态'
/

comment on column PAYMENT_ORDERS.PLATFORM is '支付平台'
/

comment on column PAYMENT_ORDERS.RETURN_URL is '同步回调地址'
/

comment on column PAYMENT_ORDERS.NOTIFY_URL is '异步通知地址'
/

comment on column PAYMENT_ORDERS.CHANNEL_TRADE_NO is '渠道交易号'
/

comment on column PAYMENT_ORDERS.EXPIRE_TIME is '过期时间'
/

comment on column PAYMENT_ORDERS.CREATE_TIME is '创建时间'
/

comment on column PAYMENT_ORDERS.PAY_TIME is '支付时间'
/

create index IDX_PAYMENT_ORDERS_MERCHANT
    on PAYMENT_ORDERS (MERCHANT_ID)
/

create index IDX_PAYMENT_ORDERS_CHANNEL_TRADE
    on PAYMENT_ORDERS (CHANNEL_TRADE_NO)
/

create index IDX_PAYMENT_ORDERS_CREATE_TIME
    on PAYMENT_ORDERS (CREATE_TIME)
/

create table PAYMENT_NOTIFY_RECORDS
(
    NOTIFY_ID       VARCHAR2(32)                      not null
        constraint PK_PAYMENT_NOTIFY_RECORDS
            primary key,
    ORDER_NO        VARCHAR2(32)                      not null,
    MERCHANT_ID     VARCHAR2(32)                      not null,
    NOTIFY_PARAMS   CLOB,
    STATUS          VARCHAR2(20)                      not null,
    NOTIFY_RESULT   VARCHAR2(512),
    RETRY_COUNT     NUMBER(3)    default 0            not null,
    NEXT_RETRY_TIME TIMESTAMP(6),
    CREATE_TIME     TIMESTAMP(6) default SYSTIMESTAMP not null,
    UPDATE_TIME     TIMESTAMP(6) default SYSTIMESTAMP not null
)
/

comment on table PAYMENT_NOTIFY_RECORDS is '支付通知记录表'
/

comment on column PAYMENT_NOTIFY_RECORDS.NOTIFY_ID is '通知ID'
/

comment on column PAYMENT_NOTIFY_RECORDS.ORDER_NO is '订单号'
/

comment on column PAYMENT_NOTIFY_RECORDS.MERCHANT_ID is '商户ID'
/

comment on column PAYMENT_NOTIFY_RECORDS.NOTIFY_PARAMS is '通知参数'
/

comment on column PAYMENT_NOTIFY_RECORDS.STATUS is '通知状态'
/

comment on column PAYMENT_NOTIFY_RECORDS.NOTIFY_RESULT is '通知结果'
/

comment on column PAYMENT_NOTIFY_RECORDS.RETRY_COUNT is '重试次数'
/

comment on column PAYMENT_NOTIFY_RECORDS.NEXT_RETRY_TIME is '下次重试时间'
/

comment on column PAYMENT_NOTIFY_RECORDS.CREATE_TIME is '创建时间'
/

comment on column PAYMENT_NOTIFY_RECORDS.UPDATE_TIME is '更新时间'
/

create index IDX_NOTIFY_RECORDS_ORDER
    on PAYMENT_NOTIFY_RECORDS (ORDER_NO)
/

create index IDX_NOTIFY_RECORDS_MERCHANT
    on PAYMENT_NOTIFY_RECORDS (MERCHANT_ID)
/

create index IDX_NOTIFY_RECORDS_RETRY
    on PAYMENT_NOTIFY_RECORDS (RETRY_COUNT, NEXT_RETRY_TIME)
/

create or replace trigger TRG_NOTIFY_RECORDS_UPD
    before update
    on PAYMENT_NOTIFY_RECORDS
    for each row
BEGIN
    :NEW.update_time := SYSTIMESTAMP;
END;
/

create table PLATFORM_BANNER
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    PIC_URL     VARCHAR2(10000) not null,
    ID          VARCHAR2(30)    not null
        constraint PK_PLATFORM_BANNER
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    PIC_DESC    CHAR(128),
    JUMP_URL    CHAR(255),
    TENANT_ID   VARCHAR2(32)
)
/

comment on table PLATFORM_BANNER is '轮播图'
/

comment on column PLATFORM_BANNER.VERSION is '版本号'
/

comment on column PLATFORM_BANNER.DELETED is '删除标记'
/

comment on column PLATFORM_BANNER.PIC_URL is '图片链接'
/

comment on column PLATFORM_BANNER.ID is '主键'
/

comment on column PLATFORM_BANNER.CREATOR is '创建者'
/

comment on column PLATFORM_BANNER.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_BANNER.UPDATER is '更新者'
/

comment on column PLATFORM_BANNER.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_BANNER.PIC_DESC is '图片描述'
/

comment on column PLATFORM_BANNER.JUMP_URL is '跳转链接'
/

comment on column PLATFORM_BANNER.TENANT_ID is '租户id'
/

create table MERCHANT_MARKING
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    NAME        VARCHAR2(64) not null,
    ID          VARCHAR2(30) not null
        constraint PK_MERCHANT_MARKING
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)
)
/

create index MERCHANT_MARKING_TENANT_ID_INDEX
    on MERCHANT_MARKING (TENANT_ID)
/

create table MERCHANT_SPECIFICATION
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(32)  not null,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)  not null,
    NAME        VARCHAR2(128) not null,
    TYPE        VARCHAR2(16)  not null
)
/

comment on table MERCHANT_SPECIFICATION is '商品主规格'
/

comment on column MERCHANT_SPECIFICATION.VERSION is '版本号'
/

comment on column MERCHANT_SPECIFICATION.DELETED is '删除标记'
/

comment on column MERCHANT_SPECIFICATION.ID is '主键'
/

comment on column MERCHANT_SPECIFICATION.CREATOR is '创建者'
/

comment on column MERCHANT_SPECIFICATION.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_SPECIFICATION.UPDATER is '更新者'
/

comment on column MERCHANT_SPECIFICATION.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_SPECIFICATION.TENANT_ID is '租户'
/

comment on column MERCHANT_SPECIFICATION.NAME is '主规格名称'
/

comment on column MERCHANT_SPECIFICATION.TYPE is '规格类型(单选：radio，多选：choices)'
/

create unique index PK_MERCHANT_SPECIFICATION
    on MERCHANT_SPECIFICATION (ID)
/

create table MERCHANT_SUB_SPECIFICATION
(
    VERSION          NUMBER,
    DELETED          NUMBER,
    SUB_NAME         VARCHAR2(32)  not null,
    SUB_NUM          NUMBER(12, 2) not null,
    SUB_PRICE        NUMBER(12, 2) not null,
    SUB_PICTURE      VARCHAR2(128),
    SPECIFICATION_ID VARCHAR2(32)  not null,
    ID               VARCHAR2(32)  not null
        constraint PK_MERCHANT_SUB_SPECIFICATION
            primary key,
    CREATOR          VARCHAR2(32),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(32),
    UPDATE_TIME      NUMBER(13),
    TENANT_ID        VARCHAR2(32)  not null
)
/

comment on table MERCHANT_SUB_SPECIFICATION is '商品子规格'
/

comment on column MERCHANT_SUB_SPECIFICATION.VERSION is '版本号'
/

comment on column MERCHANT_SUB_SPECIFICATION.DELETED is '删除标记'
/

comment on column MERCHANT_SUB_SPECIFICATION.SUB_NAME is '子规格名称'
/

comment on column MERCHANT_SUB_SPECIFICATION.SUB_NUM is '子规格库存'
/

comment on column MERCHANT_SUB_SPECIFICATION.SUB_PRICE is '子规格价格'
/

comment on column MERCHANT_SUB_SPECIFICATION.SUB_PICTURE is '子规格图片'
/

comment on column MERCHANT_SUB_SPECIFICATION.SPECIFICATION_ID is '主规格id'
/

comment on column MERCHANT_SUB_SPECIFICATION.ID is '主键'
/

comment on column MERCHANT_SUB_SPECIFICATION.CREATOR is '创建者'
/

comment on column MERCHANT_SUB_SPECIFICATION.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_SUB_SPECIFICATION.UPDATER is '更新者'
/

comment on column MERCHANT_SUB_SPECIFICATION.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_SUB_SPECIFICATION.TENANT_ID is '租户id'
/

create table MERCHANT_BANNER
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    PIC_URL     VARCHAR2(10000) not null,
    PIC_DESC    CHAR(128),
    JUMP_URL    CHAR(255),
    ID          VARCHAR2(30)    not null
        constraint PK_MERCHANT_BANNER
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)    not null
)
/

comment on table MERCHANT_BANNER is '轮播图'
/

comment on column MERCHANT_BANNER.VERSION is '版本号'
/

comment on column MERCHANT_BANNER.DELETED is '删除标记'
/

comment on column MERCHANT_BANNER.PIC_URL is '图片链接'
/

comment on column MERCHANT_BANNER.PIC_DESC is '图片描述'
/

comment on column MERCHANT_BANNER.JUMP_URL is '跳转链接'
/

comment on column MERCHANT_BANNER.ID is '主键'
/

comment on column MERCHANT_BANNER.CREATOR is '创建者'
/

comment on column MERCHANT_BANNER.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_BANNER.UPDATER is '更新者'
/

comment on column MERCHANT_BANNER.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_BANNER.TENANT_ID is '租户'
/

create index MERCHANT_BANNER_TENANT_ID_INDEX
    on MERCHANT_BANNER (TENANT_ID)
/

create table PRODUCT_KEYWORDS
(
    VERSION      NUMBER,
    DELETED      NUMBER,
    PRODUCT_ID   VARCHAR2(32) not null,
    KEY_WORDS_ID VARCHAR2(32) not null,
    ID           VARCHAR2(30) not null
        constraint PK_PRODUCT_KEYWORDS
            primary key,
    CREATOR      VARCHAR2(30),
    CREATE_TIME  NUMBER(13),
    UPDATER      VARCHAR2(30),
    UPDATE_TIME  NUMBER(13)
)
/

comment on table PRODUCT_KEYWORDS is '商品关键字'
/

comment on column PRODUCT_KEYWORDS.VERSION is '版本号'
/

comment on column PRODUCT_KEYWORDS.DELETED is '删除标记'
/

comment on column PRODUCT_KEYWORDS.PRODUCT_ID is '商品id`'
/

comment on column PRODUCT_KEYWORDS.KEY_WORDS_ID is '关键字id'
/

comment on column PRODUCT_KEYWORDS.ID is '主键'
/

comment on column PRODUCT_KEYWORDS.CREATOR is '创建者'
/

comment on column PRODUCT_KEYWORDS.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_KEYWORDS.UPDATER is '更新者'
/

comment on column PRODUCT_KEYWORDS.UPDATE_TIME is '更新时间'
/

create table PRODUCT_MARKING
(
    PRODUCT_ID  VARCHAR2(32) not null,
    MARKING_ID  VARCHAR2(32) not null,
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(32) not null
        constraint PK_PRODUCT_MARKING
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)
)
/

comment on table PRODUCT_MARKING is '商品标注'
/

comment on column PRODUCT_MARKING.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_MARKING.MARKING_ID is '标注id'
/

comment on column PRODUCT_MARKING.VERSION is '版本号'
/

comment on column PRODUCT_MARKING.DELETED is '删除标记'
/

comment on column PRODUCT_MARKING.ID is '主键'
/

comment on column PRODUCT_MARKING.CREATOR is '创建者'
/

comment on column PRODUCT_MARKING.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_MARKING.UPDATER is '更新者'
/

comment on column PRODUCT_MARKING.UPDATE_TIME is '更新时间'
/

comment on column PRODUCT_MARKING.TENANT_ID is '租户'
/

create table PRODUCT_SPECIFICATION
(
    VERSION          NUMBER,
    DELETED          NUMBER,
    PRODUCT_ID       VARCHAR2(32) not null,
    SPECIFICATION_ID VARCHAR2(32) not null,
    ID               VARCHAR2(30) not null
        constraint PK_PRODUCT_SPECIFICATION
            primary key,
    CREATOR          VARCHAR2(30),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(30),
    UPDATE_TIME      NUMBER(13)
)
/

comment on table PRODUCT_SPECIFICATION is '商品规格'
/

comment on column PRODUCT_SPECIFICATION.VERSION is '版本号'
/

comment on column PRODUCT_SPECIFICATION.DELETED is '删除标记'
/

comment on column PRODUCT_SPECIFICATION.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_SPECIFICATION.SPECIFICATION_ID is '主规格id'
/

comment on column PRODUCT_SPECIFICATION.ID is '主键'
/

comment on column PRODUCT_SPECIFICATION.CREATOR is '创建者'
/

comment on column PRODUCT_SPECIFICATION.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_SPECIFICATION.UPDATER is '更新者'
/

comment on column PRODUCT_SPECIFICATION.UPDATE_TIME is '更新时间'
/

create table MERCHANT_WAREHOUSE
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    ID          VARCHAR2(30)  not null
        constraint PK_MERCHANT_WAREHOUSE
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(32)  not null,
    NAME        VARCHAR2(128) not null
)
/

comment on table MERCHANT_WAREHOUSE is '商户仓库主数据'
/

comment on column MERCHANT_WAREHOUSE.VERSION is '版本号'
/

comment on column MERCHANT_WAREHOUSE.DELETED is '删除标记'
/

comment on column MERCHANT_WAREHOUSE.ID is '主键'
/

comment on column MERCHANT_WAREHOUSE.CREATOR is '创建者'
/

comment on column MERCHANT_WAREHOUSE.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_WAREHOUSE.UPDATER is '更新者'
/

comment on column MERCHANT_WAREHOUSE.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_WAREHOUSE.TENANT_ID is '租户id'
/

comment on column MERCHANT_WAREHOUSE.NAME is '仓库名称'
/

create table MERCHANT_WAREHOUSE_SEAT
(
    VERSION      NUMBER,
    DELETED      NUMBER,
    WAREHOUSE_ID VARCHAR2(32)  not null,
    SEAT_NAME    VARCHAR2(128) not null,
    ID           VARCHAR2(30)  not null
        constraint PK_MERCHANT_WAREHOUSE_SEAT
            primary key,
    CREATOR      VARCHAR2(30),
    CREATE_TIME  NUMBER(13),
    UPDATER      VARCHAR2(30),
    UPDATE_TIME  NUMBER(13),
    TENANT_ID    VARCHAR2(32)  not null
)
/

comment on table MERCHANT_WAREHOUSE_SEAT is '商户仓库位子主数据'
/

comment on column MERCHANT_WAREHOUSE_SEAT.VERSION is '版本号'
/

comment on column MERCHANT_WAREHOUSE_SEAT.DELETED is '删除标记'
/

comment on column MERCHANT_WAREHOUSE_SEAT.WAREHOUSE_ID is '仓库id'
/

comment on column MERCHANT_WAREHOUSE_SEAT.SEAT_NAME is '位子名称'
/

comment on column MERCHANT_WAREHOUSE_SEAT.ID is '主键'
/

comment on column MERCHANT_WAREHOUSE_SEAT.CREATOR is '创建者'
/

comment on column MERCHANT_WAREHOUSE_SEAT.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_WAREHOUSE_SEAT.UPDATER is '更新者'
/

comment on column MERCHANT_WAREHOUSE_SEAT.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_WAREHOUSE_SEAT.TENANT_ID is '租户'
/

create table PRODUCT_SUB_SPECIFICATION
(
    VERSION              NUMBER,
    DELETED              NUMBER,
    PRODUCT_ID           VARCHAR2(32),
    SUB_SPECIFICATION_ID VARCHAR2(32) not null,
    SPECIFICATION_ID     VARCHAR2(32) not null,
    ID                   VARCHAR2(30) not null
        constraint PK_PRODUCT_SUB_SPECIFICATION
            primary key,
    CREATOR              VARCHAR2(30),
    CREATE_TIME          NUMBER(13),
    UPDATER              VARCHAR2(30),
    UPDATE_TIME          NUMBER(13)
)
/

comment on table PRODUCT_SUB_SPECIFICATION is '商品子规格'
/

comment on column PRODUCT_SUB_SPECIFICATION.VERSION is '版本号'
/

comment on column PRODUCT_SUB_SPECIFICATION.DELETED is '删除标记'
/

comment on column PRODUCT_SUB_SPECIFICATION.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_SUB_SPECIFICATION.SUB_SPECIFICATION_ID is '子规格id'
/

comment on column PRODUCT_SUB_SPECIFICATION.SPECIFICATION_ID is '主规格id'
/

comment on column PRODUCT_SUB_SPECIFICATION.ID is '主键'
/

comment on column PRODUCT_SUB_SPECIFICATION.CREATOR is '创建者'
/

comment on column PRODUCT_SUB_SPECIFICATION.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_SUB_SPECIFICATION.UPDATER is '更新者'
/

comment on column PRODUCT_SUB_SPECIFICATION.UPDATE_TIME is '更新时间'
/

create table ORDER_ITEM_SPECIFICATIONS
(
    ID              VARCHAR2(64)  not null
        primary key,
    PRODUCT_SPEC_ID VARCHAR2(64)  not null,
    ORDER_ITEM_ID   VARCHAR2(64)  not null,
    NAME            VARCHAR2(255) not null,
    VALUE           VARCHAR2(4000),
    PRICE           NUMBER(20)    not null,
    CURRENCY        VARCHAR2(3),
    CURRENCY_SCALE  NUMBER(2) default 2
)
/

comment on table ORDER_ITEM_SPECIFICATIONS is '订单规格表'
/

comment on column ORDER_ITEM_SPECIFICATIONS.ID is '规格ID'
/

comment on column ORDER_ITEM_SPECIFICATIONS.PRODUCT_SPEC_ID is '商品规格ID（来自商品服务）'
/

comment on column ORDER_ITEM_SPECIFICATIONS.ORDER_ITEM_ID is '订单项ID'
/

comment on column ORDER_ITEM_SPECIFICATIONS.NAME is '规格名称'
/

comment on column ORDER_ITEM_SPECIFICATIONS.VALUE is '规格值'
/

comment on column ORDER_ITEM_SPECIFICATIONS.PRICE is '规格价格(分)'
/

comment on column ORDER_ITEM_SPECIFICATIONS.CURRENCY is '币种'
/

comment on column ORDER_ITEM_SPECIFICATIONS.CURRENCY_SCALE is '币种精度'
/

create table ORDER_PAYMENTS
(
    ID             VARCHAR2(64) not null
        primary key,
    ORDER_ID       VARCHAR2(64) not null,
    CHANNEL_ID     VARCHAR2(64),
    TRADE_NO       VARCHAR2(128),
    AMOUNT         NUMBER(20)   not null,
    CURRENCY       VARCHAR2(3),
    CURRENCY_SCALE NUMBER(2),
    USER_ID        VARCHAR2(64),
    STATUS         NUMBER(10)   not null,
    FAIL_REASON    VARCHAR2(4000),
    PAY_TIME       NUMBER(13),
    CREATOR        VARCHAR2(64),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(64),
    UPDATE_TIME    NUMBER(13),
    VERSION        NUMBER(10) default 0,
    DELETED        NUMBER(10) default 0
)
/

comment on table ORDER_PAYMENTS is '订单支付记录表'
/

comment on column ORDER_PAYMENTS.ID is '主键ID'
/

comment on column ORDER_PAYMENTS.ORDER_ID is '订单ID'
/

comment on column ORDER_PAYMENTS.CHANNEL_ID is '支付渠道ID'
/

comment on column ORDER_PAYMENTS.TRADE_NO is '支付流水号'
/

comment on column ORDER_PAYMENTS.AMOUNT is '支付金额（分）'
/

comment on column ORDER_PAYMENTS.CURRENCY is '币种'
/

comment on column ORDER_PAYMENTS.CURRENCY_SCALE is '币种精度'
/

comment on column ORDER_PAYMENTS.USER_ID is '支付用户ID'
/

comment on column ORDER_PAYMENTS.STATUS is '支付状态（1:成功,0:失败）'
/

comment on column ORDER_PAYMENTS.FAIL_REASON is '失败原因'
/

comment on column ORDER_PAYMENTS.PAY_TIME is '支付时间'
/

comment on column ORDER_PAYMENTS.CREATOR is '创建者'
/

comment on column ORDER_PAYMENTS.CREATE_TIME is '创建时间'
/

comment on column ORDER_PAYMENTS.UPDATER is '更新者'
/

comment on column ORDER_PAYMENTS.UPDATE_TIME is '更新时间'
/

comment on column ORDER_PAYMENTS.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDER_PAYMENTS.DELETED is '删除标记（0：正常，1：已删除）'
/

create table ORDER_ITEMS
(
    ID               VARCHAR2(64)  not null
        primary key,
    ORDER_ID         VARCHAR2(64)  not null,
    PRODUCT_ID       VARCHAR2(64)  not null,
    PRODUCT_NAME     VARCHAR2(255) not null,
    UNIT_PRICE       NUMBER(20)    not null,
    DISCOUNTED_PRICE NUMBER(20),
    QUANTITY         NUMBER(10)    not null,
    TOTAL_PRICE      NUMBER(20)    not null,
    CURRENCY         VARCHAR2(3),
    CURRENCY_SCALE   NUMBER(2),
    PAYMENT_STATUS   NUMBER(10),
    CREATE_TIME      NUMBER(13),
    UPDATE_TIME      NUMBER(13),
    VERSION          NUMBER(10) default 0,
    DELETED          NUMBER(10) default 0
)
/

comment on table ORDER_ITEMS is '订单项表'
/

comment on column ORDER_ITEMS.ID is '订单项ID'
/

comment on column ORDER_ITEMS.ORDER_ID is '订单ID'
/

comment on column ORDER_ITEMS.PRODUCT_ID is '商品ID'
/

comment on column ORDER_ITEMS.PRODUCT_NAME is '商品名称'
/

comment on column ORDER_ITEMS.UNIT_PRICE is '商品单价（分）'
/

comment on column ORDER_ITEMS.DISCOUNTED_PRICE is '优惠价格（分）'
/

comment on column ORDER_ITEMS.QUANTITY is '数量'
/

comment on column ORDER_ITEMS.TOTAL_PRICE is '订单项总价（分）'
/

comment on column ORDER_ITEMS.CURRENCY is '币种'
/

comment on column ORDER_ITEMS.CURRENCY_SCALE is '币种精度'
/

comment on column ORDER_ITEMS.PAYMENT_STATUS is '支付状态（0：未支付，1：已支付，2：部分支付）'
/

comment on column ORDER_ITEMS.CREATE_TIME is '创建时间'
/

comment on column ORDER_ITEMS.UPDATE_TIME is '更新时间'
/

comment on column ORDER_ITEMS.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDER_ITEMS.DELETED is '删除标记（0：正常，1：已删除）'
/

create table ORDER_RESERVATION_INFO
(
    ID                   VARCHAR2(64)  not null
        primary key,
    ORDER_ID             VARCHAR2(64)  not null,
    RESERVATION_TIME     NUMBER(13)    not null,
    RESERVER_NAME        VARCHAR2(255) not null,
    RESERVER_PHONE       VARCHAR2(20)  not null,
    DINING_NUMBER        NUMBER(10),
    ROOM_PREFERENCE      VARCHAR2(4000),
    DIETARY_REQUIREMENTS VARCHAR2(4000),
    REMARKS              VARCHAR2(4000),
    CREATE_TIME          NUMBER(13),
    UPDATE_TIME          NUMBER(13),
    VERSION              NUMBER(10) default 0,
    DELETED              NUMBER(10) default 0
)
/

comment on table ORDER_RESERVATION_INFO is '订单预订信息表'
/

comment on column ORDER_RESERVATION_INFO.ID is '主键ID'
/

comment on column ORDER_RESERVATION_INFO.ORDER_ID is '订单ID'
/

comment on column ORDER_RESERVATION_INFO.RESERVATION_TIME is '预订时间'
/

comment on column ORDER_RESERVATION_INFO.RESERVER_NAME is '预订人姓名'
/

comment on column ORDER_RESERVATION_INFO.RESERVER_PHONE is '预订人电话'
/

comment on column ORDER_RESERVATION_INFO.DINING_NUMBER is '就餐人数'
/

comment on column ORDER_RESERVATION_INFO.ROOM_PREFERENCE is '包间要求'
/

comment on column ORDER_RESERVATION_INFO.DIETARY_REQUIREMENTS is '特殊餐饮要求'
/

comment on column ORDER_RESERVATION_INFO.REMARKS is '预订备注'
/

comment on column ORDER_RESERVATION_INFO.CREATE_TIME is '创建时间'
/

comment on column ORDER_RESERVATION_INFO.UPDATE_TIME is '更新时间'
/

comment on column ORDER_RESERVATION_INFO.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDER_RESERVATION_INFO.DELETED is '删除标记（0：正常，1：已删除）'
/

create table ORDER_STATUS_LOGS
(
    ID           VARCHAR2(64) not null
        primary key,
    ORDER_ID     VARCHAR2(64) not null,
    STATUS_TYPE  NUMBER(10)   not null,
    OLD_STATUS   NUMBER(10),
    NEW_STATUS   NUMBER(10)   not null,
    OPERATOR_ID  VARCHAR2(64),
    REMARK       VARCHAR2(4000),
    OPERATE_TIME NUMBER(13),
    VERSION      NUMBER(10) default 0,
    DELETED      NUMBER(10) default 0
)
/

comment on table ORDER_STATUS_LOGS is '订单状态变更日志表'
/

comment on column ORDER_STATUS_LOGS.ID is '主键ID'
/

comment on column ORDER_STATUS_LOGS.ORDER_ID is '订单ID'
/

comment on column ORDER_STATUS_LOGS.STATUS_TYPE is '状态类型（1:订单状态 2:支付状态 3:配送状态）'
/

comment on column ORDER_STATUS_LOGS.OLD_STATUS is '原状态值'
/

comment on column ORDER_STATUS_LOGS.NEW_STATUS is '新状态值'
/

comment on column ORDER_STATUS_LOGS.OPERATOR_ID is '操作人ID'
/

comment on column ORDER_STATUS_LOGS.REMARK is '操作备注'
/

comment on column ORDER_STATUS_LOGS.OPERATE_TIME is '变更时间'
/

comment on column ORDER_STATUS_LOGS.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDER_STATUS_LOGS.DELETED is '删除标记（0：正常，1：已删除）'
/

create table ORDERS
(
    ID                 VARCHAR2(64)         not null
        primary key,
    MERCHANT_ID        VARCHAR2(64)         not null,
    ORDER_NUMBER       VARCHAR2(64)         not null,
    TOTAL_AMOUNT       NUMBER(20)           not null,
    CURRENCY           VARCHAR2(3),
    SERVICE_FEE        NUMBER(20),
    STATUS             NUMBER(10)           not null,
    ORDER_TYPE         NUMBER(10)           not null,
    PAYMENT_MODE       NUMBER(10),
    PAYMENT_CHANNEL_ID VARCHAR2(64),
    PAYMENT_TRADE_NO   VARCHAR2(128),
    PAYMENT_TIME       NUMBER(13),
    PAYMENT_DEADLINE   NUMBER(13),
    PAYMENT_STATUS     NUMBER(10),
    PAID_AMOUNT        NUMBER(20),
    TIMEZONE           VARCHAR2(64),
    RESERVATION_TIME   NUMBER(13),
    TABLE_NO           VARCHAR2(64),
    DINING_NUMBER      NUMBER(10),
    PICKUP_TIME        NUMBER(13),
    DELIVERY_TIME      NUMBER(13),
    FINISH_TIME        NUMBER(13),
    CURRENCY_SCALE     NUMBER(2),
    DELIVERY_TYPE      NUMBER(10),
    DELIVERY_STATUS    NUMBER(10),
    TENANT_ID          VARCHAR2(64)         not null,
    CREATOR            VARCHAR2(64),
    CREATE_TIME        NUMBER(13),
    UPDATER            VARCHAR2(64),
    UPDATE_TIME        NUMBER(13),
    VERSION            NUMBER(10) default 0,
    DELETED            NUMBER(10) default 0,
    PHONE_NUMBER       VARCHAR2(20),
    ORDER_CODE         VARCHAR2(50),
    COUNTRY_CODE       VARCHAR2(20),
    SOURCE_TYPE        NUMBER(10) default 1 not null
)
/

comment on table ORDERS is '订单主表'
/

comment on column ORDERS.ID is '主键ID'
/

comment on column ORDERS.MERCHANT_ID is '商户ID'
/

comment on column ORDERS.ORDER_NUMBER is '订单号'
/

comment on column ORDERS.TOTAL_AMOUNT is '订单总金额（分）'
/

comment on column ORDERS.CURRENCY is '币种'
/

comment on column ORDERS.SERVICE_FEE is '服务费（分）'
/

comment on column ORDERS.STATUS is '订单状态'
/

comment on column ORDERS.ORDER_TYPE is '订单类型'
/

comment on column ORDERS.PAYMENT_MODE is '支付方式'
/

comment on column ORDERS.PAYMENT_CHANNEL_ID is '支付渠道ID'
/

comment on column ORDERS.PAYMENT_TRADE_NO is '支付流水号'
/

comment on column ORDERS.PAYMENT_TIME is '支付时间'
/

comment on column ORDERS.PAYMENT_DEADLINE is '支付截止时间'
/

comment on column ORDERS.PAYMENT_STATUS is '支付状态'
/

comment on column ORDERS.PAID_AMOUNT is '已支付金额（分）'
/

comment on column ORDERS.TIMEZONE is '商户时区'
/

comment on column ORDERS.RESERVATION_TIME is '预订时间'
/

comment on column ORDERS.TABLE_NO is '桌号'
/

comment on column ORDERS.DINING_NUMBER is '就餐人数'
/

comment on column ORDERS.PICKUP_TIME is '取餐时间'
/

comment on column ORDERS.DELIVERY_TIME is '配送时间'
/

comment on column ORDERS.FINISH_TIME is '完成时间'
/

comment on column ORDERS.CURRENCY_SCALE is '币种精度'
/

comment on column ORDERS.DELIVERY_TYPE is '配送方式'
/

comment on column ORDERS.DELIVERY_STATUS is '配送状态'
/

comment on column ORDERS.TENANT_ID is '租户ID'
/

comment on column ORDERS.CREATOR is '创建者'
/

comment on column ORDERS.CREATE_TIME is '创建时间'
/

comment on column ORDERS.UPDATER is '更新者'
/

comment on column ORDERS.UPDATE_TIME is '更新时间'
/

comment on column ORDERS.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDERS.DELETED is '删除标记（0：正常，1：已删除）'
/

comment on column ORDERS.PHONE_NUMBER is '用户手机号'
/

comment on column ORDERS.ORDER_CODE is '订单编号'
/

comment on column ORDERS.COUNTRY_CODE is '国家编码'
/

comment on column ORDERS.SOURCE_TYPE is '订单来源类型（1：网上下单，2：商户下单）'
/

create table ORDER_DELIVERY_INFO
(
    ID                  VARCHAR2(64) not null
        primary key,
    ORDER_ID            VARCHAR2(64) not null,
    DELIVERY_TYPE       NUMBER(10)   not null,
    RIDER_ID            VARCHAR2(64),
    DELIVERY_COMPANY    VARCHAR2(255),
    TRACKING_NO         VARCHAR2(128),
    DELIVERY_PHONE      VARCHAR2(20),
    DELIVERY_NAME       VARCHAR2(255),
    DELIVERY_START_TIME NUMBER(13),
    RECEIVER_NAME       VARCHAR2(255),
    RECEIVER_PHONE      VARCHAR2(20),
    DELIVERY_ADDRESS    VARCHAR2(1000),
    DELIVERY_LATITUDE   NUMBER(17, 14),
    DELIVERY_LONGITUDE  NUMBER(17, 14),
    DELIVERY_DISTANCE   NUMBER(10, 2),
    DELIVERY_FEE        NUMBER(20),
    CURRENCY            VARCHAR2(3),
    CURRENCY_SCALE      NUMBER(2),
    CREATE_TIME         NUMBER(13),
    UPDATE_TIME         NUMBER(13),
    VERSION             NUMBER(10) default 0,
    DELETED             NUMBER(10) default 0
)
/

comment on table ORDER_DELIVERY_INFO is '订单配送信息表'
/

comment on column ORDER_DELIVERY_INFO.ID is '主键ID'
/

comment on column ORDER_DELIVERY_INFO.ORDER_ID is '订单ID'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_TYPE is '配送方式（1: 自提, 2: 快递, 3: 骑手配送）'
/

comment on column ORDER_DELIVERY_INFO.RIDER_ID is '骑手ID'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_COMPANY is '物流公司'
/

comment on column ORDER_DELIVERY_INFO.TRACKING_NO is '物流单号'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_PHONE is '配送联系电话'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_START_TIME is '配送开始时间'
/

comment on column ORDER_DELIVERY_INFO.RECEIVER_NAME is '收货人姓名'
/

comment on column ORDER_DELIVERY_INFO.RECEIVER_PHONE is '收货人电话'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_ADDRESS is '配送地址'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_LATITUDE is '配送地址纬度'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_LONGITUDE is '配送地址经度'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_DISTANCE is '配送距离(公里)'
/

comment on column ORDER_DELIVERY_INFO.DELIVERY_FEE is '配送费用(分)'
/

comment on column ORDER_DELIVERY_INFO.CURRENCY is '币种'
/

comment on column ORDER_DELIVERY_INFO.CURRENCY_SCALE is '币种精度'
/

comment on column ORDER_DELIVERY_INFO.CREATE_TIME is '创建时间'
/

comment on column ORDER_DELIVERY_INFO.UPDATE_TIME is '更新时间'
/

comment on column ORDER_DELIVERY_INFO.VERSION is '版本号（乐观锁）'
/

comment on column ORDER_DELIVERY_INFO.DELETED is '删除标记（0:正常,1:删除）'
/

create table USER_ADDRESS
(
    ID                      VARCHAR2(64)   not null
        primary key,
    USER_ID                 VARCHAR2(64)   not null,
    TYPE                    NUMBER(2)      not null,
    NAME                    VARCHAR2(255),
    PHONE                   VARCHAR2(20),
    COMPANY_NAME            VARCHAR2(255),
    CITY                    VARCHAR2(255)  not null,
    DETAIL                  VARCHAR2(1000) not null,
    LATITUDE                NUMBER(17, 14) not null,
    LONGITUDE               NUMBER(17, 14) not null,
    IS_DEFAULT              NUMBER(1)  default 0,
    IS_ENABLED              NUMBER(1)  default 1,
    IS_INVOICE_ADDRESS      NUMBER(1)  default 0,
    CREATE_TIME             NUMBER(13)     not null,
    UPDATE_TIME             NUMBER(13),
    VERSION                 NUMBER(10) default 0,
    DELETED                 NUMBER(1)  default 0,
    BUSINESS_LICENSE_NUMBER VARCHAR2(100)
)
/

comment on table USER_ADDRESS is '用户地址表'
/

comment on column USER_ADDRESS.ID is '主键ID'
/

comment on column USER_ADDRESS.USER_ID is '用户ID'
/

comment on column USER_ADDRESS.TYPE is '地址类型（1：个人地址，2：公司地址）'
/

comment on column USER_ADDRESS.NAME is '姓名'
/

comment on column USER_ADDRESS.PHONE is '联系电话'
/

comment on column USER_ADDRESS.COMPANY_NAME is '公司名称'
/

comment on column USER_ADDRESS.CITY is '城市'
/

comment on column USER_ADDRESS.DETAIL is '详细地址'
/

comment on column USER_ADDRESS.LATITUDE is '纬度'
/

comment on column USER_ADDRESS.LONGITUDE is '经度'
/

comment on column USER_ADDRESS.IS_DEFAULT is '是否默认地址（0：否，1：是）'
/

comment on column USER_ADDRESS.IS_ENABLED is '是否启用（0：否，1：是）'
/

comment on column USER_ADDRESS.IS_INVOICE_ADDRESS is '是否发票地址（0：否，1：是）'
/

comment on column USER_ADDRESS.CREATE_TIME is '创建时间'
/

comment on column USER_ADDRESS.UPDATE_TIME is '更新时间'
/

comment on column USER_ADDRESS.VERSION is '版本号（乐观锁）'
/

comment on column USER_ADDRESS.DELETED is '是否删除（0：否，1：是）'
/

comment on column USER_ADDRESS.BUSINESS_LICENSE_NUMBER is '企业营业号'
/

create table ORDER_OPERATION_LOGS
(
    ID             VARCHAR2(64) not null
        primary key,
    ORDER_ID       VARCHAR2(64) not null,
    ACTION         NUMBER(10)   not null,
    OPERATOR_ID    VARCHAR2(64),
    CONTENT        VARCHAR2(4000),
    REMARK         VARCHAR2(4000),
    OPERATE_TIME   NUMBER(13),
    AMOUNT         NUMBER(20),
    CURRENCY       VARCHAR2(3),
    CURRENCY_SCALE NUMBER(2),
    ITEM_COUNT     NUMBER(10),
    ORDER_COUNT    NUMBER(10),
    VERSION        NUMBER(10) default 0,
    TENANT_ID      VARCHAR2(64) not null,
    DELETED        NUMBER(10) default 0
)
/

comment on table ORDER_OPERATION_LOGS is '订单操作日志表'
/

comment on column ORDER_OPERATION_LOGS.ID is '主键ID'
/

comment on column ORDER_OPERATION_LOGS.ORDER_ID is '订单ID'
/

comment on column ORDER_OPERATION_LOGS.ACTION is '操作类型'
/

comment on column ORDER_OPERATION_LOGS.OPERATOR_ID is '操作人ID'
/

comment on column ORDER_OPERATION_LOGS.CONTENT is '操作内容'
/

comment on column ORDER_OPERATION_LOGS.REMARK is '操作备注'
/

comment on column ORDER_OPERATION_LOGS.OPERATE_TIME is '操作时间'
/

comment on column ORDER_OPERATION_LOGS.AMOUNT is '操作涉及的金额（分）'
/

comment on column ORDER_OPERATION_LOGS.CURRENCY is '币种'
/

comment on column ORDER_OPERATION_LOGS.CURRENCY_SCALE is '币种精度'
/

comment on column ORDER_OPERATION_LOGS.ITEM_COUNT is '操作涉及的商品数量'
/

comment on column ORDER_OPERATION_LOGS.ORDER_COUNT is '操作涉及的订单数量'
/

comment on column ORDER_OPERATION_LOGS.VERSION is '版本号（乐观锁，每次更新+1）'
/

comment on column ORDER_OPERATION_LOGS.TENANT_ID is '租户ID'
/

comment on column ORDER_OPERATION_LOGS.DELETED is '删除标记（0：正常，1：已删除）'
/

create table PRODUCT_LIST
(
    VERSION        NUMBER,
    DELETED        NUMBER,
    PRODUCT_ID     VARCHAR2(32) not null,
    SUB_PRODUCT_ID VARCHAR2(32) not null,
    ID             VARCHAR2(30) not null
        constraint PK_PRODUCT_LIST
            primary key,
    CREATOR        VARCHAR2(30),
    CREATE_TIME    NUMBER(13),
    UPDATER        VARCHAR2(30),
    UPDATE_TIME    NUMBER(13)
)
/

comment on table PRODUCT_LIST is '商品清单'
/

comment on column PRODUCT_LIST.VERSION is '版本号'
/

comment on column PRODUCT_LIST.DELETED is '删除标记'
/

comment on column PRODUCT_LIST.PRODUCT_ID is '商品id'
/

comment on column PRODUCT_LIST.SUB_PRODUCT_ID is '商品清单id'
/

comment on column PRODUCT_LIST.ID is '主键'
/

comment on column PRODUCT_LIST.CREATOR is '创建者'
/

comment on column PRODUCT_LIST.CREATE_TIME is '创建时间'
/

comment on column PRODUCT_LIST.UPDATER is '更新者'
/

comment on column PRODUCT_LIST.UPDATE_TIME is '更新时间'
/

create table PAY_PARTY
(
    PARTY_ID    VARCHAR2(32) not null
        constraint PK_PAY_PARTY
            primary key,
    NAME        VARCHAR2(100),
    PARTY_TYPE  NUMBER(1),
    TAX_ID      VARCHAR2(50),
    ADDRESS     VARCHAR2(255),
    POSTAL_CODE VARCHAR2(20),
    PHONE       VARCHAR2(20),
    EMAIL       VARCHAR2(100),
    IS_DELETED  NUMBER(1) default 0,
    VERSION     NUMBER
)
/

comment on table PAY_PARTY is '交易方'
/

comment on column PAY_PARTY.PARTY_ID is '交易方ID'
/

comment on column PAY_PARTY.NAME is '交易方名称'
/

comment on column PAY_PARTY.PARTY_TYPE is '交易方类型(1:商户,2:个人)'
/

comment on column PAY_PARTY.TAX_ID is '税号'
/

comment on column PAY_PARTY.ADDRESS is '地址'
/

comment on column PAY_PARTY.POSTAL_CODE is '邮编'
/

comment on column PAY_PARTY.PHONE is '联系电话'
/

comment on column PAY_PARTY.EMAIL is '电子邮箱'
/

comment on column PAY_PARTY.IS_DELETED is '是否删除(0-未删除，1-已删除)'
/

comment on column PAY_PARTY.VERSION is '版本号'
/

create table PAY_MERCHANT
(
    VERSION     NUMBER,
    DELETED     NUMBER,
    NAME        VARCHAR2(255),
    EMAIL       VARCHAR2(20),
    ID          VARCHAR2(30) not null
        constraint PK_PAY_MERCHANT
            primary key,
    CREATOR     VARCHAR2(30),
    CREATE_TIME NUMBER(13),
    UPDATER     VARCHAR2(30),
    UPDATE_TIME NUMBER(13),
    TENANT_ID   VARCHAR2(30)
)
/

comment on table PAY_MERCHANT is '支付商户'
/

comment on column PAY_MERCHANT.VERSION is '版本号'
/

comment on column PAY_MERCHANT.DELETED is '删除标记'
/

comment on column PAY_MERCHANT.NAME is '商户名称'
/

comment on column PAY_MERCHANT.EMAIL is '邮箱'
/

comment on column PAY_MERCHANT.ID is '主键'
/

comment on column PAY_MERCHANT.CREATOR is '创建者'
/

comment on column PAY_MERCHANT.CREATE_TIME is '创建时间'
/

comment on column PAY_MERCHANT.UPDATER is '更新者'
/

comment on column PAY_MERCHANT.UPDATE_TIME is '更新时间'
/

comment on column PAY_MERCHANT.TENANT_ID is '租户'
/

create table SYS_PAYMENT_CHANNEL
(
    CHANNEL_TYPE     NUMBER(1)     default 2 not null,
    LOGO             VARCHAR2(255),
    STATUS           NUMBER(1)     default 0,
    CHANNEL_KEY      VARCHAR2(100)           not null,
    VERSION          NUMBER,
    DELETED          NUMBER,
    ID               VARCHAR2(30)            not null
        constraint PK_SYS_PAYMENT_CHANNEL
            primary key,
    CREATOR          VARCHAR2(30),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(30),
    UPDATE_TIME      NUMBER(13),
    CHANNEL_NAME     VARCHAR2(100) default NULL,
    PAYMENT_MERCHANT VARCHAR2(20)
)
/

comment on table SYS_PAYMENT_CHANNEL is '支付类型'
/

comment on column SYS_PAYMENT_CHANNEL.CHANNEL_TYPE is '支付渠道1:线上；2:线下'
/

comment on column SYS_PAYMENT_CHANNEL.LOGO is 'Logo'
/

comment on column SYS_PAYMENT_CHANNEL.STATUS is '状态 0:启用；1:关闭'
/

comment on column SYS_PAYMENT_CHANNEL.CHANNEL_KEY is '渠道唯一key'
/

comment on column SYS_PAYMENT_CHANNEL.VERSION is '版本号'
/

comment on column SYS_PAYMENT_CHANNEL.DELETED is '删除标记'
/

comment on column SYS_PAYMENT_CHANNEL.ID is '主键'
/

comment on column SYS_PAYMENT_CHANNEL.CREATOR is '创建者'
/

comment on column SYS_PAYMENT_CHANNEL.CREATE_TIME is '创建时间'
/

comment on column SYS_PAYMENT_CHANNEL.UPDATER is '更新者'
/

comment on column SYS_PAYMENT_CHANNEL.UPDATE_TIME is '更新时间'
/

comment on column SYS_PAYMENT_CHANNEL.CHANNEL_NAME is '渠道名称'
/

comment on column SYS_PAYMENT_CHANNEL.PAYMENT_MERCHANT is '支付商户'
/

create table PAY_MERCHANT_PAYMENT_CHANNEL
(
    VERSION              NUMBER,
    DELETED              NUMBER,
    CHANNEL_NAME         VARCHAR2(100),
    ENCRYPTION_ALGORITHM VARCHAR2(50),
    CALLBACK_URL         VARCHAR2(255),
    APP_ID               VARCHAR2(500),
    MCH_ID               VARCHAR2(500),
    ID                   VARCHAR2(30) not null
        constraint PK_PAY_MERCHANT_PAYMENT_CHANNEL
            primary key,
    CREATOR              VARCHAR2(30),
    CREATE_TIME          NUMBER(13),
    UPDATER              VARCHAR2(30),
    UPDATE_TIME          NUMBER(13),
    TENANT_ID            VARCHAR2(30),
    MERCHANT_ID          VARCHAR2(32),
    PRIVATE_KEY          VARCHAR2(2000),
    PUBLIC_KEY           VARCHAR2(2000)
)
/

comment on table PAY_MERCHANT_PAYMENT_CHANNEL is '商户支付渠道配置'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.VERSION is '版本号'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.DELETED is '删除标记'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.CHANNEL_NAME is '通道名称'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.ENCRYPTION_ALGORITHM is '加密方式'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.CALLBACK_URL is '支付成功后的回调地址'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.APP_ID is 'AppId'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.MCH_ID is '支付商户号'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.ID is '主键'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.CREATOR is '创建者'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.CREATE_TIME is '创建时间'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.UPDATER is '更新者'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.UPDATE_TIME is '更新时间'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.TENANT_ID is '租户'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.MERCHANT_ID is '商户ID'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.PRIVATE_KEY is '商户的支付私钥，用于签名'
/

comment on column PAY_MERCHANT_PAYMENT_CHANNEL.PUBLIC_KEY is '商户的支付公钥，用于回调签名验证'
/

create table PAY_INVOICES
(
    DELETED            NUMBER,
    TAX                NUMBER,
    TAX_RATE           NUMBER,
    FEE_RATIO          NUMBER,
    HANDLING_FEES      NUMBER,
    TRANSACTION_TYPE   VARCHAR2(50),
    PAYEE_EMAIL        VARCHAR2(100),
    PAYEE_ADDRESS      VARCHAR2(255),
    PAYEE_TAX_ID       VARCHAR2(100),
    PAYEE_NAME         VARCHAR2(100),
    PAYEE_TYPE         NUMBER(1),
    PAYER_EMAIL        VARCHAR2(50),
    PAYER_PHONE_NUMBER VARCHAR2(50),
    PAYER_ADDRESS      VARCHAR2(255),
    PAYER_TYPE         NUMBER(1),
    STATUS             VARCHAR2(20),
    PAYMENT_METHOD     VARCHAR2(50),
    CURRENCY           VARCHAR2(10),
    TOTAL_AMOUNT       NUMBER(20),
    PAYMENT_ID         VARCHAR2(32),
    MERCHANT_ID        VARCHAR2(32),
    INVOICE_NUMBER     VARCHAR2(50),
    VERSION            NUMBER,
    PAYER_NAME         VARCHAR2(100),
    PAYER_TAX_ID       VARCHAR2(100),
    TENANT_ID          VARCHAR2(30),
    UPDATE_TIME        NUMBER(13),
    UPDATER            VARCHAR2(30),
    CREATE_TIME        NUMBER(13),
    CREATOR            VARCHAR2(30),
    ID                 VARCHAR2(30) not null
        constraint PK_PAY_INVOICES
            primary key
)
/

comment on table PAY_INVOICES is '发票'
/

comment on column PAY_INVOICES.DELETED is '删除标记'
/

comment on column PAY_INVOICES.TAX is '税额'
/

comment on column PAY_INVOICES.TAX_RATE is '税率(%)'
/

comment on column PAY_INVOICES.FEE_RATIO is '手续费比例(%)'
/

comment on column PAY_INVOICES.HANDLING_FEES is '手续费'
/

comment on column PAY_INVOICES.TRANSACTION_TYPE is '交易类型(比如订阅，下单，转账等)'
/

comment on column PAY_INVOICES.PAYEE_EMAIL is '收款放邮箱'
/

comment on column PAY_INVOICES.PAYEE_ADDRESS is '收款方地址'
/

comment on column PAY_INVOICES.PAYEE_TAX_ID is '收款方税务号'
/

comment on column PAY_INVOICES.PAYEE_NAME is '收款方名称'
/

comment on column PAY_INVOICES.PAYEE_TYPE is '收款方类型(1:商户,2:个人)'
/

comment on column PAY_INVOICES.PAYER_EMAIL is '付款方邮箱'
/

comment on column PAY_INVOICES.PAYER_PHONE_NUMBER is '付款方电话'
/

comment on column PAY_INVOICES.PAYER_ADDRESS is '付款方地址'
/

comment on column PAY_INVOICES.PAYER_TYPE is '付款方类型(1:商户,2:个人)'
/

comment on column PAY_INVOICES.STATUS is '支付状态（PENDING, PAID, FAILED）'
/

comment on column PAY_INVOICES.PAYMENT_METHOD is '支付方式（如支付宝、微信、信用卡）'
/

comment on column PAY_INVOICES.CURRENCY is '支付货币类型（如 CNY, USD）'
/

comment on column PAY_INVOICES.TOTAL_AMOUNT is '支付总金额'
/

comment on column PAY_INVOICES.PAYMENT_ID is '-- 支付ID（关联到支付业务表）'
/

comment on column PAY_INVOICES.MERCHANT_ID is '商户ID'
/

comment on column PAY_INVOICES.INVOICE_NUMBER is '发票编号'
/

comment on column PAY_INVOICES.VERSION is '版本号'
/

comment on column PAY_INVOICES.PAYER_NAME is '商户名称'
/

comment on column PAY_INVOICES.PAYER_TAX_ID is '付款方税务号'
/

comment on column PAY_INVOICES.TENANT_ID is '租户'
/

comment on column PAY_INVOICES.UPDATE_TIME is '更新时间'
/

comment on column PAY_INVOICES.UPDATER is '更新者'
/

comment on column PAY_INVOICES.CREATE_TIME is '创建时间'
/

comment on column PAY_INVOICES.CREATOR is '创建者'
/

comment on column PAY_INVOICES.ID is '主键'
/

create table T_PARTY
(
    ID           VARCHAR2(38)        not null
        constraint PK_PARTY
            primary key,
    PARTY_ID     VARCHAR2(38)        not null,
    INVOICE_ID   VARCHAR2(38),
    NAME         VARCHAR2(100)       not null,
    PARTY_TYPE   NUMBER(1)           not null,
    USER_STATUS  NUMBER(1) default 0 not null,
    TAX_ID       VARCHAR2(50),
    ADDRESS      VARCHAR2(200),
    POSTAL_CODE  VARCHAR2(20),
    PHONE        VARCHAR2(20),
    EMAIL        VARCHAR2(100),
    COUNTRY_CODE VARCHAR2(10),
    CREATE_TIME  NUMBER(20),
    UPDATE_TIME  NUMBER(20),
    IS_DELETED   NUMBER(1) default 0
)
/

comment on table T_PARTY is '交易方表'
/

comment on column T_PARTY.ID is '主键ID'
/

comment on column T_PARTY.PARTY_ID is '交易方ID'
/

comment on column T_PARTY.INVOICE_ID is '关联发票ID'
/

comment on column T_PARTY.NAME is '交易方名称'
/

comment on column T_PARTY.PARTY_TYPE is '交易方类型(1:商户,2:个人)'
/

comment on column T_PARTY.USER_STATUS is '用户状态(0:未注册,1:已注册)'
/

comment on column T_PARTY.TAX_ID is '税号'
/

comment on column T_PARTY.ADDRESS is '地址'
/

comment on column T_PARTY.POSTAL_CODE is '邮编'
/

comment on column T_PARTY.PHONE is '联系电话'
/

comment on column T_PARTY.EMAIL is '电子邮箱'
/

comment on column T_PARTY.COUNTRY_CODE is '国家代码'
/

comment on column T_PARTY.CREATE_TIME is '创建时间'
/

comment on column T_PARTY.UPDATE_TIME is '更新时间'
/

comment on column T_PARTY.IS_DELETED is '是否删除(0:未删除,1:已删除)'
/

create index IDX_PARTY_PARTY_ID
    on T_PARTY (PARTY_ID)
/

create index IDX_PARTY_INVOICE_ID
    on T_PARTY (INVOICE_ID)
/

create index IDX_PARTY_USER_STATUS
    on T_PARTY (USER_STATUS)
/

create index IDX_PARTY_COUNTRY_CODE
    on T_PARTY (COUNTRY_CODE)
/

create table PAY_INVOICE
(
    ID                     VARCHAR2(38) not null
        constraint PK_INVOICE
            primary key,
    TRANSACTION_PARTY_TYPE VARCHAR2(20) not null,
    PAYER_ID               VARCHAR2(38) not null,
    PAYEE_ID               VARCHAR2(38) not null,
    CONTRACT_ID            VARCHAR2(38),
    ORDER_ID               VARCHAR2(38),
    CREATE_TIME            NUMBER(20),
    DELIVERY_TIME          NUMBER(20),
    COMPLETE_TIME          NUMBER(20),
    TRANSACTION_ID         VARCHAR2(38),
    DIGITAL_SIGNATURE      VARCHAR2(64),
    CURRENCY               VARCHAR2(10) not null,
    SCALE                  NUMBER(2)    not null,
    PRE_TAX_AMOUNT         NUMBER(20),
    TOTAL_AMOUNT           NUMBER(20),
    DEDUCT_AMOUNT          NUMBER(20),
    STATUS                 VARCHAR2(20),
    CREATOR                VARCHAR2(38),
    UPDATER                VARCHAR2(38),
    UPDATE_TIME            NUMBER(20),
    VERSION                NUMBER(10) default 1,
    DELETED                NUMBER(1)  default 0,
    TENANT_ID              VARCHAR2(38)
)
/

comment on table PAY_INVOICE is '发票表'
/

comment on column PAY_INVOICE.ID is '主键ID'
/

comment on column PAY_INVOICE.TRANSACTION_PARTY_TYPE is '交易方类型'
/

comment on column PAY_INVOICE.PAYER_ID is '付款方ID'
/

comment on column PAY_INVOICE.PAYEE_ID is '收款方ID'
/

comment on column PAY_INVOICE.CONTRACT_ID is '合同ID'
/

comment on column PAY_INVOICE.ORDER_ID is '订单ID'
/

comment on column PAY_INVOICE.CREATE_TIME is '创建时间'
/

comment on column PAY_INVOICE.DELIVERY_TIME is '配送时间'
/

comment on column PAY_INVOICE.COMPLETE_TIME is '完成时间'
/

comment on column PAY_INVOICE.TRANSACTION_ID is '交易ID'
/

comment on column PAY_INVOICE.DIGITAL_SIGNATURE is '数字签名hash'
/

comment on column PAY_INVOICE.CURRENCY is '币种'
/

comment on column PAY_INVOICE.SCALE is '币种精度'
/

comment on column PAY_INVOICE.PRE_TAX_AMOUNT is '税前总价'
/

comment on column PAY_INVOICE.TOTAL_AMOUNT is '总价（含税）'
/

comment on column PAY_INVOICE.DEDUCT_AMOUNT is '减免金额'
/

comment on column PAY_INVOICE.STATUS is '发票状态'
/

comment on column PAY_INVOICE.CREATOR is '创建者'
/

comment on column PAY_INVOICE.UPDATER is '更新者'
/

comment on column PAY_INVOICE.UPDATE_TIME is '更新时间'
/

comment on column PAY_INVOICE.VERSION is '版本号'
/

comment on column PAY_INVOICE.DELETED is '删除标记(0:未删除,1:已删除)'
/

comment on column PAY_INVOICE.TENANT_ID is '租户ID'
/

create table PAY_INVOICE_ITEM
(
    ID             VARCHAR2(38)  not null
        constraint PK_INVOICE_ITEM
            primary key,
    INVOICE_ID     VARCHAR2(38)  not null,
    ORDER_ITEM_ID  VARCHAR2(38),
    PRODUCT_ID     VARCHAR2(38),
    PRODUCT_NAME   VARCHAR2(100) not null,
    QUANTITY       NUMBER(10)    not null,
    ORIGINAL_PRICE NUMBER(20),
    UNIT_PRICE     NUMBER(20)    not null,
    TAX_RATE       NUMBER(20, 4),
    TAX_AMOUNT     NUMBER(20)    not null,
    TOTAL_AMOUNT   NUMBER(20)    not null,
    REMARK         VARCHAR2(200)
)
/

comment on table PAY_INVOICE_ITEM is '发票明细表'
/

comment on column PAY_INVOICE_ITEM.ID is '主键ID'
/

comment on column PAY_INVOICE_ITEM.INVOICE_ID is '发票ID'
/

comment on column PAY_INVOICE_ITEM.ORDER_ITEM_ID is '订单项ID'
/

comment on column PAY_INVOICE_ITEM.PRODUCT_ID is '商品ID'
/

comment on column PAY_INVOICE_ITEM.PRODUCT_NAME is '商品名称'
/

comment on column PAY_INVOICE_ITEM.QUANTITY is '数量'
/

comment on column PAY_INVOICE_ITEM.ORIGINAL_PRICE is '原价'
/

comment on column PAY_INVOICE_ITEM.UNIT_PRICE is '单价'
/

comment on column PAY_INVOICE_ITEM.TAX_RATE is '税率'
/

comment on column PAY_INVOICE_ITEM.TAX_AMOUNT is '税额'
/

comment on column PAY_INVOICE_ITEM.TOTAL_AMOUNT is '总价（含税）'
/

comment on column PAY_INVOICE_ITEM.REMARK is '备注'
/

create table PAY_ITEM
(
    ID         VARCHAR2(38) not null
        constraint PK_PAY_ITEM
            primary key,
    CHANNEL_ID VARCHAR2(38) not null,
    TRADE_NO   VARCHAR2(64),
    AMOUNT     NUMBER(20)   not null,
    PAY_TIME   NUMBER(20),
    STATUS     NUMBER(1) default 1,
    INVOICE_ID VARCHAR2(38) not null,
    PAY_TYPE   NUMBER(1) default 1,
    REMARK     VARCHAR2(200)
)
/

comment on table PAY_ITEM is '支付项表'
/

comment on column PAY_ITEM.ID is '主键ID'
/

comment on column PAY_ITEM.CHANNEL_ID is '支付渠道ID'
/

comment on column PAY_ITEM.TRADE_NO is '交易流水号'
/

comment on column PAY_ITEM.AMOUNT is '支付金额'
/

comment on column PAY_ITEM.PAY_TIME is '支付时间'
/

comment on column PAY_ITEM.STATUS is '支付状态(0:未支付,1:已支付,2:支付失败)'
/

comment on column PAY_ITEM.INVOICE_ID is '关联发票ID'
/

comment on column PAY_ITEM.PAY_TYPE is '支付类型(1:线上支付,2:线下支付)'
/

comment on column PAY_ITEM.REMARK is '备注'
/

create table MERCHANT_PAYMENT_CHANNEL_CONFIG
(
    VERSION          NUMBER,
    DELETED          NUMBER,
    ID               VARCHAR2(30)  not null
        constraint PK_MERCHANT_PAYMENT_CHANNEL_CONFIG
            primary key,
    CREATOR          VARCHAR2(30),
    CREATE_TIME      NUMBER(13),
    UPDATER          VARCHAR2(30),
    UPDATE_TIME      NUMBER(13),
    TENANT_ID        VARCHAR2(50),
    MERCHANT_ID      VARCHAR2(32),
    PAYMENT_MERCHANT VARCHAR2(100) not null,
    CONFIG_DATA      CLOB
)
/

comment on table MERCHANT_PAYMENT_CHANNEL_CONFIG is '商户支付渠道配置'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.VERSION is '版本号'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.DELETED is '删除标记'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.ID is '主键'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.CREATOR is '创建者'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.UPDATER is '更新者'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.TENANT_ID is '租户'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.MERCHANT_ID is '商户ID'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.PAYMENT_MERCHANT is '支付商户(braintree、alipay、wechat)'
/

comment on column MERCHANT_PAYMENT_CHANNEL_CONFIG.CONFIG_DATA is '支付配置'
/

create table MERCHANT_PAY_CHANNEL
(
    VERSION      NUMBER,
    DELETED      NUMBER,
    CHANNEL_TYPE NUMBER default 2 not null,
    ID           VARCHAR2(30)     not null
        constraint PK_MERCHANT_PAY_CHANNEL
            primary key,
    CREATOR      VARCHAR2(30),
    CREATE_TIME  NUMBER(13),
    UPDATER      VARCHAR2(30),
    UPDATE_TIME  NUMBER(13),
    TENANT_ID    VARCHAR2(50),
    CHANNEL_KEY  VARCHAR2(255),
    MERCHANT_ID  VARCHAR2(38),
    STATUS       NUMBER default 0 not null
)
/

comment on table MERCHANT_PAY_CHANNEL is '商户支付渠道'
/

comment on column MERCHANT_PAY_CHANNEL.VERSION is '版本号'
/

comment on column MERCHANT_PAY_CHANNEL.DELETED is '删除标记'
/

comment on column MERCHANT_PAY_CHANNEL.CHANNEL_TYPE is '支付渠道;1:线上；2:线下'
/

comment on column MERCHANT_PAY_CHANNEL.ID is '主键'
/

comment on column MERCHANT_PAY_CHANNEL.CREATOR is '创建者'
/

comment on column MERCHANT_PAY_CHANNEL.CREATE_TIME is '创建时间'
/

comment on column MERCHANT_PAY_CHANNEL.UPDATER is '更新者'
/

comment on column MERCHANT_PAY_CHANNEL.UPDATE_TIME is '更新时间'
/

comment on column MERCHANT_PAY_CHANNEL.TENANT_ID is '租户'
/

comment on column MERCHANT_PAY_CHANNEL.CHANNEL_KEY is '渠道唯一key'
/

comment on column MERCHANT_PAY_CHANNEL.MERCHANT_ID is '商户id'
/

comment on column MERCHANT_PAY_CHANNEL.STATUS is '状态 1:启用；2:关闭'
/

create table WS_NOTIFICATION_MESSAGE
(
    ID                VARCHAR2(32)                      not null
        primary key,
    TYPE              VARCHAR2(50)                      not null,
    NOTIFICATION_TYPE VARCHAR2(50),
    CONTENT           CLOB                              not null,
    RECEIVER_ID       VARCHAR2(100)                     not null,
    SENDER_ID         VARCHAR2(100)                     not null,
    IS_READ           NUMBER(1)    default 0            not null,
    CREATE_TIME       TIMESTAMP(6) default SYSTIMESTAMP not null,
    UPDATE_TIME       TIMESTAMP(6) default SYSTIMESTAMP not null,
    TIMESTAMP         NUMBER,
    EXTRA_DATA        CLOB
)
/

comment on table WS_NOTIFICATION_MESSAGE is '消息通知表'
/

comment on column WS_NOTIFICATION_MESSAGE.ID is '主键ID'
/

comment on column WS_NOTIFICATION_MESSAGE.TYPE is '消息类型'
/

comment on column WS_NOTIFICATION_MESSAGE.NOTIFICATION_TYPE is '通知类型'
/

comment on column WS_NOTIFICATION_MESSAGE.CONTENT is '消息内容'
/

comment on column WS_NOTIFICATION_MESSAGE.RECEIVER_ID is '接收者用户ID'
/

comment on column WS_NOTIFICATION_MESSAGE.SENDER_ID is '发送者用户ID'
/

comment on column WS_NOTIFICATION_MESSAGE.IS_READ is '是否已读 0-未读 1-已读'
/

comment on column WS_NOTIFICATION_MESSAGE.CREATE_TIME is '创建时间'
/

comment on column WS_NOTIFICATION_MESSAGE.UPDATE_TIME is '更新时间'
/

comment on column WS_NOTIFICATION_MESSAGE.TIMESTAMP is '附加数据时间戳（毫秒）'
/

comment on column WS_NOTIFICATION_MESSAGE.EXTRA_DATA is '附加数据JSON字符串'
/

create index IDX_NOTIFICATION_RECEIVER
    on WS_NOTIFICATION_MESSAGE (RECEIVER_ID)
/

create index IDX_NOTIFICATION_TYPE
    on WS_NOTIFICATION_MESSAGE (TYPE)
/

create index IDX_NOTIFICATION_NOTIFICATION_TYPE
    on WS_NOTIFICATION_MESSAGE (NOTIFICATION_TYPE)
/

create index IDX_NOTIFICATION_UNREAD
    on WS_NOTIFICATION_MESSAGE (RECEIVER_ID, IS_READ)
/

create index IDX_NOTIFICATION_TIMESTAMP
    on WS_NOTIFICATION_MESSAGE (TIMESTAMP)
/

create table PLATFORM_APPROVAL_RECORD
(
    CHANGE_MEMORY NUMBER(12, 2),
    VERSION       NUMBER,
    APPROVAL_ID   VARCHAR2(32),
    RECORD_TYPE   VARCHAR2(16),
    DELETED       NUMBER,
    CREATOR       VARCHAR2(30),
    CREATE_TIME   NUMBER(13),
    TENANT_ID     VARCHAR2(32),
    UPDATER       VARCHAR2(30),
    UPDATE_TIME   NUMBER(13),
    ID            VARCHAR2(32) not null
        constraint PK_PLATFORM_APPROVAL_RECORD
            primary key,
    DESCRIPTION   VARCHAR2(128),
    DATA_SOURCE   VARCHAR2(16),
    DATA_TYPE     VARCHAR2(16)
)
/

comment on table PLATFORM_APPROVAL_RECORD is '存储变更明细表'
/

comment on column PLATFORM_APPROVAL_RECORD.CHANGE_MEMORY is '变更的存储'
/

comment on column PLATFORM_APPROVAL_RECORD.VERSION is '版本号'
/

comment on column PLATFORM_APPROVAL_RECORD.APPROVAL_ID is '审批id'
/

comment on column PLATFORM_APPROVAL_RECORD.RECORD_TYPE is '变更类型'
/

comment on column PLATFORM_APPROVAL_RECORD.DELETED is '删除标记'
/

comment on column PLATFORM_APPROVAL_RECORD.CREATOR is '创建者'
/

comment on column PLATFORM_APPROVAL_RECORD.CREATE_TIME is '创建时间'
/

comment on column PLATFORM_APPROVAL_RECORD.TENANT_ID is '租户'
/

comment on column PLATFORM_APPROVAL_RECORD.UPDATER is '更新者'
/

comment on column PLATFORM_APPROVAL_RECORD.UPDATE_TIME is '更新时间'
/

comment on column PLATFORM_APPROVAL_RECORD.ID is '主键'
/

comment on column PLATFORM_APPROVAL_RECORD.DESCRIPTION is '描述'
/

comment on column PLATFORM_APPROVAL_RECORD.DATA_SOURCE is '数据来源 order:订单；invoice：发票；product:商品'
/

comment on column PLATFORM_APPROVAL_RECORD.DATA_TYPE is '数据类型 img:图片；file：文件；video:视频；data:数据行(数据表中的数据)'
/

create index PLATFORM_APPROVAL_RECORD_TENANT_ID_INDEX
    on PLATFORM_APPROVAL_RECORD (TENANT_ID)
/

create index PLATFORM_APPROVAL_RECORD_APPROVAL_ID_INDEX
    on PLATFORM_APPROVAL_RECORD (APPROVAL_ID)
/

