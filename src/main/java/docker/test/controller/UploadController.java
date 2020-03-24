package docker.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import docker.test.exception.ResultBean;
import docker.test.utils.FileUtil;
import docker.test.utils.Md5Util;
import docker.test.utils.StringUtil;
import edu.emory.mathcs.backport.java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/17 下午1:09
 * @Modified By:
 */
@Controller
@RequestMapping("/upload")
public class UploadController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /*
     * 通过流的方式上传文件
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String  fileUpload(@RequestParam("file") MultipartFile file, String fileName) throws IOException {


        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename()+ "  ,fileName" + fileName);

        try {
            //获取输出流
            OutputStream os=new FileOutputStream("/Users/chenshengpeng/F_Work/examples/docker-test/upload/"+new Date().getTime()+file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "done";
    }

    /*
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping("fileUpload2")
    @ResponseBody
    public String  fileUpload2(@RequestParam("file") MultipartFile file, String fileName) throws IOException {
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename() + "  ,fileName" + fileName);
        String path="/Users/chenshengpeng/F_Work/examples/docker-test/upload/"+new Date().getTime()+file.getOriginalFilename();

        File newFile=new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long  endTime=System.currentTimeMillis();
        System.out.println("方法二的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "done";
    }

    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("springUpload")
    @ResponseBody
    public Object  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
    {
        try {
            long startTime = System.currentTimeMillis();
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if (multipartResolver.isMultipart(request)) {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //获取multiRequest 中所有的文件名
                Iterator iter = multiRequest.getFileNames();

                while (iter.hasNext()) {
                    //一次遍历所有文件
                    MultipartFile file = multiRequest.getFile(iter.next().toString());
                    if (file != null) {
                        String path = "/Users/chenshengpeng/F_Work/examples/docker-test/upload/springUpload/" + file.getOriginalFilename();
                        //上传
                        file.transferTo(new File(path));
                    }

                }

            }
            long endTime = System.currentTimeMillis();
            System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        }
        catch (IllegalStateException ex) {
            return ResultBean.error(ResultBean.CODE_PARAM_ERROR, ex.getMessage());
//            throw ex;
        }

        return "done";
    }


    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("springUpload2")
    @ResponseBody
    public Object  springUpload2(@RequestParam("file") MultipartFile[] files, String info, HttpServletRequest request) throws IllegalStateException, IOException
    {

        System.out.println(info);
        try {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (file != null) {
                    String path = "/Users/chenshengpeng/F_Work/examples/docker-test/upload/springUpload/" + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }
            }



            long endTime = System.currentTimeMillis();
            System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        }
        catch (IllegalStateException ex) {
            return ResultBean.error(ResultBean.CODE_PARAM_ERROR, ex.getMessage());
            //            throw ex;
        }

        return "done";
    }


    /*
     * 分片上传
     */
    @RequestMapping("blockUpload")
    @ResponseBody
    public String  blockUpload(@RequestParam("file") MultipartFile[] files, String fileInfoStr, String bigFileExtInfoStr, HttpServletRequest request) throws IllegalStateException, IOException
    {

        try {
            long startTime = System.currentTimeMillis();

            // 校验 日志 略

            JSONObject fileInfo = JSON.parseObject(fileInfoStr);
            String filePath = fileInfo.getString("filePath");
            String md5 = fileInfo.getString("md5");
            Long fileSize = fileInfo.getLong("fileSize");

            JSONObject bigFileExtInfo = JSON.parseObject(bigFileExtInfoStr);
            String blockCutSize = bigFileExtInfo.getString("blockCutSize");
            Long totalBlockNum = bigFileExtInfo.getLong("totalBlockNum");
            JSONArray blockInfoList = bigFileExtInfo.getJSONArray("blockInfoList");

            File destFile = new File(filePath);
            if (destFile.exists()) {
                log.warn("文件已经存在，无法上传，路径：" + filePath);
                return "文件已经存在";
            }

            // 生成路径
            String dirPath = filePath + "_" + blockCutSize + "_" + totalBlockNum + "_block";

            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            // 判断文件是否正在合并
            String allTmp = dirPath + File.separator + "all_blocks_tmp~";
            File tmp = new File(allTmp);
            if (FileUtil.isFileUpdate(tmp)) {
                log.info("正在合并分片中，无需上传");
                return "正在合并分片中，无需上传";
            }




            for (int i = 0; i < blockInfoList.size(); i++) {
                JSONObject blockInfo = blockInfoList.getJSONObject(i);
                MultipartFile file = files[i];
                if (file != null) {
                    // 1.	分片名称为： blockNum_blockSize.block
                    //完整路径如：gk/xxxxxx/xxxxx.jpg_5242880_10_block/1_5242880.block
                    String blockPath = dirPath + File.separator + blockInfo.getString("blockNum") + "_" + blockInfo.getString("blockSize")+ ".block";

                    File blockFile = new File(blockPath);

                    if (blockFile.exists()) {
                        log.info("分片已经存在，无需上传，路径为：" + blockPath);
                        continue;
                    }

                    // 临时分片名称：分片名称后面加  _6位随机数_tmp 。
                    // 临时文件如：gk/xxxxxx/xxxxx.jpg_5242880_10_block/1_5242880.block_de8ijh_tmp，
                    // 分片移动后，需要校验大小是否正确，如果不正确，则该分片上传失败。
                    String blockTempPath = blockPath + "" + StringUtil.getRandomChar(6) + "_tmp";
                    //上传
                    file.transferTo(new File(blockTempPath));

                    boolean result = new File(blockTempPath).renameTo(blockFile);
                    log.info("分片上传完完成，保存到：" + blockPath + ",重命名结果" + result);
                    if (blockFile.length() != blockInfo.getLong("blockSize")) {
                        log.error("分片大小校验不通过，路径：" + blockPath);
                        blockFile.delete();
                    }
                }
            }

            // 判断分片是否都上传完成。
            File[] blocks = dirFile.listFiles(new FilenameFilter() {

                @Override public boolean accept(File dir, String name) {
                    if (name.matches("^\\d+_\\d+\\.block$")) {
                        return true;
                    }
                    return false;
                }
            });


            List<File> fileList = Arrays.asList(blocks);
            Collections.sort(fileList, new Comparator<File>() {

                /**
                 * Compares its two arguments for order.  Returns a negative integer,
                 * zero, or a positive integer as the first argument is less than, equal
                 * to, or greater than the second.<p>
                 */
                @Override public int compare(File o1, File o2) {
                    String name1 = o1.getName().split("_")[0];
                    String name2 = o2.getName().split("_")[0];
                    if (Integer.valueOf(name1) > Integer.valueOf(name2)) {
                        return 1;
                    }
                    else if (Integer.valueOf(name1) < Integer.valueOf(name2)) {
                        return -1;
                    }
                    else {
                        return 0;
                    }
                }
            });

            // 分片数量足够
            if (blocks.length == totalBlockNum) {

                if (FileUtil.isFileUpdate(tmp)) {
                    log.info("正在合并分片中,无需重复合并");
                    return "正在合并分片中,无需重复合并";
                }

                // 创建文件，如果存在，则跳过合并。
                boolean result = tmp.createNewFile();
                if (result) {

                    log.info("开始合并");

                    FileChannel outChnnel = new FileOutputStream(tmp).getChannel();

                    fileList.forEach(file -> {
                        try {
                            FileChannel inChannel = new FileInputStream(file).getChannel();
                            inChannel = new FileInputStream(file).getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChnnel);
                            inChannel.close();

//                            outChnnel.position(inChannel.size());

//                            IOUtils.copy(new FileInputStream(file), outputStream);
                            log.info("合并分片文件" + file.getAbsolutePath());
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    outChnnel.close();

                    tmp.renameTo(destFile);
                    FileUtil.deleteFile(dirFile);

                    log.info("合并完成");

                }
                else {
                    log.info("其他线程正在合并分片中，停止合并");
                    return "其他线程正在合并分片中，停止合并";
                }
            }
            else {
                return "分片上传成功";
            }


            if (!fileSize.equals(destFile.length())) {
                destFile.delete();
                log.info("文件大小 不正确");
                return "文件大小 不正确" ;
            }

            // 计算MD5
            String newMd5 = Md5Util.getMD5(destFile);
            if (!newMd5.equals(md5)) {
                destFile.delete();
                log.info("md5 不正确, newMd5=" + newMd5 + ",md5=" + md5);
                return "md5 不正确" ;
            }




            long endTime = System.currentTimeMillis();
            log.info("上传完成，运行时间：" + String.valueOf(endTime - startTime) + "ms");
        }
        catch (IllegalStateException ex) {
            return ex.getMessage();
            //            throw ex;
        }

        return "上传成功";
    }

}
