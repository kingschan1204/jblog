package com.kingschan.blog.services.system;

/**
 * Created by kingschan on 2017/2/17.
 * define blog system  send e-mail general method
 */
public interface EmailNotifyService {

    /**
     * send email to many user
     * @param userNamesText
     * @param mailContent
     * @throws Exception
     */
    void sendEmailToUsersByText(String userNamesText,String mailContent)throws Exception;
}
