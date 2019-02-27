package docker.test.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/19 上午11:25
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    private String fileName;

    private String fileSize;
}
