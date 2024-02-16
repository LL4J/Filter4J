package filter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFilter {
    private static final String[] script;
    private static final Tokenizer tokenizer;

    static {
        try {
            List<String> scriptList = new ArrayList<>();
            Scanner sc = new Scanner(new File("judge.model"));
            while (sc.hasNextLine()) {
                scriptList.add(sc.nextLine());
            }
            script = scriptList.toArray(new String[0]);
            tokenizer = Tokenizer.loadFromFile("tokenize.model");
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public TextFilter() {
        throw new UnsupportedOperationException("This is a static class");
    }

    public static boolean isIllegal(String text) {
        return MinRt.doAi(tokenizer.tokenize(text), script) == 1;
    }
}
