#Давайте разберем ваш Dockerfile слой за слоем:
#
#1. FROM eclipse-temurin:17-jdk-focal as build
#
#   Этот слой используется для создания базового образа из eclipse-temurin:17-jdk-focal. Это официальный образ Docker, который предоставляет среду выполнения Java и JDK версии 17, базирующуюся на Ubuntu Focal. Этот слой является начальной точкой для сборки вашего приложения, где as build означает, что все последующие инструкции до следующего FROM будут относиться к этапу сборки build.
#
#2. WORKDIR /build
#
#   Устанавливает рабочую директорию в контейнере на /build. Все последующие инструкции, такие как COPY и RUN, будут выполняться относительно этого пути.
#
#3. COPY .mvn/ ./.mvn
#
#   Копирует директорию .mvn из вашего локального контекста сборки в директорию ./.mvn в контейнере. Это необходимо для Maven Wrapper, который используется для сборки проекта.
#
#4. COPY mvnw pom.xml  ./
#
#   Копирует файлы mvnw (Maven Wrapper, исполняемый скрипт) и pom.xml (конфигурационный файл Maven, описывающий проект и его зависимости) в текущую рабочую директорию в контейнере.
#
#5. RUN ./mvnw dependency:go-offline
#
#   Запускает Maven Wrapper с целью dependency:go-offline, которая скачивает все зависимости проекта, необходимые для сборки, чтобы они были доступны в офлайне. Это ускоряет последующие сборки, поскольку зависимости уже будут закешированы в образе.
#
#6. COPY . .
#
#   Копирует оставшуюся часть вашего проекта (исключая то, что указано в .dockerignore) в текущую рабочую директорию в контейнере. Это гарантирует, что вся ваша кодовая база доступна для сборки в контейнере.
#
#7. RUN ./mvnw package -DskipTests
#
#   Запускает сборку проекта с помощью Maven Wrapper, компилируя исходный код и упаковывая его в JAR-файл. Флаг -DskipTests указывает Maven пропустить выполнение тестов, что ускоряет процесс сборки.
#
#8. FROM eclipse-temurin:17-jdk-alpine
#
#   Начинает новый этап сборки на основе образа eclipse-temurin:17-jdk-alpine, который также предоставляет среду выполнения Java и JDK версии 17, но базируется на более легковесном дистрибутиве Linux - Alpine. Это делает конечный образ меньше по размеру.
#
#9. WORKDIR /app
#
#   Устанавливает рабочую директорию в контейнере на /app для этапа выполнения.
#
#10. COPY --from=build /build/target/*.jar run.jar
#
#    Копирует JAR-файл, созданный на этапе сборки, из директории /build/target/ в рабочую директорию текущего слоя, переименовывая его в run.jar. Используется --from=build для указания, что копирование осуществляется из предыдущего этапа сборки.
#
#11. ENTRYPOINT ["java", "-jar", "/app/run.jar"]

FROM eclipse-temurin:17-jdk-focal as build

WORKDIR /build

COPY .mvn/ ./.mvn
COPY mvnw pom.xml  ./
RUN ./mvnw dependency:go-offline

COPY . .
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /build/target/*.jar run.jar
ENTRYPOINT ["java", "-jar", "/app/run.jar"]