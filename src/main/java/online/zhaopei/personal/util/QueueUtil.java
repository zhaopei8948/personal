package online.zhaopei.personal.util;

import online.zhaopei.personal.constant.CommonConstant;
import online.zhaopei.personal.constant.MessageTypeEnum;

/**
 * Created by zhaopei on 18/5/12.
 */
public final class QueueUtil {

    public static void sendMessage(Object obj, MessageTypeEnum messageTypeEnum) throws Exception {
        CommonConstant.DISTRIBUTE_QUEUE.put(new Object[]{ messageTypeEnum.toString(), obj});
    }
}
