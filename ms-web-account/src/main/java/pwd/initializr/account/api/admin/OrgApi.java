package pwd.initializr.account.api.admin;

/**
 * pwd.initializr.account.api.admin@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-10-12 17:59
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public interface OrgApi {

  void listOrg();

  void queryTopOrg();

  void createTopOrg();

  void reviewExecution();

  void reviewRefuse();

  void reviewApprove();

  void reviewRecheck();

}
