package online.zhaopei.personal.constant;

/**
 * Created by zhaopei on 18/5/8.
 */
public enum MessageTypeEnum {
    TYPE_SEND("type_send"),
    TYPE_ERROR_FORWARD("type_error_forward"),
    ;

    private String value;

    private MessageTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
