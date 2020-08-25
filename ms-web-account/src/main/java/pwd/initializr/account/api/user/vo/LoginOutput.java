package pwd.initializr.account.api.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * pwd.initializr.account.api.user.vo@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-11-02 22:00
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "loginOutput", description = "登录响应参数")
public class LoginOutput {

  @ApiModelProperty(name = "uid", value = "登录ID", required = true, example = "0")
  @NotNull(message = "0")
  private Long uid;

  @ApiModelProperty(name = "token", value = "登录令牌", required = true, example = "token")
  @NotNull(message = "0")
  private String token;
}
