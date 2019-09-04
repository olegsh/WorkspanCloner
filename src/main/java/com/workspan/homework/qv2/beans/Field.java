package com.workspan.homework.qv2.beans;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class Field {
  private List<Entity> entities;
  private List<Link> links;

  public List<Entity> getEntities() {
    return entities;
  }

  public void setEntities(List<Entity> entities) {
    this.entities = entities;
  }

  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    // this.links = links;
    if (CollectionUtils.isEmpty(this.links)) {
      this.links = links;
    } else {
      this.links.addAll(links);
    }
  }
}
