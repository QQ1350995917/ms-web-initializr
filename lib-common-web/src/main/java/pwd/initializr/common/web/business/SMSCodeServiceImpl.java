package pwd.initializr.common.web.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pwd.initializr.common.mw.redis.RedisClient;
import pwd.initializr.common.utils.VerifyUtil;
import pwd.initializr.common.vcode.VCodeHelper;
import pwd.initializr.common.web.api.Constant;
import pwd.initializr.common.web.business.bo.SMSCode;

/**
 * pwd.initializr.common.web.business@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-09-20 22:45
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Service
@Component
public class SMSCodeServiceImpl implements SMSCodeService {

  @Autowired
  protected RedisClient redisClient;

  @Override
  public SMSCode productSMSCode(String phoneNumber) {
    if (!VerifyUtil.phoneNumber(phoneNumber)) {
      return null;
    }
    // 从 redis 中根据手机号码查找
    String smsCode = redisClient.get(Constant.REDIS_KEY_PHONE_SMS_CODE_PREFIX + phoneNumber);
    // 找到就返回，找不到就生成新的
    if (StringUtils.isEmpty(smsCode)) {
      VCodeHelper vCodeHelper = new pwd.initializr.common.vcode.SMSCode();
      smsCode = vCodeHelper.productMessage().getPresented();
      // 放入redis，并设置过期时间
      redisClient.setex(Constant.REDIS_KEY_PHONE_SMS_CODE_PREFIX + phoneNumber, 60 * 5,
          smsCode);
    }
    // TODO 调用短信网关发送短信验证码
    return new SMSCode(phoneNumber, smsCode);
  }

  @Override
  public Boolean match(SMSCode smsCode) {
    String smsCodeInRedis = redisClient
        .get(Constant.REDIS_KEY_PHONE_SMS_CODE_PREFIX + smsCode.getPhoneNumber());
    if (StringUtils.isEmpty(smsCodeInRedis)) {
      return false;
    }

    if (!smsCodeInRedis.equals(smsCode.getSmsCode())) {
      return false;
    }

    redisClient.expire(Constant.REDIS_KEY_PHONE_SMS_CODE_PREFIX + smsCode.getPhoneNumber(), 0);
    return true;
  }
}
