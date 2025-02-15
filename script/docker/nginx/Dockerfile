# nginx镜像
FROM nginx:latest
# author
MAINTAINER zipper
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
# 挂载目录
VOLUME /home/ruoyi/www
# 创建目录
RUN mkdir -p /home/ruoyi/www
# 指定路径
WORKDIR /home/ruoyi/www