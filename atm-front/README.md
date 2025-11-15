以下是整个项目的整理和描述，已经涵盖了前端和后端的功能、数据库设计以及具体的代码实现细节。您可以将它保存为项目描述文件：

---

# ATM-Web 项目描述

## 项目概述

**ATM-Web** 是一个完整的 ATM 系统应用，包含前端和后端部分。前端使用 **Vue.js** 开发，后端使用 **Spring Boot** 提供 RESTful API 接口，利用 **MyBatis** 实现数据库交互。系统支持用户注册、登录、账户管理、交易（存款、提款、转账）等功能。

## 技术栈

* **前端**：Vue.js, Vite, Vue Router
* **后端**：Spring Boot, MyBatis
* **数据库**：MySQL

## 目录结构

### 前端（`atm-front`）

* **`src/`**：前端源代码，包含页面组件和功能。

    * **`components/`**：存放可复用的 Vue 组件。

        * **`NavBar.vue`**：应用的导航栏组件，提供页面间的跳转。
        * **`TransactionList.vue`**：交易记录组件，显示用户的交易历史。
    * **`views/`**：存放不同页面视图的组件。

        * **`ChangePassword.vue`**：允许用户修改密码的页面。
        * **`Deposit.vue`**：存款页面，用户可通过此页面进行存款操作。
        * **`Home.vue`**：应用首页，显示账户信息和快捷操作。
        * **`Info.vue`**：用户个人信息页面，展示账户详情。
        * **`Login.vue`**：用户登录页面，用户输入凭证登录账户。
        * **`Register.vue`**：用户注册页面，新用户创建账户。
        * **`Transfer.vue`**：用户转账页面，允许用户进行账户间的资金转移。
        * **`Withdraw.vue`**：提款页面，允许用户提取资金。
    * **`utils/`**：存放工具函数。

        * **`request.js`**：封装 HTTP 请求，简化与后端 API 的交互。
    * **`router/`**：前端路由配置。

        * **`index.js`**：配置页面路由，定义各个页面的跳转规则。

### 后端（`atm`）

* **`src/`**：后端源代码，主要用于处理业务逻辑和数据库交互。

    * **`common/`**：

        * **`Result.java`**：统一的返回结果格式类，封装 API 响应的状态和消息。
    * **`controller/`**：处理 HTTP 请求的控制器类。

        * **`AccountController.java`**：处理与账户相关的请求，如查询余额、更新账户信息等。
        * **`TransactionController.java`**：处理与交易相关的请求，如存款、转账和提款等。
    * **`dao/`**：

        * **`DataBase.java`**：管理数据库连接和配置的类，可能包括数据库连接池的初始化。
    * **`dto/`**：数据传输对象，封装从前端接收的请求数据。

        * **`DepositRequest.java`**：存款请求的数据传输对象，包含存款金额等信息。
        * **`TransferRequest.java`**：转账请求的数据传输对象，包含转账金额和目标账户等信息。
        * **`WithdrawRequest.java`**：提款请求的数据传输对象，包含提款金额等信息。
    * **`entity/`**：实体类，通常与数据库表结构对应。

        * **`Account.java`**：账户实体类，存储账户相关数据（如账户余额、账户类型等）。
        * **`Transaction.java`**：交易实体类，记录每一笔交易的详情（如金额、交易类型等）。
    * **`mapper/`**：MyBatis 映射文件，用于与数据库交互。

        * **`AccountMapper.java`**：账户数据的 MyBatis 映射文件，用于执行账户相关的数据库操作。
        * **`TransactionMapper.java`**：交易数据的 MyBatis 映射文件，用于执行交易相关的数据库操作。
    * **`service/`**：业务逻辑层，处理账户和交易的核心逻辑。

        * **`AccountService.java`**：账户相关业务逻辑处理类，例如查询余额、更新账户信息等。
        * **`TransactionService.java`**：交易相关业务逻辑处理类，如存款、转账、提款等操作。
    * **`service/impl`**：业务逻辑层的实现类，具体实现账户和交易相关操作。

## 数据库设计

### 1. **账户表（`account`）**

| 列名           | 数据类型         | 说明            |
| ------------ | ------------ | ------------- |
| `id`         | INT          | 主键，自增长，唯一标识账户 |
| `username`   | VARCHAR(50)  | 用户名，唯一        |
| `password`   | VARCHAR(255) | 密码（加密存储）      |
| `balance`    | DECIMAL      | 账户余额          |
| `created_at` | DATETIME     | 账户创建时间        |
| `updated_at` | DATETIME     | 账户信息最后更新时间    |

### 2. **交易表（`transaction`）**

| 列名                 | 数据类型        | 说明                 |
|--------------------|-------------|--------------------|
| `id`               | INT         | 主键，自增长，唯一标识交易      |
| `account_id`       | INT         | 关联账户的 `id`，外键      |
| `transaction_type` | VARCHAR(50) | 交易类型（存款、提款、转账等）    |
| `amount`           | DECIMAL     | 交易金额               |
| `transaction_date` | DATETIME    | 交易时间               |
| `status`           | VARCHAR(20) | 交易状态（成功、失败等）       |
| `description`      | TEXT        | 交易描述（可选，例如转账目标账户等） |

## 项目功能

### 前端功能：

* **用户登录与注册**：用户可以通过 **`Login.vue`** 页面登录，创建新账户通过 **`Register.vue`** 页面。
* **账户管理**：用户可以查看和修改账户信息，通过 **`Info.vue`** 页面查看账户详情，通过 **`ChangePassword.vue`** 页面修改密码。
* **交易功能**：

    * **存款**：用户可以通过 **`Deposit.vue`** 页面进行存款操作。
    * **提款**：用户可以通过 **`Withdraw.vue`** 页面提取资金。
    * **转账**：用户可以通过 **`Transfer.vue`** 页面进行账户间的转账。
* **交易记录**：用户可以通过 **`TransactionList.vue`** 页面查看自己的交易历史。

### 后端功能：

* **账户管理**：后端提供账户查询、更新等功能，通过 `AccountController` 类和 `AccountService` 类进行管理。
* **交易功能**：后端支持存款、提款、转账等操作，使用 `TransactionController` 类和 `TransactionService` 类来实现。
* **数据库操作**：后端通过 MyBatis 映射文件（`AccountMapper` 和 `TransactionMapper`）与 MySQL 数据库进行交互，确保数据的持久化和一致性。
* **统一响应格式**：后端 API 统一使用 **`Result.java`** 格式返回操作结果，包括成功与否的状态码和消息。

## 安装和运行

### 前端

1. 安装依赖：

   ```bash
   cd atm-front
   npm install
   ```

2. 运行前端应用：

   ```bash
   npm run dev
   ```

### 后端

1. 安装依赖：

   ```bash
   cd atm
   ./gradlew build
   ```

2. 运行后端应用：

   ```bash
   ./gradlew bootRun
   ```

---

