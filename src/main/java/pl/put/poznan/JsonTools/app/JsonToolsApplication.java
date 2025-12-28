package pl.put.poznan.JsonTools.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"pl.put.poznan.JsonTools.rest"})
@ComponentScan(basePackages = "pl.put.poznan.JsonTools")
public class JsonToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonToolsApplication.class, args);
    }
}
