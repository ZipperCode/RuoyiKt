FROM node:20-alpine AS build
LABEL authors="Zipper"
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
RUN mkdir -p /tmp/admin
# 构建admin
COPY ./admin /tmp/admin
WORKDIR /tmp/admin
RUN npm install --registry=https://registry.npmmirror.com
ARG NODE_ADMIN_ENV=""
RUN env ${NODE_ADMIN_ENV} npm run build:prod
# 检查生成的文件路径，假设正确的路径为 /home/admin/dist


FROM nginx:latest
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
RUN mkdir -p /usr/share/nginx/html/admin
COPY --from=build /tmp/admin/dist /usr/share/nginx/html/admin
CMD ["nginx", "-g", "daemon off;", "-c", "/etc/nginx/conf.d/nginx.conf"]