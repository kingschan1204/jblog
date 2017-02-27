
数据结构改动SQL命令记录

--给表blog_request_log增加索引  加快查询速度（2017-02-27）
ALTER TABLE blog_request_log
ADD INDEX _index_req_log_website (`req_blog`),
ADD INDEX _index_req_log_agent_key (`req_agent`)