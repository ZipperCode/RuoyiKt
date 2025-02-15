#!bin/bash
# 创建gradle缓存卷
docker volume create --name gradle-repo

# 构建jar
docker run -it --rm --name yudao-gradle \
    -v yudao-gradle-cache:/home/gradle/.gradle \ # Gradle 缓存目录
    -v $PWD:/home/gradle/src \ # 项目源码目录
    -w /home/gradle/src \ # 设置工作目录
    gradle:jdk-17 \ # 使用 Gradle 镜像 (可指定版本)
    gradle clean build -x test # Gradle 构建命令