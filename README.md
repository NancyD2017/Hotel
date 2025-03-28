# Hotel

## Описание проекта

Task Management — это REST API сервис для управления отелями, их номерами, бронированиями и системой оценивания.

## Стек технологий

- Java 17+
- Spring Boot, Spring Security
- Kafka
- MongoDB (как база данных)
- Gradle (для сборки проекта)
- Docker и Docker Compose

## Установка и запуск

1. Клонирование репозитория

Перейдите в папку, в которой Вы желаете сохранить проект и вызовите в ней командную строку. Выполните команды:

git clone https://github.com/NancyD2017/Hotel.git

cd src/main/resources

2. Запуск инфраструктуры (MongoDB)

docker-compose up -d

3. Запуск приложения

cd ..

cd ..

cd ..

.\gradlew bootRun

4. После успешного запуска API доступно по адресу:

http://localhost:8080/api/

## Функционал системы бронирования отелей

1. Управление пользователями (UserService)

Регистрация пользователя

Поиск пользователя по ID

Обновление данных пользователя

Удаление пользователя

Проверка существования пользователя

Интеграция с Kafka: Отправка события о новом пользователе в топик statistics-users.

2. Управление отелями (HotelService)

Добавление/редактирование отеля

CRUD-операции для объектов отелей.

Фильтрация отелей: название, город, адрес, рейтинг, удаленность от центра.

Получение отелей постранично с указанием номера страницы и размера.

Система рейтингов с валидацией

3. Управление номерами (RoomService)

Добавление/редактирование номеров

Расширенный поиск номеровс фильтрами

Динамическая пагинация

4. Система бронирования (BookingService)

Создание брони

Просмотр всех броней (для администраторов).

Интеграция с Kafka: Отправка данных о бронировании в топик statistics-bookings.

5. Сбор статистики (StatisticsService)

Логирование событий

Регистрация пользователей

Создание бронирований

Экспорт данных

Получение всей статистики в виде списка

Выгрузка в CSV-файл

Хранение
