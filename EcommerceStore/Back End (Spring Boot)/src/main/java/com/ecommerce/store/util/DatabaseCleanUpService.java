package com.ecommerce.store.util;

import com.ecommerce.store.config.AppConstants;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseCleanUpService {

    @Autowired
    private EntityManager em;

    @Transactional
    public void clearAllTables(){

        if (AppConstants.DATABASE_ENGINE == AppConstants.DBType.MYSQL){
            mySqlTruncateTables();

        } else if (AppConstants.DATABASE_ENGINE == AppConstants.DBType.POSTGRES) {
            postgresTruncateTables();

        }

    }

    private void mySqlTruncateTables() {
        em.createNativeQuery("SET foreign_key_checks=0;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE addresses").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE cart_items;").executeUpdate();
        em.createNativeQuery("TRUNCATE Table carts;").executeUpdate();
        em.createNativeQuery("TRUNCATE Table category;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE products;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE role;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE user_address;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE user_role;").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE users;").executeUpdate();
        em.createNativeQuery("SET foreign_key_checks=1;").executeUpdate();
    }

    private void postgresTruncateTables(){
        em.createNativeQuery("CALL truncate_all_tables()").executeUpdate();
    }


}
