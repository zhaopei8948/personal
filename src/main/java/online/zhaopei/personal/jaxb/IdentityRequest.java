package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.*;

/**
 * Created by zhaopei on 18/5/10.
 */
@XmlRootElement(name = "IdentityRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "identityHead", "persons"
})
public class IdentityRequest {

    @XmlElement(name = "IdentityHead")
    private IdentityHead identityHead;

    private Persons persons;

    public IdentityHead getIdentityHead() {
        return identityHead;
    }

    public void setIdentityHead(IdentityHead identityHead) {
        this.identityHead = identityHead;
    }

    public Persons getPersons() {
        return persons;
    }

    public void setPersons(Persons persons) {
        this.persons = persons;
    }
}
