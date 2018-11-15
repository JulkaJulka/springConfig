package com.config;

import com.lesson3.hwStorageFile.dao.FileDAO;
import com.lesson3.hwStorageFile.dao.GeneralDAO;
import com.lesson3.hwStorageFile.dao.StorageDAO;
import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.model.Storage;
import com.lesson3.hwStorageFile.service.FileService;
import com.lesson3.hwStorageFile.service.GeneralService;
import com.lesson3.hwStorageFile.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(value = {"com"})
public class AppContext {

    @Bean(name = "storage")
    Storage storage() {
        return new Storage();
    }

    @Bean
    File file() {
        return new File();
    }

    @Bean(name = "storageDAO")
    StorageDAO storageDAO() {
        return new StorageDAO();
    }

    @Bean(name = "fileDAO")
    FileDAO fileDAO() {
        return new FileDAO(storageDAO());

    }

    @Bean
    GeneralDAO generalDAO() {
        return new GeneralDAO();
    }

    @Bean(name = "fileService")
    FileService fileService() {
        return new FileService(fileDAO());
    }

    @Bean(name = "storageService")
    StorageService storageService() {
        return new StorageService(storageDAO());
    }

    @Bean
    GeneralService generalService() {
        return new GeneralService();
    }

}
