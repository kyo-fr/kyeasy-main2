#!/bin/bash

# 停止所有服务的脚本
echo "停止RocketMQ服务..."
docker-compose -f docker-compose-rocketmq.yml down

echo "停止基础服务 (MySQL, Redis, Nacos)..."
docker-compose down

echo "所有服务已停止！"
