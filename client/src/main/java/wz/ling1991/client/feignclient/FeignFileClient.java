package wz.ling1991.client.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url = "http://127.0.0.1:18080/file")
public interface FeignFileClient {

    @GetMapping("/health")
    String health();

    @PostMapping("/single")
    String single(@RequestBody MultiValueMap<String, Object> multiValueMap);

    @PostMapping("/single/form")
    String singleForm(@RequestBody MultiValueMap<String, Object> multiValueMap);

    @PostMapping("/multiple")
    String multiple(@RequestBody MultiValueMap<String, Object> multiValueMap);

    @PostMapping("/multiple/form")
    String multipleForm(@RequestBody MultiValueMap<String, Object> multiValueMap);

}