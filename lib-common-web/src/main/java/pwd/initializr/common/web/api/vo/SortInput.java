package pwd.initializr.common.web.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * pwd.initializr.common.web.api.vo@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2020-07-27 16:31
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class SortInput {

    @ApiModelProperty(name = "key", value = "排序字段", required = false, example = "0")
    private String key;
    @ApiModelProperty(name = "value", value = "[desc|asc]", required = false, example = "0")
    private String value;

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SortInput)) {
            return false;
        }

        SortInput sortInput = (SortInput) obj;
        if (sortInput.getKey() == null) {
            return false;
        }
        if (!sortInput.getKey().equals(key)) {
            return false;
        }

        return true;
    }

    public String getKey() {
        return StringUtils.isBlank(key) ? "id" : key;
    }

    public String getValue() {
        return StringUtils.isBlank(value) ? "desc" : value;
    }
}
