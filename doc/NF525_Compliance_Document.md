# Ares Cloud系统NF525认证合规方案

## 1. 概述

本方案旨在说明Ares Cloud系统如何满足法国NF525认证要求。NF525认证是法国针对收银软件(logiciels de caisse/systèmes d'encaissement)的法规要求，旨在打击税务欺诈。它要求软件必须满足不可篡改性(inaltérabilité)、安全性(sécurisation)和数据存储/存档(conservation et archivage des données)的条件。

## 2. 系统技术架构

### 2.1 技术栈概览
- 后端框架：Spring Boot 3 + Java 17
- ORM框架：MyBatis Plus
- 数据库：Oracle Database（通过ojdbc11驱动连接）
- 连接池：Oracle UCP (Universal Connection Pool)
- 安全框架：Spring Security
- 微服务架构：Spring Cloud Alibaba

### 2.2 核心模块
- 商品服务（ares-cloud-product）：管理商品信息和仓库数据
- 商户服务（ares-cloud-merchant）：管理商户信息
- 订单服务（ares-cloud-order）：处理订单交易
- 用户服务（ares-cloud-user）：用户权限管理

## 3. Oracle数据库NF525合规特性支持

### 3.1 不可篡改性与安全性

#### 3.1.1 Audit Vault and Database Firewall (AVDF) / 统一审计 (Unified Auditing)
- Oracle数据库内置统一审计功能，可记录所有对数据库数据的操作（包括谁、何时、做了什么）
- 满足NF525对所有敏感操作（如删除、修改交易记录）的详细审计要求
- 结合Spring Security框架，系统实现了细粒度的访问控制，确保只有授权用户才能执行特定操作

#### 3.1.2 Flashback Data Archive (FDA) / 历史表 (Temporal Validity)
- 利用Oracle Flashback技术保留历史数据和更改的时间戳版本
- 即使数据被修改或删除，也能随时查询到原始状态
- 为交易数据的不可逆性和可追溯性提供技术保障

#### 3.1.3 细粒度访问控制 (Fine-Grained Access Control - FGAC)
- Oracle数据库提供行级安全控制和虚拟私有数据库(VPD)功能
- 结合系统的租户隔离设计（TenantEntity基类），确保不同商户的数据完全隔离
- 通过MyBatis Plus的注解和Wrapper条件构造器，严格控制数据访问范围

### 3.2 数据存储与存档

#### 3.2.1 RMAN (Recovery Manager) / Data Guard
- Oracle专业数据备份和恢复工具RMAN确保数据安全
- Data Guard提供灾难恢复能力，保证在系统故障或灾难情况下能完整恢复交易数据
- 满足NF525关于长期安全存储交易数据的要求

#### 3.2.2 分区 (Partitioning)
- Oracle分区功能允许按时间或其他标准将大量交易数据分割存储
- 提高大数据量场景下的查询性能和管理效率
- 方便进行年度数据归档和审计查询

## 4. 应用软件层面的NF525合规实现

### 4.1 自动编号和时间戳机制
- 系统通过继承TenantEntity基类，为每个实体自动添加id字段作为唯一标识符
- 结合Oracle序列或自增主键功能，确保每条记录具有连续且唯一的编号
- 系统自动维护创建时间和更新时间字段，保证交易记录的时间不可篡改

### 4.2 闭合过程 (Clôture journalière) 和总计功能
- 系统可以在业务层面实现日结功能，通过定时任务（schedule模块）每日统计关键业务指标
- 利用Oracle物化视图或汇总表技术，存储每日业务总额，确保与明细数据的一致性
- 通过事务机制保证日结过程的原子性和一致性

### 4.3 数据导出功能
- 系统提供标准化API接口，用于导出符合法国税务机关要求的XML或文本格式数据
- 利用Spring Boot的Web MVC框架，实现高效的数据导出服务
- 结合Oracle的高速数据导出工具（如Data Pump），提升大数据量导出性能

## 5. 租户隔离与多商户支持

系统采用多租户架构设计，通过TenantEntity基类和ApplicationContext上下文管理，实现：
- 每个商户数据的物理隔离或逻辑隔离
- 防止跨租户数据访问，确保各商户交易数据的安全性
- 支持NF525对独立商户数据保护的要求

## 6. 总结

Ares Cloud系统基于Oracle数据库和Spring Boot技术栈，已经具备了满足NF525认证的良好基础：

1. 利用Oracle数据库的审计、闪回和安全功能，为应用提供了满足NF525不可篡改性、安全性及存储要求的技术基础
2. 通过MyBatis Plus和Spring生态，简化了数据访问层的开发复杂度
3. 微服务架构有利于按模块进行NF525合规改造
4. 多租户设计天然适应法国商户环境需求

后续只需要在应用层补充连续编号生成、日结和数据导出等核心业务功能，即可形成完整的NF525合规解决方案。