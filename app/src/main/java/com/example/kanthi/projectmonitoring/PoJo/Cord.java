package com.example.kanthi.projectmonitoring.PoJo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class Cord {

  public static final String ACCOUNT_ID_FIELD_NAME = "account_id";

  @DatabaseField(generatedId = true)
  private int id;

  @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = ACCOUNT_ID_FIELD_NAME)
  private Shape shape;


  @SerializedName("x")
  @DatabaseField(columnName = "x")
  private int x;

  @SerializedName("y")
  @DatabaseField(columnName = "y")
  private int y;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public int getX() { return this.x; }

  public void setX(int x) { this.x = x; }

  public int getY() { return this.y; }

  public void setY(int y) { this.y = y; }
}