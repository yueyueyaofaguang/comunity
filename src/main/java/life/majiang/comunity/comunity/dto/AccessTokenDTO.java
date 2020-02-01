package life.majiang.comunity.comunity.dto;

import lombok.Data;

//变量超过两个将封装称类
//dto=数据传输对象
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
