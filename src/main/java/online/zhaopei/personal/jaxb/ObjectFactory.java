package online.zhaopei.personal.jaxb;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by zhaopei on 18/5/11.
 */
@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public IdentityRequest createIdentityRequest() {
        return new IdentityRequest();
    }
}
