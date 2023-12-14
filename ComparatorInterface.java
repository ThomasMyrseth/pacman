package packman;

import java.util.Comparator;

public class ComparatorInterface implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        String[] words = o1.split("\s");
        String number1String = words[words.length-1];
        int number1 = Integer.parseInt(number1String);
        
        words = o2.split("\s");
        String number2String = words[words.length-1];
        int number2 = Integer.parseInt(number2String);

        return -(number1-number2);
    }
}
