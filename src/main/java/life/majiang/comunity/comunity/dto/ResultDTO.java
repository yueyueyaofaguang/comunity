package life.majiang.comunity.comunity.dto;

import life.majiang.comunity.comunity.exception.CustomizeResCode;
import life.majiang.comunity.comunity.exception.ICustomizeResCode;
import lombok.Data;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    private Object result;

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static ResultDTO success(Object data){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(CustomizeResCode.SUCCESS.getCode());
        resultDTO.setMessage(CustomizeResCode.SUCCESS.getMessage());
        resultDTO.setResult(data);
        return resultDTO;
    }

    /**
     * 无参的成功返回
     * @return
     */
    public static ResultDTO success(){
        return success(null);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @return
     */
    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        resultDTO.setResult(null);
        return resultDTO;
    }

    /**
     *
     * @param customizeResCode
     * @return
     */
    public static ResultDTO errorOf(ICustomizeResCode customizeResCode){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(customizeResCode.getCode());
        resultDTO.setMessage(customizeResCode.getMessage());
        resultDTO.setResult(null);
        return  resultDTO;
    }

    /**
     *
     * @param message
     * @return
     */
    public static ResultDTO errorOf(String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(-1);
        resultDTO.setMessage(message);
        resultDTO.setResult(null);
        return resultDTO;
    }
}
