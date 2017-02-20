package com.kingschan.blog.services.system;

/**
 * Created by kingschan on 2017/2/17.
 * define blog system  send e-mail general method
 */
public interface EmailNotifyService {

    /**
     * send email to many user
     * @param userNamesText
     * @param mailTitle
     * @param mailContent
     * @throws Exception
     */
    void sendEmailToUsersByText(String userNamesText,String mailTitle,String mailContent)throws Exception;


    /**
     * Email to the specified target
     * @param emailAddress
     * @param emailTitle
     * @param emailContent
     * @throws Exception
     */
    void sendEmail(String emailAddress, String emailTitle, String emailContent)throws Exception;
}
