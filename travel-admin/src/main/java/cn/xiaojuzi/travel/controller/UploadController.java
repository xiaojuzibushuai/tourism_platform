package cn.xiaojuzi.travel.controller;

import cn.xiaojuzi.travel.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * @program: tourism_platform
 * @description:上传控制层
 * @author: xiaojuzi
 * @create: 2022-02-08 15:16
 **/
@Controller
@Api(tags = "上传相关接口")
public class UploadController {


    @ApiOperation(value = "图片上传")
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Object uploadImg(MultipartFile pic) throws Exception {
        //上传到OSS
        String path = UploadUtil.uploadAli(pic);
        return path;
    }

    @ApiOperation(value = "图片路径返回")
    @RequestMapping("/uploadImg_ck")
    @ResponseBody
    public Object uploadImg_ck(MultipartFile upload) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String imagePath= null;
        if(upload != null && upload.getSize() > 0){
            try {
                //图片保存, 返回路径
                imagePath =  UploadUtil.uploadAli(upload);
                //表示保存成功
                map.put("uploaded", 1);
                map.put("url",imagePath);

            }catch (Exception e){
                e.printStackTrace();
                map.put("uploaded", 0);
                Map<String, Object> mm = new HashMap<String, Object>();
                mm.put("message",e.getMessage() );
                map.put("error", mm);
            }
        }
        return map;

    }
}
