package com.wasion.meterreading.task.process;

import com.wasion.meterreading.exception.MRException;
import com.wasion.meterreading.task.context.CommandContext;

/**
 * 命令下发接口
 * 多个CommandSender之间可以实现链式调用
 * 
 * @see com.wasion.meterreading.task.context.DefaultCommandContext
 * @author w24882 xieningjie
 * @date 2020年1月7日
 */
public interface CommandSender {

    /**
     * 当前CommandSender 下发命令到平台
     * @param context
     * @throws MRException
     * @author w24882 xieningjie
     * @date 2020年1月7日
     */
    void sendCommand(CommandContext context) throws MRException;

    default boolean hasNext() {
        return false;
    }

    /**
     * 获取下一个CommandSender
     * @return
     * @author w24882 xieningjie
     * @date 2020年1月7日
     */
    default CommandSender next() {
        return null;
    }

}
