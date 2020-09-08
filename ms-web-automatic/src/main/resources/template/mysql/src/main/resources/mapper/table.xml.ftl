<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="pwd.initializr.account.persistence.dao.AdminUserDao">

  <resultMap id="AdminUserMap" type="pwd.initializr.account.persistence.entity.AdminUserEntity">
    <result column="id" jdbcType="BIGINT" property="id"/>
    <result column="pin" jdbcType="VARCHAR" property="pin"/>
    <result column="name" jdbcType="VARCHAR" property="name"/>
    <result column="gender" jdbcType="VARCHAR" property="gender"/>
    <result column="emp_no" jdbcType="VARCHAR" property="empNo"/>
    <result column="summary" jdbcType="VARCHAR" property="summary"/>
    <result column="able" jdbcType="INTEGER" property="able"/>
    <result column="del" jdbcType="INTEGER" property="del"/>
    <result column="create_time" jdbcType="TIMESTAMP" property="createTime"/>
    <result column="update_time" jdbcType="TIMESTAMP" property="updateTime"/>
    <result column="version" jdbcType="BIGINT" property="version"/>
  </resultMap>

  <!--查询单个-->
  <select id="queryById" resultMap="AdminUserMap">
    select
    id, pin, name, gender, emp_no, summary, able, del, create_time, update_time, version
    from initializr_account.admin_user
    where id = #{id}
  </select>

  <!--查询指定行数据-->
  <select id="countAllByCondition" resultType="java.lang.Long">
    select count(*) from initializr_account.admin_user
    <include refid="pwd.initializr.common.web.persistence.entity.ScopeEntity.entityQueryScope"></include>
  </select>

  <!--通过实体作为筛选条件查询-->
  <select id="queryAllByCondition" resultMap="AdminUserMap">
    select
    id, pin, name, gender, emp_no, summary, able, del, create_time, update_time
    from initializr_account.admin_user
    <include refid="pwd.initializr.common.web.persistence.entity.ScopeEntity.entityQueryScope"></include>
    <include refid="pwd.initializr.common.web.persistence.entity.SortEntity.entityQuerySort"></include>
    limit #{offset}, #{limit}
  </select>

  <!--新增所有列-->
  <insert id="insert" keyProperty="id" useGeneratedKeys="true">
    insert into initializr_account.admin_user(pin, name, gender, emp_no, summary, able, del, create_time, update_time)
    values (#{pin}, #{name}, #{gender}, #{empNo}, #{summary}, #{able}, #{del}, #{createTime}, #{updateTime})
  </insert>

  <!--通过主键修改数据-->
  <update id="update">
    update initializr_account.admin_user
    <set>
      <if test="pin != null and pin != ''">
        pin = #{pin},
      </if>
      <if test="name != null and name != ''">
        name = #{name},
      </if>
      <if test="gender != null and gender != ''">
        gender = #{gender},
      </if>
      <if test="empNo != null and empNo != ''">
        emp_no = #{empNo},
      </if>
      <if test="summary != null and summary != ''">
        summary = #{summary},
      </if>
      <if test="able != null">
        able = #{able},
      </if>
      <if test="del != null">
        del = #{del},
      </if>
      <if test="createTime != null">
        create_time = #{createTime},
      </if>
      <if test="updateTime != null">
        update_time = #{updateTime},
      </if>
    </set>
    where id = #{id}
  </update>

  <!--通过主键删除-->
  <update id="deleteById">
    update initializr_account.admin_user set del = 1, update_time = now() where id = #{id}
  </update>

  <!--通过主键删除-->
  <update id="deleteByIds">
    update initializr_account.admin_user set del = 1, update_time = now() where id in
    <foreach close=")" collection="ids" index="index" item="id" open="(" separator=",">
    #{id}
    </foreach>
  </update>

  <!--通过主键启禁-->
  <update id="ableById">
    update initializr_account.admin_user set able = #{able}, update_time = now() where id = #{id}
  </update>

  <!--通过主键启禁-->
  <update id="ableByIds">
    update initializr_account.admin_user set able = #{able}, update_time = now() where id in
    <foreach close=")" collection="ids" index="index" item="id" open="(" separator=",">
    #{id}
    </foreach>
  </update>

</mapper>
