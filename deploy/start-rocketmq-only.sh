#!/bin/bash

# RocketMQ 启动脚本
echo "正在启动 RocketMQ 服务..."

# 切换到部署目录
cd "$(dirname "$0")"

# 停止可能存在的容器
echo "停止现有的 RocketMQ 容器..."
podman-compose -f docker-compose-rocketmq.yml down

# 清理容器
echo "清理容器..."
podman rm -f rocketmq-dashboard rocketmq-broker rocketmq-nameserver 2>/dev/null || true

# 启动 RocketMQ
echo "启动 RocketMQ 服务..."
podman-compose -f docker-compose-rocketmq.yml up -d

# 等待服务启动
echo "等待服务启动..."
sleep 10

# 检查服务状态
echo "检查服务状态..."
podman ps | grep rocketmq

echo "RocketMQ 服务启动完成！"
echo "NameServer 地址: localhost:19876"
echo "Broker 地址: localhost:19091"
echo "Dashboard 地址: http://localhost:18081"

# 检查 NameServer 是否启动成功
echo "检查 NameServer 状态..."
if podman ps | grep -q "rocketmq-nameserver.*Up"; then
    echo "✅ NameServer 启动成功"
else
    echo "❌ NameServer 启动失败"
    podman logs rocketmq-nameserver --tail 20
fi

# 检查 Broker 是否启动成功
echo "检查 Broker 状态..."
if podman ps | grep -q "rocketmq-broker.*Up"; then
    echo "✅ Broker 启动成功"
else
    echo "❌ Broker 启动失败"
    podman logs rocketmq-broker --tail 20
fi
