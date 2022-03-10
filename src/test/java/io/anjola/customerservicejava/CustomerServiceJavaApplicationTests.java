package io.anjola.customerservicejava;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j

class CustomerServiceJavaApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }

    @Test
    void databaseConnectionTest() {
        log.info("Datasource connection --> {}", dataSource);
        assertThat(dataSource).isNotNull();

        try {
            Connection connection = dataSource.getConnection();
//            assertThat(connection.getCatalog()).isEqualToIgnoringCase("blogdb");
            assertThat(connection.getCatalog()).isEqualToIgnoringCase("customer_db");
            log.info("connection --> {}", connection.getCatalog());
        }catch (SQLException ex){
            log.info("An error occurred --> {}", ex);
        }
    }
}
