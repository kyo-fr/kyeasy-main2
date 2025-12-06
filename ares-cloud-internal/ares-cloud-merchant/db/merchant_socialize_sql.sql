
-- ----------------------------
-- Table structure for merchant_socialize
-- 作者 hugo
-- date 2024-10-09
-- 商户社交
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchant_socialize';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE merchant_socialize (
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
                                    FACE_BOOK   VARCHAR2(128),
                                    TWITTER     VARCHAR2(128)
)

comment on table MERCHANT_SOCIALIZE is '商户社交'

comment on column MERCHANT_SOCIALIZE.VERSION is '版本号'

comment on column MERCHANT_SOCIALIZE.DELETED is '删除标记'

comment on column MERCHANT_SOCIALIZE.YOUTUBE is 'youtube'

comment on column MERCHANT_SOCIALIZE.FACE_BOOK is 'faceBook'

comment on column MERCHANT_SOCIALIZE.BILIBILI is 'bilibili'

comment on column MERCHANT_SOCIALIZE.TIKTOK is 'tiktok'

comment on column MERCHANT_SOCIALIZE.WHATS_APP is 'whatsApp'

comment on column MERCHANT_SOCIALIZE.WECHAT is '微信'

comment on column MERCHANT_SOCIALIZE.DOU_YIN is '抖音'

comment on column MERCHANT_SOCIALIZE.ID is '主键'

comment on column MERCHANT_SOCIALIZE.CREATOR is '创建者'

comment on column MERCHANT_SOCIALIZE.CREATE_TIME is '创建时间'

comment on column MERCHANT_SOCIALIZE.UPDATER is '更新者'

comment on column MERCHANT_SOCIALIZE.UPDATE_TIME is '更新时间'

comment on column MERCHANT_SOCIALIZE.TENANT_ID is '租户'

comment on column MERCHANT_SOCIALIZE.FEAC_BOOK is 'feacbook'

comment on column MERCHANT_SOCIALIZE.TWITTER is '推特'
