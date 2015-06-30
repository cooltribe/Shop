package com.searun.shop.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer
  implements JsonSerializer<Date>
{
  public static final String DefaultPattern = "yyyy-MM-dd HH:mm:ss";
  private String dateFormat;
  private SimpleDateFormat simpleDateFormat;

  public DateSerializer()
  {
    this("yyyy-MM-dd HH:mm:ss");
  }

  public DateSerializer(String paramString)
  {
    this.dateFormat = paramString;
    this.simpleDateFormat = new SimpleDateFormat(this.dateFormat);
  }

  public String getDateFormat()
  {
    return this.dateFormat;
  }

  public JsonElement serialize(Date paramDate, Type paramType, JsonSerializationContext paramJsonSerializationContext)
  {
    if (paramDate == null);
    for (String str = ""; ; str = this.simpleDateFormat.format(paramDate))
      return new JsonPrimitive(str);
  }
}

/* Location:           E:\fanbianyi\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.searun.shop.util.DateSerializer
 * JD-Core Version:    0.6.2
 */