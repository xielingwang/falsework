package com.wamogu;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import com.tangzc.autotable.springboot.EnableAutoTable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@EnableGracefulResponse
@EnableAutoTable
@SpringBootApplication
public class FwAppMgr {
    private static final String PORT_NAME = "server.port";

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FwAppMgr.class);
        ConfigurableApplicationContext context = app.run(args);

        Environment env = context.getEnvironment();

        String textBlock = """
---------------------------------------------------------
Application '{}' is running! Access URLs:
Local: \t\thttp://localhost:{}
External: \thttp://{}:{}
Doc: \thttp://{}:{}{}/doc.html
----------------------------------------------------------
""";
        log.info(textBlock,
                env.getProperty("spring.application.name", "Falsework"),
                env.getProperty(PORT_NAME, "8080"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty(PORT_NAME, "8080"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty(PORT_NAME, "8080"),
                env.getProperty("server.servlet.context-path", "")
        );
    }

}
