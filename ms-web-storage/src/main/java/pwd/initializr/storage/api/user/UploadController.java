package pwd.initializr.storage.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pwd.initializr.common.web.api.ApiController;
import pwd.initializr.storage.business.StorageService;
import pwd.initializr.storage.business.StorageServiceImpl;
import pwd.initializr.storage.business.bo.Storage;

/**
 * pwd.initializr.storage.api.user@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-09-25 17:17
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
@Api(
    tags = "文件上传",
    value = "文件上传Api",
    description = "文件上传API"
)
@Controller(value = "uploadApi")
@RequestMapping(value = "/api/user/upload")
public class UploadController extends ApiController implements UploadApi {

  private static String UPLOADED_FOLDER = "e://temp//";

  @Autowired
  private StorageServiceImpl storageService;

  @ApiOperation(value = "文件上传页面")
  @GetMapping(value = {""})
  public String upload(Model model) {
    model.addAttribute("message", "");
    return "upload";
  }


  @ApiOperation(value = "文件上传")
  @PostMapping(value = {""})
  public String upload(MultipartFile file, Model model) {
    String name = file.getOriginalFilename();
    String suffix = name.substring(name.lastIndexOf("."), name.length());
    try {
      InputStream inputStream = file.getInputStream();
      Storage storage = storageService.uploadFile(inputStream, suffix);
      model.addAttribute("message", "单文件上传[" + name + "]成功");
    } catch (IOException e) {
      e.printStackTrace();
      model.addAttribute("message", "单文件上传[" + name + "]失败");
    } finally {
      return "upload";
    }
  }

  @PostMapping("/batch")
  public String handleFileUpload(HttpServletRequest request, Model model) {
    StringBuffer resultMessage = new StringBuffer();
    List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
    MultipartFile file;
    for (int i = 0; i < files.size(); ++i) {
      file = files.get(i);
      if (!file.isEmpty()) {
        String name = null;
        try {
          byte[] bytes = file.getBytes();
          name = file.getOriginalFilename();
          Path path = Paths.get(UPLOADED_FOLDER + name);
          Files.write(path, bytes);
          resultMessage.append("第 " + i + " 个文件[" + name + "]上传成功");
        } catch (Exception e) {
          resultMessage.append("第 " + i + " 个文件[" + name + "]上传失败");
          e.printStackTrace();
        }
      } else {
        resultMessage.append("第 " + i + " 个文件为空");
      }
    }
    model.addAttribute("message", resultMessage.toString());
    return "upload";
  }

  @Override
  public void upload(HttpServletRequest request) {

  }

  @Override
  public void upload(MultipartFile file) {

  }
}
