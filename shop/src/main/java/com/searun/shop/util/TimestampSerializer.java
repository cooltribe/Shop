/**
 * 
 */
package com.searun.shop.util;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 用于Calendar的格式化。它默认将日期格式化为 yyyy-MM-dd HH:mm:ss 格式的字符串，同时允许用户指定日期格式以覆盖默认行为。
  * @类名: CalendarSerializer.java
  * @描述: xxx
  * @作者: wangjinkui
  * @日期: 2014年8月22日 上午8:56:53
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class TimestampSerializer implements JsonSerializer<Timestamp> {

    public static final String DefaultPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式转化的样式字符串
     */
    private String dateFormat;
    
    private SimpleDateFormat simpleDateFormat;
    
    public String getDateFormat() {
        return this.dateFormat;
    }
    
    /**
     * 默认构造器
     */
    public TimestampSerializer() {
        this(DateSerializer.DefaultPattern);
    }

    /**
     * 指定格式的构造函数
     * @param patterm
     */
    public TimestampSerializer(String dateFormat) {
        super();
        this.dateFormat = dateFormat;
        this.simpleDateFormat = new SimpleDateFormat(this.dateFormat);
    }
    
    @Override
    public JsonElement serialize(Timestamp timestamp, Type type, JsonSerializationContext context) {
        String value = null;
        if (timestamp == null) {
            value = "";
        } else {
            value = this.simpleDateFormat.format(new Date(timestamp.getTime()));
        }
        
        return new JsonPrimitive(value);
    }

}
