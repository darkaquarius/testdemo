package demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huishen on 17/1/19.
 */
public class CharacterTest {

    @Test
    public void test(){
        String str1 = "hello";
        byte[] bytes1 = str1.getBytes();
        if(bytes1.length >= 3){
            System.out.println("bigger than 3 characters");
        }

        String str2 = "你好";
        byte[] bytes2 = str2.getBytes();
        if(bytes2.length >= 3){
            System.out.println("bigger than 3 characters");
        }
    }

    /**
     *
     */
    @Test
    public void testCharacterType() {
        // String word = ".?,，：q下ゲ�";
        // String word = "����";
        // String word = "qq����";
        // String word = "○○○○ゲーム";
        // String word = "《旅かえる》";
        // String word = "qq";
        String word = "微信";

        for (char ch : word.toCharArray()) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
            System.out.println(ch + ": " + ub);
        }
    }

    @Test
    public void testIsUnicodeIdentifierPart() {
        char[] chs = new char[] {
            'ر',
            '0',
            '䡤',
            'l',
            '微',
            ':',
            '�',
            '。',
            ' '
        };
        for (char ch : chs) {
            boolean unicodeIdentifierPart = Character.isUnicodeIdentifierPart(ch);
            System.out.println(ch + ": " + unicodeIdentifierPart);
        }
    }

    @Test
    public void cleanInvalidWord() {
        List<String> words = Arrays.asList(
            "لعبة صالون تلوين الاظافر",
            "qq",
            "微信",
            "《旅かえる》",
            "○○○○ゲーム",
            ".?,，：q下ゲ�",
            "qq����",
            "2345���䡤",
            "，qq",
            "[[q]]",
            "[[]]",
            "qq��",
            "q'q",
            "\'tm\'"
        );
        words.forEach(word -> {
            String validWord = filterInvaldChar(word);
            System.out.println(word + ": " + validWord);
        });
    }

    public static String filterInvaldChar(String raw) {
        char[] chars = raw.toCharArray();
        List<Character> ret = new ArrayList<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            boolean b = Character.isUnicodeIdentifierPart(chars[i]);
            if (b) {
                ret.add(chars[i]);
            } else {
                Character.UnicodeBlock ub = Character.UnicodeBlock.of(chars[i]);
                if (!ret.isEmpty() && (i != chars.length -1) && !Character.UnicodeBlock.SPECIALS.equals(ub)) {
                    ret.add(chars[i]);
                }
            }
        }


        // 去掉最后面的符号
        for (int i = ret.size() - 1; i >= 0; i--) {
            if (!Character.isUnicodeIdentifierPart(ret.get(i))) {
                ret.remove(i);
            } else {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        ret.forEach(sb::append);
        return sb.toString();
    }

    private boolean isValidWord(String word) {
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((0 == i || (chars.length -1 == i)) && !Character.isUnicodeIdentifierPart(chars[i])) {
                return false;
            }
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(chars[i]);
            if (Character.UnicodeBlock.SPECIALS.equals(ub)) {
                return false;
            }
        }
        return true;
    }

}
