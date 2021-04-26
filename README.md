# SbConstant

#### 介绍
**SBConstant**，常量抽取工具，主要是为了满足公司编码规范，做的抽取工具，希望大家喜欢。 基于Antlr4开发了，SBConstant。
各种开发规范，都有要求不能在代码里出现魔数，在实际落实的时候会出现不能出现任何字符串的要求，虽说有道理但是依然是一个重复性很高的操作。
所以开发SBConstant,自动对代码进行改造。
目前**SBConstant**可以实现两种范式：
- mybatis实体，抽取数据库字段
- 普通类，方法内部，魔法数提成常量

##### 实体抽取
使用场景，**mybatis plus**插件，支持**QueryWrapper**字段查询，如下：
```java
queryWrapper.eq("tenant", sysUser.getRelTenantIds());
```
这时候，会引入魔法字符**tenant**,通过程序实现的实体抽取功能可以把,实体数据库的column抽取出常量如：
```java
public class CustomerAppConstant {
    //命名空间
    public static final String ID = "id"
    public static final String CREATE_BY = "create_by"
    public static final String CREATE_TIME = "create_time"
    public static final String UPDATE_BY = "update_by"
    public static final String UPDATE_TIME = "update_time"
}
```
抽取命令
```sh
java -cp SBConstant.jar:./libs com.lame.sbconstant.SBConstant -ai -f CustomerApp.java
```
#### 方法内部变量抽取
会生成一个抽取后的类，所有变量被提前，因为开起了自动识别项目类型,所以可以自动别
```sh
java -cp SBConstant.jar:./libs com.lame.sbconstant.SBConstant -ai -f CustomerApp.java
```
#### 软件架构
基于antlr4，的java语法解析器。java文件解析成语法数后，完成常量识别和字段抽取工作


#### 安装教程

1.  安装java 
2.  gradle release


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


