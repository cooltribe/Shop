package com.searun.shop.util;

import android.util.Log;

public class LogTag
{
  private static boolean islog = true;

  public static void d(String paramString1, String paramString2)
  {
    if (islog)
      Log.d(paramString1, paramString2);
  }

  public static void e(String paramString1, String paramString2)
  {
    if (islog)
      Log.e(paramString1, paramString2);
  }

  public static void f(String paramString1, String paramString2)
  {
    if (islog)
      Log.e(paramString1, paramString2);
  }

  public static void i(String paramString1, String paramString2)
  {
    if (islog)
      Log.i(paramString1, paramString2);
  }

  private static String logFormat(String paramString, Object[] paramArrayOfObject)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfObject.length)
      {
        String str = String.format(paramString, paramArrayOfObject);
        return "[" + Thread.currentThread().getId() + "] " + str;
      }
      if ((paramArrayOfObject[i] instanceof String[]))
        paramArrayOfObject[i] = prettyArray((String[])paramArrayOfObject[i]);
    }
  }

  private static String prettyArray(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 0)
      return "[]";
    StringBuilder localStringBuilder = new StringBuilder("[");
    int i = -1 + paramArrayOfString.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        localStringBuilder.append(paramArrayOfString[i]);
        localStringBuilder.append("]");
        return localStringBuilder.toString();
      }
      localStringBuilder.append(paramArrayOfString[j]);
      localStringBuilder.append(", ");
    }
  }

  public static void v(String paramString1, String paramString2)
  {
    if (islog)
      Log.v(paramString1, paramString2);
  }

  public static void w(String paramString1, String paramString2)
  {
    if (islog)
      Log.w(paramString1, paramString2);
  }
}

