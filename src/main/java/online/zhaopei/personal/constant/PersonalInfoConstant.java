package online.zhaopei.personal.constant;

/**
 * Created by zhaopei on 18/5/10.
 */
public enum PersonalInfoConstant {
    STATUS_FAIL("R"),
    STATUS_WAIT_FOR_VERIFICATION("W"),
    STATUS_PASS("P"),
    ;

    private String value;

    private PersonalInfoConstant(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
