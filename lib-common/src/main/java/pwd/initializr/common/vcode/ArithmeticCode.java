package pwd.initializr.common.vcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * pwd.initializr.common.vcode@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-09-18 19:18
 *
 * @author DingPengwei[dingpengwei@eversec.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class ArithmeticCode implements VCodeHelper {

  @Override
  public String productMessage() {
    return null;
  }

  @Override
  public String productMessage(Integer length) {
    //定义验证码的宽度和高度
    int width = 100, height = 30;
    //在内存中创建图片
    BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    //创建图片上下文
    Graphics2D g = image.createGraphics();
    //产生随机对象，用于算术表达式的数字
    Random random = new Random();
    //设置背景
    g.setColor(getRandomColor(240,250));
    //设置字体
    g.setFont(new Font("微软雅黑",Font.PLAIN,22));
    //开始绘制
    g.fillRect(0,0,width,height);

    //干扰线的绘制，绘制线条到图片中
    g.setColor(getRandomColor(180,230));
    for (int i = 0; i < 100; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int x1 = random.nextInt(60);
      int y1 = random.nextInt(60);
      g.drawLine(x,y,x1,y1);
    }

    //对算术验证码表达式的拼接
    int num1 = (int) (Math.random()*10+1);
    int num2 = (int) (Math.random()*10+1);
    int operator = random.nextInt(3);
    String operatorStr = null;
    int result = 0;
    switch (operator) {
      case 0:
        operatorStr = "+";
        result = num1+num2;
        break;
      case 1:
        operatorStr = "-";
        result = num1-num2;
        break;
      case 2:
        operatorStr = "*";
        result = num1*num2;
        break;

    }

    //图片显示的算术文字
    String calc = num1 + " "+ operatorStr +" "+num2+" = ?";
    //设置随机颜色
    g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
    //绘制表达式
    g.drawString(calc,5,25);
    //结束绘制
    g.dispose();

    //输出图片到页面

    return  String.valueOf(result);
  }

  @Override
  public BufferedImage productImage() {
    return null;
  }

  @Override
  public BufferedImage productImage(String codeMessage) {
    return null;
  }

  public static Color getRandomColor(int fc,int bc){
    //利用随机数
    Random random = new Random();
    if(fc>255){
      fc = 255;
    }
    if(bc>255){
      bc = 255;
    }
    int r = fc+random.nextInt(bc-fc);
    int g = fc+random.nextInt(bc-fc);
    int b = fc+random.nextInt(bc-fc);

    return  new Color(r,g,b);

  }

}
