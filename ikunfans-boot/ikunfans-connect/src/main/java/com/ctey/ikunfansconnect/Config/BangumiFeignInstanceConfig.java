package com.ctey.ikunfansconnect.Config;

import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.ctey.ikunfanscommon.Static.ConnModuleStatic.BANGUMI_BASE_API;

@Configuration
public class BangumiFeignInstanceConfig {
    @Bean("bangumiInstance")
    public BangumiFeignAPIConfig getBangumiInstance() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .client(new OkHttpClient())
                .options(new Request.Options(5, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true))
                .target(BangumiFeignAPIConfig.class, BANGUMI_BASE_API);
    }
}
