# Crakkle Music 音乐平台

Crakkle Music 是一个包含听众端、管理端和服务端的音乐平台项目。项目提供歌曲浏览与播放、歌单、评论、歌手页面、用户账号及后台管理等功能。

> 本仓库包含课程项目代码和演示资源。首次运行前需要配置本地 MySQL 及文件资源路径。

## 项目结构

| 路径 | 说明 | 技术 |
| --- | --- | --- |
| [`music-client/`](music-client/) | 听众使用的网页，包括播放、搜索、歌单和个人页面 | Vue 2、Vue Router、Vuex、Element UI |
| [`music-manage/`](music-manage/) | 管理后台 | Vue 2、Element UI、ECharts |
| [`music-server/`](music-server/) | 提供接口和静态资源访问的服务端 | Java 8、Spring Boot 2.7、MyBatis-Plus |
| [`music.sql`](music.sql) | MySQL 数据库结构与初始数据 | MySQL |
| [`ziyuanwenjian/`](ziyuanwenjian/) | 项目使用的资源文件 | — |
| [`Crakkle Music Platform Project Description.markdown`](Crakkle%20Music%20Platform%20Project%20Description.markdown) | 更详细的项目设计说明 | — |

## 本地运行

### 1. 准备环境

- JDK 8、MySQL、Node.js 与 npm。
- 前端依赖包含较旧的 Webpack 3 和 `node-sass` 4；建议使用与这些依赖兼容的旧版 Node.js 环境。较新的 Node.js 版本可能无法直接安装依赖。
- 服务端目录提供 Maven Wrapper，无需预先安装全局 Maven。

### 2. 初始化数据库

创建名为 `music` 的数据库，然后导入仓库根目录的 SQL 文件：

```sh
mysql -u root -p -e 'CREATE DATABASE IF NOT EXISTS music DEFAULT CHARACTER SET utf8mb4;'
mysql -u root -p music < music.sql
```

`music.sql` 会执行 `DROP TABLE IF EXISTS`，请只导入到用于本项目的数据库，避免覆盖已有数据。

### 3. 配置并启动服务端

编辑 [`music-server/src/main/resources/application.properties`](music-server/src/main/resources/application.properties)：

- 将 `spring.datasource.username` 和 `spring.datasource.password` 改为本地 MySQL 账号信息；数据库地址默认是 `localhost:3306/music`。
- 将 `resource.base-path` 改为本机仓库中 `ziyuanwenjian` 的**绝对路径**。仓库中的默认值指向原开发者的电脑，不能直接复用。

然后运行：

```sh
cd music-server
./mvnw spring-boot:run
```

Windows 可使用 `mvnw.cmd spring-boot:run`。服务端默认监听 `http://localhost:8888`。

### 4. 启动听众端

在另一个终端运行：

```sh
cd music-client
npm install
npm run dev
```

打开 `http://localhost:8081`。听众端的接口地址在 [`music-client/src/config/index.js`](music-client/src/config/index.js) 中配置，开发环境默认指向 `http://localhost:8888`。

### 5. 启动管理端

在第三个终端运行：

```sh
cd music-manage
npm install
npm run dev
```

打开 `http://localhost:8080`。若管理端无法连接服务端，请检查其前端接口地址以及服务端的跨域配置。

## 常用命令

两个前端目录均提供以下 npm 脚本：

```sh
npm run dev    # 开发服务器
npm run build  # 生成生产构建
npm run lint   # 代码检查
npm run unit   # 单元测试
npm run e2e    # 端到端测试
```

在 `music-server/` 中可使用 `./mvnw test` 运行服务端测试。

## 配置提示

- 开发环境端口分别为：服务端 `8888`、听众端 `8081`、管理端 `8080`。如果修改端口，也要同步修改前端接口地址和服务端允许的跨域来源。
- 听众端生产环境接口地址目前是占位域名；部署前需修改 [`music-client/src/config/index.js`](music-client/src/config/index.js)。
- 项目目录中包含已提交的 `node_modules`，但安装和运行仍应以各前端目录的 `package.json` 与锁文件为准。

## 更多信息

角色与模块设计见[项目说明文档](Crakkle%20Music%20Platform%20Project%20Description.markdown)。
