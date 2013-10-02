package bo.gob.mintrabajo.ovt;

import com.typesafe.config.Config;
import name.marcelomorales.siqisiqi.bonecp.DataSourceProvider;
import name.marcelomorales.siqisiqi.configuration.ConfigProvider;
import name.marcelomorales.siqisiqi.openjpa.EntityManagerFactoryProvider;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepositoryFactoryBean;
import name.marcelomorales.siqisiqi.openjpa.spring.SpringRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.OpenJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Marcelo Morales
 *         Since: 9/16/13
 */
@Configuration
@ComponentScan({"bo.gob.mintrabajo.ovt.services"})
@EnableTransactionManagement
@EnableJpaRepositories(value = {"bo.gob.mintrabajo.ovt.repositories"},
        repositoryFactoryBeanClass = OpenJpaRepositoryFactoryBean.class)
public class Cableado {

    @Bean
    public ConfigProvider configProvider() {
        return new ConfigProvider("ejemplos", "ovt").
                lifted(System.getProperty("stage"));
    }

    @Bean
    public Config config() {
        return configProvider().get();
    }

    @Bean
    @Named("baseextra")
    public Config configExtra() {
        //return configProvider().get().getConfig("baseextra");
        return configProvider().lifted("baseextra").get();
    }

    @Bean(destroyMethod = "close")
    public DataSourceProvider boneCpProvider() throws Exception {
        return new DataSourceProvider(config());
    }

    @Bean(destroyMethod = "close")
    @Named("baseextra")
    public DataSourceProvider boneCpProviderExtra() throws Exception {
        return new DataSourceProvider(configExtra());
    }

    @Bean
    public DataSource boneCp() throws Exception {
        return boneCpProvider().get();
    }

    @Bean
    @Named("baseextra")
    public DataSource boneCpExtra() throws Exception {
        return boneCpProviderExtra().get();
    }

    @Bean(destroyMethod = "close")
    public EntityManagerFactoryProvider entityManagerFactoryProvider() throws Exception {
        final EntityManagerFactoryProvider provider = new EntityManagerFactoryProvider(config(), boneCp());
        SpringRegistrar.registerEntities(provider, "bo.gob.mintrabajo.ovt.entities");
        return provider;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception {
        return entityManagerFactoryProvider().get();
    }

//    @Bean(destroyMethod = "close")
//    @Named("baseextra")
//    public EntityManagerFactoryProvider entityManagerFactoryProviderExtra() throws Exception {
//        final EntityManagerFactoryProvider provider = new EntityManagerFactoryProvider(configExtra(), boneCpExtra());
//        SpringRegistrar.registerEntities(provider, "ejemplo1.modelo.entities");
//        return provider;
//    }
//
//    @Bean
//    @Named("baseextra")
//    public EntityManagerFactory entityManagerFactoryExtra() throws Exception {
//        return entityManagerFactoryProviderExtra().get();
//    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        final JpaTransactionManager jpaTransactionManager =
                new JpaTransactionManager(entityManagerFactory());
        jpaTransactionManager.setDefaultTimeout(1000);
        return jpaTransactionManager;
        // esto con atomikos return new JtaTransactionManager(userTransaction)
    }

    @Bean
    public OpenJpaDialect openJpaDialect() {
        return new OpenJpaDialect();
    }
}
