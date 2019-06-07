package com.thinkstep.test.onlineusers.base;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.config.SchemaConfig;
import com.wix.mysql.distribution.Version;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Optional;


@Configuration
public class TestSuite {

    public static EmbeddedMysql embeddedMysql;

    @BeforeClass
    public static void warmup() {

        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(
                MysqldConfig.aMysqldConfig(Version.v5_7_15)
                        .withPort(3307)
                        .withTimeZone("Europe/Berlin")
                        .withUser("test", "test")
                        .build()
        ).addSchema(
                SchemaConfig
                        .aSchemaConfig("onlineusers")
                        .withScripts(ScriptResolver.classPathScripts("init_ddl.sql"))
                        .build()
        ).start();
    }


    @AfterClass
    public static void tearDown() {
        Optional.ofNullable(embeddedMysql).ifPresent(e -> e.stop());
    }
}
