package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement(name = "DATA")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfhcData {

    @XmlElement(name = "RECORD")
    List<SfhcRecord> sfhcRecordList = new ArrayList<SfhcRecord>();

    public List<SfhcRecord> getSfhcRecordList() {
        return sfhcRecordList;
    }

    public void setSfhcRecordList(List<SfhcRecord> sfhcRecordList) {
        this.sfhcRecordList = sfhcRecordList;
    }
}
