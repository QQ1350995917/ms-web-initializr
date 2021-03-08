package pwd.initializr.search.api.robot;

import io.swagger.annotations.Api;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.initializr.common.web.api.robot.RobotController;
import pwd.initializr.common.web.api.vo.Meta;
import pwd.initializr.common.web.business.bo.PageableQueryResult;
import pwd.initializr.search.api.robot.vo.DocumentVO;
import pwd.initializr.search.api.robot.vo.SearchInputVo;
import pwd.initializr.search.business.DocumentService;
import pwd.initializr.search.business.bo.DocumentBO;
import pwd.initializr.search.business.bo.SearchInputBO;
import pwd.initializr.search.rpc.RPCDocument;
import scala.MatchError;

/**
 * pwd.initializr.search.api.robot@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-05-13 11:34
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Api(
    tags = "信息写入与搜索",
    value = "documentApi",
    description = "信息写入与搜索API"
)
@RestController(value = "documentApi")
@RequestMapping(value = "/api/robot/document")
public class DocumentController extends RobotController implements DocumentApi {

  @Resource
  private DocumentService documentService;

  @Override
  public void replace(@Valid @NotNull(message = "参数不能为空") String indexName,
      @Valid @NotNull(message = "参数不能为空") List<DocumentVO> input) {
    LinkedList<DocumentBO> documentBOS = new LinkedList<>();
    Optional.ofNullable(input).orElseGet(LinkedList::new).forEach(documentVO -> {
      DocumentBO documentBO = new DocumentBO();
      BeanUtils.copyProperties(documentVO, documentBO);
      documentBOS.add(documentBO);
    });
    int replace = documentService.batchReplace(indexName, documentBOS);
    outputData(String.valueOf(replace));
  }

  @Override
  public void delete(@Valid @NotNull(message = "参数不能为空") String indexName,
      @Valid @NotNull(message = "参数不能为空") List<String> input) {
    int delete = documentService.batchDelete(indexName, input);
    outputData(String.valueOf(delete));
  }

  @Override
  public void search(@Valid @NotNull(message = "参数不能为空") SearchInputVo input) {
    SearchInputBO searchInputBO = new SearchInputBO();
    BeanUtils.copyProperties(input, searchInputBO);
    outputData(search0(documentService.search(searchInputBO)));
  }

  private PageableQueryResult<DocumentVO> search0(PageableQueryResult<? extends RPCDocument> search) {
    PageableQueryResult<DocumentVO> result = new PageableQueryResult<>();
    if (search != null) {
      result.setSize(search.getSize());
      result.setIndex(search.getIndex());
      result.setTotal(search.getTotal());
      List<DocumentVO> elements = new LinkedList<>();
      search.getElements().forEach(articleBO -> {
        DocumentVO searchOutputVO = new DocumentVO();
        BeanUtils.copyProperties(articleBO, searchOutputVO);
        elements.add(searchOutputVO);
      });
      result.setElements(elements);
    }
    return result;
  }
}
