Запуск приложения 
`./mvnw compile quarkus:dev`

Sql запросы для базы данных 
`src/main/resources/db/migration/migration.sql`

Конфигурационный файл для запуска программы
`src/main/resources/application.yml
`

Обработка асинхронных http запросов и редиректа
`src/main/java/org/acme/http/`

Фича для корректного сохранения данных в базу данных
`src/main/java/org/acme/db/hibernate/CamelSnakeCaseNamingStrategy.java`

обработка enum полей для базы данных
`src/main/java/org/acme/db/hibernate/PostgreSQLEnumType.java`

класс утилита для трансформаций
`src/main/java/org/acme/business/utils/TransformationUtils.java
`

валидация
`src/main/java/org/acme/business/utils/ValidateUtils.java
`

Объекты(модели)
`src/main/java/org/acme/business/models/`

Утилита для отправки уведомлений (в процессе)
`src/main/java/org/acme/business/jobs/`

Rest api контроллеры
`src/main/java/org/acme/business/controllers/`

Класс для обработки объектов(моделей)
`src/main/java/org/acme/business/controllers/BasicController.java`

Контроллер для проверки безопасности
`src/main/java/org/acme/business/controllers/ApiSecurityController.java`

базовый класс для работы с контроллерами, объектами и запросами
`src/main/java/org/acme/business/controllers/MainController.java`