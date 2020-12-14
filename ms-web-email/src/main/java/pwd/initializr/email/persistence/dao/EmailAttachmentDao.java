package pwd.initializr.email.persistence.dao;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pwd.initializr.email.persistence.entity.EmailAttachmentEntity;
import pwd.initializr.common.web.persistence.entity.ScopeEntity;
import pwd.initializr.common.web.persistence.entity.SortEntity;

/**
 * <h2>email_attachment数据表操作</h2>
 * date 2020-12-14 16:13
 *
 * @author Automatic[www.dingpengwei@foxmail.com]
 * @since 0.0.1-SNAPSHOT
 */
@Mapper
public interface EmailAttachmentDao {

  /**
   * <h2>通过主键启用/禁用数据</h2>
   * date 2020-12-14 16:13
   *
   * @param id 主键
   * @param able 业务数据
   * @return java.lang.Integer 受影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer ableById(@Param("id") Long id, @Param("able") Integer able);

  /**
   * <h2>通过主键批量启用/禁用数据</h2>
   * date 2020-12-14 16:13
   *
   * @param ids 主键集合
   * @return java.lang.Integer 受影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer ableByIds(Set<Long> ids, int able);

  /**
   * <h2>统计指定条件下命中的数据行数</h2>
   * date 2020-12-14 16:13
   *
   * @param scopes 查询条件集合
   * @return java.lang.Long 总行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Long countByCondition(@Param("scopes") LinkedHashSet<? extends ScopeEntity> scopes);

  /**
   * <h2>通过主键删除数据</h2>
   * date 2020-12-14 16:13
   *
   * @param id 主键
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer deleteById(@Param("id") Long id);

  /**
   * <h2>通过主键批量删除数据</h2>
   * date 2020-12-14 16:13
   *
   * @param ids 主键集合
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer deleteByIds(@Param("ids") Set<Long> ids);

  /**
   * <h2>新增数据</h2>
   * date 2020-12-14 16:13
   *
   * @param entity 实例对象
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer insert(@Param("entity") EmailAttachmentEntity entity);

  /**
   * <h2>新增数据（批量新增）</h2>
   * date 2020-12-14 16:13
   *
   * @param entities 实例对象
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer insertByBatch(@Param("entities") List<EmailAttachmentEntity> entities);

  /**
   * <h2>新增或者替换数据</h2>
   * date 2020-12-14 16:13
   *
   * @param entity 实例对象
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer insertOrReplace(@Param("entity") EmailAttachmentEntity entity);

  /**
   * <h2>新增或者替换数据（批量新增或者替换）</h2>
   * date 2020-12-14 16:13
   *
   * @param entities 实例对象
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer insertOrReplaceByBatch(@Param("entities") List<EmailAttachmentEntity> entities);

  /**
   * <h2>在指定的条件下查询数据</h2>
   * date 2020-12-14 16:13
   *
   * @param scopes 查询条件
   * @param sorts 排序条件
   * @param offset 查询起始位置
   * @param limit 查询条数
   * @return 对象列表
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  List<EmailAttachmentEntity> queryByCondition(
      @Param("scopes") LinkedHashSet<? extends ScopeEntity> scopes,
      @Param("sorts") LinkedHashSet<? extends SortEntity> sorts,
      @Param("offset") Long offset, @Param("limit") Long limit);

  /**
   * <h2>根据主键进行查询</h2>
   * date 2020-12-14 16:13
   *
   * @param id 主键
   * @return EmailAttachmentEntity
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  EmailAttachmentEntity queryById(@Param("id") Long id);


  /**
   * <h2>通过主键更新数据</h2>
   * date 2020-12-14 16:13
   *
   * @param entity 实例对象
   * @return java.lang.Integer 影响行数
   * @author Automatic[www.dingpengwei@foxmail.com]
   * @since 0.0.1-SNAPSHOT
   */
  Integer updateById(@Param("entity") EmailAttachmentEntity entity);
}
