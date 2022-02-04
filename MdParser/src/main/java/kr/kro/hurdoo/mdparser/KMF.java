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
                .replaceAll("\\[([^\\[\\]]+)]\\(wiki\\/([^)]+)\\)","[$1](https://minecraft.fandom.com/Bedrock_Edition_$2)")
                .replaceAll("\\[([^\\[\\]]+)]\\((http(s?)://([^)]+))\\)","<a href='$2' target='_blank'>$1</a>")

                .replaceAll("`([^`\\n]*)\\(([^`\\n]*)\\)([^`\\n]*)`","$1&#40;$2&#41;$3")
                .replaceAll("\\(([^()]*)\\(([^()]*)\\)([^()]*)\\)","$1&#40;$2&#41;$3")
                .replaceAll("\\n([^`\\n]*)\\(([^`\\n]*)\\)([^`\\n]*)\\n-","$1&#40;$2&#41;$3\n-")
                .replaceAll("\\[([^\\[\\]]+)]\\(([^()]*)\\)","[$1]&#40;$2&#41;")

                .replaceAll("[*]{2}([^*]+)[*]{2}","<b>$1</b>")
                .replaceAll("[_]{2}([^*]+)[_]{2}","<u>$1</u>")
                .replaceAll("[~]{2}([^*]+)[~]{2}","<del>$1</del>")
                .replaceAll("_([^*]+)_","<i>$1</i>")
                .replaceAll("\\*([^ ][^*\\n]+)\\*","<i>$1</i>")

                .replaceAll("`([^`]+)`","<code style='color:black;'>$1</code>")
                .replaceAll("\\(([^()]+)\\)","<span style='font-size: 12px;'>$1</span>")
                .replaceAll("\\* ([^\\n]+)\\n","<p>$1</p>\n")
                .replaceAll("([^-\\n]+)\\n-","<p></p><p style=\"font-size:20px;\"><strong><em>『 $1 』</em></strong></p>")
                .replaceAll("\\n([^\\n]+)\\n=","<p></p><hr><p style='font-size:36px;'><b>$1</b></p><p></p><p></p>")

                .replaceAll("\\n#([^\\n#]+)\\n","\n<p style=\"text-align:center;\"><strong><span style=\"font-size:22px;\">▶ $1 ◀</span></strong></p>\n")
                .replaceAll("\\n##([^\\n#]+)\\n","\n<p style=\"text-align:center;\"><strong>▶ $1 ◀</strong></p>\n")
                .replaceAll("\\n###([^\\n#]+)\\n","\n<p style=\"text-align:center;\">[ $1 ]</p>\n")

                .replaceAll("&#40;","(").replaceAll("&#41;",")")
                .replaceAll("\\[([^\\[\\]]+)]\\(([^()]+)\\)","<ins title='$2'>$1</ins>")
                .replaceAll("%20"," ")

        ;

        System.out.println(INTRO);
        System.out.println(Main.parseTable(content));
        System.out.println(OUTRO);
    }

    private static final String INTRO = """
            <p style="text-align: center;">
            \t<br>
            </p>

            <p style="text-align:center;"><strong><span style="font-size:22px;">▶ 베드락 에디션 1.18<span style="color:rgb(226,80,65);">.버전</span><span style="color:rgb(44,130,201);">.버전</span> ◀</span></strong></p>

            <p style="text-align:center;"><strong>한줄요약</strong></p>

            <p style="text-align: center;">
            \t<br>
            </p>
            
            <p style="text-align: center;">
            \t<br>
            </p>
            """;

    private static final String OUTRO = """
            <p style="text-align:center;"><strong><span style="font-size:22px;">&lt; 베드락 에디션 베타 1.18<span style="color:rgb(226,80,65);">.10</span><span style="color:rgb(44,130,201);">.21</span> &gt;</span></strong></p>
                        
            <p style="text-align:center;">[ <a href="#" target="_blank" title="">공식 변경 로그</a> | <a href="https://minecraft.fandom.com/wiki/Bedrock_Edition_beta_버전" target="_blank" title="">마인크래프트 위키</a> ]</p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
                        
            <p style="text-align:center;"> <a href="https://www.koreaminecraft.net/?act=&vid=&mid=update&category=&search_target=tag&search_keyword=%EC%95%BC%EC%83%9D+%EC%97%85%EB%8D%B0%EC%9D%B4%ED%8A%B8" target="_blank" title=""><strong>[</strong><strong>야생 업데이트</strong> 전체 보기<strong>]</strong></a>
            	<br><a href="https://www.koreaminecraft.net/?act=&vid=&mid=update&category=&search_target=tag&search_keyword=1.18" target="_blank" title=""><strong>[</strong><strong>1.18</strong> 업데이트 전체 보기<strong>]</strong></a>
            	<br><a href="https://www.koreaminecraft.net/?act=&vid=&mid=update&category=&search_target=tag&search_keyword=1.18.버전" target="_blank" title=""><strong>[</strong><strong>1.18.버전</strong> 베타 전체 보기<strong>]</strong></a></p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
                        
            <p style="text-align:center;"><strong>1.19</strong>는 <strong>야생 업데이트</strong>에요.</p>
                        
            <p style="text-align:center;"><a href="https://www.koreaminecraft.net/index.php?mid=update&category=6427&document_srl=2875840" target="_blank" title=""><strong>[ 마인콘 2021 내용 보러 가기 ]</strong></a></p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
            <hr>
                        
            <p style="text-align:center;">
            	<br>
            </p>
            <hr style="text-align:center;">
                        
            <p style="text-align:center;"><strong>베드락 에디션 베타 테스트에 참여해 보시겠어요?</strong></p>
                        
            <p style="text-align:center;">베타 테스트는 <strong>Android, Windows, Xbox One</strong>에서 참여할 수 있어요.</p>
                        
            <p style="text-align:center;">베드락 에디션의 베타는, 자바 에디션의 스냅샷과 다르게 <strong>게임 전체가 베타 버전</strong>으로 바뀌어요.</p>
                        
            <p style="text-align:center;">베타에서는 <strong>렐름에 참여할 수 없고</strong>, 자신과 같이 <strong>베타 테스트에 참여 중인 플레이어</strong>랑만 멀티플레이를 할 수 있어요.</p>
                        
            <p style="text-align:center;">베타 버전에서 플레이한 월드는 정식 출시 버전에서 즐길 수 없으니, 베타에서 월드를 열기 전에 먼저 <strong>월드 복사</strong>를 해두는 것이 좋아요.</p>
                        
            <p style="text-align:center;">마지막으로, 베타 버전은 불안정해요. 그러니까 베타 아니겠어요?</p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
                        
            <p style="text-align:center;">준비가 되었다면, 아래의 글을 따라 베타 테스트에 참여해보세요.</p>
                        
            <p style="text-align:center;"><a href="https://www.koreaminecraft.net/review/2189651" target="_blank" title=""><strong><span style="font-size:20px;">[ 베드락 에디션 베타 테스트 참여 방법 ]</span></strong></a></p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
            <hr>
                        
            <p style="text-align:center;"><strong>베드락 에디션 프리뷰에 참여해 보시겠어요?<br></strong></p>
                        
            <p style="text-align:center;">프리뷰는 현재 <strong>iOS</strong>에서만 참여할 수 있어요.</p>
                        
            <p style="text-align:center;">베드락 프리뷰는 <strong>정식 출시 버전과 다른 앱</strong>으로 새로 설치되어요.</p>
                        
            <p style="text-align:center;">기존 <strong>설정과 월드 등이 호환되지는 않아요.</strong> 기기에 따라 <strong>일부 기능을 사용하지 못할</strong> 수도 있어요.</p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
                        
            <p style="text-align:center;">준비가 되었다면, 아래의 글을 따라 프리뷰에 참여해보세요.</p>
                        
            <p style="text-align:center;"><a href="https://www.koreaminecraft.net/update/3094908" target="_blank" title=""><strong><span style="font-size:20px;">[ 베드락 에디션 프리뷰 참여 방법 ]</span></strong></a></p>
                        
            <p style="text-align:center;">
            	<br>
            </p>
            """;
}
