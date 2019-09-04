package com.workspan.homework.qv2.helpers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.homework.qv2.beans.Entity;
import com.workspan.homework.qv2.beans.Field;
import com.workspan.homework.qv2.beans.Link;
import com.workspan.homework.qv2.quasi.Graph;
import org.apache.commons.collections4.CollectionUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loadHelperService")
public class LoaderHelper {
  private static Logger logger = LoggerFactory.getLogger(LoaderHelper.class);

  private static ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private UtilsHelper utilsService;

  public Graph buildGraph(String filepath) throws Exception {
    logger.info("heelo world");
    Field field = mapper.readValue(new File(filepath), Field.class);
    if (field == null || CollectionUtils.isEmpty(field.getEntities()) ||
        CollectionUtils.isEmpty(field.getLinks())) {
      throw new Exception("Invalid JSON input provided");
    }
    Graph graph = new Graph();
    for (Entity e : field.getEntities()) {
      utilsService.buildIds(e.getEntity_id());
      graph.addEntity(e);
    }
    for (Link l : field.getLinks()) {
      Entity a = graph.getEntity(l.getFrom());
      Entity b = graph.getEntity(l.getTo());
      graph.addEdge(a, b);
    }
    return graph;
  }

  /**
   * Clones current graph and add new
   * nodes to current graph as well
   * Based on DepthFirstTraversal (DFT) algorithm
   * @param graph - current graph
   * @param rootId - id as starting point
   * @return graph - modified graph with new cloned nodes
   */
  public Graph cloner(Graph graph, int rootId) {
    Graph cgraph = new Graph();
    Entity root = graph.getEntity(rootId);
    Set<Entity> visited = new LinkedHashSet<>();
    LinkedList<Entity> stack = new LinkedList<>();
    Map<Entity, Entity> bucket = new HashMap<>();
    stack.push(root);
    Entity clone = utilsService.clonewId(root);
    cgraph.addEntity(clone);
    bucket.put(root, clone);

    while (!stack.isEmpty()) {
      Entity entity = stack.pop();
      if (!visited.contains(entity)) {
        visited.add(entity);
        for (Entity e : graph.getAdjVertices(entity)) {
          Entity temp = utilsService.clonewId(e);
          bucket.put(e, temp);
          cgraph.addEntity(temp);
          cgraph.addEdge(bucket.get(entity), temp);
          stack.push(e);
        }
      }
    }
    // merge 2 graphs
    merge(graph, cgraph, root, clone);
    return graph;
  }

  public Field convertGraph2Field(Graph graph) {
    Field field = new Field();
    List<Entity> entities = graph.getAllAdjVertices();
    field.setEntities(entities);
    for (Entity e : entities) {
      List<Entity> subs = graph.getAdjVertices(e);
      add2Field(e, subs, field);
    }
    return field;
  }

  /*
  Write Java object into JSON Object
   */
  public JSONObject generateJson(Field field) throws Exception {
    JSONObject result = null;
    try {
      JSONParser parser = new JSONParser();
      // Java object to JSON string
      String jsonString = mapper.writeValueAsString(field);
      if (StringUtils.isBlank(jsonString)) {
        logger.error("Failed to convert Field to JSON");
        throw new Exception("Failed to convert Field to JSON");
      }
      result = (JSONObject) parser.parse(jsonString);
    } catch (JsonProcessingException jpe) {
      logger.error(jpe.getMessage());
      throw jpe;
    }
    return result;
  }

  /*
  write Java object into JSON file output
   */
  public void writeJsonFile(String filePath, Field field) throws Exception {
    try {
      // Java object to JSON file
      mapper.writeValue(new File(filePath), field);
    } catch (JsonMappingException jme) {
      logger.error(jme.getMessage());
      throw jme;
    } catch (JsonGenerationException jge) {
      logger.error(jge.getMessage());
      throw jge;
    } catch (IOException ioe) {
      logger.error(ioe.getMessage());
      throw ioe;
    }
  }

  private void add2Field(Entity e, List<Entity> subs, Field field) {
    if (e == null
        || subs == null
        || field == null) {
      logger.error("Invalid inputs detected");
      return;
    }
    if (CollectionUtils.isEmpty(subs)) {
      logger.info("Entity: " + e.toString() + " has no links");
      return;
    }
    List<Link> links = new LinkedList<>();
    for (Entity entity : subs) {
      Link l = new Link();
      l.setFrom(e.getEntity_id());
      l.setTo(entity.getEntity_id());
      links.add(l);
    }
    if (CollectionUtils.isNotEmpty(links)) {
      field.setLinks(links);
    }
  }

  private void merge(Graph graph, Graph cgraph, Entity a, Entity b) {
    graph.addEntity(b);
    graph.addEdge(a, b);

    Set<Entity> visited = new LinkedHashSet<>();
    LinkedList<Entity> stack = new LinkedList<>();
    stack.push(b);
    while (!stack.isEmpty()) {
      Entity entity = stack.pop();
      if (!visited.contains(entity)) {
        visited.add(entity);
        for (Entity e : cgraph.getAdjVertices(entity)) {
          graph.addEntity(e);
          graph.addEdge(entity, e);
          stack.push(e);
        }
      }
    }
  }
}
