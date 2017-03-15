package com.kingschan.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kingschan on 2017/3/13.
 * 下载图片工具类
 */
public class DownLoadImgUtil {

    private Logger log = LoggerFactory.getLogger(DownLoadImgUtil.class);
    private String fileName;
    private String fileType;
    private String filePath;


    public String getFileName() {
        return fileName;
    }

    public DownLoadImgUtil setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DownLoadImgUtil setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public DownLoadImgUtil setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }


    /**
     * 支持的文件类型
     */
    private static Map<String, String> suffixMap;

    static {
        suffixMap = new HashMap<String, String>();
        suffixMap.put("image/gif", ".gif");
        suffixMap.put("image/jpeg", ".jpg");
        suffixMap.put("image/jpg", ".jpg");
        suffixMap.put("image/png", ".png");
        suffixMap.put("image/bmp", ".bmp");
        suffixMap.put("image/webp", ".jpg");
        suffixMap.put("image/tiff", ".tif");

    }

    /**
     * 下载图片到本地
     *
     * @param urlpath       远程图片路径
     * @param localDiskPath 本地硬盘路径
     * @return 如果返回Null表示失败
     * @throws IOException
     */
    public DownLoadImgUtil download(String urlpath, String localDiskPath) throws IOException {
        log.debug("download url:{}", urlpath);
        HttpURLConnection connection = null;
        URL url = new URL(urlpath);
        String suffix = null;

        connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setUseCaches(true);
        //不设置这个有些网站会返回403
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36 TheWorld 6");
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            log.error("http status error :{}", url);
            return null;
        }

        log.debug("{},{}", connection.getContentType());
        suffix = suffixMap.get(connection.getContentType());
        if (null == suffix) {
            log.error("unsupported type:{}", url);
            return null;
        }
        String filename = String.valueOf(System.currentTimeMillis());
        String filepath = String.format("%s/%s.%s", localDiskPath, filename, suffix);
        File tmpFile = new File(filepath);
        byte[] dataBuf = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(), 8192);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
        int count = 0;
        while ((count = bis.read(dataBuf)) != -1) {
            bos.write(dataBuf, 0, count);
        }
        bos.flush();
        bos.close();
        log.debug("file path:{}", url);
        return new DownLoadImgUtil().setFileName(filename).setFilePath(filepath).setFileType(connection.getContentType());
    }
}




