package com.FinTrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinTrackApplication {

    private static final Logger logger = LoggerFactory.getLogger(FinTrackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinTrackApplication.class, args);
        logger.info("\n" +
                "                                                                 \n" +
                "   ███████╗██╗███╗   ██╗████████╗██████╗  █████╗  ██████╗██╗  ██╗ \n" +
                "   ██╔════╝██║████╗  ██║╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝ \n" +
                "   █████╗  ██║██╔██╗ ██║   ██║   ██████╔╝███████║██║     █████╔╝  \n" +
                "   ██╔══╝  ██║██║╚██╗██║   ██║   ██╔══██╗██╔══██║██║     ██╔═██╗  \n" +
                "   ██║     ██║██║ ╚████║   ██║   ██║  ██║██║  ██║╚██████╗██║  ██╗ \n" +
                "   ╚═╝     ╚═╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ \n" +
                "               Financial Management Application                \n");

    }

}
