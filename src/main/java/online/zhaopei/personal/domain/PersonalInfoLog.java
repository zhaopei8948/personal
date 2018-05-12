package online.zhaopei.personal.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/8.
 */
public class PersonalInfoLog implements Serializable {

    private String seqNo;

    private String personalName;

    private String idNumber;

    private Date requestTime;

    private String response;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
