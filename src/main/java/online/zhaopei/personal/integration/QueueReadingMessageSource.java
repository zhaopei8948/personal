package online.zhaopei.personal.integration;

import online.zhaopei.personal.constant.ChannelConstant;
import online.zhaopei.personal.constant.CommonConstant;
import online.zhaopei.personal.transformer.ObjectToByteArrayTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

/**
 * Created by zhaopei on 17/12/21.
 */
public class QueueReadingMessageSource extends IntegrationObjectSupport implements MessageSource<Object> {

    private static final Log LOGGER = LogFactory.getLog(QueueReadingMessageSource.class);

    @Override
    public Message<Object> receive() {
        Message<Object> message = null;
        Object[] messageAndType = CommonConstant.DISTRIBUTE_QUEUE.poll();
        if (null != messageAndType) {
            Assert.isTrue(2 == messageAndType.length, "Distribute queue message length must equals 2");
            Assert.isInstanceOf(String.class, messageAndType[0], "First message must be of type [java.lang.String]");
            message = this.getMessageBuilderFactory().withPayload(messageAndType[1]).setHeaderIfAbsent(ChannelConstant.MESSAGE_HEADER_TYPE, (String) messageAndType[0]).build();
            LOGGER.info("Created message: [" + message + "]");
        }
        return message;
    }
}
