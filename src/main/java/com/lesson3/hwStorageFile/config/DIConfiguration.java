package com.lesson3.hwStorageFile.config;

import com.lesson3.hwStorageFile.dao.FileDAO;
import com.lesson3.hwStorageFile.dao.StorageDAO;
import com.lesson3.hwStorageFile.model.File;
import com.lesson3.hwStorageFile.model.Storage;
import com.lesson3.hwStorageFile.service.FileService;
import com.lesson3.hwStorageFile.service.StorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.lesson3.hwStorageFile"})
public class DIConfiguration {
    @Bean(name = "file")
    File file() {
        return new File();
    }

    @Bean(name = "storage")
    Storage storage() {
        return new Storage();
    }

    @Bean(name = "fileService")
    FileService fileService() {
        return new FileService();
    }

    @Bean(name = "storageService")
    StorageService storageService() {
        return new StorageService();
    }

    @Bean(name = "fileDAO")
    FileDAO fileDAO() {
        return new FileDAO();

    }

    @Bean(name = "storageDAO")
    StorageDAO storageDAO() {
        return new StorageDAO();
    }
}
