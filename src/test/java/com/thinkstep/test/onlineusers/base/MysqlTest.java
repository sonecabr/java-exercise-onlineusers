package com.thinkstep.test.onlineusers.base;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MysqlTest {

    @Resource
    private EntityManager entityManager;

    @Test
    public void shouldConnectToMysql() {
        Query query = entityManager.createNativeQuery("SELECT 1");
        Assert.assertEquals(
                BigInteger.valueOf(1l),
                query.getSingleResult()
        );
    }
}
