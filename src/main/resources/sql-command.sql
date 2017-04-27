
数据结构改动SQL命令记录

--给表blog_request_log增加索引  加快查询速度（2017-02-27）
ALTER TABLE blog_request_log
ADD INDEX _index_req_log_website (`req_blog`),
ADD INDEX _index_req_log_agent_key (`req_agent`)

-- 视图
create view view_users_url(id,url) as
select users.id,
case
when website.id is not null then CONCAT('http://',website.website_name,'.51so.info')
when sina_profile_url <> '' and sina_profile_url is not null then CONCAT('http://weibo.com/',sina_profile_url)
when sina_uid is not null then CONCAT('http://weibo.com/u/',sina_uid)
else '' end url
 from blog_user users
left join blog_website website on users.id=website.webiste_creator

--向user表增加列 用户url
ALTER TABLE blog_user
add COLUMN user_url VARCHAR (50) not null default '';

--初始化user表的url
update  blog_user  u inner join view_users_url v on u.id=v.id set u.user_url=v.url