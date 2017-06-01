package com.snowcattle.game.db.service.async.transaction.factory;

import com.redis.transaction.enums.GameTransactionCause;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/4/13.
 */
@Service
public class DbGameTransactionCauseFactory {
    public GameTransactionCause asyncDbSave = new GameTransactionCause("asyncDbSave");

    public GameTransactionCause getAsyncDbSave() {
        return asyncDbSave;
    }
}
