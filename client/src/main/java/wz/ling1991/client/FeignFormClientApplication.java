package wz.ling1991.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wz.ling1991.client.feignclient.FeignFileClient;

import java.io.*;


@Slf4j
@RestController
@RequestMapping("/feign")
@EnableFeignClients
@SpringBootApplication
public class FeignFormClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignFormClientApplication.class, args);
    }


    @Autowired
    private FeignFileClient feignFileClient;

    @GetMapping("/health")
    public String health() throws IOException {
        String rs1 = feignFileClient.health();
        log.info("rs1:{}", rs1);
        InputStream in = new FileInputStream("E:\\github\\feign-form-demo\\server\\src\\main\\java\\wz\\ling1991\\server\\FeignFormServerApplication.java");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        byte[] bytes = out.toByteArray();
        ByteArrayResource file = new ByteArrayResource(bytes) {
            //必须重写这个方法
            @Override
            public String getFilename() {
                return "temp";
            }
        };
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("file", file);
        String rs2 = feignFileClient.single(multiValueMap);
        log.info("rs2:{}", rs2);
        multiValueMap.add("id", "id");
        multiValueMap.add("name", "name");
        String rs3 = feignFileClient.singleForm(multiValueMap);
        log.info("rs3:{}", rs3);
        MultiValueMap<String, Object> multiValueMap2 = new LinkedMultiValueMap<>();
        multiValueMap2.add("file", file);
        multiValueMap2.add("file", file);
        String rs4 = feignFileClient.multiple(multiValueMap2);
        log.info("rs4:{}", rs4);
        multiValueMap2.add("id", "id");
        multiValueMap2.add("name", "name");
        String rs5 = feignFileClient.multipleForm(multiValueMap2);
        log.info("rs5:{}", rs5);
        return rs1;
    }

}
