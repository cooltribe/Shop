package com.searun.shop.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil
{
  public static void show(Context paramContext, int paramInt)
  {
    Toast.makeText(paramContext, paramInt, 0).show();
  }

  public static void show(Context paramContext, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Toast.makeText(paramContext, paramInt, 1).show();
      return;
    }
    Toast.makeText(paramContext, paramInt, 0).show();
  }

  public static void show(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    Toast.makeText(paramContext, paramString, 0).show();
  }

  public static void show(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    if (paramBoolean)
    {
      Toast.makeText(paramContext, paramString, 1).show();
      return;
    }
    Toast.makeText(paramContext, paramString, 0).show();
  }
}

