package com.nullptr.one.util;

import android.content.res.XmlResourceParser;
import com.nullptr.one.MyApplication;
import com.nullptr.one.R;
import com.nullptr.one.bean.URLData;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/5/10
 * @DESCRIPTION 用于从url.xml中获取Url
 */
public class URLManager {

    public static URLData getURL(String urlKey) {
        //XmlResourceParser：一个动态解析Xml的类
        //生成XmlResourceParser类
        XmlResourceParser xmlResourceParser = MyApplication.getContext().getResources()
                .getXml(R.xml.url);
        int eventCode;
        try {
            eventCode = xmlResourceParser.getEventType();
            while (eventCode != XmlPullParser.END_DOCUMENT) {
                //不读完XML时
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (xmlResourceParser.getName().equals("Node")) {
                            //如果是Url
                            String key = xmlResourceParser.getAttributeValue(null, "Key");   //获取key
                            if (key.trim().equals(urlKey)) {
                                //找到对应的Url时
                                URLData urlData = new URLData();
                                urlData.setKey(urlKey);
                                int expires = Integer
                                        .parseInt(xmlResourceParser
                                                .getAttributeValue(null, "Expires"));
                                urlData.setExpires(expires);
                                urlData.setNetType(
                                        xmlResourceParser.getAttributeValue(null, "NetType"));
                                urlData.setUrl(xmlResourceParser.getAttributeValue(null, "Url"));
                                return urlData;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventCode = xmlResourceParser.next();   //转到下一个
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //未找到，return null
        return null;
    }
}
