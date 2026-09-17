package com.example.reconciliation.infrastructure.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.reconciliation.domain.repository",
    entityManagerFactoryRef = "reconciliationEntityManagerFactory",
    transactionManagerRef = "reconciliationTransactionManager"
)
public class PostgreSQLConfig {

    private static final String ENTITY_PACKAGE = "com.example.reconciliation.domain.model";
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int IDLE_TIMEOUT = 600;
    private static final int MAX_LIFETIME = 1800;
    private static final int MINIMUM_IDLE = 5;
    private static final int MAXIMUM_POOL_SIZE = 20;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.reconciliation")
    public DataSource reconciliationDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        
        return dataSourceBuilder
            .type(org.apache.tomcat.jdbc.pool.DataSource.class)
            .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean reconciliationEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            DataSource reconciliationDataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.jdbc.batch_size", 50);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.jdbc.batch_versioned_data", true);
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.generate_statistics", false);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.use_sql_comments", false);
        
        return builder
            .dataSource(reconciliationDataSource)
            .packages(ENTITY_PACKAGE)
            .properties(properties)
            .persistenceUnit("reconciliation")
            .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager reconciliationTransactionManager(
            EntityManagerFactory reconciliationEntityManagerFactory) {
        return new JpaTransactionManager(reconciliationEntityManagerFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.reconciliation.hikari")
    public HikariConfigurationProperties hikariProperties() {
        return new HikariConfigurationProperties();
    }

    public static class HikariConfigurationProperties {
        private int connectionTimeout = CONNECTION_TIMEOUT;
        private int idleTimeout = IDLE_TIMEOUT;
        private int maxLifetime = MAX_LIFETIME;
        private int minimumIdle = MINIMUM_IDLE;
        private int maximumPoolSize = MAXIMUM_POOL_SIZE;
        private String poolName = "ReconciliationHikariPool";
        private boolean autoCommit = false;
        private boolean allowPoolSuspension = false;
        private boolean readOnly = false;
        private String registerMbeans = "true";
        private String connectionTestQuery = "SELECT 1";

        public int getConnectionTimeout() { return connectionTimeout; }
        public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
        public int getIdleTimeout() { return idleTimeout; }
        public void setIdleTimeout(int idleTimeout) { this.idleTimeout = idleTimeout; }
        public int getMaxLifetime() { return maxLifetime; }
        public void setMaxLifetime(int maxLifetime) { this.maxLifetime = maxLifetime; }
        public int getMinimumIdle() { return minimumIdle; }
        public void setMinimumIdle(int minimumIdle) { this.minimumIdle = minimumIdle; }
        public int getMaximumPoolSize() { return maximumPoolSize; }
        public void setMaximumPoolSize(int maximumPoolSize) { this.maximumPoolSize = maximumPoolSize; }
        public String getPoolName() { return poolName; }
        public void setPoolName(String poolName) { this.poolName = poolName; }
        public boolean isAutoCommit() { return autoCommit; }
        public void setAutoCommit(boolean autoCommit) { this.autoCommit = autoCommit; }
        public boolean isAllowPoolSuspension() { return allowPoolSuspension; }
        public void setAllowPoolSuspension(boolean allowPoolSuspension) { this.allowPoolSuspension = allowPoolSuspension; }
        public boolean isReadOnly() { return readOnly; }
        public void setReadOnly(boolean readOnly) { this.readOnly = readOnly; }
        public String getRegisterMbeans() { return registerMbeans; }
        public void setRegisterMbeans(String registerMbeans) { this.registerMbeans = registerMbeans; }
        public String getConnectionTestQuery() { return connectionTestQuery; }
        public void setConnectionTestQuery(String connectionTestQuery) { this.connectionTestQuery = connectionTestQuery; }
    }
}