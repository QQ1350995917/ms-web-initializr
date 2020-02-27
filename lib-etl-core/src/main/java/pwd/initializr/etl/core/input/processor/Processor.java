package pwd.initializr.etl.core.input.processor;

import com.alibaba.fastjson.JSONObject;
import java.util.concurrent.BlockingQueue;
import pwd.initializr.etl.core.input.over.Over;

/**
 * pwd.initializr.etl.core.input.processor@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-02-27 20:37
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public interface Processor {

  void setConfig(JSONObject config);

  void process(Object object);

  void setBlockingQueue(BlockingQueue blockingQueue);

  BlockingQueue getBlockingQueue();

  void setOver();

  Over getOver();

}
