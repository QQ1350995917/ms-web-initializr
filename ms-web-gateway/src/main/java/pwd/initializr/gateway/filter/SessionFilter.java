package pwd.initializr.gateway.filter;

import com.alibaba.fastjson.JSON;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pwd.initializr.account.rpc.RPCSession;
import pwd.initializr.account.rpc.RPCToken;
import pwd.initializr.common.mw.redis.RedisClient;
import pwd.initializr.common.web.api.ApiConstant;
import pwd.initializr.common.web.api.vo.Meta;
import pwd.initializr.common.web.api.vo.Output;
import pwd.initializr.gateway.list.KeyValueList;
import reactor.core.publisher.Mono;

/**
 * pwd.initializr.gateway.filter@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-11-09 08:52
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Component
public class SessionFilter implements GlobalFilter, Ordered {

  @Value("${account_secret}")
  private String ACCOUNT_SECRET;
  @Value("${account_login_prefix}")
  private String SESSION_PREFIX_USER;
  @Value("${account_login_prefix_admin}")
  private String SESSION_PREFIX_ADMIN;
  @Value("${filter_skip_all:true}")
  private Boolean filterSkipAll;

  @Autowired
  private RedisClient redisClient;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (filterSkipAll) {
      return chain.filter(exchange);
    }
    ServerHttpRequest request = exchange.getRequest();
    ServerHttpResponse response = exchange.getResponse();
    String method = request.getMethodValue();
    String path = request.getURI().getPath();

    if (KeyValueList.skipToken(path, method)) {
      // 白名单
      return chain.filter(exchange);
    }

    String SESSION_PREFIX;
    if (path.contains(KeyValueList.adminPath)) {
      SESSION_PREFIX = SESSION_PREFIX_ADMIN;
    } else {
      SESSION_PREFIX = SESSION_PREFIX_USER;
    }
    // 1：获取header中的uid和token信息
    String uid = request.getHeaders().getFirst(ApiConstant.HTTP_HEADER_KEY_UID);
    String token = request.getHeaders().getFirst(ApiConstant.HTTP_HEADER_KEY_TOKEN);
    if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)) {
      return buildSessionErrorMono(request, response, "请求参数错误");
    }
    // 2：根据uid在redis中找到保存的用户信息字符串
    String key = StringUtils.join(new String[]{SESSION_PREFIX, uid});
    String userJson = redisClient.get(key);
    if (StringUtils.isEmpty(userJson)) {
      // Session 未获取到 超时或者未登录
      return buildSessionErrorMono(request, response, "未登录或登录超时");
    }
    // 3：反序列化用户信息字符串
    RPCSession RPCSession = JSON.parseObject(userJson, RPCSession.class);
    try {
      // 4：验请求携带的token信息
      RPCToken.verifyToken(RPCSession, token, ACCOUNT_SECRET);
    } catch (Exception e) {
      // Session 获取到 验证失败
      return buildSessionErrorMono(request, response, "请求参数错误");
    }

//  request.getHeaders().add(ApiConstant.HTTP_HEADER_KEY_UID, uid);
    ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
        .header(ApiConstant.HTTP_HEADER_KEY_UID, new String[]{uid}).build();
    //将现在的request 变成 change对象
    ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequest).build();
    // Session 获取到 验证成功
    return chain.filter(serverWebExchange);
  }

  private Mono<Void> buildSessionErrorMono(ServerHttpRequest request, ServerHttpResponse response,
      String message) {
    RequestPath path = request.getPath();
    String url = request.getURI().getPath();
    String redirect;
    if (path.value().contains(KeyValueList.adminPath)) {
      redirect = KeyValueList.adminLogin;
    } else {
      redirect = KeyValueList.userLogin;
    }
    String method = request.getMethodValue();
    response.setStatusCode(HttpStatus.resolve(401));
    HttpHeaders httpHeaders = response.getHeaders();
    httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
    httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    httpHeaders.add("Location", redirect);
    Output<Object> objectOutput = new Output<>(new Meta(HttpStatus.UNAUTHORIZED.value(), message));
    String warning = JSON.toJSONString(objectOutput);
    DataBuffer bodyDataBuffer = response.bufferFactory()
        .wrap(warning.getBytes(StandardCharsets.UTF_8));
//    return response.setComplete();
    return response.writeWith(Mono.just(bodyDataBuffer));
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
