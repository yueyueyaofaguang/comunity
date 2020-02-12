package life.majiang.comunity.comunity.controller;

import life.majiang.comunity.comunity.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @RequestMapping("file/upload")
    @ResponseBody
    public FileDto upload(){
        FileDto fileDto = new FileDto();
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/wechat.jpeg");
        return fileDto;
    }
}
