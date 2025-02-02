#!/bin/bash

echo "Начинаем установку"

if [ "$(id -u)" -ne 0 ]; then
    echo "Пожалуйста, запустите скрипт с правами root: sudo ./install.sh"
    exit 1
fi


CURRENT_USER=$(logname)
echo "Добавляем пользователя $CURRENT_USER в группу docker..."
usermod -aG docker $CURRENT_USER || { echo "  Ошибка при добавлении пользователя в группу docker!"; exit 1; }


echo "Обновляем список пакетов"
apt update -y || { echo "  Ошибка при обновлении пакетов!"; exit 1; }

echo "Обновляем установленные пакеты"
apt upgrade -y || { echo "  Ошибка при обновлении системы!"; exit 1; }


echo "Устанавливаем Docker"
apt install -y docker.io || { echo "  Ошибка установки Docker!"; exit 1; }


echo "Устанавливаем Git"
apt install -y git || { echo "  Ошибка установки Git!"; exit 1; }


echo "Устанавливаем Curl"
apt install -y curl || { echo "  Ошибка установки Curl!"; exit 1; }


echo "Устанавливаем Maven"
apt install -y maven || { echo "  Ошибка установки Maven!"; exit 1; }

if ! command -v docker-compose &> /dev/null; then
    echo "Загружаем Docker Compose..."
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose || { echo "  Ошибка скачивания Docker Compose!"; exit 1; }
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose || { echo "  Ошибка установки Docker Compose!"; exit 1; }
fi

echo "Проверяем версии"
docker --version || { echo "  Docker не установлен!"; exit 1; }
docker-compose --version || { echo "  Docker Compose не установлен!"; exit 1; }
mvn -version || { echo "  Maven не установлен!"; exit 1; }


echo "Удаляем старый код"
rm -rf /opt/SAST-SBER || { echo "  Ошибка при удалении старого проекта!"; exit 1; }

echo "Клонируем репозиторий"
cd /opt
git clone -b dev --single-branch https://github.com/4IPE/SAST-SBER.git || { echo "  Ошибка при клонировании репозитория!"; exit 1; }
cd SAST-SBER

echo "Сборка проекта с помощью Maven"
mvn clean install -DskipTests || { echo "  Ошибка сборки проекта! Проверьте логи."; exit 1; }

echo "Запускаем контейнеры."
docker-compose up -d --build || { echo "  Ошибка при запуске контейнеров!"; exit 1; }

