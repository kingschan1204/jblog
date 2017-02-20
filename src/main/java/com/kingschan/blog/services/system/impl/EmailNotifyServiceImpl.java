package com.kingschan.blog.services.system.impl;

import com.kingschan.blog.dao.UserDao;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.system.EmailNotifyService;
import com.kingschan.blog.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by kingschan on 2017/2/17.
 */
@Service
public class EmailNotifyServiceImpl  implements EmailNotifyService {

    @Qualifier("UserDaoImpl")
    @Autowired
    private UserDao userDao;
    @Resource(name="sender")
    private JavaMailSenderImpl mail;
    @Value("${system.notice.account}")
    private String fromAccount;

    @Override
    public void sendEmailToUsersByText(String userNamesText, String mailTitle, String mailContent) throws Exception {
        String target= RegexUtil.findStrByRegx(userNamesText, "\\@\\w+");
        if (!target.isEmpty()) {
            String[] users=target.replaceAll("\\@|\\:", "").split(",");
            List<User> lis= userDao.getUsersByUserNames(users);
            List<String> emails= new ArrayList<String>();
            for (User u:lis) {
                if (u.getUserEmailActivate()){
                    emails.add(u.getUserEmail());
                }
            }
            if (emails.size()==0)return;
            JavaMailSender sender =mail;
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
            helper.setTo(emails.toArray(new String[1]));
            helper.setFrom(fromAccount);
            helper.setSentDate(new Date());
            helper.setSubject(mailTitle);
            helper.setText(mailContent,true);// html: true
            sender.send(message);
        }
    }

    @Override
    public void sendEmail(String emailAddress, String emailTitle, String emailContent) throws Exception {
        if (null==emailAddress||null==emailTitle||null==emailContent) {
            return;
        }
        JavaMailSender sender =mail;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(emailAddress);
        helper.setFrom(fromAccount);
        helper.setSentDate(new Date());
        helper.setSubject(emailTitle);
        helper.setText(emailContent,true);// html: true
        sender.send(message);
    }
}
