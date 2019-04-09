package com.xlauncher.util;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置HttpMessageConverter转换器
 * @author 白帅雷
 * @date 2018-07-04
 */
public class HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        //加入text/html类型的支持
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(MediaType.MULTIPART_FORM_DATA);
        setSupportedMediaTypes(mediaTypes);
    }
}
