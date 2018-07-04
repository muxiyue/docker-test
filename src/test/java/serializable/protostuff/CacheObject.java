package serializable.protostuff;

/**
 * 针对list，map等类型，序列化，反序列化存在问题，使用对象包装下。
 * 处理逻辑，暂时针对非基本类型，进行序列化处理。
 * Created by chenshengpeng on 2017/7/25.
 */
public class CacheObject<T> {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
