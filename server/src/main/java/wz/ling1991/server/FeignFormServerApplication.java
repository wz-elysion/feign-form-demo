package wz.ling1991.server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file")
@SpringBootApplication
public class FeignFormServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(FeignFormServerApplication.class, args);
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    /**
     * 单文件上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/single")
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return "文件原名为：" + multipartFile.getOriginalFilename();
    }

    /**
     * 表单+文件
     *
     * @param multipartFile
     * @param reqVo
     * @return
     */
    @PostMapping("/single/form")
    public String upload(@RequestParam("file") MultipartFile multipartFile, ReqVo reqVo) {
        log.info("reqVo = {}", reqVo);
        return "文件原名为：" + multipartFile.getOriginalFilename();
    }

    /**
     * 多文件
     *
     * @param multipartFiles
     * @return
     */
    @PostMapping("/multiple")
    public String upload(@RequestParam("file") MultipartFile[] multipartFiles) {
        return "共" + multipartFiles.length + "个文件。";
    }

    /**
     * 多文件 + 表单
     *
     * @param multipartFiles
     * @return
     */
    @PostMapping("/multiple/form")
    public String upload(@RequestParam("file") MultipartFile[] multipartFiles, ReqVo reqVo) {
        log.info("reqVo = {}", reqVo);
        return "共" + multipartFiles.length + "个文件。";
    }

    @Data
    class ReqVo {
        private String id;
        private String name;
    }

}
