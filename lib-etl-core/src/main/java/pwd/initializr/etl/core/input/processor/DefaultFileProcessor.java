package pwd.initializr.etl.core.input.processor;

import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import pwd.initializr.etl.core.input.over.Over;
import pwd.initializr.etl.core.input.over.OverFactory;

/**
 * pwd.initializr.etl.core.input.processor@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-02-27 10:11
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public abstract class DefaultFileProcessor implements FileProcessor {

  private BlockingQueue<Map> blockingQueue;
  private String rowDelimiter;
  private String columnDelimiter;
  private String completeSuffix;
  private String suffix;
  private JSONObject overConfig;

  public DefaultFileProcessor() {

  }

  public DefaultFileProcessor(JSONObject config) {
    this.setConfig(config);
  }

  @Override
  public DefaultFileProcessor setConfig(JSONObject config) {
    this.rowDelimiter = config.getString("rowDelimiter");
    this.columnDelimiter = config.getString("columnDelimiter");
    this.overConfig = config.getJSONObject("over");
    this.suffix = config.getString("suffix");
    this.completeSuffix = config.getString("completeSuffix");
    this.overConfig.put("suffix", this.suffix);
    this.overConfig.put("completeSuffix", this.completeSuffix);
    return this;
  }

  public BlockingQueue<Map> getBlockingQueue() {
    return this.blockingQueue;
  }

  public void setBlockingQueue(BlockingQueue<Map> blockingQueue) {
    this.blockingQueue = blockingQueue;
  }

  @Override
  public void process(Object object) {
    String filePathFaker = object.toString();
    if (filePathFaker != null) {
      String ok = filePathFaker + "." + this.completeSuffix;
      String data = filePathFaker + "." + this.suffix;
      String oking = ok + ".ing";
      String dataing = data + ".ing";
      new File(ok).renameTo(new File(oking));
      new File(data).renameTo(new File(dataing));
      this.onProcess(data);
      this.onOver(filePathFaker);
    }
  }

  @Override
  public Over getOver() {
    String strategy = overConfig.getString("strategy");
    return OverFactory.getInstance(strategy, overConfig);
  }

  @Override
  public String getRowDelimiter() {
    return rowDelimiter;
  }

  @Override
  public String getColumnDelimiter() {
    return columnDelimiter;
  }

  public abstract void onProcess(String filePath);

  public void onOver(String filePathFaker) {
    this.getOver().over(filePathFaker);
  }




}
