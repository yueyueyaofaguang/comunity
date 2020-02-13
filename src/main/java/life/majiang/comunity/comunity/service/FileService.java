package life.majiang.comunity.comunity.service;

import life.majiang.comunity.comunity.dto.FileCreate;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Service
public class FileService {
    @Value("${UPLOADED_FOLDER}")
    public String UPLOADED_FOLDER;
    public FileCreate upload(MultipartFile attach){
        FileCreate fileCreate = new FileCreate();
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

            fileCreate.setFileName(realFile.getName());
            fileCreate.setFilePath("/upload/"+realFile.getName());

        } catch (Exception e) {
            System.out.println("上传失败，异常信息：" + e.getMessage());
        }

        return  fileCreate;
    }
}
