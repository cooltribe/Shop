/**
 * 
 */
package com.searun.shop.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.GsonBuilder;

/**
  * @类名: GsonUtils.java
  * @描述: GSON使用的工具类。
  * 一般情况下，请使用createCommonBuilder方法来创建GsonBuilder，然后再获得Gson对象，而尽量不要直接构建Gson对象。
  * 如果需要增加自定义的格式控制，可以在利用createCommonBuilder创建GsonBuilder之后，再给该GsonBuilder增加自定义能力。
  * @作者: wangjinkui
  * @日期: 2014年8月22日 上午8:51:46
  * @版本:  V1.0
  * ============================================================================
  * 版权所有 2010-2014 江苏辉源供应链管理有限公司（SEARUN）,并保留所有权利。
  * ----------------------------------------------------------------------------
  * 提示：未经许可不能随意拷贝复制使用本软件，否则SEARUN将保留追究的权力。
  * ----------------------------------------------------------------------------
  * 官方网站：http://www.sy56.com
  * ============================================================================
 */
public class GsonUtils {
    
    /**
      * 使用常用配置创建一个GsonBuilder实例
      * @作者: wangjinkui
      * @日期: 2014年8月22日 上午8:52:37
      * @方法名: createCommonBuilder 
      * @方法描述: 
      * @参数描述: @return
      * @异常描述: XXXX
     */
    public static GsonBuilder createCommonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeHierarchyAdapter(Date.class, new DateSerializer())
            .registerTypeHierarchyAdapter(Calendar.class, new CalendarSerializer())
            .registerTypeHierarchyAdapter(Timestamp.class, new TimestampSerializer())
            .serializeNulls()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableInnerClassSerialization();
        
        return gsonBuilder;
    }
}
