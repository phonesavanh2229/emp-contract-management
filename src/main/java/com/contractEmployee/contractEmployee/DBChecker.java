//package com.contractEmployee.contractEmployee;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//
//@Component
//public class DBChecker implements CommandLineRunner {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void run(String... args) throws Exception {
//        try (Connection conn = dataSource.getConnection()) {
//            System.out.println("✅ JDBC URL: " + conn.getMetaData().getURL());
//            System.out.println("✅ Driver Name: " + conn.getMetaData().getDriverName());
//            System.out.println("✅ DB Version: " + conn.getMetaData().getDatabaseProductVersion());
//        }
//    }
//}
