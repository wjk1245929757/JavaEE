package com.example.demo.config;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExtRateLimiter {
 
  // 以每秒为单位固定的速率值往令牌桶中添加令牌
  double permitsPerSecond();
 
  // 在规定的毫秒数中，如果没有获取到令牌的话，则直接走服务器降级处理
  long timeout();
}