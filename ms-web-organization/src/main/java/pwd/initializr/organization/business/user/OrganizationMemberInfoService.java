package pwd.initializr.organization.business.user;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pwd.initializr.common.web.api.vo.Output;
import pwd.initializr.organization.business.user.bo.OrganizationMemberInfo;

/**
 * pwd.initializr.organization.business.user@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-11-05 16:29
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@FeignClient(value = "${ms.account.name}", fallback = OrganizationMemberInfoServiceImpl.class)
public interface OrganizationMemberInfoService {

  @RequestMapping(value = "/api/robot/user", method = RequestMethod.GET)
  Output<List<OrganizationMemberInfo>> fetchMemberInfo(
      @RequestParam(value = "userIds") Long[] userIds);
}
