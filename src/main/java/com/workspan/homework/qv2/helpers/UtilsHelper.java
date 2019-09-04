package com.workspan.homework.qv2.helpers;

import com.workspan.homework.qv2.beans.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service("utilsService")
public class UtilsHelper {
  private static Logger logger = LoggerFactory.getLogger(UtilsHelper.class);
  private static Set<Integer> idBank = new HashSet<>();

  /**
  generate random number with exclusion of some numbers
  already existed in Used Random number bank
   @param start start of range (inclusive)
   @param end end of range (exclusive)
   @return the random number within start-end but not one
   already in exclusion bank
   */
  public Integer getRandomIdWithExcl(int start, int end) {
    int rInt = generateRandomIntRange(start, end);
    if(idBank.isEmpty()) {
      idBank.add(rInt);
      return rInt;
    } else {
      while (idBank.contains(rInt)) {
        rInt = generateRandomIntRange(start, end);
      }
      return rInt;
    }
  }

  /**
   * Populate Set of used already ids for each entity
   * @param id insert used up id into the bank
   */
  public void buildIds(int id) {
    idBank.add(id);
  }

  public Entity clonewId(Entity source) {
    if (source == null) {
      logger.error("Unable to clone null entity");
      return null;
    }
    Entity clone = source.clone();
    clone.setEntity_id(getRandomIdWithExcl(1, 20));
    return clone;
  }

  private int generateRandomIntRange(int min, int max) {
    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }
}
