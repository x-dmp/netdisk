package edu.cuit.fs;

import edu.cuit.fs.utils.DbUtil;
import edu.cuit.fs.utils.FileSystemUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FsApplication {

    public static void main(String[] args) {
        FileSystemUtil.init();
        DbUtil.init();
        SpringApplication.run(FsApplication.class, args);
    }

}
