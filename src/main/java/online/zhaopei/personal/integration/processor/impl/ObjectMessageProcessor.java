package online.zhaopei.personal.integration.processor.impl;

import online.zhaopei.personal.integration.processor.MessageProcessor;
import online.zhaopei.personal.jaxb.IdentityRequest;
import online.zhaopei.personal.service.PersonalInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhaopei on 18/5/10.
 */
@Component
public class ObjectMessageProcessor implements MessageProcessor {

    private static final Log LOGGER = LogFactory.getLog(ObjectMessageProcessor.class);

    @Autowired
    private PersonalInfoService personalInfoService;

    @Override
    public void process(Object obj) {

        if (obj instanceof IdentityRequest) {
            LOGGER.info("process [IdentityRequest]");
            this.personalInfoService.processIdentityRequest((IdentityRequest) obj);
        } else {
            LOGGER.error("Unknown Type " + obj.getClass() + " is process");
        }
    }
}
