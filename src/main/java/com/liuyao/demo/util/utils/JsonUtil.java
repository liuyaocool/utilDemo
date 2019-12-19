package com.liuyao.demo.util.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.anxin.modules.pad.entity.EcsPadHardwareRespEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class JsonUtil
{



    /**
     * 
     * @author wangwei JSON工具类
     * @param 
     * 
     */
    
    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list)
    {
        JSONArray jsonArray = JSONArray.fromObject(list);

        return jsonArray.toString();
    }
    
    /***
     * 将对象序列化为JSON文本
     * @param object
     * @return
     */
    public static String toJSONString(Object object)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return jsonArray.toString();
    }

    /***
     * 将JSON对象数组序列化为JSON文本
     * @param jsonArray
     * @return
     */
    public static String toJSONString(JSONArray jsonArray)
    {
        return jsonArray.toString();
    }

    /***
     * 将JSON对象序列化为JSON文本
     * @param jsonObject
     * @return
     */
    public static String toJSONString(JSONObject jsonObject)
    {
        return jsonObject.toString();
    } 
    
    /***
     * 将对象转换为List对象
     * @param object
     * @return
     */
    public static List toArrayList(Object object)
    {
        List arrayList = new ArrayList();

        JSONArray jsonArray = JSONArray.fromObject(object);

        Iterator it = jsonArray.iterator();
        while (it.hasNext())
        {
            JSONObject jsonObject = (JSONObject) it.next();

            Iterator keys = jsonObject.keys();
            while (keys.hasNext())
            {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }

        return arrayList;
    }

    /***
     * 将对象转换为Collection对象
     * @param object
     * @return
     */
    public static Collection toCollection(Object object)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toCollection(jsonArray);
    }

    /***
     * 将对象转换为JSON对象数组
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object)
    {
        return JSONArray.fromObject(object);
    }

    /***
     * 将对象转换为JSON对象
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object)
    {
        return JSONObject.fromObject(object);
    }

    /***
     * 将对象转换为HashMap
     * @param object
     * @return
     */
    public static HashMap toHashMap(Object object)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JsonUtil.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }

    /***
     * 将对象转换为List>
     * @param object
     * @return
     */
    // 返回非实体类型(Map)的List
    public static List<Map<String, Object>> toList(Object object)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray)
        {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext())
            {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }

    /***
     * 将JSON对象数组转换为传入类型的List
     * @param 
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass)
    {
        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将对象转换为传入类型的List
     * @param 
     * @param jsonArray
     * @param objectClass
     * @return
     */
    public static <T> List<T> toList(Object object, Class<T> objectClass)
    {
        JSONArray jsonArray = JSONArray.fromObject(object);

        return JSONArray.toList(jsonArray, objectClass);
    }

    /***
     * 将JSON对象转换为传入类型的对象
     * @param 
     * @param jsonObject
     * @param beanClass
     * @return
     */
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass)
    {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将将对象转换为传入类型的对象
     * @param 
     * @param object
     * @param beanClass
     * @return
     */
    public static <T> T toBean(Object object, Class<T> beanClass)
    {
        JSONObject jsonObject = JSONObject.fromObject(object);

        return (T) JSONObject.toBean(jsonObject, beanClass);
    }

    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 泛型T 代表主实体类型
     * @param 泛型D 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName 从实体类在主实体类中的属性名称
     * @param detailClass 从实体类型
     * @return
     */
    public static <T, D> T toBean(String jsonString, Class<T> mainClass,
            String detailName, Class<D> detailClass)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);

        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
        List<D> detailList = JsonUtil.toList(jsonArray, detailClass);

        try
        {
            BeanUtils.setProperty(mainEntity, detailName, detailList);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @return
     */
    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass,
            String detailName1, Class<D1> detailClass1, String detailName2,
            Class<D2> detailClass2)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);

        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);

        try
        {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }
    
    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @param detailName3 从实体类在主实体类中的属性
     * @param detailClass3 从实体类型
     * @return
     */
    public static <T, D1, D2, D3> T toBean(String jsonString,
            Class<T> mainClass, String detailName1, Class<D1> detailClass1,
            String detailName2, Class<D2> detailClass2, String detailName3,
            Class<D3> detailClass3)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
        JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);

        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);
        List<D3> detailList3 = JsonUtil.toList(jsonArray3, detailClass3);

        try
        {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
            BeanUtils.setProperty(mainEntity, detailName3, detailList3);
        }
        catch (Exception ex)
        {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }

        return mainEntity;
    }

    /***
     * 将JSON文本反序列化为主从关系的实体
     * @param 主实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailClass 存放了多个从实体在主实体中属性名称和类型
     * @return
     */
    public static <T> T toBean(String jsonString, Class<T> mainClass,
            HashMap<String, Class> detailClass)
    {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
        for (Object key : detailClass.keySet())
        {
            try
            {
                Class value = (Class) detailClass.get(key);
                BeanUtils.setProperty(mainEntity, key.toString(), value);
            }
            catch (Exception ex)
            {
                throw new RuntimeException("主从关系JSON反序列化实体失败！");
            }
        }
        return mainEntity;
    }

    @Autowired
    private static RedisUtils redisUtils;

    public static void main(String[] args) {

    	EcsPadHardwareRespEntity resp1 = new EcsPadHardwareRespEntity();
    	EcsPadHardwareRespEntity resp2 = new EcsPadHardwareRespEntity();
    	List<EcsPadHardwareRespEntity> list1 = new ArrayList<>();
    	List<EcsPadHardwareRespEntity> list2 = new ArrayList<>();
    	resp1.setID("18820024");
    	resp1.set时间("2018-07-16 09:20:52");
    	resp1.set气体1("CH");
    	resp1.set报警值1("25,50,75");
    	resp1.set浓度1("0.0");
    	resp1.set气体2("H2S");
    	resp1.set报警值2("7,15,60");
    	resp1.set浓度2("0.0");
    	resp1.set经度("120.499168");
    	resp1.set纬度("36.17604");
    	
    	resp2.setID("18820025");
    	resp2.set时间("2018-07-16 09:20:52");
    	resp2.set气体1("CH");
    	resp2.set报警值1("25,50,75");
    	resp2.set浓度1("0.0");
    	resp2.set气体2("H2S");
    	resp2.set报警值2("7,15,60");
    	resp2.set浓度2("0.0");
    	resp2.set经度("120.4991");
    	resp2.set纬度("36.17681");
    	
    	list1.add(resp1);
    	list1.add(resp2);
    	
    	System.out.println(toJSONArray(list1));
    	list2 = toList(toJSONArray(list1),EcsPadHardwareRespEntity.class);

        redisUtils.set("test4",list2);

    	System.out.println(list2.get(0).getID());
    	System.out.println(list2.get(1).getID());
	}
}