package online.zhaopei.personal.jaxb;

import online.zhaopei.personal.jaxb.adapter.TimestampAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "personalName", "idNumber", "signTime", "status", "billType"
})
public class PersonalInfoList {

    @XmlElement(name = "PersonalName")
    private String personalName;

    @XmlElement(name = "IdNumber")
    private String idNumber;

    @XmlElement(name = "SignTime")
    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Date signTime;

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "BillType")
    private String billType;

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

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }
}
