ПРОЕКТ "Видеотека"
- 
Описание проекта.
  - Создание сайта с функциональностью сервиса онлайн-кинотеатра (онлайн-проката), без наполнения видео контентом. 
  - Структура хранения данных: БД catalog-db хранит информацию о фильмах и их параметрах (страны, режиссеры, жанры, цены, оценки и отзывы). БД orders-db хранит информацию о заказах пользователей. БД security-db содержит информацию о пользователях и ролях. Корзина пользователя хранится в Redis.
  - Предусмотрены роли пользователя, менеджера и админа. Пользователи сервиса имеют доступ к каталогу фильмов с описаниями, рейтингами и отзывами, а также фильтрацией по различным параметрам. Пользователь может выставлять оценки фильмам и писать отзывы. Пользователь может купить фильм (навсегда) или взять его в аренду (на определенный срок 24 часа). Менеджер имеет возможность добавлять фильмы и редактировать инофрмацию о фильмах, а также модерировать отзывы. Админ управляет доступами пользователей.
  - Реализован процесс отправки уведомлений на емэйл с помощью брокера очередей RabbitMQ. Уведомления отправляются при регистрации пользователя и покупке фильмов. Безопасность реализована с помощью JWT и поддерживается на двух уровнях — на уровне гейтвея проверка соответствия роли из токен эндпойнту запроса, а также на уровне эндпойнтов контроллеров с помощью аннотаций @Pre-Authorize 


Технологии 
- Spring Boot 2.7.9
- Maven 
- Микросервисная архитектура
- PostgreSQL
- FlyWay
- Redis
- RabbitMQ
- Spring Security
- JWT
- SpringData JPA
- Docker 
- Front-end: React

Инструкции по запуску 
1) склонировать проект с GitHub
2) распаковать secret.zip в корень проекта
3) сборка проекта: во вкладке Maven раскрыть Videoteka-Square. Выполнить clean, а затем package
4) запустить сборку docker — файл docker-compose.yaml
5) после старта всех контейнеров запустить проект в браузере: localhost:3000
