## 技术规划

### 基础

- [ ] 引入 apijson 试验 // 放弃
- [ ] 引入 amis

### 云资源支持

- OSS [OSSUnity](https://github.com/linkxs/OSSUnity) [Dante OSS](https://gitee.com/herodotus/dante-oss)
- 

### 异常处理

- [ ] 引入 [graceful-response](https://doc.feiniaojin.com/graceful-response/home.html)
- [x] 固定返回报文
- [x] 统一错误类型
- [ ] 全局统一异常处理

### 数据库处理

- [x] 引入 mybatis-plus-ext
- [x] 自动建表
- [x] 自动 Mapper 和 Repository
- [ ] 分页
- [ ] 实体关连
- [ ] 使用 ServiceImpl 中的操作消息
- [x] `FwQueryKit` Bean 转 Wrapper 类

### 接口

- [x] 引入 knife4j, openapi3版本

### 特性

- [x] 配置类型化 /api/sys_config/site

### 缓存相关

- [ ] 引入 redis
- [ ] 使用 Cache

### 解耦

- [ ] 引入 MessageQueue

### 权限安全

- [x] 引入 spring-security
- [x] RBAC 设计
- [x] 实现 jwt 登录
- [ ] @Expose/@NotExpose 暴露/不暴露接口
- [ ] @Permission/@Role 接口鉴权，快速实现用户、角色、权限功能
- [ ] @Ban 功能禁用，快速实现禁用用户评论、发帖等功能
- [ ] @Log 快速实现日志记录
- [ ] AuthUtil.getUser refresh login hasPermission

### 文件管理

- [ ] MinIO

### 搜索

- [ ] ES等

### 微信相关

- 登录

### 接口安全

- [ ] 签名
- [ ] 加密
- [ ] ip白名单
- [ ] 限流
- [x] 参数校验
- [ ] 请求日志
- [ ] 幂等设计
- [ ] 限制记录条数
- [ ] 异步处理
- [ ] 数据脱敏

### 基础 CRUD

- [x] 单个创建
- [x] 单个删除
- [x] 单个更新
- [x] 单个查询
- [x] 查询列表
- [x] 查询分页
- [x] 批量创建
- [x] 批量删除
- [x] 批量更新
- [x] 批量查询
- [ ] 查询列表导出
- [ ] 查询分页导出

## 功能规划

### demo

- GTD
  - [x] TodoItem
  - [ ] Label / Project
- Blog
  - [ ] Article

### 第三方对接

- [ ] 支付 [day-pay](https://github.com/dromara/dax-pay)
- [ ] 微信 WxJava
- [ ] 短信 [SMS4J](https://github.com/dromara/SMS4J)
- 

## 开发部署

### 开发

#### gradle

- [ ] gradle 配置

#### maven

- [x] maven 配置
- [x] maven-优先使用国内镜像仓库
- [x] maven 引入和配置 spotless 插件
- [x] `dao`、`kit`、`biz` 模块化

#### 其他

- [x] 引入 JavaPoet，重复代码生成
- [ ] 引入 spoon，重复代码生成 // 待定
- [ ] JavaPoet 生成代码优化
  - [ ] 注解配置 / 配置文件
  - [ ] 存在不重复生成

### 部署

## 前端

### 接口

- [ ] https://github.com/reeli/ts-codegen

