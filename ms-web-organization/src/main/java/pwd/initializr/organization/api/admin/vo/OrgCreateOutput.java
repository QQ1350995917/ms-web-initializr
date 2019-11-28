package pwd.initializr.organization.api.admin.vo;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * pwd.initializr.account.api.admin.vo@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-10-16 20:26
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "orgCreateOutput", description = "组织创建响应参数")
public class OrgCreateOutput {

  /**
   * 主键ID
   */
  @NotNull
  private Long id;
  /**
   * 外键上级组织ID
   */
  @NotNull
  private Long pid;
  /**
   * 组织名称
   */
  @Null
  private String name;
  /**
   * 组织logo
   */
  @Null
  private String logo;
  /**
   * 组织描述
   */
  @Null
  private String description;
  /**
   * 组织口号
   */
  @Null
  private String slogan;
  /**
   * 组织等级
   */
  @NotNull
  private Integer level = 0;
  /**
   * 组织排序
   */
  @NotNull
  private Integer sort = 0;
  /**
   * 成员数量
   */
  @NotNull
  private Integer members = 1;
  /**
   * 处理过程 see {@link }
   */
  @NotNull
  private Integer progress = 0;
  /**
   * 状态
   * see {@link pwd.initializr.organization.persistence.dao.ConstantStatus}
   */
  @NotNull
  private Integer status = 0;
  /**
   * 创建时间
   */
  @NotNull
  private Long createTime;
}
