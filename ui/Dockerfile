## 阶段一，依赖创建
FROM node:20-alpine AS deps
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
RUN mkdir -p /tmp/admin
WORKDIR /tmp/admin
COPY ./admin/package*.json ./
RUN npm install --registry=https://registry.npmmirror.com

## 阶段2，构建项目
FROM node:20-alpine AS build
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
RUN mkdir -p /tmp/admin
# 构建admin
COPY ./admin /tmp/admin
WORKDIR /tmp/admin
COPY --from=deps /tmp/admin/node_modules ./node_modules
ENV NODE_OPTIONS="--max-old-space-size=2048"
ARG NODE_ADMIN_ENV=""
RUN env ${NODE_ADMIN_ENV} npm run build:prod

# 阶段3，构建nginx
FROM nginx:latest
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
RUN mkdir -p /usr/share/nginx/html/admin
COPY --from=build /tmp/admin/dist /usr/share/nginx/html/admin
CMD ["nginx", "-g", "daemon off;", "-c", "/etc/nginx/conf.d/nginx.conf"]