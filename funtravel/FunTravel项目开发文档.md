# FunTravel项目开发文档

<p align="right">刘俊伟 18302010042 </p>

## 一、项目简介

- `FunTravel`是一个在线图片分享社区项目，支持用户注册登录，用户可以浏览并评论他人上传的图片，也可以上传自己的图片，还可以和志同道合的用户互加好友。

- 本项目采用**前后端分离**的开发模式进行开发，前端使用`Vue + Element-ui`搭建，后端是用`SpringBoot`框架。
- 前端GitHub仓库地址：https://github.com/To-D/FunTravel_Frontend
- 后端Github仓库地址：https://github.com/To-D/Funtravel_Backend



## 二、项目需求分析和精化

### 1. 技术要求

​	1.1 前后端可以使用最新框架

​	1.2 服务器建议使用`Apache Tomcat`，数据库建议使用`MySQL`

### 2. 注册页

​	2.1 表单

​			2.1.1 必填项：**用户名（唯一）**、**邮箱（唯一）**、**密码**、**确认密码**、**验证码**五项

​			2.1.2 必填项不填或不符合对应要求，注册按钮不启用

​			2.1.3 必填项不填，要给出不可为空的醒目提示

​	2.2 用户名

​			2.2.1 用户名长度限制在4-15位（包含4和15）

​			2.2.2 用户填写的用户名不能与数据库中已有用户的用户名重复

​	2.3 邮箱

​			2.3.1 邮箱需为标准的邮箱格式

​			2.3.2 用户填写的邮箱不能与数据库中已有用户的邮箱重复

​			2.3.3 不标准的邮箱格式或是重复需要给出对应的醒目提示

​	2.4 密码与确认密码

​			2.4.1 密码长度为6至12位(包含6和12)

​			2.4.2 密码与确认密码需要保持一致

​			2.4.3 密码强弱性检查及UI醒目显示

​			2.4.4 密码不得明文传输，要在前端加密再传输

​	2.5 点击注册

​		2.5.1 表单有加载提示

​		2.5.2 注册成功后自动登录，转到主页

​		2.5.3 注册失败给出失败原因的提示，表单停止加载，不清空表单

### 3. 登录页

3.1 表单

​	3.1.1 必填项：**用户名/邮箱**,**密码**,**验证码**

​	3.1.2 必填项不填要给出醒目提示

​	3.1.3 必填项不填，登录按钮不启用

3.2 密码不得明文传输，要在前端加密再传输

3.3点击登录

​	3.3.1 表单有加载中提示

​	3.3.2 登陆成功返回登陆前页面

​	3.3.3 登录失败给出“用户名和密码错误”提示，表单停止加载，不清空表单

### 4. 导航栏

4.1 导航栏左边为网站logo

4.2 导航栏右边

​	4.2.1 始终包含**首页**,**搜索**

​	4.2.1 未登录时最后一项为**未登录**

​		4.2.1.1 点击“未登录”跳转到登录页面

​	4.2.2 登录后最后一项为**用户名**

​		4.2.2.1 鼠标悬浮在用户名上显示下拉菜单

​		4.2.2.2 下拉菜单包括**我的收藏**，**上传**，**我的图片**，**我的好友**，**退出登录**

​			4.2.2.3 当有好友请求/好友请求通过时,下拉菜单和我的好友都显示红点

​		4.2.2.3 鼠标悬浮在下拉菜单项上要有UI变化

4.3 导航栏在除登录、注册以外的页面始终显示

### 5. 首页

5.1 导航栏下用轮播来展示**最热**的旅游图片（最热指收藏最多，5个）

​	5.2 用户点击图片可以跳转到详情页

5.2 轮播下展示最新的旅游图片（至少3个)

​	5.2.1 最新旅游图片包含**作者**、**主题**和**发布时间**

​	5.2.2 用户都点击图片跳转到详情页

### 6. 图片详情页

6.1 必须使用通用的展品详情页面

6.2 需要展示**图片**,**作者**,**标题**,**主题**,**简介**,**收藏数**,**国家**,**城市**,**发布时间**,**用户评论**

6.3 用户在此页面进行收藏/取消收藏

   ​	6.3.1 收藏/取消收藏需要有UI变化

6.4 鼠标移动到图片上显示放大镜并进行局部放大

### 7. 搜索页

7.1 提供一个输入框供用户输入搜索信息

​	7.1.1 支持模糊匹配

7.2 筛选和排序

​	7.2.1 支持根据**标题/主题**筛选

​	7.2.2 支持根据**热度/时间**排序

​	7.2.3 先筛选,再排序

​	7.2.4 默认按照标题筛选,按照热度排序

7.3 分页

​	7.3.1 每页不超过9张

​	7.3.2 翻页不刷新页面

### 8. 上传页面

8.1表单必填项

​	8.1.1 输入框:**图片上传**,**标题**,**作者**,**主题**,**简介**

​		8.1.1.1 从本机选择图片上传,要在合适位置提供预览

​	8.1.2 下拉框:**国家**,**城市**

​	8.1.3 必填项不填需给出醒目提示

8.2 提交前用对话框询问用户确认信息是否无误

​	8.2.1 用户点击确认,上传信息,刷新页面并给出UI提示

​	8.2.2 用户点击取消,则关闭对话框

8.3 提供一个"修改"的版本

​	8.3.1 自动填充表单

​	8.3.2 "提交"按钮换成"确认修改"按钮

​	8.3.3 提交成功后,数据库要更新该图片的上传时间

### 9. 我的照片页

9.1 分页显示用户上传的照片

9.2 每张照片下提供修改和删除按钮

​	9.2.1 点击修改进入该图片上传页面的修改版本

​	9.2.2 点击删除,弹出对话框提示"是否确定删除该图片及信息?"

​		9.2.2.1 用户点击确定则删除

### 10. 我的收藏页

10.1 分页显示用户收藏的图片

10.2 图片下有"取消收藏"的按钮,点击后从用户收藏列表中**立即移除**

10.3 "我的足迹"

​	10.3.1 点击显示最近浏览过的10个图片标题,不可重复(如果少于10个则全部显示)

​	10.3.2 点击标题跳转到相应详情页面

### 11. 我的好友页

11.1左侧显示用户的好友列表

​		11.1.1 左上方显示添加好友和好友申请

​			11.1.1.1 点击添加好友弹出搜索框

​					11.1.1.1.1 可根据用户名/邮箱进行搜索并添加

​					11.1.1.1.2 后台收到请求发送添加好友提醒

​					11.1.1.1.3 前端给出发送成功的提示

​	11.1.1 每一项包括**用户名(邮箱)**,**注册时间**,**聊天窗口**

11.2 点击用户名/邮箱,跳转到该用户的收藏页面

​	11.2.1 在合适位置显示"xxx的收藏"

​	11.2.2 若用户设置不可查看,则给予提示



## 三、项目设计

### 前端

<img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729160538488.png" alt="image-20200729160538488" style="zoom:80%;" />

<center>图 1 前端主要组件</center>

#### 1. 组件设计

**页面**

- `Login.vue`                        —— 登录页
- `Register.vue`                  —— 注册页
- `Home.vue`                          —— 主页
- `PictureDetail.vue`       —— 详情页
- `Search.vue`                      —— 搜索图片页
- `Upload.vue`                      —— 上传页
- `Modify.vue`                      —— 修改页
- `MyPictures.vue`              —— 我的照片页
- `MyFavorite.vue`              —— 我的收藏页
- `MyFriends.vue`                —— 我的好友页

**复用组件**

- `NavBar.vue`                      —— 导航栏，所有页面都包含（注册、登录除外）
  - 接受一个`page`参数，用于导航栏定位当前位置
- `Footer.vue`                      —— 页脚，所有页面都包含（注册、登录除外）
- `ShowPicture.vue`           ——  分页展示图片，用于搜索后的结果展示，收藏页的展示，我的照片页的展示等
  - 接受一个`pictures`参数，是需要展示的图片列表
- `Logo.vue`                          —— 页面logo
- `GLOBAL.vue`                      —— 保存了一些全局变量，本项目主要是后端图片的映射地址
- `UploadForm.vue`              —— 上传图片的表单，复用可以作为修改的表单
  - 接受一个`Boolean`和一个可选的`picture`参数，boolean指明当前是否为上传，picture则是修改时用于填充的图片数据。
- `PasswordStrength.vue` —— 密码强度校验
- `VerificationCode.vue` —— 验证码组件，注册和登录页面使用 

#### 2. UI设计

- 前端整体的UI设计理念是**整齐、简洁但不乏味**，突出**图片**本身

- 配色设计

  - 前端使用的主色调是黑白灰，网页标题、图片标题等使用的是纯度很高的黑/白色，而一些辅助信息使用的是灰色
  - 前端使用的辅助色是`"#f48840"`,主要用于一些点缀，如按钮的背景色，logo的点缀色，icon的颜色等，增加网站的趣味性和色彩，避免过于单调。

  <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729161309896.png" alt="image-20200729161309896" style="zoom:80%;" />

  <center> 图 2 UI辅助色</center>

  - 除此之外，网站还使用了其他一些点缀色，主要是遵循用户的使用习惯，例如通知使用了绿色、黄色、红色，分别表示”成功“、”警告“、”危险“等操作提示。

- 字体设计

  - 前端使用的字体是`'Roboto', sans-serif`，这款字体比较方正，棱角明显，没有衬线，比较符合整齐简约的设计理念。
  - 在字体大小上，一般根据信息的重要程度做适当的调整。

- 版式设计

  - 本网站的版式主要根据页面的功能进行调整，除了导航栏和页脚以外，并没有统一的整体版式设计。

  - 在一些细节上，例如图片的展示上，则遵循了一个不那么严格的统一，既将图片置于一个卡片之中，设置阴影。

    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729162807658.png" alt="image-20200729162807658" style="zoom:80%;" />

    <center>图 3  图片展示卡片的版式设计</center>

- 前端主要界面的UI展示

  - **登录页**

    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163130891.png" alt="image-20200729163130891" style="zoom: 80%;" />

    <center>图 4  登录页</center>

  - **主页**

    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163309867.png" alt="image-20200729163309867" style="zoom:80%;" />
    
    <center>图 5 主页</center>
    
  - **搜索页**
  
    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163746592.png" alt="image-20200729163746592" style="zoom:80%;" />
    
    <center>图 6 搜索页</center>
    
  - **图片详情页**
  
    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163621167.png" alt="image-20200729163621167" style="zoom:80%;" />
    
    <center>图 7 图片详情页</center>
    
  - **图片上传页**
  
    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163722891.png" alt="image-20200729163722891" style="zoom:80%;" />
    
    <center>图 8 图片上传页</center>
    
  - **好友页**
  
    <img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729163824886.png" alt="image-20200729163824886" style="zoom:80%;" />
    
    <center>图 9 我的好友页</center>



### 后端

#### 1. 技术选择

- 后端采用`Spring Boot`框架搭建而成
- 数据库使用`MySQL`，使用`JPA`访问和修改

#### 2. 业务模块划分

<img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729164756795.png" alt="image-20200729164756795" style="zoom:80%;" />

<center>图 10 后端项目结构</center>

本次项目后端比较简单，业务基本可以划分为User相关和Picture相关两块，外加一个token的管理，后端我一共分成了三个模块

- `JWT`：登录状态的维持使用`JWT`生成token来解决，当用户登录成功时，后端返回 token 值，前端保存在本地后在后面的每次请求中都在 header 中带着 token 进行请求。后端重写过滤器，在过滤器中解析 token 值并将 token 中带的对象放到登录用户中，让 security 认为请求已经是在登录状态下进行。
- `User`：主要负责与用户相关的操作，如基本的登录、注册，以及互加好友功能。
- `Picture`：负责与图片有关的操作，如最新、最热图片的获取和搜索等功能。

JWT的功能相对独立，且与Security关联比较重，所以放置在了security包中，user和picture各自拥有一个Controller和一个Service, Controller层主要负责与前端对接，Service层提供各种具体的服务。

#### 2. 数据库设计

##### 2.1 数据库结构概述

![image-20200729165156465](C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729165156465.png)

<center>图 11 项目数据库结构</center>

项目使用一个`fun_travel`库，包含`users`，`pictures`，`comments`, `messages`, `topics`, `pictures_topics`，`pictures_comments`，`users_friends`，`users_collections`八个表。	

##### 2.2 数据表展示

- `users`表：保存注册用户信息

|  id  | user_name |  email  | user_password | register_time|View|
| :--: | :-------: | :-----: | :------: |:--:|:--:|
| Long  |  VARCHAR  | VARCHAR | VARCHAR  |datetime|int（收藏是否可见）|

- `pictures`表：保存上传图片的信息

|  id  |  title  | author  |  intro  | collection_count | nation  |  city   | release_time | uploader |url|
| :--: | :-----: | :-----: | :-----: | :--------------: | :-----: | :-----: | :----------: | :------: |:--:|
| Long  | VARCHAR | VARCHAR | VARCHAR |       INT        | VARCHAR | VARCHAR |     DATE     | VARCHAR  |VARCHAAR|

- `comments`表：保存图片的评论信息

id |comment | user_name|time |picture_id
:--:|:--:|:--:|:--:|:--:
Long|VARCHAR|VARCHAR|datetime|Long

- `messages`表：保存所有的通知消息信息
id |content | sent_time|target |source |type
:--:|:--:|:--:|:--:|:--:|:--:
Long|VARCHAR|datetime|Long|VARCHAR|VARCHAR


- `topics`表：保存图片的主题信息

id|topic
:--:|:--:
Long|VARCHAR

- `pictures_topics`表：保存图片与主题之间的对应关系

| picture_id |  topic  |
|  :--------: | :-----: |
|     Long     | VARCHAR |

- `users_friends`表：保存用户的好友关系

| user_id | friend_id |
| :-------: | :---------: |
|  LONG  |   LONG   |

- `users_collections`表：保存用户与图片的收藏关系

| user_id | collection_id |
| :-------: | :--------: |
|  Long  |    Long     |

##### 2.3 数据表之间的关系

这些数据表之间的关系可以用下面的图表示：

![image-20200729172416939](C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729172416939.png)

<center>图 12 数据表之间的关系</center>

### 接口约定

#####  1. 用户相关

- `/register`

  - 参数：username(String); email(String); password(String);
  - 返回值：一个字符串，表示是否成功或者出现用户名/邮箱重复的情况

- `/login`

  - 参数：username(String); password(String);
  - 返回值：返回后端生成的token信息，以及登录用户名 —— token(String); username(String);

- `/setView`

  - 参数：username(String);  view(int);
  - 返回值：String表示是否成功

- `/searchUser`

  - 参数：username（String 执行搜索的用户的用户名），searchName(String 搜索用户的名字)
  - 返回值：若已经是好友，则返回alreadyFriend=true，否则返回搜索到的用户信息。

- `/addFriend`

  - 参数：username，friendName
  - 返回值：默认返回“success”，若已经发送过好友消息，则返回”duplicate“

- `/hasMessage`

  - 参数：username
  - 返回值：如果当前用户有未处理的消息，则返回true，否则返回false

- `/getMessage`

  - 参数：username
  - 返回值：返回一个布尔值hasMessage，表示是否有未处理的消息，如果有，则再返回消息的列表

- `/allowFriendApplication`

  - 参数：id（所处理的message的id）
  - 返回值：默认返回success

- `/refuseFriendApplication`

  - 参数：id（所处理的message的id）
  - 返回值：默认返回success

- `/alreadyRead`

  - 参数：id（所处理的message的id）
  - 返回值：默认返回success

- `/getView`

  - 参数：username

  - 返回值：若username对应的用户设定的可见性为PUBLIC则返回true，否则返回false

    

##### 2. 图片相关

- `/getHottestPictures`

  - 参数：无
  - 返回值：收藏数最高的五张图片和相关的主题信息

- `/getNewestPictures`

  - 参数：无
- 返回值：最新上传的三张图片和相关的主题信息
  
- `/getPictureDetail`

  - 参数：图片的id
  - 返回值：返回对应图片的详细信息和主题信息

- `/postComment`

  - 参数：username(String 发评论的用户名)，content（评论内容），pictureId（评论的图片id）
  - 返回值：默认返回success

- `/isCollected`

  - 参数：username，pictureId
  - 返回值：若pictureId对应的图片被username对应的用户收藏了，则返回true，否则返回false

- `/collect`

  - 参数：username，pictureId
  - 返回值：默认返回success

- `/cacelCollect`

  - 参数：username，pictureId
  - 返回值：默认返回success

- `/search`

  - 参数：keyword（搜索关键字），filter（过滤方式），sort（排序方式）
  - 返回值：根据keyword进行的模糊匹配得到的图片结果集合

- `/upload`（上传和修改共用）

  - 参数：author，intro，title，file，nation，city，topics，pictureId（修改时提供）
  - 返回值：返回上传状态的信息

- `/canModify`

  - 参数：username，pictureId
  - 返回值：若username对应的用户是picture的上传者，则返回true，否则返回false

- `/getMyPictures`

  - 参数：username 
  - 返回值：返回username对应的用户上传的所有图片

- `/deletePicture`

  - 参数：pictureId
  - 返回值：默认返回success

- `getFriendFavorite`

  - 参数：username

  - 返回值：返回username对应的用户的view值，若view为1，则返回他所收藏的图片

    

## 四、项目实现难点

这一部分，我将介绍我的一些功能模块的实现思路、碰到的一些麻烦以及我的解决办法。

#### 1. 密码的两次加密

本项目中，我对用户输入的密码进行了两次加密，一次是**前端传输之前**，使用`md5`进行了第一次加密，到了后台之后，在**入库之前**，我又用`BCrypt`算法进行了二次加密。当用户的密码是123456时，经过两次加密后的密码变成了$2a$10$uSPFYIkec6w8uLz1jzdD4。这样即使是数据库的管理人员也无法获知用户的密码，可以保障用户的账户安全。

#### 2. JPA的使用

本项目中后端最大的麻烦就是JPA的使用，关于各个数据库之间的关系我花了很多时间去学习，其间碰到了各种错误。

例如：有时取值时会报出无限循环的错误，经过查询资料，我通过在管理方添加`@JsonIgnore`注释解决了这个问题。

再比如：因为映射关系无法删除被管理方的内容，只能先从管理方中剔除要删除的内容，之后才能顺利删除。

还有新加入的列设置为not null会导致之前的数据冲突，导致数据库创建不成功。

以及@ManyToMany的各种注解，需要区分标在管理方还是被管理方，以及标注的方式，稍有不慎就会报错。

#### 3. el-upload上传图片

在图片的上传上，我使用的是element-ui提供的el-upload组件，但是这个组件会自动完成上传，与项目中要求填写表单信息之后一并提交的要求不符合。在禁用了它的自动上传后，el-upload又有一套自己的生命周期方法，上传的文件也被进行了封装，导致后端一直没有办法取到file的值，后来我通过研究它的生命周期钩子函数以及封装的文件，在用户上传文件时，调用on-change钩子函数，从封装的文件中提取出file.raw作为文件本体，成功完成了上传。

#### 4. 消息提醒功能

当用户收到新的好友消息时，用户在其他页面是无法知晓的，这样会带来不好的用户体验。因此我在navbar组件中加入了一个红点提醒，当有消息时，用户名那一项会出现红点，鼠标悬浮在上面时，会看到MyFriends表单项出现红点，再点击MyFriends进入我的好友页面，就可以通过左边好友列表中的Message按钮来处理消息。其内部实现机制是navbar组件每次创建时，都会像后端的`/hasMessage`接口发送一个请求，如果返回true，它就会出现红点提示。

<img src="C:\Users\12444\AppData\Roaming\Typora\typora-user-images\image-20200729183429686.png" alt="image-20200729183429686" style="zoom:80%;" />

<center>图 13 消息提醒引导</center>

#### 5. 修改轮播图插件源码

本项目的轮播图使用的不是element-ui提供的轮播图插件，因为我觉得那个太普通了，而且很丑。我选择的是`owl-carousel`，一个基于jquery的轮播图插件，它提供了一个vue的版本，但是这个版本年久失修，有很多bug，导致一直没办法达到我想要的效果，因此我拆开了它的源码，尽管我找到了bug所在，但是不管我怎么修改，用的时候还是老样子。后来我查阅资料才知道，原来在项目引入的是它打包后的生产版本js文件，因此我把这个组件单独拿出来建了一个项目，重新打包引入，修复了问题。

#### 6. 好友功能的各种细节

关于好友功能，实现的时候才发现有非常多的细节，拿搜索举例来说

- 理论上说用户不能够搜索自己的用户名，因为那是没有意义的。
- 用户应该不能搜索已经和自己成为好友的用户的用户名
- 用户已经向另一名用户发送了好友请求，那么再次请求应该给出提示

这些细节我通过前后端的分解各自实现了，用户填入的用户名会首先和自己比对，如果是一样的则直接提醒用户，不请求后端。后端负责的任务除了找出搜索的用户外，还需要查看搜索的用户的消息列表是不是已经有了这条好友申请消息，此外还要比对用户的好友列表，看看用户是不是在搜索一个已经成为自己好友的用户。

#### 7. 足迹的保存

足迹的保存我是通过在localStorage中存储实现的，但是localStorage只能存储字符串，因此我使用了`JSON.parse`和`JSON.stringify`两个方法，分别负责将字符串转化为数组和把数组转化为字符串。或许将足迹存储在数据库中也是一个好办法，但是出于效率考虑，我还是选择在前端解决它。



## 五、个人总结

本次PJ是我第一次独立尝试使用`Vue+Spring Boot`同时进行前后端的开发，也是我第三次做一套web系统，第一次是web应用设计课程上使用原生`html+css+js+php`开发，那次PJ的完成度并不高，但是是一次很好的web实践。第二次做web项目是上学期的软件工程课程，一下子从原生开发跳到了最新的框架式开发，我付出了非常多的时间来适应和学习，最后作为前端人员和其他三名同学一起做出了全部满分的成绩，这对我是莫大的鼓舞，同时也相对熟练地掌握了Vue的使用，但是那次开发的分工非常明确，导致我对后端一无所知，这是当时最大的缺憾。

本课程注重的是JavaEE的基础，但是相较于大一刚学完web开发的同学来说，我已经习惯了前后端分离的开发方式，因此，在听说可以使用框架之后，我就转回了我所习惯的Vue+SpringBoot的开发框架，只不过要从0开始学习SpringBoot，确实付出了一段很艰难的前期积淀和学习准备，以至于同学们已经用Servlet风风火火地做了很多之后，我甚至还没有搞定token。

好在SpringBoot本身具备着非常高效的开发潜能，在掌握了本次PJ需要的技术要点之后，我的进度开始逐步推进，最终按预期实现了需要的功能。期间又进行了非常多的尝试，比如第一次试着修改插件的源码来满足自己的需要，第一次使用JPA，动态创建数据库的感觉确实非常的酷炫。

与此同时，对`Servlet+JSP+JavaBean`的学习也让我对SpringBoot的底层细节有了更多的了解，使我有了更平滑的学习曲线，学习和实践同步推进，web开发能力大大提升。