package serializable.protostuff;

/**
 * 通用响应
 * @author:
 * Created on 2016年2月25日 上午8:51:03
 * @modify author:修改人
 * Modify on 修改时间
*/
public class GenericResponse<T> {
	
	private String requestId;
	
	private String code;
	
	private Object success;
	
	private String message;
	
	private T result;
	
	public GenericResponse() {
		super();
	}

	public GenericResponse(String requestId, T result) {
		this(requestId, "0", true, null, result);
	}
	
	public GenericResponse(String requestId, String code, boolean success,
			String message, T result) {
		super();
		this.requestId = requestId;
		this.code = code;
		this.success = success;
		this.message = message;
		this.result = result;
	}

	//**********************
	// Getter & Setter
	//**********************
	public String getRequestId() {
		return requestId;
	}

	public String getCode() {
		return code;
	}
	
	public void setSuccess(Object success){
		this.success = success;
	}

	public boolean isSuccess() {
		if(!Boolean.class.isAssignableFrom(success.getClass())){
			if("1".equals(success.toString())){
				success = true;
			}else{
				success = false;
			}
		}
		return (boolean)success;
	}

	public String getMessage() {
		return message;
	}

	public T getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		return "success: " + isSuccess() 
				+ ", code: " + getCode() 
				+ ", msg: " + getMessage()
				+ ", requestId: " + getRequestId() 
				+ ", result: " + getResult();
	}

	public Object getSuccess() {
		return success;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
