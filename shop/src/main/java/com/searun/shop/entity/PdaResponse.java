package com.searun.shop.entity;

import java.io.Serializable;

public class PdaResponse<T>
  implements Serializable
{
  private static final long serialVersionUID = -7816945325851639128L;
  private long currentTotal = 0L;
  private T data;
  private String message;
  private boolean success;
  private long total = 0L;

  public PdaResponse()
  {
    this(true, "", null);
  }

  public PdaResponse(boolean paramBoolean, String paramString, T paramT)
  {
    this.success = paramBoolean;
    this.message = paramString;
    this.data = paramT;
  }

  public long getCurrentTotal()
  {
    return this.currentTotal;
  }

  public T getData()
  {
    return this.data;
  }

  public String getMessage()
  {
    return this.message;
  }

  public long getTotal()
  {
    return this.total;
  }

  public boolean isSuccess()
  {
    return this.success;
  }

  public void setCurrentTotal(long paramLong)
  {
    this.currentTotal = paramLong;
  }

  public void setData(T paramT)
  {
    this.data = paramT;
  }

  public void setMessage(String paramString)
  {
    this.message = paramString;
  }

  public void setSuccess(boolean paramBoolean)
  {
    this.success = paramBoolean;
  }

  public void setTotal(long paramLong)
  {
    this.total = paramLong;
  }
}
