#!/bin/bash

# 启动所有服务的脚本
echo "启动基础服务 (MySQL, Redis, Nacos)..."
docker-compose up -d

echo "等待基础服务启动完成..."
sleep 1

echo "启动RocketMQ服务..."
docker-compose -f docker-compose-rocketmq.yml up -d

echo "所有服务启动完成！"
echo ""
echo "服务访问地址："
echo "- MySQL: localhost:3306"
echo "- Redis: localhost:6379"
echo "- Nacos: http://localhost:18848/nacos (用户名/密码: nacos/nacos)"
echo "- RocketMQ Dashboard: http://localhost:18080"
echo ""
echo "查看服务状态："
docker-compose ps
docker-compose -f docker-compose-rocketmq.yml ps
