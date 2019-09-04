package com.workspan.homework.qv2.quasi;

import com.workspan.homework.qv2.beans.Entity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("graphService")
public class Graph {
  private Map<Entity, List<Entity>> adjVertices = new HashMap<>();

  public void addEntity(int id, String name, String desc) {
    addEntity(new Entity(id, name, desc));
  }

  public void addEntity(Entity entity) {
    if (!adjVertices.containsKey(entity)) {
      adjVertices.putIfAbsent(entity, new ArrayList<Entity>());
    }
  }

  public Entity getEntity(int id) {
    Entity entity = new Entity(id);
    for (Map.Entry<Entity, List<Entity>> e : adjVertices.entrySet()) {
      if (entity.equals(e.getKey())) {
        return e.getKey();
      }
    }
    return null;
  }

  /*
  add new edge between 2 entities, from A to B
   */
  public void addEdge(Entity entityA, Entity entityB) {
    if (entityA == null || entityB == null) {
      return;
    }
    adjVertices.get(entityA).add(entityB);
  }

  public List<Entity> getAdjVertices(Entity entity) {
    return adjVertices.get(entity);
  }

  public List<Entity> getAdjVertices(int index) {
    return adjVertices.get(new Entity(index));
  }

  public List<Entity> getAllAdjVertices() {
    List<Entity> entities = new LinkedList<>();
    for (Map.Entry<Entity, List<Entity>> e : adjVertices.entrySet()) {
      entities.add(e.getKey());
    }
    return entities;
  }
}
