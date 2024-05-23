# 项目配置流程

## 1-开发环境和端口

后端：IntelliJ IDEA 2024.1.1（**JDK 17** + **Spring Boot 3.1.5**）

桌面端：WebStorm 2024.1.2（**Node 18.12.0** + **Electron 21.4.4**）

后端项目名：ikunfans-boot

桌面端项目名：ikunfans

后端端口号：14800

桌面端端口号：13880

## 2-数据库配置

请在本地运行**MySQL 8**实例，下面给出使用Docker快速配置MySQL 8的方式

```shell
docker run -d
        -p 3306:3306
        --name=mysql-sample
        --env=MYSQL_ROOT_PASSWORD=123456
        mysql:latest
```

运行实例后，在附件文件夹中找到本项目所有表的SQL文件导入数据库中（上述文件中预先导入了admin账户及其测试阶段存储的示例数据，方便对照进行调试）

最后修改后端项目中`ikunfans-app/src/main/resources/application.yml`的如下代码即可

```
spring:
  datasource:
    url: jdbc:mysql://<IP地址:端口号>/<数据库名>?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&allowMultiQueries=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&allowMultiQueries=true
    username: <用户名>
    password: <密码>
```

## 3-后端项目配置

在提前部署好代理环境的情况下，后端项目在IDEA默认的捆绑Maven中可正常运行

本文档不会涉及到修改Maven路径/Maven镜像配置/Maven仓库报错相关的内容

后端项目导入完成后，可以在插件**Maven Helper**中对项目根目录进行`Run Maven -> clean`操作检验是否成功

**后端项目运行后，如果报如下的错误，属于正常情况，可以忽略**

```
SLF4J: Class path contains multiple SLF4J providers.
SLF4J: Found provider [ch.qos.logback.classic.spi.LogbackServiceProvider@6385cb26]
SLF4J: Found provider [org.slf4j.simple.SimpleServiceProvider@38364841]
SLF4J: See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual provider is of type [ch.qos.logback.classic.spi.LogbackServiceProvider@6385cb26]
```

### 3-1 使用接口的API KEY

以下配置在遇到接口完全不可用，或者KEY被ban的情况下才需要进行更改

后端项目使用的通义千问APP KEY可以在`ikunfans-common`包下的`Static/ConnectModuleStatic`中找到

后端项目使用的Bangumi相关配置可以在`ikunfans-connect`包下的`Config/BangumiFeignAPIConfig`中找到

## 4-桌面端项目配置

本文档不会涉及到Electron Builder打包相关的内容，请自行查阅框架官网：https://www.kaka996.com/

首先安装对应的包，这里推荐使用pnpm，需要在`ikunfans`和`ikunfans/frontend`两个目录下各运行：

```shell
pnpm i
```

然后运行根目录package.json中的dev脚本即可，dev被重定向为`ee-bin dev`，因此不要删除该行

```
npm run dev
```

## 5-系统使用指南

**（默认的用户名和密码为admin和123456）**

进入界面后，输入用户名和密码即可实现登录和注册操作，注册成功后会自动登陆

登录之后自动跳转到主页，页面最上方的粉色区域中，粉色区域最上方的小块区域是可拖动区域，可以拖动窗口至任意位置，其余图表的功能分别为：

1. **点击电视图标，会跳转到主页**
2. **点击用户图标，会跳转到用户页**
3. **点击齿轮图标，会跳转到登出页**
4. **点击叉号图标，则直接关闭系统**



### 5-1 主页操作

向输入框中输入搜索建议，点击右侧的搜索图标即可向后端发起推荐请求，**推荐功能中的Bangumi API接口非常不稳定，随时都有可能失效，属于正常现象**，当输入给大模型的推荐信息非常奇怪时（比如输入“科比”），也会拉长该功能的响应时间，**该功能设置的超时时间为30s**

接口返回失败时，提示”远端调用繁忙，请稍后再试“，此时可以等待接口恢复正常，也可以查看其他功能

接口返回成功时，会按排名显示推荐的动画条目，同时搜索按钮锁定1分钟无法使用

### 5-2 用户页操作

用户页和主页的页面设计非常相似，由于是从本地数据库取数据，接口响应的时间可以控制在100ms左右，进入页面时立即调用接口响应式渲染数据

**主页和用户页中的动画条目都可以执行如下功能：**

1. 点击预览图查看动画条目的大图
2. 点击“跳转链接”按钮，可使用默认浏览器分别跳转到Bangumi，Mikan和动漫花园的对应界面，方便查看更详细的信息，讨论区和磁力链接等信息

### 5-3 登出页操作

目前仅设计了登出按钮，点击即可实现切换账号的操作，同时上次登陆的Token也将被弃用
