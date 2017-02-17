package com.kingschan.blog.common.qiniu;

import javax.annotation.PostConstruct;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛管理
 *
 * @author kings.chan
 */
@Component
public class QiNiuManagement {


    //设置好账号的ACCESS_KEY和SECRET_KEY
    @Value("${qiniu.access.key}")
    private String ACCESS_KEY;
    @Value("${qiniu.secret.key}")
    private String SECRET_KEY;
    //要上传的空间
    @Value("${qiniu.bucketname}")
    private String bucketname;
    private Auth auth;

    @PostConstruct
    private void inital() {
        //密钥配置
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    }

    ///////////////////////指定上传的Zone的信息//////////////////
    //第一种方式: 指定具体的要上传的zone
    //注：该具体指定的方式和以下自动识别的方式选择其一即可
    //要上传的空间(bucket)的存储区域为华东时
    // Zone z = Zone.zone0();
    //要上传的空间(bucket)的存储区域为华北时
    // Zone z = Zone.zone1();
    //要上传的空间(bucket)的存储区域为华南时
    // Zone z = Zone.zone2();
    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    Zone z = Zone.autoZone();
    Configuration c = new Configuration(z);
    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    /**
     * 覆盖上传
     *
     * @return
     */
    public String getRepliceUpToken(String key) {
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        //如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        //第三个参数是token的过期时间
        return auth.uploadToken(bucketname, key);
    }

    /**
     * 上传文件
     *
     * @param filepath
     * @param key      传null就自动生成
     * @return
     * @throws Exception
     */
    public JSONObject upload(String filepath, String key) throws Exception {
        //调用put方法上传
        Response res = uploadManager.put(filepath, key, null == key ? getUpToken() : getRepliceUpToken(key));
        return JSONObject.fromObject(res.bodyString());
    }

    /**
     * 删除资源
     *
     * @param key
     * @throws Exception
     */
    public void delRes(String key) throws QiniuException {
        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth,c);
        //调用delete方法移动文件
        bucketManager.delete(bucketname, key);
    }
}
