package online.zhaopei.personal.integration;

import online.zhaopei.personal.configuration.PersonalProp;
import online.zhaopei.personal.constant.ChannelConstant;
import online.zhaopei.personal.constant.CommonConstant;
import online.zhaopei.personal.constant.MessageTypeEnum;
import online.zhaopei.personal.integration.activator.ReceiveObjectMessageServiceActivator;
import online.zhaopei.personal.integration.processor.MessageProcessor;
import online.zhaopei.personal.transformer.ByteArrayToObjectTransformer;
import online.zhaopei.personal.transformer.CommonTransformer;
import online.zhaopei.personal.transformer.ObjectToByteArrayTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaopei on 17/12/20.
 */
@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    private final static Log LOGGER = LogFactory.getLog(IntegrationConfiguration.class);

    @Autowired
    private PersonalProp personalProp;


    @Bean(ChannelConstant.CHANNEL_SEND)
    public MessageChannel sendChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(ChannelConstant.CHANNEL_ERROR_FORWARD)
    public MessageChannel errorForwardChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(ChannelConstant.CHANNEL_RECEIVE)
    public MessageChannel receiveChannel() {
        return new DirectChannel();
    }

    @Bean(ChannelConstant.CHANNEL_OBJECT_SEND)
    public MessageChannel objectSendChannel() {
        return new DirectChannel();
    }

    @Bean(ChannelConstant.CHANNEL_FILE_TO_BYTE_ARRAY)
    public MessageChannel fileToByteArrayChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean(ChannelConstant.CHANNEL_BYTE_ARRAY_TO_OBJECT)
    public MessageChannel byteArrayToObject() {
        return new DirectChannel();
    }

    @Bean(ChannelConstant.CHANNEL_OBJECT_TO_BYTE_ARRAY)
    public MessageChannel objectToByteArrayChannel() {
        return new DirectChannel();
    }

    @Bean(ChannelConstant.CHANNEL_DISTRIBUTE)
    public MessageChannel distributeChannel() {
        return new DirectChannel();
    }

    private static MessageHandler buildFileWriteMessageHandler(String dir, FileNameGenerator fileNameGenerator, boolean split) {
        FileWritingMessageHandler handler = null;
        if (split) {
            handler = new FileWritingMessageHandler(new SpelExpressionParser().parseExpression(
                    "@filePara.getTodayDir('" + dir + "')"));
        } else {
            handler = new FileWritingMessageHandler(new File(dir));
        }
        handler.setDeleteSourceFiles(true);
        handler.setExpectReply(false);
        handler.setFileNameGenerator(fileNameGenerator);
        handler.setAutoCreateDirectory(true);
        return handler;
    }

    @Bean
    public Object filePara() {
        return new Object() {

            public String getTodayDir(String dir) {
                return StringUtils.isEmpty(dir) ? "" : dir + File.separator +
                        CommonConstant.DATE_FORMAT.format(Calendar.getInstance().getTime());
            }
        };
    }

    @Bean
    @Autowired
    public ExecutorService taskExecutor(PersonalProp personalProp) {
        return Executors.newFixedThreadPool(personalProp.getPoolSize());
    }

    @Bean
    @InboundChannelAdapter(value = ChannelConstant.CHANNEL_RECEIVE, poller = @Poller(fixedDelay = "${personal.scanRate}", maxMessagesPerPoll = "${personal.messageQueue}"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(this.personalProp.getReceiveDir()));
        source.setFilter(new SimplePatternFileListFilter(this.personalProp.getReceiveFilePattern()));
        return source;
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_FILE_TO_BYTE_ARRAY)
    public MessageHandler receiveBackupMessageHandler() {
        return buildFileWriteMessageHandler(this.personalProp.getReceiveBackupDir(),
                new DefaultFileNameGenerator(this.personalProp.getReceiveBackupPrefix(),
                        this.personalProp.getSuffix(), true), true);
    }

    @Bean
    @InboundChannelAdapter(value = ChannelConstant.CHANNEL_OBJECT_SEND, poller = @Poller(fixedDelay = "${personal.scanRate}", maxMessagesPerPoll = "${personal.messageQueue}"))
    public MessageSource<Object> queueReadingMessageSource() {
        return new QueueReadingMessageSource();
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_SEND)
    public MessageHandler sendHandler() {
        return buildFileWriteMessageHandler(this.personalProp.getSendDir(),
                new DefaultFileNameGenerator(this.personalProp.getSendPrefix(),
                        this.personalProp.getSuffix(), true), false);
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_SEND)
    public MessageHandler sendBackupHandler() {
        return buildFileWriteMessageHandler(this.personalProp.getSendBackupDir(),
                new DefaultFileNameGenerator(this.personalProp.getSendBackupPrefix(),
                        this.personalProp.getSuffix(), true), true);
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_ERROR_FORWARD)
    public MessageHandler errorForwardHandler() {
        return buildFileWriteMessageHandler(this.personalProp.getErrorForwardDir(),
                new DefaultFileNameGenerator(this.personalProp.getSendPrefix(),
                        this.personalProp.getSuffix(), true), false);
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_ERROR_FORWARD)
    public MessageHandler errorForwardBackupHandler() {
        return buildFileWriteMessageHandler(this.personalProp.getErrorForwardBackupDir(),
                new DefaultFileNameGenerator(this.personalProp.getSendBackupPrefix(),
                        this.personalProp.getSuffix(), true), true);
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_OBJECT_TO_BYTE_ARRAY)
    public HeaderValueRouter headerValueRouter() {
        HeaderValueRouter route = new HeaderValueRouter(ChannelConstant.MESSAGE_HEADER_TYPE);
        route.setChannelMapping(MessageTypeEnum.TYPE_SEND.toString(), ChannelConstant.CHANNEL_SEND);
        route.setChannelMapping(MessageTypeEnum.TYPE_ERROR_FORWARD.toString(), ChannelConstant.CHANNEL_ERROR_FORWARD);
        return route;
    }

    @Bean
    @ServiceActivator(inputChannel = ChannelConstant.CHANNEL_BYTE_ARRAY_TO_OBJECT)
    @Autowired
    public MessageHandler objectMessageHandler(ExecutorService executorService, MessageProcessor messageProcessor) {
        ReceiveObjectMessageServiceActivator receiveObjectMessageServiceActivator =  new ReceiveObjectMessageServiceActivator();
        receiveObjectMessageServiceActivator.setExecutorService(executorService);
        receiveObjectMessageServiceActivator.setMessageProcessor(messageProcessor);
        return receiveObjectMessageServiceActivator;
    }

    @Bean
    @Transformer(inputChannel = ChannelConstant.CHANNEL_RECEIVE, outputChannel = ChannelConstant.CHANNEL_FILE_TO_BYTE_ARRAY)
    public FileToByteArrayTransformer fileToByteArrayTransformer() {
        FileToByteArrayTransformer fileToByteArrayTransformer = new FileToByteArrayTransformer();
        fileToByteArrayTransformer.setDeleteFiles(true);
        return fileToByteArrayTransformer;
    }

    @Bean
    @Transformer(inputChannel = ChannelConstant.CHANNEL_FILE_TO_BYTE_ARRAY, outputChannel = ChannelConstant.CHANNEL_BYTE_ARRAY_TO_OBJECT)
    @Autowired
    public ByteArrayToObjectTransformer byteArrayToObjectTransformer(CommonTransformer commonTransformer) {
        ByteArrayToObjectTransformer byteArrayToObjectTransformer = new ByteArrayToObjectTransformer();
        byteArrayToObjectTransformer.setCommonTransformer(commonTransformer);
        return byteArrayToObjectTransformer;
    }

    @Bean
    @Transformer(inputChannel = ChannelConstant.CHANNEL_OBJECT_SEND, outputChannel = ChannelConstant.CHANNEL_OBJECT_TO_BYTE_ARRAY)
    @Autowired
    public ObjectToByteArrayTransformer objectToByteArrayTransformer(CommonTransformer commonTransformer) {
        ObjectToByteArrayTransformer objectToByteArrayTransformer = new ObjectToByteArrayTransformer();
        objectToByteArrayTransformer.setCommonTransformer(commonTransformer);
        return objectToByteArrayTransformer;
    }
}
