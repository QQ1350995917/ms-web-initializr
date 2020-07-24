package pwd.initializr.common.web.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import pwd.initializr.common.utils.GzipUtil;
import pwd.initializr.common.utils.StringUtil;
import pwd.initializr.common.utils.VerifyUtil;
import pwd.initializr.common.web.api.vo.Meta;
import pwd.initializr.common.web.api.vo.Output;
import pwd.initializr.common.web.business.SMSCodeService;
import pwd.initializr.common.web.exception.BaseException;

/**
 * pwd.initializr.common.web.api@ms-web-initializr
 *
 * <P>API请求/响应框架</P>
 * <P>识别API请求头信息。</P>
 * <P>处理API响应输出。</P>
 *
 * date 2019-09-14 14:59
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
*/
@Slf4j
public class ApiController {

  public static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal();
  public static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal();

  @Autowired
  protected SMSCodeService smsCodeService;

  protected String local = "en";

  public static void setRequestLocal(HttpServletRequest httpServletRequest) {
    requestLocal.set(httpServletRequest);
  }

  public static void setResponseLocal(HttpServletResponse httpServletResponse) {
    responseLocal.set(httpServletResponse);
  }

  public static HttpServletRequest getRequest() {
    return requestLocal.get();
  }

  public static Long getUid() {
    String uid = getRequest().getHeader(ApiConstant.HTTP_HEADER_KEY_UID);
    if (uid == null || uid.trim().equals("")) {
      return null;
    }
    return Long.parseLong(uid);
  }

  public static String getToken() {
    return getRequest().getHeader(ApiConstant.HTTP_HEADER_KEY_TOKEN);
  }

  public static String getClientOS() {
    return getRequest().getHeader(ApiConstant.HTTP_HEADER_KEY_OS);
  }

  public static HttpServletResponse getResponse() {
    return responseLocal.get();
  }

  public String getLocal() {
    return local == null ? "en" : local;
  }

  @ModelAttribute
  public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
    setRequestLocal(request);
    setResponseLocal(response);
  }

  public <T extends ApiController> void outputExceptionToLog(Class<T> clazz, Exception e,
      Object... input) {
    if (e instanceof BaseException) {
      // 如果是baseException，是手动抛出，不需要告警，只打印info日志
      log.info(String
          .format("IntervalServerError:className:%s，requestParams:%s，", clazz.getName(),
              JSONObject.toJSONString(input)), e);
    } else {
      log.error(String
          .format("IntervalServerError:className:%s，requestParams:%s，", clazz.getName(),
              JSONObject.toJSONString(input)), e);
    }
  }

  public void outputException(int code, String message) {
    Meta meta = new Meta(code, message);
    Output<Object> objectOutput = new Output<>(meta, null);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public <T> void outputException(Meta meta, T t) {
    Output<Object> objectOutput = new Output<>(meta, t);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }


  public void outputException(int code) {
    String message = ApiProperties.apiBundles.get(getLocal()).getString(code + "");
    Meta meta = new Meta(code, message);
    Output<Object> objectOutput = new Output<>(meta, null);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public void outputData() {
    Meta meta = new Meta();
    Output<Object> objectOutput = new Output<>(meta, null);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public void outputData(Integer code) {
    Meta meta = new Meta(code);
    Output<Object> objectOutput = new Output<>(meta, null);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public <T> void outputData(Meta meta, T t) {
    Output<Object> objectOutput = new Output<>(meta, t);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public <T> void outputData(Meta meta) {
    Output<Object> objectOutput = new Output<>(meta, null);
    this.finalOutput(JSON.toJSONString(objectOutput));
  }

  public <T> void outputData(T t) {
    this.outputData(new Meta(), t);
  }

  private void finalOutput(String data) {
    String clientEncoding = getRequest().getHeader("Accept-Encoding");
    boolean canGzip = false;
    if (clientEncoding != null && clientEncoding.indexOf("gzip") > -1
        && StringUtil.null2Str(data).length() >= 200) {
      canGzip = true;
      getResponse().setHeader("Content-Encoding", "gzip");
    }
    getResponse().setHeader("Content-Type", "application/json");
    getResponse().setCharacterEncoding("UTF-8");
    try {
      if (canGzip) {
        byte[] e = this.compressData(data);
        ServletOutputStream out = getResponse().getOutputStream();
        if (e != null) {
          out.write(e);
        }
        out.flush();
        out.close();
      } else {
        getResponse().getWriter().append(data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private byte[] compressData(String data) {
    byte[] bytes = null;

    try {
      bytes = GzipUtil.compress(data.getBytes("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return bytes;
  }


  protected void verifyManual(String identify) {

  }

  protected void verifyPhone(String phoneNumber) {
    if (VerifyUtil.phoneNumber(phoneNumber)) {
      smsCodeService.productSMSCode(phoneNumber);
      outputData();
    } else {
      outputException(400);
    }
  }
}
