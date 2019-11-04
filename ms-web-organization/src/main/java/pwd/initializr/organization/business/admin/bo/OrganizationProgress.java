package pwd.initializr.organization.business.admin.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pwd.initializr.organization.persistence.dao.OrganizationEntity.Progress;
import pwd.initializr.organization.persistence.dao.OrganizationProgressEntity.Type;

/**
 * pwd.initializr.organization.business.admin.bo@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-11-04 13:50
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrganizationProgress {

  /**
   * 主键
   */
  private Long id;
  /**
   * 组织ID
   */
  private Long orgId;
  /**
   * 提交人ID
   */
  private Long editorId;
  /**
   * 提交内容
   */
  private String content;
  /**
   * 关联ID
   */
  private Long refId;
  /**
   * 数据类型
   * see {@link Type}
   */
  private Integer type;
  /**
   * 审核进度 see {@link Progress}
   */
  private Integer progress;
  /**
   * 审核答复时间
   */
  private Long auditorTime;
  /**
   * 数据状态 see {@link pwd.initializr.organization.persistence.dao.ConstantStatus}
   */
  private Integer status;
  /**
   * 创建时间
   */
  private Long createTime;
  /**
   * 更新时间
   */
  private Long updateTime;
}
