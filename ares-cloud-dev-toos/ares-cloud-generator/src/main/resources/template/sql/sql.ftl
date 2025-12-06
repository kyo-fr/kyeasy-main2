<#if dbType == "MySQL">
-- ----------------------------
-- Table structure for ${tableName}
-- 作者 ${author}
-- date ${date}
-- ${tableComment}
-- ----------------------------
DROP TABLE IF EXISTS `${tableName}`;
CREATE TABLE `${tableName}`  (
<#if fieldList??>
    <#list fieldList as column>
        `${column.fieldName}` ${column.fieldType}<#if column.length?? && (column.length?number > 0)>(${column.length}<#if column.point?? && (column.point?number > 0)>,${column.point}</#if>)</#if> <#if column.defValue??>DEFAULT ${column.defValue}</#if> <#if column.isNull == 2>NOT NULL</#if> COMMENT '${column.fieldComment}',
    </#list>
</#if>
primary key (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC  COMMENT '${tableComment}';
</#if>

<#if dbType == "Oracle">
-- ----------------------------
-- Table structure for ${tableName}
-- 作者 ${author}
-- date ${date}
-- ${tableComment}
-- ----------------------------
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE ${tableName}';
EXCEPTION
WHEN OTHERS THEN
IF SQLCODE != -942 THEN
RAISE;
END IF;
END;
/
CREATE TABLE ${tableName} (
<#if fieldList??>
    <#list fieldList as column>
   ${column.fieldName} ${column.fieldType}<#if column.length?? && (column.length?number > 0)>(${column.length}<#if column.point?? && (column.point?number > 0)>,${column.point}</#if>)</#if> <#if column.defValue??>DEFAULT ${column.defValue}</#if> <#if column.isNull == 2>NOT NULL</#if>,
    </#list>
</#if>
CONSTRAINT PK_${tableName} PRIMARY KEY (id)
);
-- 添加表注释
COMMENT ON TABLE ${tableName} IS '${tableComment}';
<#if fieldList??>
<#list fieldList as column>
-- 添加列注释
COMMENT ON COLUMN ${tableName}.${column.fieldName} IS '${column.fieldComment}';
</#list>
</#if>
</#if>