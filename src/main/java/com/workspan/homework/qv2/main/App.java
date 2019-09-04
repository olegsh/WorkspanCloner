package com.workspan.homework.qv2.main;

import com.workspan.homework.qv2.IClone;
import com.workspan.homework.qv2.processor.CloneImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.workspan.homework"})
public class App implements CommandLineRunner {
  private static Logger logger = LoggerFactory.getLogger(App.class);

  @Autowired
  private IClone cloneService;

  public static void main(String...args) {
    logger.info("STARTING THE APPLICATION");
    SpringApplication.run(App.class, args);
    logger.info("APPLICATION FINISHED");
  }

  @Override
  public void run(String...args) throws Exception {
    logger.info("EXECUTING : command line runner");
    try {
      // IClone cloneService = new CloneImpl();
      if (args == null || args.length <= 0) {
        throw new Exception("FAIL: Input params are not optional");
      }
      if (StringUtils.isBlank(args[0])) {
        throw new Exception("Input JSON file is required");
      }
      if (StringUtils.isBlank(args[1])) {
        throw new Exception("Pointer to starting Entity is required");
      }
      int id = -1;
      if (StringUtils.isNumeric(args[1])) {
        id = Integer.parseInt(args[1]);
      } else {
        throw new Exception("Starting Entity ID must be a number " + args[1]);
      }
      cloneService.cloneToFile(args[0], id);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
  }
}
