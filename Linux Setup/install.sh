#!/bin/bash

echo "Начинаем установку..."

if [ "$(id -u)" -ne 0 ]; then
    echo "Пожалуйста, запустите скрипт с правами root: sudo ./install.sh"
    exit 1
fi

CURRENT_USER=$(logname)
echo "Добавляем пользователя $CURRENT_USER в группу docker..."
usermod -aG docker $CURRENT_USER || { echo "Ошибка при добавлении пользователя в группу docker"; exit 1; }

echo "Обновляем список пакетов..."
apt update -y || { echo "Ошибка при обновлении пакетов"; exit 1; }

echo "Обновляем установленные пакеты..."
apt upgrade -y || { echo "Ошибка при обновлении системы"; exit 1; }

install_if_not_exists() {
    PACKAGE=$1
    if dpkg -s "$PACKAGE" &>/dev/null; then
        echo "$PACKAGE уже установлен. Пропускаем установку."
    else
        echo "Устанавливаем $PACKAGE..."
        apt install -y "$PACKAGE" || { echo "Ошибка установки $PACKAGE"; exit 1; }
    fi
}

echo "Доступные версии Amazon Corretto (от 21 и выше):"
AVAILABLE_VERSIONS=$(apt-cache search amazon-corretto | awk '{print $1}' | grep "java-[2-9][0-9]*-amazon-corretto-jdk")

if [[ -z "$AVAILABLE_VERSIONS" ]]; then
    echo "   Нет доступных версий Amazon Corretto 21 и выше!"
    exit 1
fi

echo "$AVAILABLE_VERSIONS"

while true; do
    read -p "Введите желаемую версию Amazon Corretto (21 или выше): " JAVA_VERSION
    if [[ ! "$JAVA_VERSION" =~ ^[2-9][0-9]*$ ]]; then
        echo "   Ошибка: Введите корректную версию от 21 и выше!"
    elif ! echo "$AVAILABLE_VERSIONS" | grep -q "java-${JAVA_VERSION}-amazon-corretto-jdk"; then
        echo "   Ошибка: Версия $JAVA_VERSION недоступна!"
    else
        break
    fi
done

PACKAGE_NAME="java-${JAVA_VERSION}-amazon-corretto-jdk"

if dpkg -l | grep -q "$PACKAGE_NAME"; then
    echo "Выбранная версия ($PACKAGE_NAME) уже установлена."
else
    echo "Устанавливаем Amazon Corretto $JAVA_VERSION..."
    apt install -y "$PACKAGE_NAME" || { echo "Ошибка установки Amazon Corretto $JAVA_VERSION"; exit 1; }
fi

echo "Настраиваем Amazon Corretto $JAVA_VERSION как основную версию..."
update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-${JAVA_VERSION}-amazon-corretto/bin/java 200
update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/java-${JAVA_VERSION}-amazon-corretto/bin/javac 200

echo "Выберите нужную версию Java вручную:"
sudo update-alternatives --config java

java -version || { echo "Java не установлена"; exit 1; }


if ! command -v mvn &> /dev/null; then
    echo "Устанавливаем Maven..."
    apt install -y maven || { echo "Ошибка установки Maven"; exit 1; }
else
    echo "Maven уже установлен. Пропускаем установку."
fi

mvn -version || { echo "Maven не установлен"; exit 1; }

if ! command -v docker &> /dev/null; then
    echo "Устанавливаем Docker..."
    apt install -y docker-ce docker-ce-cli containerd.io || { echo "Ошибка установки Docker"; exit 1; }
else
    echo "Docker уже установлен. Пропускаем установку."
fi

install_if_not_exists "git"
install_if_not_exists "curl"

if ! command -v docker-compose &> /dev/null; then
    echo "Загружаем и устанавливаем Docker Compose..."
    curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose || { echo "Ошибка скачивания Docker Compose"; exit 1; }
    chmod +x /usr/local/bin/docker-compose
    ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose || { echo "Ошибка установки Docker Compose"; exit 1; }
else
    echo "Docker Compose уже установлен. Пропускаем установку."
fi

echo "Проверяем версии..."
docker --version || { echo "Docker не установлен"; exit 1; }
docker-compose --version || { echo "Docker Compose не установлен"; exit 1; }

echo "Удаляем старый код (если есть)..."
rm -rf /opt/SAST-SBER || { echo "Ошибка при удалении старого проекта"; exit 1; }

echo "Клонируем репозиторий "
cd /opt
git clone -b dev --single-branch https://github.com/4IPE/SAST-SBER.git || { echo "Ошибка при клонировании репозитория"; exit 1; }
cd SAST-SBER

echo "Сборка библиотеки sast-dto..."
cd sast-dto
mvn clean install -DskipTests || { echo "Ошибка сборки sast-dto"; exit 1; }
cd ..

echo "Сборка всего проекта..."
mvn clean install -DskipTests || { echo "Ошибка сборки проекта"; exit 1; }

echo "Запускаем контейнеры..."
docker-compose up -d --build || { echo "Ошибка при запуске контейнеров"; exit 1; }
