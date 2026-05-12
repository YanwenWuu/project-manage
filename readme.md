# 一、开发规范
## 1 适用范围
项目所有微服务统一遵循该结构：xxx-sdk + xxx-service，后续新增服务严格对齐
## 2 整体架构原则
**分层单向依赖**：Adapter → Application → Domain ← Infrastructure

核心业务逻辑只允许写在 domain 层

对外接口、DTO、枚举、Feign 契约全部下沉到 xxx-sdk

所有服务目录、包名、命名、分层职责完全统一
## 3 模块划分规范
### 3.1 xxx-sdk 契约模块
**定位**：仅做跨服务调用契约，无业务逻辑、无框架依赖。

**目录结构**：
```plaintext
xxx-sdk
├── dto
│   ├── request     # 外部调用入参DTO
│   └── response    # 对外出参DTO
├── enums           # 对外公开枚举
└── feign           # Feign客户端接口
```
**约束**：
- SDK 只允许依赖基础包，不能引入业务、MyBatis、Redis 等
- DTO 只做数据载体，不写业务规则
- 其他微服务只能引用 SDK，禁止直接依赖业务服务
### 3.2 xxx-service 业务服务模块
固定一级包结构：
``` plaintext
com.xxx.xxx
├── adapter         # 适配层
├── application     # 应用层
├── domain          # 领域核心层
├── infrastructure  # 基础设施层
├── config          # 配置类
├── mapper          # MyBatis Mapper
└── XxxApplication # 启动类
```
#### 3.2.1 adapter 适配层
``` plaintext
adapter
├── controller      # HTTP 接口控制器
└── wrapper         # 外部防腐层（第三方接口、MQ、外部RPC适配）
```
**职责约束**： 
- 只做：参数校验、请求转发、结果封装 
- 禁止写任何业务逻辑 
- 只能调用 application 层 
- 入参出参优先使用 sdk 中 DTO
#### 3.2.2 application 应用层
``` plaintext
application
├── service         # 应用服务（流程编排、事务）
└── converter   # DTO/领域对象转换器
```
**职责约束**： 
- 负责：业务流程编排、事务控制、对象转换 
- 不写核心业务规则，业务规则下沉 domain 
- 所有对象转换统一收敛到 converter 
- 事务注解 @Transactional 只允许加在应用层
#### 3.2.3 domain 领域层（DDD 核心）
``` plaintext
domain
├── entity      # 领域实体
├── vo          # 值对象
├── repository  # 仓储接口（仅定义，不实现）
├── service     # 领域服务（纯业务规则）
└── common      # 领域公共组件
```
domain/common 固定存放： 
- 基础领域实体父类 BaseEntity
- 领域业务异常 DomainException
- 业务常量、业务校验工具（无框架依赖）

**约束**：
- domain 层不依赖任何基础设施、Redis、DB、HTTP
- 领域实体不只存字段，可包含自身业务行为方法
- Repository 只定义接口，实现放在 infrastructure
#### 3.2.4 infrastructure 基础设施层
```plaintext
infrastructure
├── repository      # 仓储接口实现类
└── utils           # 技术工具类
```
**职责约束**：
- 实现 domain 层 Repository 接口
- 封装 DB、Redis、加密、JWT、第三方调用等技术细节
- 只做技术实现，不编排业务流程
#### 3.2.5 config / mapper
- config：所有配置类（MyBatis、Redis、Feign、跨域、线程池等）
- mapper：MyBatis 接口，仅做 CRUD，无业务逻辑
## 4 DTO 转换统一规则
**流转顺序**：
SDK请求DTO → Application内部DTO → Domain实体

Domain实体 → Application内部DTO → SDK响应DTO

所有转换代码统一放在 application.common.converter

Controller、ApplicationService 禁止散落写转换逻辑
## 5 命名规范
- 包名：全小写，固定单词不变：adapter、application、domain、infrastructure
DTO：入参 XxxReq，出参 XxxResp
- 领域实体：XxxEntity
- 值对象：XxxVO
- 应用服务：XxxApp
- 领域服务：XxxDomainService
- 转换器：XxxConverter
## 6 异常统一规范
- 业务校验异常：domain 层抛出 XXException
- 全局异常统一在 adapter 层全局异常处理器捕获封装
- 禁止在 Controller、Service 随意 try-catch 吞异常
## 7 全局依赖约束
- 禁止逆向依赖：domain 不准依赖 application、infrastructure

# 二、项目整体模块依赖与职责说明

## 项目模块总览

project-manage
├── auth-sdk               # 认证服务对外契约模块
├── auth-service           # 认证服务核心实现
├── common                 # 项目公共依赖/基础组件
├── gateway-service        # 网关服务
├── orchestration-sdk      # 编排服务对外契约模块
├── orchestration-service  # 编排服务（施工策划）
├── project-sdk            # 项目服务对外契约模块
├── project-service        # 项目核心业务服务
├── workflow-sdk           # 工作流对外契约模块
├── workflow-service       # 工作流引擎服务
└── pom.xml                # 父pom，统一管理版本与依赖



### 1 模块依赖关系（单向依赖，无循环）
```text
- gateway-service        → common, auth-sdk
- auth-service           → common, auth-sdk
- auth-sdk               → common
- project-service        → common, auth-sdk, workflow-sdk, orchestration-sdk
- project-sdk            → common
- workflow-service       → common
- workflow-sdk           → common
- orchestration-service  → common, auth-sdk, workflow-sdk
- orchestration-sdk      → common
- common                 → 无业务依赖

```
### 2 依赖原则
- 所有模块均为同级，无上下级关系。
- 服务间调用必须依赖对方的 xxx-sdk，不能直接依赖 xxx-service。
- 所有业务模块均依赖 common 公共组件。
- 禁止循环依赖。
- 父 pom.xml 统一管理所有依赖版本，各子模块禁止私自定义版本号
### 3 各模块职责与详细说明
**职责**：项目所有微服务共享的基础组件与工具包。

**存放内容**：
- 统一返回体 `Result`
- 全局异常基类、通用工具类
- 常量定义、项目通用枚举
- 通用配置类（Jackson 序列化、时间格式化、跨域全局配置等）
- 通用技术工具类（加密、日期处理、JSON 解析、线程池工具等）

**依赖规则**：
- 无任何业务模块依赖，仅依赖 Spring 基础及第三方基础工具包
- 项目内其他所有微服务、SDK 模块必须依赖 common
#### 3.2 auth-sdk + auth-service
##### auth-sdk
**职责**：认证服务对外契约，供所有需要鉴权的微服务调用

**存放内容**：
- 登录 / 鉴权相关的 DTO（request/response）
- 对外公开枚举（认证状态、登录类型）
- Feign 客户端接口定义
- 
**依赖**：
- 仅依赖 common
- 所有需要鉴权的服务（gateway、project、orchestration 等）只依赖 auth-sdk，不直接依赖 auth-service
##### auth-service
**职责**：认证中心核心实现，负责用户登录、鉴权、Token 管理
**存放内容**：
- 用户认证领域模型（用户、权限、Token）
- 登录、鉴权、Token 刷新业务逻辑
- 用户信息数据库操作

**依赖**：common
- 不被其他服务直接依赖，仅通过 auth-sdk 对外提供接口
####  3.3 gateway-service
**职责**：项目统一入口网关，负责请求路由、鉴权、限流、日志

**核心能力**：
- 统一入口路由分发到各业务服务 
- 请求鉴权（依赖 auth-sdk 校验 Token） 
- 全局日志、请求 ID 传递 
- 限流、熔断、跨域处理

**依赖**：common、auth-sdk
所有外部请求必须通过 gateway 进入，不允许直接访问业务服务

#### 3.4 project-service
**职责**：项目管理核心业务服务，处理项目创建、维护、权限分配

**核心能力**：
- 项目 CRUD、状态管理 
- 项目成员权限管理

**依赖**：common、auth-sdk

**调用关系**：
依赖 auth-sdk 做用户身份校验(本次可选)
#### 3.5 workflow-service
**职责**：工作流引擎服务，负责流程定义、流程实例执行、任务流转
**核心能力**：
- 流程定义管理（新增、修改、部署） 
- 流程实例启动、推进、状态管理 
- 任务节点分配、状态变更

**依赖**：common
为 project-service、orchestration-service 提供流程执行能力
#### 3.6 orchestration-service
**职责**：编排本次施工策划与流程服务

**核心能力**： 跨服务任务编排、流程调度

**依赖**：common、auth-sdk

与 workflow-service 分工：
workflow-service：专注流程引擎本身（节点、流转、状态）
orchestration-service：专注跨服务、跨系统的业务流程编排（针对本次施工策划）
