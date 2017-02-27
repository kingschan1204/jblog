# jblog
A multi-user blog system implemented with Spring /MVC+ Hibernate + MySQL + Bootstrap + freemarker.

# about jblog

> Now deployed in http://51so.info and my blog home is http://kingschan.51so.info 


### 部署方法及二次开发的步骤正在整理中，不日我将整理好公布 ~ 

## 背景
本人程序员一枚，从事web开发时常要学习各种新技术，总结遇到的各种问题，已便下次遇到些类问题时能快速搞定，并将此分享帮助有需要的人。<br>最好的记性也抵不过岁月的冲击。常言道，时光能冲淡一切！把重要的东西写下来保存是个很不错的选择。
- 写作的过程中会总结增强理解
- 分享能帮助有需要有人，还能在线与人交流一起探讨
- 下次遇到此类问题有据可查
- 温故而知新
- 等等...

## 遇到的问题&诞生
写作找个好的平台还是挺重要的，最初我是在`QQ空间`发表日志，后来发现好友都不是同一个圈子的。另个`QQ空间` 对附件，代码这方面的支持不好。而且交流的圈子也比较有限。不利于交流和传播。后来我在网上找了一下其它博客平台，如`新浪`用了几个月发现功能及界面太丑，发表代码这个是硬伤。后来又加入`iteye`,`CSDN` 代码的问题解决了，但是操作不便利，界面太丑，当然iteye的自定义个性域名，我很喜欢。到最后的`github` 我突然有个想法从脑中闪过，要不我自立门户，自已开发一个？至此51so.info 就诞生了。寓意：`我要搜信息`

## 想法
> 致力打造属于更合适用户使用的博文平台，集百家之长。

![51so.info](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/blog.png )

## 现状
目前平台已上线了第一版，个人博客功能基本完成。目前内测阶段。暂未开放注册。[平台主页](http://51so.info),[登录](http://51so.info/pub/login.do)，[我的博客](http://kingschan.51so.info)
由于此项目设计，开发，测试，部署等等都是我一个人在搞，所以目前进度不是很理想 T_T... ... 只能在业余时间开展。

## 现有功能
- **泛型域名访问个人主页：用户名.51so.info**
- **响应式布局，一套UI自适应手机，电脑，平板电脑**
- 文章管理
- 书签
- 文章分类
- 留言板
- **文章评论，点赞，回复，emojify表情**
- 文章标签
- **文章全文检索**
- 博客banner设置
- **博客多皮肤（目前只开发了一套皮肤，往后会有多套）**
- 新浪微薄登录
- **多编辑器支持html,markdown风格编写博文**

## 界面截图
登录界面：
![登录](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/login.png )

文章列表
![文章列表](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_article_list.png )

文件上传
![文件上传](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_file_upload.png )

后台首页
![后台首页](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_home.png )
报表
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_report.png )

资源管理
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_res_list.png)
博客信息设置
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/admin_site_info.png )
文章详情
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/font-article-info.png )

### 评论

![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/article-info-comment1.png )
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/article-info-comment.png )

文章归档
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/font-article-list.png )
首页
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/font-index.png)
标签墙
![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/font-lable.png)

### 手机访问效果

![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/mobile-article-info.PNG )

![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/mobile-home.PNG)

![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/mobile-list.PNG )

![](https://raw.githubusercontent.com/kingschan1204/jblog/master/git-res/mobile.PNG)

## 记事 [更新图文介绍](http://about.51so.info/)
- 2015-11-11 诞生
- 2016-11-12 开始开发
- 2015-11-27 第一篇博文上线
- 2016-02-02 开发版本建立，【spring】 升级为4.2
- 2016-02-24 开启【hibernate】二级缓存
- 2016-02-29 添加sitemap功能
- 2016-03-02 把【hibernate4】升级为5，把实体改成注解方式的配置
- 2016-03-04 加入【hibernate search 5.5.2】  开发博客全文检索功能
- 2016-03-05 后台文章列表管理查询功能开发
- 2016-03-17 抛弃【wangeditor】 采用百度【ueditor】 并改进ueditor
- 2016-03-18 首页改版
- 2016-04-01 引入【七牛】
- 2016-04-05 加入【bootbox】插件
- 2016-04-06 支持【泛型域名】http://`用户名`.51so.info访问博客主页
- 2016-04-07 加入【404】公益页面展示
- 2016-07-08 支持【新浪微博】登录
- 2016-07-12 【评论】功能上线
- 2016-08-02 解决【session跨域】共享问题
- 2016-08-12 加入【WebUploader】整合jquery showloading...
- 2016-08-25 启用druid 统计跟踪性能
- 2016-08-30 【标签墙】上线
- 2016-09-22 加入报表【echarts】
- 2016-10-08 日志输出分类监控
- 2016-10-14 加入Java分析user-agent开源包，平台启用http请求记录日志
- 2016-10-21 后台皮肤v1.0发布
- 2016-10-26 加入皮肤【green】
- 2016-11-07 找回密码功能上线
- 2016-11-11 阿里云mysql主机架构优化
- 2016-11-14 系统加入system@notice.51so.info 企业邮箱事件提醒
- 2016-11-17 点赞功能上线
- 2016-11-21 图片压缩样式定义
- 2016-11-24 markdown 代码高亮 文章加入markdown编辑器 博客支持通知及页脚设置
- 2016-11-25 加入emojify表情包，优化markdown编辑器
- 2016-12-01 403页面，空agent禁止访问，不记录后台处理url，及404
- 2016-12-02 可以@多人并转成a标签单击可以查看用户详情 及用户详情提示markdown 放入Java处理。
- 2016-12-06 后台ui 大改版
- 2016-12-09 文章喜欢功能加入
- 2016-12-14 平台首页改版
- 2016-12-20 样试调整，文章内容图片支持view缩放,markdown表格无边框修复
- 2016-12-22 加入banner功能
- 2016-12-26 自动分析h1,h2,h3,h4,h5标签写生文章导航到文章详情页的右边
- 2017-01-05 留言版功能发布
- 2017-01-10 文章评论改版
- 2017-01-17 文章详情增加《上一篇》《下一篇》
- 2017-02-07 私密博客支持，资源管理支持key查找,文章支持发布与更新二种方式,标签墙支持显示指定的标签的文章
- 2017-02-09 文章详情增加【相似文章】
- 2017-02-14 改用mmseg4j分词，文章导航固定在右边，项目重构
- 2017-02-15 个人图像支持裁剪及ajax上传
- 2017-02-16 文章详情评论插件更新，表情盒子优化
- 2017-02-17 `正式开源https://github.com/kingschan1204/jblog`
- 2017-02-20 更新支持发送邮件至评论里提到的所有人

## 立项功能待开发
- 专题的概念
- 关注 博文收藏,点赞
- 好友功能
- 回复可以像新浪微博一样统计条数，及显示回复列表
- 评论编辑器位置优化
- 随机阅读文章
- 自定义导航
- 草稿箱
- 评论点赞明细
- 打赏
- ...
