package com.gavinin.yt_bus_display.task;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.gavinin.yt_bus_display.common.ConstStatus.DB_LOCAL;

/**
 * @ClassName DirectoryCheck
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 03:01
 * @Version 1.0
 **/
@Component
public class DirectoryCheck {

    @PostConstruct
    public void init() {

        try {
            Path path = Paths.get(DB_LOCAL);
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignore) {

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
