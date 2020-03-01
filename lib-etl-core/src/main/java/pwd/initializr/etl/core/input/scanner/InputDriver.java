package pwd.initializr.etl.core.input.scanner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * pwd.initializr.etl.core.input.scanner@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-02-27 10:14
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class InputDriver {

  private BlockingQueue<Map<String, Object>> blockingQueue = new ArrayBlockingQueue<>(128);
  private ExecutorService executorService = Executors.newFixedThreadPool(1);
  private List<Scanner> scanners = new LinkedList<>();

  public InputDriver() {

  }

  public InputDriver(JSONObject config) {
    this.setConfig(config);
  }

  public InputDriver setConfig(JSONObject config) {
    Integer capacity = config.getInteger("capacity");
    this.blockingQueue = new LinkedBlockingQueue<>(capacity);

    JSONArray scanners = config.getJSONArray("scanners");
    Iterator<Object> iterator = scanners.iterator();
    while (iterator.hasNext()) {
      JSONObject scannerConfig = (JSONObject) iterator.next();
      String type = scannerConfig.getString("type");
      Scanner instance = ScannerFactory.getInstance(type);
      instance.setConfig(scannerConfig).setBlockingQueue(this.blockingQueue);
      this.scanners.add(instance);
    }
    return this;
  }

  public BlockingQueue<Map<String, Object>> getBlockingQueue() {
    return blockingQueue;
  }

  public void start() {
    this.executorService = Executors.newFixedThreadPool(scanners.size());
    for (Scanner scanner : scanners) {
      if (scanner.isEnable()) {
        this.executorService.execute(scanner);
      }
    }
  }
}
