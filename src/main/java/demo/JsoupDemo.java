package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.List;

/**
 * Created by huishen on 17/6/23.
 *
 */

public class JsoupDemo {

    @Test
    public void test1() {
        String content = "<div class=\"ProfileHeader-detailItem\"><span class=\"ProfileHeader-detailLabel\">居住地</span><div class=\"ProfileHeader-detailValue\"><span><!-- react-text: 2217 -->现居<!-- /react-text --><!-- react-text: 2218 -->澳门<!-- /react-text --></span><span><!-- react-text: 2220 -->，曾在<!-- /react-text --><!-- react-text: 2221 -->重庆、珠海<!-- /react-text --><!-- react-text: 2222 -->住过<!-- /react-text --></span></div></div>";

        Document doc = Jsoup.parse(content);

        Elements elements = doc.getElementsByClass("ProfileHeader-detailItem");

        for (Element element : elements) {
            List<Node> nodes = element.childNodes();
            Node node = nodes.get(0);
            TextNode node1 = (TextNode) node.childNode(0);
            String wholeText = node1.getWholeText();
            System.out.println(wholeText);
        }

    }

}
