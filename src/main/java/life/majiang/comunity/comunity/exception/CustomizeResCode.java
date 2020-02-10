package life.majiang.comunity.comunity.exception;

public enum CustomizeResCode implements ICustomizeResCode {
    SUCCESS(200, "成功啦！"),
    QUESTION_NOT_FOUND(2001, "你的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或者评论进行回复"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    COTENT_NOT_FOUND(2007, "输入内容不能为空"),
    NO_LOGIN(2008,"未登录不能进行评论，请先登录");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeResCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
