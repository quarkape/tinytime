# 小时光

【分享与记录生活的点滴】

---

### :mortar_board: 后端项目

- [后端项目、SQL文件、图片资源](https://github.com/quarkape/tinytime_backend)

---

### :sunny: 小论坛

- 发帖子。帖子可以设置背景，包括纯色背景和默认的花纹背景两种类型；也可以附带一张图片。帖子包含两种类别，一种是日常趣事分享或者吐槽，另一种是生活经验或者优秀资源分享。在首页，可以查看根据热度和两种类别来查看帖子列表。

- 评论。支持评论和引用。原本支持点赞、举报和收藏功能，不过因为没怎么做好就把这些功能撤下了，只保留了浏览量和评论量两个部分。首页热度列表会优先根据浏览量排序，再根据评论量排序。引用别人的评论再进行评论会有标识。

---

### :snowman: 每日一句

- 美句。美句在页面中称之为“晴圆”，调用的是金山词霸的每日一句接口。美句会展示包含在今天在内的前四天的数据，包括图片，英文原句和中文翻译。

- “毒汤”。毒汤调用的是毒汤日历APP的接口，每天可以获取到0~4句内容。

- 单击每日一句，可以复制句子。

- 对每日一句表明自己的态度，包括“蚌埠住了”、“扎心了”两种，且可以查看对当日句子投票的人数。未来可以新增一个对每日一句进行评论并展示评论列表的功能。

---

### :cyclone: 表情包

基于每日一句，可以制作表情包。表情包的表情需要预先上传，暂不支持用户自定义表情。如果需要增加表情，需要首先到数据库中增加表情的数量这一数据，然后将表情包按照已经序号编排规则进行编号，并且后缀名必须统一，最后小程序会自动根据这个数据循环展示对应数量的表情包供用户筛选。制作后的表情包可以存储到本地。未来也可以开发一键存储到本地并跳转到新建帖子的页面。

---

### :ocean: 内容管理

- 对已发布的帖子进行删除操作。

- 查看自己的评论列表。

- 当有人对自己的帖子进行评论，或者自己的评论被引用的时候，会收到消息。

- 当注册账号或者修改账号密码的时候，会收到相应的通知。

---

### :foggy: 数据统计

- 登陆时间统计。统计登录小程序的时间端并以图表呈现。

- 行为偏好统计。统计自己的评论次数、收藏次数、点赞次数，并以图表呈现。由于目前点赞和收藏功能均撤下了，所以该图标应该会展示100%的评论或者无数据。

- 帖子类型偏好统计。统计自己发布的帖子在趣闻和分享两类上面的分布情况并以图表呈现。

---

### :penguin: 其他

- 更新日志、关于、清除缓存、使用指引。

---

### :palm_tree: 使用方法

- 在微信开发者工具中将前端项目导入，导入时填写自己的小程序ID。

- 将后端项目部署至服务器中，数据库相关参数配置详见application.yml。

- 将静态资源文件放到服务器上的指定位置，静态资源文件的位置可以在tinytime_backend/scr/main/java/club/hue/config/MvcConfig.java中进行配置。

- 将数据文件导入服务器数据库当中。数据库的名称需要与后端配置文件application.yml中的一致。

- 由于小程序注册、找回密码需要依赖用户邮箱，因此我们需要申请SMTP服务。相关操作不复杂请自行探索，相关配置需要在utils/SendMailUtil.java中进行修改。主要需要修改为自己的邮箱，以及自己邮箱的授权码。如果你使用的是其他邮箱，那么你的SMTP服务器地址也需要在此文件中修改。

  完成上述准备工作后，可以正式开始启动后端，使用微信小程序了。

