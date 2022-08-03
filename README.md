# 高校快递代取管理系统

## 项目介绍
本管理系统是一个后端基于 Spring + SpringMVC + MybatisPlus + MySQL + Shiro，前端采用 Bootstrap + JS + Ajax + layer + ECharts的高校快递代取管理系统，主要针对于高校师生而设计，可满足高校师生日常快递代取需求。

### 功能模块介绍
项目包含`个人用户角色` 与 `管理员角色`两种角色用户；
* `个人用户角色`
  包含 `登录/注册`、`订单发布与接收`、`订单支付`、`个人信息管理`等模块;
* `管理员角色`
  包含`注册审核`、`订单管理`、`用户管理`、`用户订单成交量`、`用户增长量`等模块；

### 相关配置说明

#### db 部分
* express/sql/express-ssm.sql  -> 本系统所需的 db 建表语句与测试数据
* express/src/main/resource/conf/mysql.properties   -> mysql 相关配置，包含 mysql 的账号密码配置，其中本系统的 database 名为 `express`

#### 支付宝沙箱支付
* express/src/main/resource/conf/cfg.properties  -> 支付宝沙箱支付配置，相关账户请到支付宝申请

## 使用说明
本地搭建好环境后即可进入本系统，普通用户注册成功后经管理员审核通过后方可使用本系统。
* 普通用户 <br/>
  账号：12345678@qq.com <br/>
  密码：123456 <br/>
  
  账号：88888888@qq.com <br/>
  密码：123456 <br/>
  
* 管理员 <br/>
  账号：admin@admin.com <br/>
  密码：admin <br/>
