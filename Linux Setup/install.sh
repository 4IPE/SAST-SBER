#!/bin/bash

echo "Начинаем установку..."

# Проверяем, запущен ли скрипт с root-правами
if [ "$(id -u)" -ne 0 ]; then
    echo "Пожалуйста, запустите скрипт с правами root: sudo ./install.sh"
    exit 1
fi

# Добавляем текущего пользователя в группу docker
CURRENT_USER=$(logname)
echo "Добавляем пользователя $CURRENT_USER в группу docker..."
usermod -aG docker $CURRENT_USER || { echo "Ошибка при добавлении пользователя в группу docker"; exit 1; }

# Обновляем систему
echo "Обновляем список пакетов..."
apt update -y || { echo "Ошибка при обновлении пакетов"; exit 1; }

echo "Обновляем установленные пакеты..."
apt upgrade -y || { echo "Ошибка при обновлении системы"; exit 1; }

# Функция проверки установки пакета
install_if_not_exists() {
    PACKAGE=$1
    if dpkg -s "$PACKAGE" &>/dev/null; then
        echo "$PACKAGE уже установлен. Пропускаем установку."
    else
        echo "Устанавливаем $PACKAGE..."
        apt install -y "$PACKAGE" || { echo "Ошибка установки $PACKAGE"; exit 1; }
    fi
}

# Установка пакетов, если они не установлены
install_if_not_exists "docker.io"
install_if_not_exists "git"
install_if_not_exists "curl"
install_if_not_exists "maven"

# Установка Docker Compose (если отсутствует)
if ! command -v docker-compose &> /dev/null; then
    echo "Загружаем и устанавливаем Docker Compose..."
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose || { echo "Ошибка скачивания Docker Compose"; exit 1; }
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose || { echo "Ошибка установки Docker Compose"; exit 1; }
else
    echo "Docker Compose уже установлен. Пропускаем установку."
fi

# Проверяем версии установленных программ
echo "Проверяем версии..."
docker --version || { echo "Docker не установлен"; exit 1; }
docker-compose --version || { echo "Docker Compose не установлен"; exit 1; }
mvn -version || { echo "Maven не установлен"; exit 1; }

# Удаляем старый код, если он есть
echo "Удаляем старый код (если есть)..."
rm -rf /opt/SAST-SBER || { echo "Ошибка при удалении старого проекта"; exit 1; }

# Клонируем репозиторий
echo "Клонируем репозиторий (ветка dev)..."
cd /opt
git clone -b dev --single-branch https://github.com/4IPE/SAST-SBER.git || { echo "Ошибка при клонировании репозитория"; exit 1; }
cd SAST-SBER

# Сборка проекта через Maven
echo "Сборка проекта с помощью Maven..."
mvn clean install -DskipTests || { echo "Ошибка сборки проекта"; exit 1; }

# Запуск контейнеров с пересборкой образов
echo "Запускаем контейнеры..."
docker-compose up -d --build || { echo "Ошибка при запуске контейнеров"; exit 1; }

