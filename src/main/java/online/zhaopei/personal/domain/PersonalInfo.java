package online.zhaopei.personal.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/8.
 */
public class PersonalInfo implements Serializable {

    private String seqNo;

    private String personalName;

    private String idNumber;

    private String status;

    private Date decTime;

    private Date updateTime;

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

    public Date getDecTime() {
        return decTime;
    }

    public void setDecTime(Date decTime) {
        this.decTime = decTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
