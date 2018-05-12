package online.zhaopei.personal.integration.activator;

import online.zhaopei.personal.integration.processor.MessageProcessor;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutorService;

/**
 * Created by zhaopei on 18/5/10.
 */
public class ReceiveObjectMessageServiceActivator extends AbstractMessageHandler {

    private ExecutorService executorService;

    private MessageProcessor messageProcessor;

    @Override
    protected void handleMessageInternal(Message<?> message) throws Exception {
        this.executorService.execute(new Runnable() {
            @Override
            public void run() {
                Assert.notNull(message, "message must not be null");
                Object payload = message.getPayload();
                Assert.notNull(payload, "message payload must not be null");
                messageProcessor.process(payload);
            }
        });
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }
}
