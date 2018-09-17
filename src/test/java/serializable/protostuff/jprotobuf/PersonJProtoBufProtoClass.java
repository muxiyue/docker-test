package serializable.protostuff.jprotobuf;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
@ProtobufClass
public class PersonJProtoBufProtoClass {
    public String name;
	public Integer id;
	public String email;
	public Double doubleF;
	public Float floatF;
	public byte[] bytesF;
	@Protobuf(fieldType= FieldType.FLOAT, order=5, required=false)
	public Boolean boolF;
}