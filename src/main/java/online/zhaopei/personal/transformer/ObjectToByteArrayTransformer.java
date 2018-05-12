package online.zhaopei.personal.transformer;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaopei on 18/5/9.
 */
public class ObjectToByteArrayTransformer extends IntegrationObjectSupport implements Transformer {

    private static final Log LOGGER = LogFactory.getLog(ObjectToByteArrayTransformer.class);

    private static final String PREFIX = "object_";

    public static final String ORIGINAL_OBJECT = "originalObject";

    private Map<Class<?>, Marshaller> marshallerMap = new HashMap<Class<?>, Marshaller>();

    private CommonTransformer commonTransformer;

    @Override
    public Message<?> transform(Message<?> message) {
        try {
            Assert.notNull(message, "Message must not be null");
            Object playload = message.getPayload();
            Assert.notNull(playload, "Message playload must not be null");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] result = this.commonTransformer.ObjectToByteArray(playload);
            Message<?> transformedMessage = this.getMessageBuilderFactory().withPayload(result)
                    .copyHeaders(message.getHeaders())
                    .setHeaderIfAbsent(ORIGINAL_OBJECT, playload)
                    .build();

            return transformedMessage;
        } catch (Exception e) {
            throw new MessagingException(message, "failed to transform Object Message", e);
        }
    }

    public CommonTransformer getCommonTransformer() {
        return commonTransformer;
    }

    public void setCommonTransformer(CommonTransformer commonTransformer) {
        this.commonTransformer = commonTransformer;
    }
}
