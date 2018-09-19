FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=qnadb
ENV MYSQL_USER=app_root
ENV MYSQL_PASSWORD=root123
ENV MYSQL_CI_URL=jdbc:mysql://localhost:3306/qnadb
ADD ./target/Server-1.0.jar /usr/src/Server-1.0.jar
ENTRYPOINT ["java","-jar","Server-1.0.jar"]