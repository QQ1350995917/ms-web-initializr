package pwd.initializr.account.api.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import pwd.initializr.common.web.api.vo.PageInput;

/**
 * pwd.initializr.account.api.admin.vo@ms-web-initializr
 *
 * <h1>请求参数：用户查询参数</h1>
 *
 * date 2020-08-08 20:28
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
@ApiModel(value = "adminUserQueryInput", description = "管理员用户查询接口请求参数")
public class AdminUserQueryInput implements Serializable {

  @ApiModelProperty(name = "pin", value = "身份证编号", required = false, example = "010010015501010000")
  private String pin;
  @ApiModelProperty(name = "name", value = "姓名", required = false, example = "曹操")
  private String name;
  @ApiModelProperty(name = "gender", value = "性别", required = false, example = "1")
  private Integer gender;
  @ApiModelProperty(name = "empNo", value = "工号", required = false, example = "010-000")
  private String empNo;
  @ApiModelProperty(name = "level", value = "管理员等级", required = false, example = "1")
  private Integer level;
}
