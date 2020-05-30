package com.example.hotel.controller;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.util.AliyunOSSUtil;
import com.example.hotel.vo.ResponseVO;
import org.slf4j.LoggerFactory;
import com.example.hotel.bl.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 庄子元 181830266@smail.nju.edu.cn
 * @date 2020/5/25 17:09
 */
@RestController()
@RequestMapping("/api/user")
public class AliyunOSSController {
    private static final String UPLOAD_SUCCESS = "上传成功";
    private static final String UPLOAD_FAILURE = "上传失败";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;
    @Autowired
    private HotelService hotelService;

    @PostMapping("/{hotelId}/uploadHotelPicture")
    public ResponseVO uploadHotelPicture(@PathVariable Integer hotelId, @RequestParam("file") MultipartFile file) {
        String url = uploadPicture(file);
        if (url == null) {
            return ResponseVO.buildFailure(UPLOAD_FAILURE);
        }
        hotelService.updateHotelPicture(hotelId, url);
        return ResponseVO.buildSuccess(UPLOAD_SUCCESS);
    }

    @PostMapping("/{userId}/uploadUserPortrait")
    public ResponseVO uploadUserPortrait(@PathVariable Integer userId, @RequestParam("file") MultipartFile file) {
        String url = uploadPicture(file);
        if (url == null) {
            return ResponseVO.buildFailure(UPLOAD_FAILURE);
        }
        accountService.updatePortrait(userId, url);
        return ResponseVO.buildSuccess(UPLOAD_SUCCESS);
    }

    private String uploadPicture(MultipartFile file) {
        logger.info("文件上传");
        String filename = file.getOriginalFilename();
        try {
            assert filename != null;
            if (!"".equals(filename.trim())) {
                // 把文件下载到本地
                File newFile = new File(filename);
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(file.getBytes());
                os.close();
                file.transferTo(newFile);
                // 上传到OSS并且删除本地图片
                String uploadUrl = AliyunOSSUtil.upLoad(newFile);
                if (newFile.delete()) {
                    logger.info("img has been deleted");
                }
                return uploadUrl;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
}
