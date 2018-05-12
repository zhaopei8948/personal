package online.zhaopei.personal.jaxb;

import online.zhaopei.personal.jaxb.adapter.DateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
        "messageNo", "messageTime", "count", "ecssCspNo", "companyId"
})
public class IdentityHead {

    private String messageNo;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date messageTime;

    private Integer count;

    private String ecssCspNo;

    private String companyId;

    public String getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(String messageNo) {
        this.messageNo = messageNo;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getEcssCspNo() {
        return ecssCspNo;
    }

    public void setEcssCspNo(String ecssCspNo) {
        this.ecssCspNo = ecssCspNo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
