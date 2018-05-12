package online.zhaopei.personal.jaxb;

import online.zhaopei.personal.jaxb.adapter.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "messageNo", "messageTime", "count", "ecssCspNo"
})
public class PersonalInfoHead {

    @XmlElement(name = "MessageNo")
    private String messageNo;

    @XmlElement(name = "MessageTime")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date messageTime;

    @XmlElement(name = "Count")
    private Integer count;

    @XmlElement(name = "EcssCspNo")
    private String ecssCspNo;

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
}
