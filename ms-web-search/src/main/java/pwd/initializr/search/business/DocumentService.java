package pwd.initializr.search.business;

import java.util.List;
import pwd.initializr.common.web.business.bo.PageableQueryResult;
import pwd.initializr.search.business.bo.DocumentBO;
import pwd.initializr.search.business.bo.SearchInputBO;
import pwd.initializr.search.rpc.RPCSearchOutput;

/**
 * pwd.initializr.search.business@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-06-14 21:24
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public interface DocumentService {

  int batchReplace(String indexName, List<DocumentBO> documentBOS);

  int batchDelete(String indexName, List<String> ids);

  PageableQueryResult<RPCSearchOutput> query(String sql);

  PageableQueryResult<RPCSearchOutput> search(SearchInputBO searchInputBO);

}
