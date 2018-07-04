package serializable.protostuff;

import com.alibaba.fastjson.TypeReference;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.Delegate;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import serializable.JavaSerialize.JavaSerialize;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class IoProtostuff {


    protected final static Log log = LogFactory.getLog(IoProtostuff.class);

    private static Objenesis objenesis = new ObjenesisStd(true);


    private static Map<Class, Class> filterClass = new HashMap<Class, Class>() {
        {
            put(List.class, ArrayList.class);
            put(Map.class, HashMap.class);
        }
    };

    // 时间戳转换Delegate，解决时间戳转换后错误问题
    private final static Delegate<Timestamp> TIMESTAMP_DELEGATE = new TimestampDelegate();


    static {
        // 反序列化，不执行构造函数。
//        System.getProperties().setProperty("protostuff.runtime.always_use_sun_reflection_factory", "true");
    }

    private final static DefaultIdStrategy idStrategy = ((DefaultIdStrategy) RuntimeEnv.ID_STRATEGY);

    private static Map<Class, Schema> cachedSchema = new ConcurrentHashMap<Class, Schema>();


    static {
        idStrategy.registerDelegate(TIMESTAMP_DELEGATE);
    }

    @SuppressWarnings("unchecked")
    private static Schema getSchema(Class cls) {
        Schema schema = (Schema) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls, idStrategy);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     *
     * @desc 序列化对象 ，非基本类型是包装了一层CacheObject
     * @taskId
     * @author chenshengpeng
     * @date 2016-6-23下午2:39:06
     * @param object
     * @return
     */
    public static <T> byte[] serialize(T object) {
        Class clazz = object.getClass();

        Object newObject = object;
        // 非基本类型，统一使用CacheObject包装一层。
        if (!isBaseDataType(clazz)) {
            CacheObject cacheObject = new CacheObject();
            cacheObject.setResult(object);
            newObject =  cacheObject;
        }

       return serializeInner(newObject);
    }

    private static <T> byte[] serializeInner(T object) {
        Class<T> clazz = (Class<T>) object.getClass();

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(object, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     *
     * @param bytes
     * @param clazz
     * @return
     */
    private static Object unserializeInner(byte[] bytes, Class clazz) {

        try {
//            Object instance = objenesis.newInstance(clazz);
            Object instance = clazz.newInstance();
            Schema schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
            return instance;
        }
        catch (Exception e) {
            log.error("unserialize obj error.", e);
        }
        return null;
    }

    /**
     * 反序列化，非基本类型是包装了一层CacheObject
     * @param bytes
     * @param clazz
     * @return
     */
    public static Object unserialize(byte[] bytes, Class... clazz) {

        Class newClazz = null;
        try {
            if (clazz != null && clazz.length > 0) {
                newClazz = clazz[0];
            }

            // 判断是否基本类型，非基本类型包装一层。
            if (newClazz == null || !isBaseDataType(newClazz)) {
                try {
                    return ((CacheObject) unserializeInner(bytes, CacheObject.class)).getResult();
                }
                catch(Exception e) {
                    //做下容错处理
                    return unserializeInner(bytes, String.class);
                }
            }

            return unserializeInner(bytes, newClazz);
        }
        catch (Exception e) {
            log.error("unserialize obj error.", e);
        }
        return null;
    }


    private static final String TYPE_NAME_PREFIX = "class ";

    /**
     * 根据type获取class名称.
     * @param type
     * @return
     */
    public static String getClassName(Type type) {
        if (type==null) {
            return "";
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        int beginIndex = className.indexOf("<");
        if (beginIndex >= 0) {
            //        int endIndex = className.lastIndexOf(">");
            className = className.substring(0, beginIndex);
        }
        return className;
    }

    // 判断是否基本数据类型，暂时只是string类型不包装。
    public static boolean isBaseDataType(Class clazz)
    {
        return
            (
                clazz.equals(String.class)
//                    ||
//                    clazz.equals(Integer.class)||
//                    clazz.equals(Byte.class) ||
//                    clazz.equals(Long.class) ||
//                    clazz.equals(Double.class) ||
//                    clazz.equals(Float.class) ||
//                    clazz.equals(Character.class) ||
//                    clazz.equals(Short.class) ||
//                    clazz.equals(BigDecimal.class) ||
//                    clazz.equals(BigInteger.class) ||
//                    clazz.equals(Boolean.class) ||
////                    clazz.equals(Date.class) ||
//                    clazz.isPrimitive()
            );
    }

    /**
     * 获取class
     * @param type
     * @return
     * @throws ClassNotFoundException
     */
    public static Class getClass(Type type)
        throws ClassNotFoundException {
        String className = getClassName(type);
        if (className==null || className.isEmpty()) {
            return null;
        }
        return Class.forName(className);
    }

    /**
     * 将接口替换成实现类
     * @param clazz
     * @return
     */
    private static Class filterInterfaceToClass(Class clazz) {
        Class toClass = filterClass.get(clazz);
        if (toClass != null) {
            return toClass;
        }

        return clazz;
    }

    /**
     * 获取实例 如果是接口，如list，需要传入arraylist
     * @param type
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object newInstance(Type type)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = getClass(type);
        if (clazz==null) {
            return null;
        }
        clazz = filterInterfaceToClass(clazz);
        return clazz.newInstance();
    }

    

    public static void main2(String[] args)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        TypeReference reference = new TypeReference<GenericResponse<GoodsBrand>>() {
        };
        TypeReference reference2 = new TypeReference<GenericResponse<List<GoodsBrand>>>() {
        };
        System.out.println(reference.getType());
        GoodsBrand b = new GoodsBrand();
        b.setBrandName("aaaaa");
        List<GoodsBrand> list = new ArrayList<GoodsBrand>();
        list.add(b);
        GenericResponse<GoodsBrand> c= new GenericResponse<GoodsBrand>();
        c.setResult(b);


        ParameterizedType genericSuperclass = (ParameterizedType) reference.getClass().getGenericSuperclass();
        //获取users的Type
        ParameterizedType usersType = (ParameterizedType) genericSuperclass.getActualTypeArguments()[0];

        ParameterizedType genericSuperclass2 = (ParameterizedType) reference2.getClass().getGenericSuperclass();
        ParameterizedType listType = (ParameterizedType) genericSuperclass2.getActualTypeArguments()[0];

        Type rawType = usersType.getRawType();
        System.out.println(rawType);

        Type type = usersType.getActualTypeArguments()[0];

        Type type2 = listType.getActualTypeArguments()[0];


        System.out.println("==== type  11111========" + type.getTypeName());
        System.out.println("==== listtype  222222========" + type2);

        System.out.println("1111=="+ (newInstance(type) instanceof GoodsBrand)) ;
        System.out.println("2222=="+ (newInstance(type2) instanceof List)) ;



//        System.out.println(usersType.getActualTypeArguments().length);

        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(new Date())));
//        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(1234L), Long.class));
//        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(1234), Integer.class));
//        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(1234.23F), Float.class));


        //序列化对象
//        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("c:\\test\\objectFile.obj"));
//        Customer customer = new Customer("王麻子", 24);
//        out.writeObject("你好!");    //写入字面值常量
//        out.writeObject(new Date());    //写入匿名Date对象
//        out.writeObject(customer);    //写入customer对象
//        out.close();


        //反序列化对象
//        byte[] b1 = new byte[100];
//        FileInputStream in = new byte[]{"\\x0b\\xfa\\a\\x12java.sql.Timestamp\\b\\x00\\x0c";
//        in.read(b1);
//
//        System.out.println("--------" + IoProtostuff.unserialize(b1));




        // list 包装一层
//        CacheObject g = new CacheObject();
//        g.setResult(list);
//        byte[] bb2 = IoProtostuff.serialize(g);
//        CacheObject gg = IoProtostuff.unserialize(bb2, CacheObject.class);
//        System.out.println(((List<GoodsBrand>)gg.getResult()).get(0).getBrandName());

        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(new Timestamp(System.currentTimeMillis()))));

        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(new Timestamp(System.currentTimeMillis()))));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaa", list);
        map.put("date", new Date());

        // map 包装一层
//        CacheObject gm = new CacheObject();
//        gm.setResult(map);
//        byte[] bbm = IoProtostuff.serialize(gm);
//        CacheObject ggm = IoProtostuff.deserialize(bbm, CacheObject.class);
//        System.out.println(((List<GoodsBrand>)((HashMap)ggm.getResult()).get("aaa")).get(0).getBrandName());


        // map 无法反序列化 丢数据
//        byte[] maps = IoProtostuff.serializeInner(map);
//        Map mapg = (Map) IoProtostuff.unserializeInner(maps, map.getClass());
//        System.out.println(((List<GoodsBrand>)mapg.get("aaa")).get(0).getBrandName());


        // 包装一层处理
        byte[] maps = IoProtostuff.serialize(map);
        Map mapg = (Map) IoProtostuff.unserialize(maps);
        System.out.println(((List<GoodsBrand>)mapg.get("aaa")).get(0).getBrandName());
        System.out.println((mapg.get("date")));

        // list 无法反序列化 丢数据
        byte[] bb = IoProtostuff.serialize(list);
//        List<GoodsBrand> list11 = new ArrayList<GoodsBrand>();
        List<GoodsBrand> lists3 = (List<GoodsBrand>) IoProtostuff.unserialize(bb);
        System.out.println(lists3.get(0).getBrandName());
        List<GoodsBrand> lists = (List<GoodsBrand>) IoProtostuff.unserialize(bb,List.class);
        System.out.println(lists.get(0).getBrandName());
//        System.out.println(o instanceof  GenericResponse);
//        System.out.println(o.getResult() instanceof  GoodsBrand);
    }

    public static void main(String[] args) {
        List list2 = new ArrayList(){{
            add("test");
        }};

        System.out.println(JavaSerialize.unserializeOld(JavaSerialize.serializeOld(list2)));
        System.out.println(IoProtostuff.unserialize(IoProtostuff.serialize(list2)));

    }

}
