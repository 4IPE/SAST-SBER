#!/bin/bash

echo "Начинаем установку..."

if [ "$(id -u)" -ne 0 ]; then
    echo " Пожалуйста, запустите скрипт с правами root: sudo ./install.sh"
    exit 1
fi

CURRENT_USER=$(logname)
echo "Добавляем пользователя $CURRENT_USER в группу docker..."
usermod -aG docker $CURRENT_USER
и
echo "Обновляем пакеты..."
apt update -y && apt upgrade -y

echo "Устанавливаем Docker, Docker Compose и Maven"
apt install -y docker.io git curl maven

if ! [ -x "$(command -v docker-compose)" ]; then
    echo "Загружаем Docker Compose..."
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
fi

echo " Проверяем версии..."
docker --version
docker-compose --version
mvn -version

echo "Удаляем старый код (если есть)..."
rm -rf /opt/SAST-SBER

echo "Клонируем репозиторий (ветка dev)..."
cd /opt
git clone -b dev --single-branch https://github.com/4IPE/SAST-SBER.git
cd SAST-SBER

echo "Сборка проекта с помощью Maven..."
mvn clean install -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Ошибка сборки проекта! Проверьте логи и исправьте ошибки."
    exit 1
fi

echo "Запускаем контейнеры..."
docker-compose up  --build

