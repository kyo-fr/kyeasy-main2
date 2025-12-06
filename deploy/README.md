# Docker Compose 服务管理

本项目将Docker服务分为两个独立的compose文件，便于管理和维护。

## 文件结构

- `docker-compose.yml` - 基础服务 (MySQL, Redis, Nacos)
- `docker-compose-rocketmq.yml` - RocketMQ相关服务 (NameServer, Broker, Dashboard)

## 快速启动

### 方式一：使用脚本（推荐）

```bash
# 启动所有服务
./start-all.sh

# 停止所有服务
./stop-all.sh
```

### 方式二：手动启动

```bash
# 1. 启动基础服务
docker-compose up -d

# 2. 等待基础服务启动完成（约10秒）
sleep 10

# 3. 启动RocketMQ服务
docker-compose -f docker-compose-rocketmq.yml up -d
```

## 服务访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| MySQL | localhost:3306 | 数据库服务 |
| Redis | localhost:6379 | 缓存服务 |
| Nacos | http://localhost:18848/nacos | 服务注册与配置中心 |
| RocketMQ Dashboard | http://localhost:18080 | 消息队列管理界面 |

## 常用命令

### 查看服务状态
```bash
# 查看基础服务状态
docker-compose ps

# 查看RocketMQ服务状态
docker-compose -f docker-compose-rocketmq.yml ps
```

### 查看服务日志
```bash
# 查看基础服务日志
docker-compose logs -f

# 查看RocketMQ服务日志
docker-compose -f docker-compose-rocketmq.yml logs -f

# 查看特定服务日志
docker-compose logs -f mysql
docker-compose -f docker-compose-rocketmq.yml logs -f rocketmq
```

### 重启服务
```bash
# 重启基础服务
docker-compose restart

# 重启RocketMQ服务
docker-compose -f docker-compose-rocketmq.yml restart
```

### 停止服务
```bash
# 停止基础服务
docker-compose down

# 停止RocketMQ服务
docker-compose -f docker-compose-rocketmq.yml down
```

## 数据持久化

所有数据都保存在 `./data/` 目录下：
- `./data/mysql/` - MySQL数据
- `./data/redis/` - Redis数据
- `./data/nacos/` - Nacos数据
- `./data/rocketmq/` - RocketMQ数据

## 注意事项

1. 启动顺序很重要：先启动基础服务，再启动RocketMQ服务
2. 如果遇到网络问题，可以尝试删除网络后重新创建：
   ```bash
   docker network rm deploy_ares-network
   ```
3. 如果遇到端口冲突，请检查端口占用情况：
   ```bash
   lsof -i :3306  # MySQL
   lsof -i :6379  # Redis
   lsof -i :18848 # Nacos
   lsof -i :19876 # RocketMQ NameServer
   lsof -i :18080 # RocketMQ Dashboard
   ```
