# 基础镜像
FROM redis:7-alpine
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
# 挂载目录
VOLUME /home/redis
# 创建目录
RUN mkdir -p /home/redis && mkdir -p /home/redis/data && chmod 777 /home/redis/data
# 指定路径
WORKDIR /home/redis