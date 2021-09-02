FROM adoptopenjdk/openjdk11:ubi
RUN mkdir /opt/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/app/todo-app.jar
CMD ["java", "-jar", "/opt/app/todo-app.jar"]
