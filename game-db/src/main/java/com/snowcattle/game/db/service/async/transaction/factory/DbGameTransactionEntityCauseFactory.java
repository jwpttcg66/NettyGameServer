package com.snowcattle.game.db.service.async.transaction.factory;

import com.redis.transaction.enums.GameTransactionEntityCause;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
public class DbGameTransactionEntityCauseFactory {

    public final GameTransactionEntityCause asyncDbSave = new GameTransactionEntityCause("asyncDbSave");

    public GameTransactionEntityCause getAsyncDbSave() {
        return asyncDbSave;
    }
}
