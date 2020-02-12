package life.majiang.comunity.comunity.enums;

public enum NotificationTypeEnum {
    COMMENT_NOTIFICATION(1),
    QUESTION_NOTIFICATION(0)
    ;
    private int type;

    NotificationTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
