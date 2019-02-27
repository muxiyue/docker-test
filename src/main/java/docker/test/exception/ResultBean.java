package docker.test.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultBean {
	public static final String CODE_SUCCESS = "0000";//成功
	public static final String CODE_PARAM_ERROR = "1001";//参数错误
	public static final String CODE_INVALID = "1002";//appId、用户id、vId不匹配（修改）
    public static final String CODE_WARN = "1003";//图片文件不存在
	public static final String CODE_FAILURE = "3000";//失败
	public static final String CODE_OTHER = "9999";//系统异常
	public static final String CODE_NOT_EXIST = "2000";//svid不存在
    public static final String CODE_NOT_CONTENT = "1006";//内容Id不存在

	public static final String MSG_SUCCESS="成功";
	public static final String MSG_FAILURE="failure";
    /**
     * 信息代码
     */
	private String resultCode;
    /**
     * 信息说明
     */
    private String  resultDesc;
    /**
     * 返回数据或jqgrid中的root
     */
    private Object data;
    /**
     * 总的页数
     */
    private Integer total;
    /**
     * 总记录数
     */
    private Integer totalCount;

    public ResultBean(String resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

    public ResultBean(String resultCode, String resultDesc, Object data) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
		this.data = data;
	}

    public ResultBean(String resultCode, String resultDesc, Object data, Integer total, Integer totalCount) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
		this.data = data;
		this.total = total;
		this.totalCount = totalCount;
	}

    public static ResultBean ok() {
        return new ResultBean(CODE_SUCCESS, MSG_SUCCESS);
    }

	public static ResultBean ok(Object data) {
        return new ResultBean(CODE_SUCCESS, MSG_SUCCESS, data);
    }
	public static ResultBean ok(String resultCode, String msg) {
		return new ResultBean(resultCode, msg);
	}
	public static ResultBean ok(Object data, Integer total, Integer totalCount){
		return new ResultBean(CODE_SUCCESS, MSG_SUCCESS, data, total, totalCount);
	}

	public static ResultBean error(String resultCode, String resultDesc){
        return new ResultBean(resultCode, resultDesc);
    }

	public static ResultBean error(String resultDesc){
        return new ResultBean(CODE_FAILURE, resultDesc);
    }

    public String getResultCode() {
        return resultCode;
    }

    public ResultBean setresultCode(String resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public ResultBean setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultBean setData(Object data) {
        this.data = data;
        return this;
    }

    public Integer getTotal() {
		return total;
	}

	public ResultBean setTotal(Integer total) {
		this.total = total;
		return this;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public ResultBean setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
		return this;
	}
}
