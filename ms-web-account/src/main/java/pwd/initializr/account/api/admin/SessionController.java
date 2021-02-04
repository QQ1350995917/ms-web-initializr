package pwd.initializr.account.api.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.initializr.account.api.vo.CaptchaOutput;
import pwd.initializr.account.api.vo.SessionCreateFailOutput;
import pwd.initializr.account.api.vo.SessionCreateFailOutput.FailType;
import pwd.initializr.account.api.vo.SessionCreateInput;
import pwd.initializr.account.api.vo.SessionCreateOkOutput;
import pwd.initializr.account.api.vo.SessionCreateOutput;
import pwd.initializr.account.api.vo.SessionInitOutput;
import pwd.initializr.account.api.vo.SessionStatus;
import pwd.initializr.account.business.admin.AdminAccountService;
import pwd.initializr.account.business.admin.AdminKeyService;
import pwd.initializr.account.business.admin.AdminUserService;
import pwd.initializr.account.business.session.SessionService;
import pwd.initializr.account.business.admin.bo.AdminAccountBO;
import pwd.initializr.account.business.admin.bo.AdminUserBO;
import pwd.initializr.account.business.session.bo.SessionBO;
import pwd.initializr.account.business.session.bo.SessionBOAnonymous;
import pwd.initializr.account.business.session.bo.CaptchaBO;
import pwd.initializr.account.business.session.bo.SessionBONamed;
import pwd.initializr.account.rpc.RPCToken;
import pwd.initializr.common.utils.CryptographerRsa;
import pwd.initializr.common.web.api.admin.AdminController;
import pwd.initializr.common.web.api.vo.Meta;
import pwd.initializr.common.web.persistence.entity.EntityAble;
import pwd.initializr.common.web.persistence.entity.EntityDel;

/**
 * pwd.initializr.account.api.admin@ms-web-initializr
 *
 * <h1>控制层逻辑：管理员session生命周期接口</h1>
 * <p>fixme: 逻辑漏洞，无法拦截机器登录情况，执行方式为：无 token 刷新页面（生成新的token）</p>
 * date 2019-10-25 20:18
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Api(
    tags = "会话管理",
    value = "adminSessionManageApi",
    description = "[登录，信息查询，退出]"
)
@RestController(value = "adminSessionApi")
@RequestMapping(value = "/api/admin/session")
@Slf4j
public class SessionController extends AdminController implements SessionApi {


  @Value("${account.admin.session.anonymous.expires.seconds}")
  private Integer anonymousSessionExpiresSeconds;

  @Value("${account.admin.session.anonymous.captcha.threshold}")
  private Integer anonymousSessionCaptchaThreshold;

  @Value("${account.admin.session.named.secret}")
  private String namedSessionSecret;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private AdminAccountService adminAccountService;

  @Autowired
  private AdminUserService adminUserService;

  @Autowired
  private AdminKeyService adminKeyService;

  @Override
  public void loginByNameAndPwd(@Valid @NotNull(message = "参数不能为空") SessionCreateInput input) {
    log.info(JSON.toJSONString(input));

    String anonymousToken = getToken();
    if (StringUtils.isBlank(anonymousToken)) {
      // token 不能为空
      outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.TokenISNull)));
      return;
    }
    if (input == null || StringUtils.isBlank(input.getLoginName()) || StringUtils
        .isBlank(input.getLoginName())) {
      // 输入不能为空
      outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.ParamsISNull)));
      return;
    }
    SessionBOAnonymous sessionBOAnonymous = sessionService.querySessionAnonymous(anonymousToken);
    if (sessionBOAnonymous == null) {
      // anonymousToken 过期
      outputException(408, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.TokenISExpires)));
      return;
    }
    if (sessionBOAnonymous.getTimes() >= anonymousSessionCaptchaThreshold) {
      // 需要校验验证码
      if (StringUtils.isBlank(input.getCaptcha())) {
        // 识别输入的验证码为空
        outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.CaptchaISNull)));
        return;
      }
      if (!input.getCaptcha().equals(sessionBOAnonymous.getCaptcha())) {
        // 验证码错误
        outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.CaptchaISError)));
        return;
      }
    }
    String loginPwd = input.getLoginPwd();
    // TODO 使用RSA解密登录密码
//    try {
//      loginPwd = CryptographerRsa.decryptByRsa(loginPwd,adminKeyService.getPrivateKey());
//    } catch (Exception e) {
//      log.error(e.getMessage());
//      outputException(400,"登录密码解密异常");
//      return;
//    }
    AdminAccountBO accountByNameAndPwd = adminAccountService.queryByNameAndPwd(input.getLoginName(),loginPwd);
    if (accountByNameAndPwd == null) {
      // 登录失败，更新错误登录次数
      sessionBOAnonymous.setTimes(sessionBOAnonymous.getTimes() + 1);
      sessionService.updateAnonymousSession(anonymousToken, sessionBOAnonymous);
      if (sessionBOAnonymous.getTimes() >= anonymousSessionCaptchaThreshold) {
        sessionService.createCaptcha(anonymousToken);
        outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(true,FailType.ParamsISError)));
      } else {
        outputException(407, new SessionCreateOutput<>(SessionStatus.ANONYMOUS.getNumber(),new SessionCreateFailOutput(FailType.ParamsISError)));
      }
      return;
    }

    if (accountByNameAndPwd.getAble() == EntityAble.DISABLE.getNumber()) {
      outputData(new Meta(403,"该账号已禁用，请联系管理员开启"));
      return;
    }
    if (accountByNameAndPwd.getDel() == EntityDel.YES.getNumber()) {
      outputData(new Meta(410,"该账号已删除"));
      return;
    }
    // 查询user信息
    AdminUserBO adminUserBO = adminUserService.queryById(accountByNameAndPwd.getUid());
    if (adminUserBO == null) {
      outputException(500);
      return;
    }
    if (adminUserBO.getAble() == EntityAble.DISABLE.getNumber()) {
      outputData(new Meta(403,"该用户已禁用，请联系管理员开启"));
      return;
    }
    if (adminUserBO.getDel() == EntityDel.YES.getNumber()) {
      outputData(new Meta(410,"该用户已删除"));
      return;
    }

    SessionBONamed sessionBONamed = new SessionBONamed(adminUserBO.getId(), adminUserBO.getName(),
        accountByNameAndPwd.getId(), accountByNameAndPwd.getLoginName(),
        System.currentTimeMillis());
    String namedToken = RPCToken.generateToken(sessionBONamed, namedSessionSecret);
    sessionService.createNamedSession(namedToken, sessionBONamed);
    sessionService.deleteAnonymousToken(anonymousToken);
    outputData(new SessionCreateOutput<>(SessionStatus.NAMED.getNumber(),new SessionCreateOkOutput(
        sessionBONamed.getUid(), sessionBONamed.getAccountId(), namedToken)));
  }


  @Override
  public void loginCaptchaRefresh() {
    String token = getToken();
    if (StringUtils.isBlank(token)) {
      // 参数不合规
      outputException(417);
      return;
    }
    SessionBOAnonymous sessionBOAnonymous = sessionService.querySessionAnonymous(token);
    if (sessionBOAnonymous == null) {
      // token 过期
      outputException(417);
      return;
    }
    if (sessionBOAnonymous.getTimes() < anonymousSessionCaptchaThreshold) {
      // 无需验证码
      outputException(417);
      return;
    }
    CaptchaBO captchaBO = sessionService.createCaptcha(token);
    if (captchaBO == null) {
      outputException(500);
      return;
    }

    CaptchaOutput sessionCaptchaOutput = new CaptchaOutput();
    BeanUtils.copyProperties(captchaBO, sessionCaptchaOutput);
    outputData(sessionCaptchaOutput);
  }

  @Override
  public void loginInitializr(Long aid, Long uid, String token) {
    SessionBO session = sessionService.createSession(token, uid);
    if (session instanceof SessionBONamed) {
      SessionInitOutput loginCookieOutput = new SessionInitOutput();
      loginCookieOutput.setStatus(SessionStatus.NAMED.getNumber());
      outputData(202,loginCookieOutput);
      return;
    }
    if (session instanceof SessionBOAnonymous) {
      Boolean captchaRequired = false;
      // 是否对该匿名 token 产生验证码
      if (((SessionBOAnonymous) session).getTimes() >= anonymousSessionCaptchaThreshold) {
        captchaRequired = true;
        sessionService.createCaptcha(token);
      }
      SessionInitOutput loginInitOutput = new SessionInitOutput();
      loginInitOutput.setStatus(SessionStatus.ANONYMOUS.getNumber());
      loginInitOutput.setToken(((SessionBOAnonymous) session).getToken());
      loginInitOutput.setExpires(anonymousSessionExpiresSeconds);
      loginInitOutput.setCaptchaRequired(captchaRequired);
      // TODO 登录方式列表
      outputData(loginInitOutput);
      return;
    }
    // 生成匿名token失败
    outputException(500);
  }

  @Override
  public void logout() {
    if (sessionService.deleteNamedSession(getUid())) {
      outputData(200);
    } else {
      outputException(500);
    }
  }

  @Override
  public void querySessionInfo() {
    // TODO 基本信息
    // TODO 权限信息
    SessionBONamed session = sessionService.querySessionNamed(getUid());
    if (session == null) {
      super.outputException(401);
      return;
    } else {
      JSONObject content = new JSONObject();
      content.put("roles", new String[]{"admin"});
      content.put("introduction", "I am a super administrator");
      content.put("avatar",
          "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
      content.put("name", "Super Admin");
      super.outputData(content);
    }
  }
}
