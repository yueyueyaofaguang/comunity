package life.majiang.comunity.comunity.exception;

public class GetPageException extends RuntimeException{
    private String message;
    private Integer code;

    public GetPageException(ICustomizeResCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
