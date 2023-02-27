package com.bike_server.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * 基于Jackson封装的JSON工具类,
 * 封装掉复杂的操作,只暴漏简单易用的方法
 */
public class JsonUtility {

    private static final String failJsonInfo = "{\"code\":\"500\", \"msg\": \"Conversion failure\"}";

    private void serializationConfig(ObjectMapper mapper){
        this.serializationConfig(mapper,"yyyy-MM-dd HH:mm:ss", false, false);
    }
    private void serializationConfig(ObjectMapper mapper, String dataFormat){
        this.serializationConfig(mapper,dataFormat, false, false);
    }
    private void serializationConfig(ObjectMapper mapper, boolean isIgnoreNull, boolean isIgnoreDefault){
        this.serializationConfig(mapper,"yyyy-MM-dd HH:mm:ss", isIgnoreNull, isIgnoreDefault);
    }
    /**
     * serializationConfig()
     * 设置序列化的一些配置
     * @param dataFormat: 指定序列化的格式,默认格式为 yyyy-MM-dd HH:mm:ss
     * @param isIgnoreNull: 设置序列化时是否忽略属性为null的值, 默认为 false, 不忽略
     * @param isIgnoreDefault: 设置序列化时是否忽略属性值为默认值的值, 默认为 false, 不忽略
     */
    private void serializationConfig(ObjectMapper mapper,
                                     String dataFormat,
                                     boolean isIgnoreNull,
                                     boolean isIgnoreDefault){
        mapper.setDateFormat(new SimpleDateFormat(dataFormat));
        if (isIgnoreNull) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (isIgnoreDefault){
            mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        }
    }

    /**
     * 对反序列化进行一些配置,
     * @param isIgnoreUnknownProperty: 反序列化时是否忽略在 json 中存在但 Java 对象不存在的属性,默认为true,忽略
     */
    private void deSerializationConfig(ObjectMapper mapper, boolean isIgnoreUnknownProperty){
        if (isIgnoreUnknownProperty){
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        else {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        }
    }

    // 返回一个默认配置的 mapper
    public ObjectMapper mapperFactory(){
        ObjectMapper mapper = new ObjectMapper();
        this.serializationConfig(mapper);
        return mapper;
    }
    public ObjectMapper mapperFactory(String dataFormat){
        ObjectMapper mapper = new ObjectMapper();
        this.serializationConfig(mapper, dataFormat);
        return mapper;
    }

    public ObjectMapper mapperFactory(boolean isIgnoreNull,boolean isIgnoreDefault){
        ObjectMapper mapper = this.mapperFactory("yyyy-MM-dd HH:mm:ss", isIgnoreNull, isIgnoreDefault, true);
        return mapper;
    }

    public ObjectMapper mapperFactory(String dataFormat,boolean isIgnoreNull,boolean isIgnoreDefault){
        ObjectMapper mapper = this.mapperFactory(dataFormat, isIgnoreNull, isIgnoreDefault, true);
        return mapper;
    }
    // 返回自定义配置的mapper
    public ObjectMapper mapperFactory(String dataFormat,boolean isIgnoreNull,boolean isIgnoreDefault,boolean isIgnoreUnknownProperty){
        ObjectMapper mapper = new ObjectMapper();
        this.serializationConfig(mapper, dataFormat, isIgnoreNull, isIgnoreDefault);
        this.deSerializationConfig(mapper, isIgnoreUnknownProperty);
        return mapper;
    }

    // 将Java对象转换成没有格式化的JSON字符串
    public String  jsonParser_ByObject(ObjectMapper mapper, Object object){
        return this.jsonParser_ByObject(mapper, object,false);
    }
    /**
     * 将Java对象转换成一个JSON字符串
     * @param mapper 根据传入的mapper对象的进行转换
     * @param object 需要转换成JSON的Java对象,可以是集合,如List,Map
     * @param isFormat 是否返回一个经过格式化美化的JSON,默认为 false 不美化
     * @return String:返回一个JSON字符串
     */
    public String jsonParser_ByObject(ObjectMapper mapper, Object object, boolean isFormat){
        String jsonString = JsonUtility.failJsonInfo;
        try {
            if (isFormat){
                jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            }
            else{
                jsonString = mapper.writeValueAsString(object);
            }
        } catch (JsonProcessingException e) {
            System.err.println(object+"转换json失败!");
            e.printStackTrace();
        }
        return jsonString;
    }

    /**
     *  将 JSON字符串解析成 Java对象
     * @param mapper 根据传入的mapper对象的进行转换
     * @param jsonString 需要解析JSON字符串
     * @param aClass 根据 Class 推导 Java对象的类型1
     * @return 返回解析好的 Java对象
     */
    public  Object objectParser_ByJSON(ObjectMapper mapper, String jsonString, Class aClass){
        Object o = null;
        try {
            o = mapper.readValue(jsonString, aClass);
        } catch (JsonProcessingException e) {
            System.err.println(jsonString+"解析失败!");
            e.printStackTrace();
        }
        return o;
    }

    /**
     *
     * @param mapper  根据传入的mapper对象的配置信息进行转换
     * @param jsonString 需要解析的json字符串
     * @param type 提供一个类型引用实例, 例如 new TypeReference<Map<String, Object>>(){} 或 new TypeReference<List<Objectr>>{}
     * @param <E> 类型参数, 即使 集合元素类型
     * @return 返回解析好的 集合对象
     */
    public <E> Collection collectionParser_ByJSON(ObjectMapper mapper, String jsonString, TypeReference<E> type){
        Collection collection = null;
        try {
            collection = (Collection) mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            System.err.println(jsonString+"解析失败!指定解析的类型为:"+type);
            e.printStackTrace();
        }
        return collection;
    }

    /**
     * 根据传入的 mapper对象的映射规则将 json字符串解析成 JSON节点树形对象
     * @param mapper
     * @param jsonString 需要的解析json对象
     * @return 返回的 JSON节点树形对象
     */
    public JsonNode readJsonToNode(ObjectMapper mapper, String jsonString){
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            System.err.println(jsonString+"解析失败!");
            e.printStackTrace();
        }
        return jsonNode;
    }

    /**
     * 将Java对象转换成JSON节点对象,方便后续修改
     * @param mapper
     * @param o 需要转换成JSON节点对象的Java对象
     * @return 转换好的JSON节点对象
     */
    public JsonNode readObjectToNode(ObjectMapper mapper, Object o){
        return mapper.valueToTree(o);
    }

    /**
     * 将json节点对象转换成 Java对象
     * @param mapper 映射器对象
     * @param jsonNode 需要转换的 json节点对象
     * @param aClass 转换后的Java对象的Class
     * @return 返回转换好的 对象
     */
    public Object readNodeToObject(ObjectMapper mapper, JsonNode jsonNode, Class aClass){
        Object object = null;
        try {
            object = mapper.treeToValue(jsonNode, aClass);
        } catch (JsonProcessingException e) {
            System.err.println(jsonNode+"节点转换失败!");
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 为 指定的 objectNode 添加基本属性(不包括对象类型的属性),相当于为json对象添加或者设置属性
     * @param node 需要添加或者修改属性值的 对象节点, 即使json对象
     * @param key 属性名, 不存在则添加该属性,存在则修改该属性
     * @param value 属性值, 需要添加或者修改的属性值
     * @throws Exception 当时 传入的 value 类型 不是 int ,double, boolean, String 任意一个时,会抛出异常
     */
    public void setAttribute(ObjectNode node, String key, Object value) throws Exception {
        String type = null;
        type = value instanceof String ? "String" : type;
        type = value instanceof Integer ? "int" : type;
        type = value instanceof Double ? "double" : type;
        type = value instanceof Boolean ? "boolean" : type;
        if (type == null){
            throw new Exception("value 不能为 null !");
        }
        switch (type){
            case "String": {
                node.put(key, (String) value);
                break;
            }
            case "int": {
                node.put(key, (Integer) value);
                break;
            }
            case "double": {
                node.put(key, (Double) value);
                break;
            }
            case "boolean": {
                node.put(key, (Boolean) value);
                break;
            }
            default: {
                throw new Exception("value参数类型错误!只能为int, double, String, boolean");
            }
        }
    }

    /**
     * 给指定节点添加对象属性
     * @param node 指定节点
     * @param key 属性名,存在则修改,不存在则新建
     * @param value 对象属性
     */
    public void setNodeAttribute(ObjectNode node, String key, ObjectNode value){
        node.set(key, value);
    }

    // 删除指定节点的指定属性
    public void removeAttribute(ObjectNode objectNode, String key){
        objectNode.remove(key);
    }

    // 查找指定节点
    public JsonNode findNode(JsonNode jsonNode, String key){
        return jsonNode.path(key);
    }
}