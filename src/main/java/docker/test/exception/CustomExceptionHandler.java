package docker.test.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常处理器
 * @author:
 * Created on 2016年3月30日 上午11:45:55
 * @modify author:修改人
 * Modify on 修改时间
*/
@ControllerAdvice
public class CustomExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected MessageSource messageSource;


	@ExceptionHandler(MultipartException.class)
    @ResponseBody
	public ResultBean handleBindException(HttpServletRequest request, MultipartException ex){

            return ResultBean.error(ResultBean.CODE_PARAM_ERROR, ex.getMessage());

	}

}
