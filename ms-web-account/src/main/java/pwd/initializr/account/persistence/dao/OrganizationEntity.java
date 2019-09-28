package pwd.initializr.account.persistence.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * pwd.initializr.account.persistence.dao@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-09-27 17:03
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
public class OrganizationEntity {

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
   * 处理过程 see {@link Progress}
   */
  @NotNull
  private Integer progress = 0;
  /**
   * 状态 see {@link pwd.initializr.account.persistence.dao.ConstantStatus}
   */
  @NotNull
  private Integer status = 0;
  /**
   * 创建时间
   */
  @NotNull
  private Long createTime;
  /**
   * 更新时间
   */
  @NotNull
  private Long updateTime;


  public enum Progress {

    //                        |--->审核通过4--->重新复核5---|
    // 新建0--->待审核1--->审核中2                          |
    //          ^             |--->审核拒绝3---|           |
    //          |-----------------------------|<---------|
    NEW(0),
    REVIEW_PENDING(1),
    REVIEW_EXECUTION(2),
    REVIEW_REFUSE(3),
    REVIEW_APPROVE(4),
    REVIEW_RECHECK(5);

    private int value;

    Progress(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }
  }
}
