package pwd.initializr.storage.api.user;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pwd.initializr.storage.api.robot.vo.UploadInput;

/**
 * pwd.initializr.storage.api.user@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-09-25 17:03
 *
 * @author DingPengwei[dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public interface UploadApi {


  void upload(HttpServletRequest request);

  void upload(MultipartFile file);

}
