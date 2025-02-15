# 基础镜像
FROM mysql:8.4.3
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
ENV TZ=Asia/Shanghai
# 执行sql脚本
ADD ./*.sql /docker-entrypoint-initdb.d/
RUN chmod -R 775 /docker-entrypoint-initdb.d
