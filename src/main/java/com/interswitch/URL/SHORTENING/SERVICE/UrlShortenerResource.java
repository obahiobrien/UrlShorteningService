package com.interswitch.URL.SHORTENING.SERVICE;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequestMapping("/rest/url")
@RestController
public class UrlShortenerResource {
    @Autowired
    StringRedisTemplate redisTemplate;
    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id) {
        String url = redisTemplate.opsForValue().get(id);
        System.out.println("URL Retrieve:" + url);
        return url;
    }

    @PostMapping
    public String create(@RequestBody String url) {
//        UrlValidator urlValidator = new UrlValidator(
//                new String[]{"http","https"}
//        );

     //   if (urlValidator.isValid(url)) {
            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
            System.out.println("URl Id generated: "+ id);
            redisTemplate.opsForValue().set(id, url);
            return id;
       // }

        //throw new RuntimeException("URl Invalid" + url);
    }

}
