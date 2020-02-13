package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.FileCreate;
import life.majiang.comunity.comunity.dto.FileDto;
import life.majiang.comunity.comunity.provider.QiniuProvider;
import life.majiang.comunity.comunity.service.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletRequest;
import java.io.File;

@Controller
public class FileController {
    @Autowired(required = false)
    private QiniuProvider qiniuProvider;
    @Autowired(required = false)
    private FileService fileService;
    @Value("${qiniu.bucket.host.name}")
    private String hostName;

//    public final static String UPLOADED_FOLDER = "static/upload";
//    @RequestMapping("/file/upload")
//    @ResponseBody
//    public FileDto upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile attach){
//        FileDto fileDto = new FileDto();
//        try {
//            File filePath=new File(UPLOADED_FOLDER);
//            /**
//             * 文件路径不存在则需要创建文件路径
//             */
//            if(!filePath.exists()){
//                filePath.mkdirs();
//            }
//
//            //最终文件名
//            File realFile=new File(UPLOADED_FOLDER + File.separator + UUID.randomUUID().toString() + ".jpg");
//            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);
//
//            fileDto.setSuccess(1);
//            fileDto.setMessage("上传成功");
//            fileDto.setUrl("/upload/"+realFile.getName());
//        } catch (Exception e) {
//            fileDto.setSuccess(0);
//            fileDto.setMessage("上传失败，异常信息：" + e.getMessage());
//        }
//        return  fileDto;
//    }

    @RequestMapping(value = "/file/upload")
    @ResponseBody
    public FileDto qiniuyunAdd(ServletRequest request,
                                    @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) {
        String realPath = request.getServletContext().getRealPath("/")+"upload/";
        String qiniuPath = "";
        FileDto fileDto = new FileDto();
        FileCreate fileCreate = fileService.upload(file);
        //上传文件方法返回重命名，文件名称
        String s = fileCreate.getFileName();

        String uploadPath = qiniuProvider.upload(realPath+s);
        //组合七牛云外链(七牛云生成域名+保存的文件KEY)
        qiniuPath = "http://"+hostName+"/" + uploadPath;
        //添加到七牛云


        File file1 = new File(realPath+s);
        if (file1.isFile()) {
            //删除服务器图片
            FileUtils.deleteQuietly(file1);
        }

        if(StringUtils.isBlank(uploadPath)){
            fileDto.setSuccess(0);
            fileDto.setMessage("上传失败");
            return fileDto;
        }

        fileDto.setSuccess(1);
        fileDto.setMessage("上传成功");
        fileDto.setUrl(qiniuPath);
        return fileDto;
    }
}
