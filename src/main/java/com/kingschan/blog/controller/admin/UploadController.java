package com.kingschan.blog.controller.admin;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

@RequestMapping("/admin")
@Controller
public class UploadController {
	
	/*@RequestMapping("/upload_page")
	public String uploadPage(){
		
		return "/admin/upload";
	}*/
    @ResponseBody
    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
    )
    public String uploadFile(MultipartHttpServletRequest request) {

        try {
            Iterator<String> itr = request.getFileNames();

            while (itr.hasNext()) {
                String uploadedFile = itr.next();
                MultipartFile file = request.getFile(uploadedFile);
                String mimeType = file.getContentType();
                String filename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                InputStream input = file.getInputStream();
                File source = new File("C://"+filename);
                file.transferTo(source);
//                FileUpload newFile = new FileUpload(filename, bytes, mimeType);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{error}";
        }

        return "{ok}";
    }

}
