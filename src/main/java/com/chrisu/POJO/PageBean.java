package com.chrisu.POJO;

import java.util.List;

/**
 * 分页查询的结果类
 *
 * @param <T>
 */
public class PageBean<T> {

  private int totalCount; // 总数目
  private List<T> rows; // 当前页的数据列表

  public PageBean() {
  }

  public PageBean(int totalCount, List<T> rows) {
    this.totalCount = totalCount;
    this.rows = rows;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public List<T> getRows() {
    return rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  @Override
  public String toString() {
    return "PageBean{" +
        "totalCount=" + totalCount +
        ", rows=" + rows +
        '}';
  }
}
