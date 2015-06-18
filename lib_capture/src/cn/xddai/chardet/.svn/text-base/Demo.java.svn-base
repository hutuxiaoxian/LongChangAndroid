
package cn.xddai.chardet;

import java.io.IOException;
import java.net.URL;

/**
 *
 * @author xddai
 */
public class Demo
{
    public static void main(String[] args)throws IOException
    {
        CharsetDetector charDect = new CharsetDetector();
        URL url = new URL("http://www.qq.com/");
        String[] probableSet = charDect.detectChineseCharset(url.openStream());
        for (String charset : probableSet)
        {
            System.out.println(charset);
        }
    }
}
