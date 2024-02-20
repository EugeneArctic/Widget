# Быстрый старт 


Билд докер образа
```docker build . -t widget ```   
Запуск докер образа
```docker run -dp 8080:8080 widget ```

# Документация по проекту
## API  
Веб-сервис для работы с виджетами через HTTP REST API. Сервис хранит только виджеты, предполагая, что все клиенты работают с одной и той же доской. 

Документация swagger по методам апи: http://localhost:8080/swagger-ui/index.html

Переключение хранения виджетов в application.properties :

```spring.profiles.active=sqlDB``` - в h2 базе

```spring.profiles.active=in-mem```- в памяти

## Database
В application.properties надо поменять на папку где планируете хранить h2 database

```spring.datasource.url=jdbc:h2:file:/Users/yvgolo17/test;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE```

Подключение к консоле базы данных доступно по  http://localhost:8080/h2-console/

В поле JDBC URL укажите: ```jdbc:h2:/path-to-local-database```