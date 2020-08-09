package pwd.initializr.account.api.admin;

import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.initializr.account.api.admin.vo.AdminAccountInput;
import pwd.initializr.account.api.admin.vo.AdminAccountOutput;
import pwd.initializr.account.api.admin.vo.AdminUserInput;
import pwd.initializr.account.api.admin.vo.AdminUserOutput;
import pwd.initializr.account.api.admin.vo.AdminUserQueryInput;
import pwd.initializr.account.api.admin.vo.CreateAdminInput;
import pwd.initializr.account.business.admin.AdminAccountService;
import pwd.initializr.account.business.admin.AdminUserService;
import pwd.initializr.account.business.admin.AdminUserServiceWrap;
import pwd.initializr.account.business.admin.bo.AdminAccountBO;
import pwd.initializr.account.business.admin.bo.AdminUserBO;
import pwd.initializr.common.web.api.vo.Meta;
import pwd.initializr.common.web.api.vo.PageOutput;
import pwd.initializr.common.web.business.bo.PageableQueryResult;
import pwd.initializr.common.web.persistence.entity.EntityAble;

/**
 * pwd.initializr.account.api.admin@ms-web-initializr
 *
 * <h1>控制层接口：管理员信息</h1>
 *
 * date 2019-10-26 8:14
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Api(
    tags = "人员管理",
    value = "adminManageApi",
    description = "[创建管理员/账号，人员/账号列表，人员/账号启/禁用，人员/账号删除]"
)
@RestController(value = "admin")
@RequestMapping(value = "/api/admin/admin")
@Validated
public class AdminController extends pwd.initializr.common.web.api.admin.AdminController implements
    AdminApi {

  @Autowired
  private AdminUserServiceWrap adminUserServiceWrap;
  @Autowired
  private AdminUserService adminUserService;
  @Autowired
  private AdminAccountService adminAccountService;

  @Override
  public void create(@Valid @NotNull(message = "参数不能为空") CreateAdminInput input) {
    AdminUserBO adminUserBO = new AdminUserBO();
    AdminAccountBO adminAccountBO = new AdminAccountBO();
    BeanUtils.copyProperties(input.getUser(), adminUserBO);
    BeanUtils.copyProperties(input.getAccount(), adminAccountBO);
    AdminUserBO userBO = adminUserServiceWrap.insert(adminUserBO, adminAccountBO);
    super.outputData(userBO.getId());
  }

  @Override
  public void delAccount(
      @Valid @NotNull(message = "参数不能为空") @Size(message = "参数不能为空") List<Long> ids) {

  }

  @Override
  public void delUser(
      @Valid @NotNull(message = "参数不能为空") @Size(message = "参数不能为空") List<Long> ids) {
    Integer del = adminUserServiceWrap.deleteByUserId(ids);
    outputData(del);
  }

  @Override
  public void disableAccount(@Valid @NotNull(message = "参数不能为空") List<Long> ids) {

  }

  @Override
  public void disableUser(@Valid @NotNull(message = "参数不能为空") List<Long> ids) {
    Integer able = adminUserServiceWrap.ableByUserId(ids, EntityAble.DISABLE);
    outputData(able);
  }

  @Override
  public void enableAccount(@Valid @NotNull(message = "参数不能为空") List<Long> ids) {

  }

  @Override
  public void enableUser(@Valid @NotNull(message = "参数不能为空") List<Long> ids) {
    Integer able = adminUserServiceWrap.ableByUserId(ids, EntityAble.ENABLE);
    outputData(able);
  }


  @Override
  public void listAccount(@PathVariable("uid") Long userId) {
    AdminAccountBO queryCondition = new AdminAccountBO();
    List<AdminAccountBO> adminAccountBOS = adminAccountService.queryByUserId(userId);
    PageOutput<AdminAccountOutput> result = new PageOutput<>();
    adminAccountBOS.forEach(adminAccountBO -> {
      AdminAccountOutput adminAccountOutput = new AdminAccountOutput();
      BeanUtils.copyProperties(adminAccountBO, adminAccountOutput);
      result.getElements().add(adminAccountOutput);
    });
    outputData(result);
  }

  @Override
  public void listUser(AdminUserQueryInput input) {
    // TODO 查询条件参数没有应用
    AdminUserBO queryCondition = new AdminUserBO();
    PageableQueryResult<AdminUserBO> adminUserBOPageableQueryResult = adminUserService
        .queryAllByCondition(queryCondition, input.getPage().getIndex(), input.getPage().getSize());
    PageOutput<AdminUserOutput> result = new PageOutput<>();
    adminUserBOPageableQueryResult.getElements().forEach(adminUserBO -> {
      AdminUserOutput adminUserOutput = new AdminUserOutput();
      BeanUtils.copyProperties(adminUserBO, adminUserOutput);
      result.getElements().add(adminUserOutput);
    });
    result.setTotal(adminUserBOPageableQueryResult.getTotal());
    result.setIndex(adminUserBOPageableQueryResult.getIndex());
    result.setSize(adminUserBOPageableQueryResult.getSize());
    outputData(result);
  }


  @Override
  public void updateAccount(@PathVariable("id") Long id, @RequestBody AdminAccountInput input) {
    AdminAccountBO adminAccountBO = new AdminAccountBO();
    adminAccountBO.setId(id);
    adminAccountBO.setLoginPwd(input.getLoginPwd());
    Integer update = adminAccountService.update(adminAccountBO);
    outputData(new Meta(),update);
  }


  @Override
  public void updateUser(@PathVariable("uid") Long id, @RequestBody AdminUserInput input) {
    AdminUserBO adminUserBO = new AdminUserBO();
    BeanUtils.copyProperties(input,adminUserBO);
    adminUserBO.setId(id);
    Integer update = adminUserService.update(adminUserBO);
    outputData(new Meta(),update);
  }
}
