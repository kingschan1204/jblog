package com.kingschan.blog.services.system;

/**
 * Created by kingschan on 2017/2/21.
 * remark:  validate user or spider  robot request url path
 */
public interface UrlValidationService {

    /**
     * validate category url sample :// xxx.51so.info/category/keyword
     * @param websiteId
     * @param urlPath
     * @return
     * @throws Exception
     */
    boolean validateCategory(String websiteId,String urlPath)throws Exception;

    /**
     * validate lable url sample :/tags/xxxx
     * @param websiteId
     * @param urlPath
     * @return
     * @throws Exception
     */
    boolean validateLable(String websiteId,String urlPath)throws Exception;

    /**
     * validate date url sample : /date/20160216 or 201602
     * @param websiteId
     * @param urlPath
     * @return
     * @throws Exception
     */
    boolean validateDate(String websiteId,String urlPath)throws Exception;

}
