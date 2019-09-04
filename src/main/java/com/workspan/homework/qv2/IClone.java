package com.workspan.homework.qv2;

import com.workspan.homework.qv2.quasi.Graph;
import org.json.simple.JSONObject;

public interface IClone {
  JSONObject cloneJson(JSONObject jsonObject, int entityId);
  JSONObject clone(String fileUrl, int entityId);
  void cloneToFile(String fileUrl, int entityId);
}
