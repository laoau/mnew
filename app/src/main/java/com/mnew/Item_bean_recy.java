package com.mnew;

public class Item_bean_recy {
  private String title;
  private String imageurl;
  private String lianjie;
  private String time;

  public Item_bean_recy(String title, String imageurl, String lianjie, String time) {
    this.title = title;
    this.imageurl = imageurl;
    this.lianjie = lianjie;
    this.time = time;
  }

  public Item_bean_recy() {}

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageurl() {
    return this.imageurl;
  }

  public void setImageurl(String imageurl) {
    this.imageurl = imageurl;
  }

  public String getLianjie() {
    return this.lianjie;
  }

  public void setLianjie(String lianjie) {
    this.lianjie = lianjie;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
