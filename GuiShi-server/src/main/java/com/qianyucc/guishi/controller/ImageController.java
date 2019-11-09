package com.qianyucc.guishi.controller;

import com.qianyucc.guishi.model.vo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * @author lijing
 * @date 2019-11-02 10:35
 * @description
 */
@Controller
@RequestMapping("/api/images/")
public class ImageController {
    @Value("${sources.images.path}")
    private String imagesDir;

    @GetMapping("/{fileName}.{fileType}")
    public void getImage(@PathVariable("fileName") String fileName, @PathVariable("fileType") String fileType, HttpServletResponse res) {
        res.setHeader("Content-Type", "image/jpeg");
        ServletOutputStream out = null;
        FileInputStream in = null;
        try {
            File file = new File(imagesDir + fileName + "." + fileType);
            out = res.getOutputStream();
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = 0;
            while ((i = in.read(buffer)) != -1) {
                out.write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
