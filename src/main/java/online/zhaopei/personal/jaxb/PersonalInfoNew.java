package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement(name = "PersonalInfoNew")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "personalInfoHead", "personalInfoListList"
})
public class PersonalInfoNew {

    @XmlElement(name = "PersonalInfoHead")
    private PersonalInfoHead personalInfoHead;

    @XmlElement(name = "PersonalInfoList")
    private List<PersonalInfoList> personalInfoListList = new ArrayList<PersonalInfoList>();

    public PersonalInfoHead getPersonalInfoHead() {
        return personalInfoHead;
    }

    public void setPersonalInfoHead(PersonalInfoHead personalInfoHead) {
        this.personalInfoHead = personalInfoHead;
    }

    public List<PersonalInfoList> getPersonalInfoListList() {
        return personalInfoListList;
    }

    public void setPersonalInfoListList(List<PersonalInfoList> personalInfoListList) {
        this.personalInfoListList = personalInfoListList;
    }
}
