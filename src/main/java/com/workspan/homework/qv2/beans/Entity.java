package com.workspan.homework.qv2.beans;

import com.workspan.homework.qv2.helpers.UtilsHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class Entity  implements Serializable {
  private static final long serialVersionUID = 1L;

  private int entity_id;
  private String name;
  private String description;

  public Entity() {
   super();
  }

  public Entity(int entity_id) {
    this.entity_id = entity_id;
    this.name = null;
    this.description = null;
  }

  public Entity(int entity_id, String name, String description) {
    this.entity_id = entity_id;
    this.name = name;
    this.description = description;
  }

  public Entity(int entity_id, String name) {
    this.entity_id = entity_id;
    this.name = name;
  }

  public int getEntity_id() {
    return entity_id;
  }

  public void setEntity_id(int entity_id) {
    this.entity_id = entity_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Entity clone() {
    Entity clone = null;
    if (this != null) {
      clone = new Entity();
      clone.setEntity_id(this.getEntity_id());
      clone.setName(this.getName());
      if (StringUtils.isNotBlank(this.getDescription())) {
        clone.setDescription(this.getDescription());
      }
    }
    return clone;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 13)
        .append(entity_id)
        .toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Entity))
      return false;
    if (obj == this) {
      return true;
    }
    Entity entity = (Entity)obj;
    return new EqualsBuilder()
        .append(this.entity_id, entity.entity_id)
        .isEquals();
  }

  @Override
  public String toString() {
    return
        "id: " + entity_id + " name: " + name;
  }
}
