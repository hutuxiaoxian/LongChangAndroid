package com.imageco.library;

/**
 * Created by IntelliJ IDEA. User: OYQX Date: 11-12-15 Time: 下午2:10
 */
public class CaptureResult
{
    /**
     * 扫描结果
     */
    private String capresutl="";

    /**
     *
     */
    private static CaptureResult capInstance = new CaptureResult();

    /**
     * 获取扫码结果实例
     *
     * @return 获取扫码结果实例
     */
    public static CaptureResult getCapInstance()
    {
        return capInstance;
    }

    /**
     * 重置实例数据
     */
    public static void resetInstance()
    {
        if (capInstance != null)
        {
            capInstance.setCapresutl("");
        }
    }

    public String getCapresutl()
    {
        return capresutl;
    }

    public void setCapresutl(String capresutl)
    {
        this.capresutl = capresutl;
    }
}
