package com.kingschan.blog.test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPIndexAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import java.util.List;

/**
 * Created by kingschan on 2017/2/23.
 * lucene segment Test
 */
public class LuceneTest {

    public static void main(String[] args) throws Exception{
        String text="我们做软件开发的，大部分人都离不开跟数据库打交道，特别是erp开发的，跟数据库打交道更是频繁，存储过程动不动就是上千行，如果数据量大，人员流动大，那么我们还能保证下一段时间系统还能流畅的运行吗？我们还能保证下一个人能看懂我们的存储过程吗？那么我结合公司平时的培训和平时个人工作经验和大家分享一下，希望对大家有帮助。";
      /*  System.out.println(HanLP.segment(text));
        //标准分词
        List<Term> termList = StandardTokenizer.segment(text);
        System.out.println(termList);
        //NLP分词
        List<Term> termLis = NLPTokenizer.segment(text);
        System.out.println(termLis);*/
        //索引分词
       /* List<Term> termList1 = IndexTokenizer.segment(text);
        int index=0;
        for (Term term : termList1)
        {

            System.out.println(index+":"+term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
            index++;
        }
        //关键字提取
        List<String> keywordList = HanLP.extractKeyword(text, 5);
        System.out.println(keywordList);
        //自动摘要
        List<String> sentenceList = HanLP.extractSummary(text, 5);
        System.out.println(sentenceList);*/
        /*
        Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        System.out.println("N-最短分词：" + nShortSegment.seg(text) + "\n最短路分词：" + shortestSegment.seg(text));*/

        for (int i = 0; i < text.length(); ++i)
        {
            System.out.print(text.charAt(i) + "" + i + " ");
        }
        System.out.println();
        Analyzer analyzer1 = new HanLPIndexAnalyzer();
        TokenStream tokenStream1 = analyzer1.tokenStream("field", text);
        tokenStream1.reset();
        int _index=1;
        while (tokenStream1.incrementToken())
        {
            CharTermAttribute attribute = tokenStream1.getAttribute(CharTermAttribute.class);
            // 偏移量
            OffsetAttribute offsetAtt = tokenStream1.getAttribute(OffsetAttribute.class);
            // 距离
            PositionIncrementAttribute positionAttr = tokenStream1.getAttribute(PositionIncrementAttribute.class);
            // 词性
            TypeAttribute typeAttr = tokenStream1.getAttribute(TypeAttribute.class);
            //if (offsetAtt.endOffset()-offsetAtt.startOffset()==1)continue;
            /*System.out.print(_index + ":");
            System.out.println(attribute);
            _index++;*/
            System.out.printf("[%d:%d %d] %s/%s\n", offsetAtt.startOffset(), offsetAtt.endOffset(), positionAttr.getPositionIncrement(), attribute, typeAttr.type());
        }


       /* System.out.println("------------------mmseg4j--------------------------------------------");
        Analyzer analyzer = new MaxWordAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("text", text);
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        PositionIncrementAttribute positionIncrementAttribute = tokenStream.addAttribute(PositionIncrementAttribute.class);
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        TypeAttribute typeAttribute = tokenStream.addAttribute(TypeAttribute.class);

        tokenStream.reset();
        int position = 0;
        while (tokenStream.incrementToken()) {
            int increment = positionIncrementAttribute.getPositionIncrement();
            if(increment > 0) {
                position = position + increment;
                System.out.print(position + ":");
            }
            int startOffset = offsetAttribute.startOffset();
            int endOffset = offsetAttribute.endOffset();
            String term = charTermAttribute.toString();
            System.out.println(term);
           // System.out.println("[" + term + "]" + ":(" + startOffset + "-->" + endOffset + "):" + typeAttribute.type());
        }*/
    }
}
