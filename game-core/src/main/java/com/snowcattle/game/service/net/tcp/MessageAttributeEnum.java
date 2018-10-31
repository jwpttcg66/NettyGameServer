package com.snowcattle.game.service.net.tcp;

/**
 * @author jwp
 *	message附带参数
 */
public enum MessageAttributeEnum {

    /**
     * tcp跟udp使用
     */
    DISPATCH_SESSION,

    /**
     * http使用
     */
    DISPATCH_HTTP_REQUEST,

    ;
}
