package com.workspan.homework.qv2.beans;

import java.io.Serializable;

public class Link implements Serializable {
  private static final long serialVersionUID = 1L;
  private int from;
  private int to;

  public Link() {
    super();
  }

  public Link(int from, int to) {
    this.from = from;
    this.to = to;
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }

  public String toString() {
    return "from: " + this.from + " to: " + this.to;
  }
}
