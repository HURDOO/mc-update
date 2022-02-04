package kr.kro.hurdoo.mdparser;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        KMF.parse(new File("1.18.20.23/1.18.20.23.md"));
    }

    public static String parseTable(String str) {
        List<Extension> extensions = List.of(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(str);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(extensions).build();
        return htmlRenderer.render(document);
    }
}
