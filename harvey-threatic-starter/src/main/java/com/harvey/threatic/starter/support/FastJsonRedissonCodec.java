package com.harvey.threatic.starter.support;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import io.netty.buffer.Unpooled;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

import java.nio.charset.StandardCharsets;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-10
 */
public class FastJsonRedissonCodec extends BaseCodec {
    private final Encoder encoder = obj -> {
        String json = JSON.toJSONString(obj, JSONWriter.Feature.WriteClassName);
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        return Unpooled.wrappedBuffer(jsonBytes);
    };
    
    private final Decoder<Object> decoder = (buf, state) -> {
        String json = buf.toString(StandardCharsets.UTF_8);
        return JSON.parseObject(json, Object.class, JSONReader.Feature.SupportAutoType);
    };
    
    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }
    
    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}
