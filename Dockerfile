# 使用官方的 Maven 镜像作为构建阶段的基础镜像
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# 将项目的源代码复制到容器中
COPY src/test/java .

# 使用 Maven 构建项目
RUN mvn clean package -DskipTests

# 使用官方的 OpenJDK 运行时镜像作为运行阶段的基础镜像
FROM openjdk:17-jdk-slim
WORKDIR /app

# 从构建阶段复制生成的 jar 文件到运行阶段
COPY --from=build /app/target/bigevent.jar .

# 暴露应用程序监听的端口
EXPOSE 8080

# 运行应用程序
CMD ["java", "-jar", "your-application.jar"]
