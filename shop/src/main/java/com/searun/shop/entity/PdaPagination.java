package com.searun.shop.entity;

public class PdaPagination
{
  private int amount;//每页加载个数 
  private boolean asc;//排序方式
  private boolean needsPaginate;//是否需要分页
  private boolean needsSort;//是否需要排序
  private String sortProperty;//排序属性
  private int startPos;
  /**
   * 当前页数
  */
  private int pageNumber;
  

  public PdaPagination()
  {
    this.needsPaginate = true;
    this.needsSort = false;
    this.startPos = -1;
    this.amount = 10;
    this.sortProperty = null;
    this.asc = false;
    this.pageNumber = 1;
  }

  public PdaPagination(int paramInt1, int paramInt2, String paramString, boolean paramBoolean)
  {
    this.needsPaginate = true;
    this.needsSort = true;
    this.startPos = paramInt1;
    this.amount = paramInt2;
    this.sortProperty = paramString;
    this.asc = paramBoolean;
  }

  public PdaPagination(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.needsPaginate = paramBoolean1;
    this.needsSort = paramBoolean2;
    this.startPos = 0;
    this.amount = 10;
    this.sortProperty = "id";
    this.asc = true;
  }

  public int getAmount()
  {
    return this.amount;
  }

  public String getSortProperty()
  {
    return this.sortProperty;
  }

  public int getStartPos()
  {
    return this.startPos;
  }

  public boolean isAsc()
  {
    return this.asc;
  }

  public boolean isNeedsPaginate()
  {
    return this.needsPaginate;
  }

  public boolean isNeedsSort()
  {
    return this.needsSort;
  }

  public void setAmount(int paramInt)
  {
    this.amount = paramInt;
  }

  public void setAsc(boolean paramBoolean)
  {
    this.asc = paramBoolean;
  }

  public void setNeedsPaginate(boolean paramBoolean)
  {
    this.needsPaginate = paramBoolean;
  }

  public void setNeedsSort(boolean paramBoolean)
  {
    this.needsSort = paramBoolean;
  }

  public void setSortProperty(String paramString)
  {
    this.sortProperty = paramString;
  }

  public void setStartPos(int paramInt)
  {
    this.startPos = paramInt;
  }

public int getPageNumber() {
	return pageNumber;
}

public void setPageNumber(int pageNumber) {
	this.pageNumber = pageNumber;
}
  
}
