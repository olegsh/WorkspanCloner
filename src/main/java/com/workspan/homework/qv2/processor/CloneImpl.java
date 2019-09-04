package com.workspan.homework.qv2.processor;

import com.workspan.homework.qv2.IClone;
import com.workspan.homework.qv2.beans.Field;
import com.workspan.homework.qv2.helpers.LoaderHelper;
import com.workspan.homework.qv2.quasi.Graph;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CloneImpl implements IClone {
  private static Logger logger = LoggerFactory.getLogger(CloneImpl.class);

  @Autowired
  private LoaderHelper loadHelperService;

  @Override
  public JSONObject cloneJson(JSONObject jsonObject, int entityId) {
    return null;
  }

  @Override
  public JSONObject clone(String fileUrl, int entityId) {
    try {
      if (entityId <= 0) {
        throw new Exception("Invalid Id entry detected");
      }
      if (StringUtils.isBlank(fileUrl)) {
        throw new Exception(("No file input detected"));
      }

      Graph graphLoaded = loadHelperService.buildGraph(fileUrl);
      if (graphLoaded == null) {
        throw new Exception("Failed to load Graph from JSON file: " + fileUrl);
      }
      Graph cloned = loadHelperService.cloner(graphLoaded, entityId);
    } catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
    return null;
  }

  @Override
  public void cloneToFile(String fileUrl, int entityId) {
    try {
      if (entityId <= 0) {
        throw new Exception("Invalid Id entry detected");
      }
      if (StringUtils.isBlank(fileUrl)) {
        throw new Exception(("No file input detected"));
      }
      Graph graphLoaded = loadHelperService.buildGraph(fileUrl);
      if (graphLoaded == null) {
        throw new Exception("Failed to load Graph from JSON file: " + fileUrl);
      }
      Graph cloned = loadHelperService.cloner(graphLoaded, entityId);
      Field field = loadHelperService.convertGraph2Field(cloned);
      // JSONObject jsonObject = loadHelperService.generateJson(field);
      loadHelperService.writeJsonFile("./output.json", field);
    } catch ( Exception ex) {
      logger.error(ex.getMessage(), ex);
    }
  }
}
