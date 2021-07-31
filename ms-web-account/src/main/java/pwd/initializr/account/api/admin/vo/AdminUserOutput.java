package pwd.initializr.account.api.admin.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * pwd.initializr.account.api.admin.vo@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2021-07-31 23:27
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "adminUserOutput", description = "管理员用户接口请求参数")
public class AdminUserOutput extends AdminUserInput {

}
