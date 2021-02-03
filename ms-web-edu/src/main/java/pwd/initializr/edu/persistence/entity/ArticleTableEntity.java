package pwd.initializr.edu.persistence.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <h2>article_table数据表实体类</h2>
 * date 2021-02-02 18:01
 *
 * @author Automatic[www.dingpengwei@foxmail.com]
 * @since 0.0.1-SNAPSHOT
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ArticleTableEntity implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
    * 主键
    * ID
    */
  private Long id;
  /**
    *
    * 上级ID
    */
  private Long pid;
  /**
    *
    * 标签名称
    */
  private String name;
  /**
    *
    * 原始数据标识
    */
  private String data;
  /**
    *
    * 叶子节点，0：非叶子节点；1：叶子节点
    */
  private Integer leaf;
  /**
    *
    * 可用性状态，0：不可用；1：可用；
    */
  private Integer able;
  /**
    *
    * 删除状态，0：未删除，1：已删除；
    */
  private Integer del;
  /**
    *
    * 创建时间
    */
  private Date createTime;
  /**
    *
    * 修改时间
    */
  private Date updateTime;

//  @Override
//  public boolean equals(Object obj) {
//    // fixme: 视情况而定是否修改或删除equals
//    if (obj == null) {
//      return false;
//    }
//    if (!(obj instanceof ArticleTableEntity)) {
//      return false;
//    }
//    return super.equals(obj);
//  }
//
//  @Override
//  public int hashCode() {
//    // fixme: 视情况而定是否修改或删除hashCode
//    return super.hashCode();
//  }
}
