//package docker.test.mbean.spring;
//
//import com.sun.org.glassfish.gmbal.ManagedOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jmx.export.annotation.ManagedAttribute;
//import org.springframework.jmx.export.annotation.ManagedOperationParameter;
//import org.springframework.jmx.export.annotation.ManagedOperationParameters;
//import org.springframework.jmx.export.annotation.ManagedResource;
//import org.springframework.jmx.support.ConnectorServerFactoryBean;
//import org.springframework.remoting.rmi.RmiRegistryFactoryBean;
//
//import javax.management.MalformedObjectNameException;
//
//@ManagedResource(objectName="docker.test.mbean:type=Person", description="My Managed Person Bean")
//@Component
//public class Person {
//
//    private String name = "这个是名字";
//    private String desc;
//
//    @ManagedAttribute(defaultValue = "我来了", description = "名称字段")
//    public String getName() {
//        return name;
//    }
//
//    @ManagedAttribute()
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @ManagedAttribute
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    @ManagedOperation()
//    @ManagedOperationParameters({
//        @ManagedOperationParameter(name = "x", description = "The first number"),
//        @ManagedOperationParameter(name = "y", description = "The second number")})
//    public int add_1(int x, int y) {
//        return x + y;
//    }
//
//    @ManagedOperation
//    public int add_2(int x, int y){
//        return x + y;
//    }
//
//    @Override public String toString() {
//        return "Person{" + "name='" + name + '\'' + ", desc='" + desc + '\'' + '}';
//    }
//
//
//
//
//
////<!-- 配置服务器端连接器RMI -->
////<bean class="org.springframework.remoting.rmi.RmiRegistryFactoryBean">
////    <property name="port" value="2088"/>
////</bean>
////<bean id="serverConnector"
////    class="org.springframework.jmx.support.ConnectorServerFactoryBean">
////  <property name="objectName" value="connector:name=rmi"/>
////         <!-- 客户端链接地址配置 -->
////  <property name="serviceUrl"
////    value="service:jmx:rmi://localhost/jndi/rmi://localhost:2088/myconnector"/>
////</bean>
//
//    @Bean
//    public RmiRegistryFactoryBean rmiRegistryFactoryBean() {
//        RmiRegistryFactoryBean rmiRegistryFactoryBean =  new RmiRegistryFactoryBean();
//        rmiRegistryFactoryBean.setPort(2088);
//        return rmiRegistryFactoryBean;
//    }
//
//    @Bean
//    public ConnectorServerFactoryBean connectorServerFactoryBean() throws MalformedObjectNameException {
//        ConnectorServerFactoryBean connectorServerFactoryBean =  new ConnectorServerFactoryBean();
//        connectorServerFactoryBean.setObjectName("bean:name=test");
//        connectorServerFactoryBean.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://localhost:2088/myconnector");
//        return connectorServerFactoryBean;
//    }
//
///// <bean id="exporter" class="org.springframework.jmx.export.MBeanExporter">
////        <property name="assembler" ref="assembler"/>
////        <property name="namingStrategy" ref="namingStrategy"/>
////        <property name="autodetect" value="true"/>
////    </bean>
////
////    <bean id="jmxAttributeSource"
////    class="org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource"/>
////
////    <!-- will create management interface using annotation metadata -->
////    <bean id="assembler"
////    class="org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler">
////        <property name="attributeSource" ref="jmxAttributeSource"/>
////    </bean>
////
////    <!-- will pick up the ObjectName from the annotation -->
////    <bean id="namingStrategy"
////    class="org.springframework.jmx.export.naming.MetadataNamingStrategy">
////        <property name="attributeSource" ref="jmxAttributeSource"/>
////    </bean>
//
//
////    @Bean
////    public AnnotationJmxAttributeSource annotationJmxAttributeSource() {
////        return new AnnotationJmxAttributeSource();
////    }
////
////    @Bean
////    public MetadataMBeanInfoAssembler metadataMBeanInfoAssembler() {
////        MetadataMBeanInfoAssembler metadataMBeanInfoAssembler = new MetadataMBeanInfoAssembler();
////        metadataMBeanInfoAssembler.setAttributeSource(annotationJmxAttributeSource());
////        return metadataMBeanInfoAssembler;
////    }
////
////    @Bean
////    public MetadataNamingStrategy namingStrategy() {
////        MetadataNamingStrategy metadataNamingStrategy = new MetadataNamingStrategy();
////        metadataNamingStrategy.setAttributeSource(annotationJmxAttributeSource());
////        return metadataNamingStrategy;
////    }
////
////    @Bean
////    public MBeanExporter mBeanExporter()  {
////        MBeanExporter mBeanExporter =  new MBeanExporter();
////        mBeanExporter.setNamingStrategy(namingStrategy());
////        mBeanExporter.setAssembler(metadataMBeanInfoAssembler());
////        mBeanExporter.setAutodetect(true);
////        return mBeanExporter;
////    }
//}
