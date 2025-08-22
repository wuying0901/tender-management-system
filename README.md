# 招标信息管理系统

一个基于Vue 3 + Spring Boot的现代化招标信息管理系统，提供招标信息的查询、展示和管理功能。

## 技术栈

### 前端
- Vue 3 (Composition API)
- Vue Router 4
- Vuex 4
- Element Plus (UI组件库)
- Axios (HTTP客户端)
- Day.js (日期处理)

### 后端
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (内存数据库)
- Lombok
- Maven

## 项目结构

```
zhaobiao/
├── src/                          # 后端源码
│   └── main/
│       ├── java/com/zhaobiao/
│       │   ├── controller/       # 控制器层
│       │   ├── service/          # 服务层
│       │   ├── repository/       # 数据访问层
│       │   ├── entity/           # 实体类
│       │   └── dto/              # 数据传输对象
│       └── resources/
│           ├── application.yml   # 配置文件
│           └── data.sql          # 初始数据
├── frontend/                     # 前端源码
│   ├── src/
│   │   ├── views/               # 页面组件
│   │   ├── components/          # 通用组件
│   │   ├── router/              # 路由配置
│   │   ├── store/               # 状态管理
│   │   ├── api/                 # API接口
│   │   └── assets/              # 静态资源
│   ├── public/                  # 公共文件
│   └── package.json             # 前端依赖
├── pom.xml                      # Maven配置
└── README.md                    # 项目说明
```

## 功能特性

### 招标信息管理
- 招标项目列表展示
- 项目详情查看
- 高级搜索和筛选
- 分页显示

### 交互功能
- 项目跟进
- 日程提醒
- 项目笔记
- 联系招标方
- 附件下载

### 用户体验
- 响应式设计
- 现代化UI界面
- 实时数据更新
- 移动端适配

## 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- Maven 3.6+

### 后端启动

1. 进入项目根目录
```bash
cd zhaobiao
```

2. 使用Maven编译和运行
```bash
# 使用项目指定的Maven路径
D:\maven\apache-maven-3.9.5\bin\mvn clean install
D:\maven\apache-maven-3.9.5\bin\mvn spring-boot:run
```

或者使用系统Maven
```bash
mvn clean install
mvn spring-boot:run
```

3. 后端服务将在 http://localhost:8080 启动

### 前端启动

1. 进入前端目录
```bash
cd frontend
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run serve
```

4. 前端应用将在 http://localhost:3000 启动

## API接口

### 招标项目相关
- `GET /api/tender-projects` - 获取项目列表
- `GET /api/tender-projects/{id}` - 获取项目详情
- `GET /api/tender-projects/search` - 搜索项目
- `POST /api/tender-projects` - 创建项目
- `PUT /api/tender-projects/{id}` - 更新项目
- `DELETE /api/tender-projects/{id}` - 删除项目

### 辅助接口
- `GET /api/tender-projects/locations` - 获取地区列表
- `GET /api/tender-projects/categories` - 获取分类列表

## 数据库

项目使用H2内存数据库，启动时会自动创建表结构并插入示例数据。

- 数据库控制台：http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- 用户名: `sa`
- 密码: `password`

## 部署

### 后端部署
```bash
mvn clean package
java -jar target/tender-management-0.0.1-SNAPSHOT.jar
```

### 前端部署
```bash
cd frontend
npm run build
```

构建后的文件在 `frontend/dist` 目录中。

## 开发说明

### 添加新的招标项目
1. 在 `src/main/resources/data.sql` 中添加INSERT语句
2. 重启应用，数据会自动加载

### 自定义样式
- 前端样式文件位于 `frontend/src/views/` 各组件中
- 使用Element Plus组件库，支持主题定制

### API扩展
- 在 `src/main/java/com/zhaobiao/controller/` 中添加新的控制器
- 在 `src/main/java/com/zhaobiao/service/` 中添加业务逻辑
- 在 `frontend/src/api/` 中添加对应的API调用

## 注意事项

1. 确保Maven路径正确配置
2. 前端开发时后端服务需要运行
3. 首次启动会自动创建数据库表
4. 示例数据仅供演示使用

## 许可证

MIT License
