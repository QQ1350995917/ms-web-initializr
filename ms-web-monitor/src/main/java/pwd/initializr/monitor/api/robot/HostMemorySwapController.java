package pwd.initializr.monitor.api.robot;

import io.swagger.annotations.Api;
import java.util.LinkedHashSet;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.initializr.common.web.api.vo.PageInput;
import pwd.initializr.common.web.api.vo.PageOutput;
import pwd.initializr.common.web.api.vo.ScopeInput;
import pwd.initializr.common.web.api.vo.SortInput;
import pwd.initializr.common.web.business.bo.PageableQueryResult;
import pwd.initializr.common.web.business.bo.ScopeBO;
import pwd.initializr.common.web.business.bo.SortBO;
import pwd.initializr.monitor.api.robot.vo.HostMemorySwapInput;
import pwd.initializr.monitor.api.robot.vo.HostMemorySwapOutput;
import pwd.initializr.monitor.business.HostMemorySwapService;
import pwd.initializr.monitor.business.bo.HostMemoryMemorySwapBO;

/**
* project-generator-test@ms-web-initializr
*
* <h1>HostSwap控制层接口实现</h1>
*
* date 2020-10-23 11:58
*
* @author Automatic[dingpengwei@foxmail.com]
* @version 0.0.1-SNAPSHOT
* @since 0.0.1-SNAPSHOT
*/
@Api(
  tags = "HostSwap信息结构",
  value = "HostSwapManageApi",
  description = "[列表查询，详情查询，启/禁用，删除，新增，修改]"
)
@RestController(value = "HostSwap")
@RequestMapping(value = "/api/robot/swap")
@Slf4j
public class HostMemorySwapController extends pwd.initializr.common.web.api.admin.AdminController implements
    HostMemorySwapApi {

  @Autowired
  private HostMemorySwapService service;

  @Override
  public void list(String scopes, String sorts, String page) {
    PageInput pageInput = PageInput.parse(page);
    LinkedHashSet<ScopeBO> scopeBOS = ScopeInput.parse(scopes);
    LinkedHashSet<SortBO> sortBOS = SortInput.parse(sorts);
    PageableQueryResult<HostMemoryMemorySwapBO> pageableQueryResult = service
      .queryAllByCondition(scopeBOS, sortBOS, pageInput.getIndex(), pageInput.getSize());
    PageOutput<HostMemorySwapOutput> result = new PageOutput<>();
    pageableQueryResult.getElements().forEach(bo -> {
      HostMemorySwapOutput output = new HostMemorySwapOutput();
      BeanUtils.copyProperties(bo, output);
      result.getElements().add(output);
    });
    result.setTotal(pageableQueryResult.getTotal());
    result.setIndex(pageableQueryResult.getIndex());
    result.setSize(pageableQueryResult.getSize());
    outputData(result);
  }

  @Override
  public void detail(@Valid @NotNull(message = "参数不能为空") Long id) {
    HostMemoryMemorySwapBO bo = service.queryById(id);
    HostMemorySwapOutput output = new HostMemorySwapOutput();
    BeanUtils.copyProperties(bo,output);
    outputData(output);
  }

  @Override
  public void create(@Valid @NotNull(message = "参数不能为空") HostMemorySwapInput input) {
    HostMemoryMemorySwapBO bo = new HostMemoryMemorySwapBO();
    BeanUtils.copyProperties(input,bo);
    service.insert(bo);
    outputData(200,bo.getId());
  }
}
