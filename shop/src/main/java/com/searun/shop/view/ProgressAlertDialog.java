package com.searun.shop.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgressAlertDialog extends Dialog
{
  private static ProgressAlertDialog progressAlertDialog = null;
  private Context context = null;

  public ProgressAlertDialog(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }

  public ProgressAlertDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  protected ProgressAlertDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
  }

  public static ProgressAlertDialog createDialog(Context paramContext)
  {
    try
    {
      progressAlertDialog = new ProgressAlertDialog(paramContext, 2131427472);
      progressAlertDialog.setContentView(2130903070);
      progressAlertDialog.getWindow().getAttributes().gravity = 17;
      return progressAlertDialog;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    try
    {
      if (progressAlertDialog == null)
        return;
      ((AnimationDrawable)((ImageView)progressAlertDialog.findViewById(2131034205)).getBackground()).start();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public ProgressAlertDialog setMessage(String paramString)
  {
    try
    {
      TextView localTextView = (TextView)progressAlertDialog.findViewById(2131034206);
      if (localTextView != null)
        localTextView.setText(paramString);
      return progressAlertDialog;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public ProgressAlertDialog setTitile(String paramString)
  {
    return progressAlertDialog;
  }
}

/* Location:           E:\fanbianyi\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.searun.shop.view.ProgressAlertDialog
 * JD-Core Version:    0.6.2
 */