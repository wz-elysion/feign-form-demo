package wz.ling1991.client.feignclient;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class FeignConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringEncoder(getMessageConverters());
    }


    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(getMessageConverters()));
    }

    private ObjectFactory<HttpMessageConverters> getMessageConverters() {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        messageConverter.setSupportedMediaTypes(mediaTypes);

        List<HttpMessageConverter<?>> messageConvertersList = new ArrayList<>(messageConverters.getObject().getConverters());

        int count = 0;
        for (HttpMessageConverter<?> httpMessageConverter : messageConvertersList) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            } else if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                messageConvertersList.set(count, messageConverter);
            }
            count++;
        }
        return () -> new HttpMessageConverters(messageConvertersList);
    }


}
