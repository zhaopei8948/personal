package online.zhaopei.personal.transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

/**
 * Created by zhaopei on 18/5/8.
 */
public class ByteArrayToObjectTransformer extends IntegrationObjectSupport implements Transformer {

    private static final Log LOGGER = LogFactory.getLog(ByteArrayToObjectTransformer.class);

    private static final String PREFIX = "byte_array_";

    public static final String ORIGINAL_BYTE_ARRAY = PREFIX + "originalByteArray";

    private CommonTransformer commonTransformer;

    @Override
    public Message<?> transform(Message<?> message) {
        try {
            Assert.notNull(message, "Message must not be null");
            Object playload = message.getPayload();
            Assert.notNull(playload, "Message playload must not be null");
            Assert.isInstanceOf(byte[].class, playload, "Message playload must be of type [byte[]]");
            byte[] bytes = (byte[]) playload;
            Object result = this.commonTransformer.ByteArrayToObject(bytes);
            Message<?> transformedMessage = this.getMessageBuilderFactory().withPayload(result)
                    .copyHeaders(message.getHeaders())
                    .setHeaderIfAbsent(ORIGINAL_BYTE_ARRAY, bytes)
                    .build();
            return transformedMessage;
        } catch (Exception e) {
            throw new MessagingException(message, "failed to transform byte[] Message", e);
        }

    }

    public CommonTransformer getCommonTransformer() {
        return commonTransformer;
    }

    public void setCommonTransformer(CommonTransformer commonTransformer) {
        this.commonTransformer = commonTransformer;
    }
}
