package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.FileDto;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Controller
public class FileController {
    public final static String UPLOADED_FOLDER = "static/upload";
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(HttpServletRequest request,
                          @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach){
        FileDto fileDto = new FileDto();
        try {
            File filePath=new File(UPLOADED_FOLDER);
            /**
             * 文件路径不存在则需要创建文件路径
             */
            if(!filePath.exists()){
                filePath.mkdirs();
            }

            //最终文件名
            File realFile=new File(UPLOADED_FOLDER + File.separator + UUID.randomUUID().toString() + ".jpg");
            FileUtils.copyInputStreamToFile(attach.getInputStream(), realFile);

            fileDto.setSuccess(1);
            fileDto.setMessage("上传成功");
            fileDto.setUrl("/upload/"+realFile.getName());
        } catch (Exception e) {
            fileDto.setSuccess(0);
            fileDto.setMessage("上传失败，异常信息：" + e.getMessage());
        }
        return  fileDto;
    }
}
