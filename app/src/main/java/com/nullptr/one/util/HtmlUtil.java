package com.nullptr.one.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @AUTHOR nullptr
 * @DATE 创建时间: 2018/7/19
 * @DESCRIPTION
 */
public class HtmlUtil {

    /**
     * 用Jsoup解决Html图片适应屏幕的问题
     * @param htmltext html文本
     * @return 解决后文本
     */
    public static String getNewContent(String htmltext){

        Document doc=Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        return doc.toString();
    }

}
