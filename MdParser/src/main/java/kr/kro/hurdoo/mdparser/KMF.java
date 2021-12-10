package kr.kro.hurdoo.mdparser;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class KMF {

    public static void parse(File file) throws Exception {

        String content;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String s;
            StringBuilder builder = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                builder.append(s).append("\n");
            }
            content = builder.toString();
        }

        content = content
                .replaceAll("\\[([^\\[\\]]+)]\\(BUG\\)","<a href='https://bugs.mojang.com/browse/$1' style='font-size:10px;'>#</a>")
                .replaceAll("\\[([^\\[\\]]+)]\\((http(s?)://([^)]+))\\)","<a href='$2' target='_blank'>$1</a>")

                .replaceAll("`([^`\\n]*)\\(([^`\\n]*)\\)([^`\\n]*)`","$1&#40;$2&#41;$3")
                .replaceAll("\\(([^()]*)\\(([^()]*)\\)([^()]*)\\)","$1&#40;$2&#41;$3")
                .replaceAll("\\n([^`\\n]*)\\(([^`\\n]*)\\)([^`\\n]*)\\n-","$1&#40;$2&#41;$3\n-")
                .replaceAll("\\[([^\\[\\]]+)]\\(([^()]*)\\)","[$1]&#40;$2&#41;")

                .replaceAll("[*]{2}([^*]+)[*]{2}","<b>$1</b>")
                .replaceAll("[_]{2}([^*]+)[_]{2}","<u>$1</u>")
                .replaceAll("[~]{2}([^*]+)[~]{2}","<del>$1</del>")
                .replaceAll("_([^*]+)_","<i>$1</i>")
                .replaceAll("\\*([^*\\n]+)\\*","<i>$1</i>")

                .replaceAll("`([^`]+)`","<code style='color:black;'>$1</code>")
                .replaceAll("\\(([^()]+)\\)","<span style='font-size: 12px;'>$1</span>")
                .replaceAll("\\* ([^\\n]+)\\n","<p>$1</p>\n")
                .replaceAll("([^-\\n]+)\\n-","<p></p><p style=\"font-size:20px;\"><strong><em>『 $1 』</em></strong></p>")
                .replaceAll("\\n([^\\n]+)\\n=","<p></p><p></p><p style='font-size:36px;'><b>$1</b></p><p></p><p></p>")

                .replaceAll("&#40;","(").replaceAll("&#41;",")")
                .replaceAll("\\[([^\\[\\]]+)]\\(([^()]+)\\)","<ins title='$2'>$1</ins>")
                .replaceAll("%20"," ")

        ;

        /*// table
        String[] split1 = content
                .replaceAll("(\\n\\|([^\\n]+)\\n(\\|:-:)+\\|([^*]+)\\|\\n\\n)",
                        "__TABLE_DIVIDE_START__$1__TABLE_DIVIDE_END__\n\n")
                .split("__TABLE_DIVIDE_START__");

        StringBuilder str = new StringBuilder();

        for(int i=0;i<split1.length;i++) {
            if(i == 0) {
                str.append(split1[i]);
                continue;
            }
            String[] split2 = split1[i].split("__TABLE_DIVIDE_END__");
            str.append(parseTable(split2[0]))
                    .append(split2[1]);
        }

        System.out.println(str);*/

        System.out.println(parseTable(content));
    }

    private static String parseTable(String str) {
        List<Extension> extensions = List.of(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(str);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().extensions(extensions).build();
        return htmlRenderer.render(document);
    }

    /*public static void parse(File file) {
        HtmlRenderer renderer = new HtmlRenderer.Builder()
                .nodeRendererFactory(context -> new NodeRenderer() {
                    @Override
                    public Set<Class<? extends Node>> getNodeTypes() {
                        return Set.of(Heading.class, FencedCodeBlock.class);
                    }

                    @Override
                    public void render(Node node) {
                        HtmlWriter writer = context.getWriter();

                        if(node instanceof Heading heading) {
                            Text text = (Text) heading.getFirstChild();

                            switch(heading.getLevel()) {
                                case 1:
                                    writer.raw("<h1>" + text.getLiteral() + "</h1>");
                                    // @TODO: image
                                    break;
                                case 2:
                                    writer.raw("<strong><em><span style=\"font-size:20px;\">&ldquo; ");
                                    writer.raw((text.getLiteral()));
                                    writer.raw(" &rdquo;</span></em>");
                                    break;
                                default:
                                    System.out.println("Cannot resolve heading level: " + heading.getLevel());
                            }
                            return;
                        }

                        if(node instanceof Paragraph paragraph) {
                            Text text = (Text) paragraph.getFirstChild();
                            writer.raw("");
                        }

                    }
                }).build();
    }*/
}
