package com.snowcattle.game.db.service.async.transaction.factory;

import com.redis.config.GlobalConstants;
import com.redis.transaction.enums.GameTransactionEntityCause;
import com.redis.transaction.factory.GameTransactionKeyFactory;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class DbGameTransactionKeyFactory extends GameTransactionKeyFactory {
    /**
     * 获取玩家锁
     * @param cause
     * @return
     */
    public String getPlayerTransactionEntityKey(GameTransactionEntityCause cause, String redisKey, String union){
        return redisKey + cause.getCause() + GlobalConstants.Strings.commonSplitString + union;
    }

}
