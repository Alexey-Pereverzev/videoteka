ПРОЕКТ "Видеотека"
- 
Описание проекта.
  - Создание сайта с функциональностью сервиса онлайн-кинотеатра (онлайн-проката), без наполнения видео контентом. Пользователи сервиса будут иметь возможность доступа к каталогу фильмов с описаниями, рейтингами и отзывами, а также фильтрацией по различным параметрам. Пользователь может выставлять оценки фильмам и писать отзывы. 
  - Предусмотрены отдельные роли менеджера и админа. Менеджер имеет возможность добавлять фильмы и редактировать инофрмацию о фильмах, а также модерировать отзывы. Админ управляет доступами пользователей. 


Технологии 
- Spring Boot 2.7.9
- Maven 
- Микросервисная архитектура
- PostgreSQL
- FlyWay
- Redis
- RabbitMQ
- Docker 
- Front-end: React

Инструкции по запуску 
1) склонировать проект с GitHub
2) распаковать secret.zip в корень проекта
3) сборка проекта: Во вкладке Maven раскрыть Videoteka-Square. Выполнить clean, а затем package
4) запустить сборку docker - файл docker-compose.yaml
5) после старта всех контейнеров запустить проект в браузере: localhost:3000