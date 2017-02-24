package com.kingschan.blog.services.system.task;

import com.kingschan.blog.dao.ReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by kingschan on 2017/2/24.
 */
@Service("BlogStatisticalTask")
public class BlogStatisticalTaskService {

    private Logger log = LoggerFactory.getLogger(BlogStatisticalTaskService.class);

    @Qualifier("ReportDaoImpl")
    @Autowired
    private ReportDao reportDao;


    /**
     * 刷新博客统计信息
     */
    public void refreshBlogStatistical(){
        try {
            log.info("start refreshBlogStatistical");
            reportDao.refreshBlogStatistical();
            log.info("end refreshBlogStatistical");
        } catch (Exception e) {
            log.error("refreshBlogStatistical",e);
            e.printStackTrace();
        }
    }
}
