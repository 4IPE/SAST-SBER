#!/bin/bash

echo "Начинаем установку..."


if [ "$(id -u)" -ne 0 ]; then
    echo "⚠️  Пожалуйста, запустите скрипт с правами root: sudo ./install.sh"
    exit 1
fi


CURRENT_USER=$(logname)
echo "Добавляем пользователя $CURRENT_USER в группу docker..."
usermod -aG docker $CURRENT_USER


echo "Обновляем пакеты"
apt update -y && apt upgrade -y

echo "Устанавливаем Docker и Docker Compose"
apt install -y docker.io git curl

if ! [ -x "$(command -v docker-compose)" ]; then
    echo "Загружаем Docker Compose"
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
fi

echo "Проверяем версии"
docker --version
docker-compose --version


echo "Клонируем репозиторий."
cd /opt
git clone -b dev --single-branch https://github.com/4IPE/SAST-SBER.git
cd SAST-SBER

echo "Запускаем контейнеры"
docker-compose up
