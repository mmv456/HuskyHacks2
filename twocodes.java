
//import java.util.Arrays;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
//import tester.*;

//to hold 
class Utils {
    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    public void listFilesForFolder(final File folder) throws IOException{
        int i = 0;
        for (final File fileEntry : folder.listFiles()) {
            i++;
        }
        String d[][] = new String[i + 1][i + 1];
        String axes[] = new String[i];
        int j = 0;
        for (final File fileEntry1 : folder.listFiles()) { 
            int k = 0;
            axes[j] = fileEntry1.getName();
            for (final File fileEntry2 : folder.listFiles()) {
                String fe1 = readFile(fileEntry1.getPath(), Charset.defaultCharset()).replaceAll("\\s", "");
                String fe2 = readFile(fileEntry2.getPath(), Charset.defaultCharset()).replaceAll("\\s", "");
                d[j][k] = Integer.toString(levenPerc(fe1, fe2));
                k++;
            }
            j++;
        }
        String acc = "ACCURACY ," + conCat(axes, i);
//        for (int z = 0; z <= 1; z++) {
//            d[0][z] = axes[z];
//        }
        for (int y = 0; y < i; y++) {
            acc = acc + axes[y] + "," + conCat(d[y], i);
        }
        PrintWriter out = new PrintWriter("DejaVu.csv");
        out.println(acc);
        out.close();
    }
    
    //concatenates an array of strings of length i
    String conCat(String[] a, int i) {
        String newline = System.getProperty("line.separator");
        String acc = "";
        for (int x = 0; x < i; x++) {
            if (x != i-1){
                acc = acc + a[x] + ",";
            }
            else{
                acc = acc + a[x] + newline;
            }
        }
        return acc;
    }

    //compares 2 strings for the same chars using Levenshtein Distance Algo
    static int levenDist(String s, String t) { 
        // for all i and j, d[i,j] will hold the Levenshtein distance between
        // the first i characters of s and the first j characters of t
        // note that d has (m+1)*(n+1) values
        int m = Math.max(t.length(), s.length());
        int n = Math.min(t.length(), s.length());
        if (n == 0) {
            return m;
        }
        else{
            String p = t;
            String q = s;
            if (t.length() > s.length()) {
                s = p;
                t = q;
            }
            int d[][] = new int [2][m + 1];

            // source prefixes can be transformed into empty string by
            // dropping all characters
            //        for (int i = 0; i <= s.length(); i++) {
            //            d[i][0] = i;
            //        }
            // target prefixes can be reached from empty source prefix
            // by inserting every character
            for (int i = 0; i <= m; i++) {
                d[0][i] = i;
            }
            int substitutionCost;
            for (int j = 1; j <= n; j++) {
                d[1][0] = j;
                for (int i = 1; i <= m; i++) {
                    if (s.charAt(i - 1) == t.charAt(j - 1)) {
                        substitutionCost = 0;
                    }
                    else {
                        substitutionCost = 1;
                    }
                    d[1][i] = Math.min(d[0][i] + 1,                   // deletion
                            Math.min(d[1][i-1] + 1,                   // insertion
                                    d[0][i-1] + substitutionCost));  // substitution
                }
                for (int i = 0; i <= m; i++){
                    d[0][i] = d[1][i];
                }
            }
            return d[1][m];
        }
    }
    //converts the levenDist of 2 strings into the approx. percentage they are different
    static int levenPerc(String a, String b) {
        //the mean length of the 2 strings
        int m = (a.length() + b.length()) / 2;
        //the int percentage the two strings are equal
        int p = (100 * (m - levenDist(a, b))) / m;
        if (p < 0) {
            //this basically implies that the short one copied pasted directly from the larger one
            return 0;
        }
        else {
            return p;
        }
    }
}

class ExamplesUtils {
//    boolean testLevenshteinDistance(Tester t) {
//        return t.checkExpect(Utils.levenDist("kitten", "sitting"), 3) &&
//                t.checkExpect(Utils.levenDist("abcdefh", "zmxcvou"), 7) &&
//                t.checkExpect(Utils.levenDist("abcd", "abcd"), 0) &&
//                t.checkExpect(Utils.levenDist("", "abcd"), 4) &&
//                t.checkExpect(Utils.levenDist("zofp", ""), 4) &&
//                t.checkExpect(Utils.levenDist("abed", "abcd"), 1) &&
//                t.checkExpect(Utils.levenDist("abcd", "abcdffe"), 3) &&
//                t.checkExpect(Utils.levenDist("a  bc d", "abcd"), 3) &&
//                t.checkExpect(Utils.levenDist("ab cd", "ab  cd"), 1);
//    }
//
//    boolean testLevenPerc(Tester t) {
//        return t.checkExpect(Utils.levenPerc("kitten", "sitting"), 50) &&
//                t.checkExpect(Utils.levenPerc("abcdefh", "zmxcvou"), 0) &&
//                t.checkExpect(Utils.levenPerc("abcd", "abcd"), 100) &&
//                t.checkExpect(Utils.levenPerc("", "abcd"), 0) &&
//                t.checkExpect(Utils.levenPerc("zofp", ""), 0) &&
//                t.checkExpect(Utils.levenPerc("abed", "abcd"), 75) &&
//                t.checkExpect(Utils.levenPerc("abcd", "abcdffe"), 40) &&
//                t.checkExpect(Utils.levenPerc("a  bc d", "abcd"), 40) &&
//                t.checkExpect(Utils.levenPerc("ab cd", "ab  cd"), 80) &&
//                t.checkExpect(Utils.levenPerc("abcdefghijklmnopqrstuvwxyz","172830472987437173"),
//                        0);
//    }

    //    void testStuff() {
    //        int a = 9;
    //        int b= 0;
    //
    //        PrintWriter printWriter = new PrintWriter("file.txt");
    //        printWriter.println("hello");
    //        printWriter.close();
    //        PrintWriter out = new PrintWriter("filename.csv");
    //        String c = "row 1, col1, 2, 3";
    //        out.println(c);
    //
    //        out.close();
    //    }
}