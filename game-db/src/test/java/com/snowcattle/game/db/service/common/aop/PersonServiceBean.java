package com.snowcattle.game.db.service.common.aop;

import com.snowcattle.game.db.service.common.aop.Person;
import com.snowcattle.game.db.service.common.aop.PersonService;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/22.
 */
@Service("personService")
public class PersonServiceBean implements PersonService {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public PersonServiceBean() {
    }

    public PersonServiceBean(String user) {
        super();
        this.user = user;
    }

    public void save(Person person) {
        System.out.println("执行PerServiceBean的save方法");
        //throw new RuntimeException("======");
    }
}
