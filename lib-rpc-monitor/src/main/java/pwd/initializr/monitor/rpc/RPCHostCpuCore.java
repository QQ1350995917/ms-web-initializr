package pwd.initializr.monitor.rpc;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * pwd.initializr.monitor.rpc@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-10-21 15:24
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RPCHostCpuCore implements Serializable {
    @ApiModelProperty(value = "主机名称")
    private String id;
    @ApiModelProperty(value = "核心编号")
    private String index;
    @ApiModelProperty(value = "CPU生产商")
    private String vendor;
    @ApiModelProperty(value = "CPU类别")
    private String model;
    @ApiModelProperty(value = "CPU缓存数量")
    private Long cacheSize;
    @ApiModelProperty(value = "CPU的总量MHz")
    private Integer mhz;
    @ApiModelProperty(value = "总核心数")
    private Integer totalCores;
    @ApiModelProperty(value = "主板总路数")
    private Integer totalSockets;
    @ApiModelProperty(value = "主板每路支持最大核心数")
    private Integer coresPerSocket;
}
