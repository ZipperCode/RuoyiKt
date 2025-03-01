FROM eclipse-temurin:17-jdk AS build
ENV GRADLE_OPTS="-Xmx1g -Xms512m -XX:MaxMetaspaceSize=512m"
WORKDIR /app
# 先复制所有文件
COPY . /app
# 添加执行权限
RUN chmod +x gradlew
RUN ./gradlew :server:clean :server:bootJar --no-daemon --no-parallel -x test

FROM eclipse-temurin:17-jdk
MAINTAINER zipper
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
WORKDIR /app
COPY --from=build /app/server/build/libs/app.jar app.jar

## 设置 TZ 时区
ENV TZ=Asia/Shanghai
## 设置 JAVA_OPTS 环境变量，可通过 docker run -e "JAVA_OPTS=" 进行覆盖
ENV JAVA_OPTS="-Xms256m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
## 应用参数
ENV ARGS=""
# 启动认证服务
#ENTRYPOINT ["java", "${JAVA_OPTS}", "-jar", "app.jar", "$ARGS"]
CMD java ${JAVA_OPTS} -jar app.jar $ARGS
## 启动后端项目
# CMD java ${JAVA_OPTS} -jar app.jar $ARGS
