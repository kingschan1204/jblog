package com.kingschan.blog.services.system.impl;

import com.kingschan.blog.po.User;
import com.kingschan.blog.services.system.EmailNotifyService;
import com.kingschan.blog.util.RegexUtil;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by kingschan on 2017/2/17.
 */
@Service
public class EmailNotifyServiceImpl  implements EmailNotifyService {

    @Override
    public void sendEmailToUsersByText(String userNamesText, String mailContent) throws Exception {
        String target= RegexUtil.findStrByRegx(userNamesText, "\\@\\w+");
        if (!target.isEmpty()) {
            String[] users=target.replaceAll("\\@|\\:", "").split(",");

        }
    }
}
