import java.util.Random;

public class Main{
    public static void main(String[] args){
        Parser parser = new Parser("./wordlist.txt");
        double[][] proba= new double[26][26]; 
        double[][] cumProba= new double[26][26]; 
        for(int i = 0; i<26; i++){
            for(int j=0; j<26; j++){
                proba[i][j] = 0;
            }
        }

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        try{
            String[] table = parser.parse();
            for(String s:table){
                char[] charArray = s.toCharArray();
                for(int i = 0; i<charArray.length-1; i++){
                    char c = charArray[i];
                    int cInd = 0, cNextInd = 0;
                    char cNext = charArray[i+1];
                    for(int j = 0; j<26; j++){
                        if(alphabet[j]==c) cInd = j;
                        if(alphabet[j]==cNext) cNextInd = j;
                    }
                    proba[cInd][cNextInd]+=1.0;
                }
            }
        } catch(Exception e){}

        for(int i = 0; i<26; i++){
            double sumProba = 0.0;
            for(int j = 0; j<26; j++){
                sumProba+=proba[i][j];
            }
            double cum = 0.0;
            for(int j = 0; j<26; j++){
                cum += sumProba > 0 ? (double) proba[i][j] / sumProba : 0;
                cumProba[i][j] = cum;
            }
        }

        Random random = new Random();
        String gen = generateWord(cumProba, alphabet, random, 8);
        
        int maxLength = 8;
        int nWords = 30;
        String[] nWordList = new String[nWords];
        for(int i = 0; i<nWords; i++){
            nWordList[i] = generateWord(cumProba,alphabet,random, maxLength);
        }
        for(String w:nWordList){
            System.out.print(w+", ");
        }

        System.out.println("generated word : "+gen);
    }
    public static String generateWord(double[][] proba, char[] alphabet, Random random, int maxLength){
        int curInd = random.nextInt(26);
        return generateWord(proba, alphabet, alphabet[curInd], random, maxLength);
    }

    public static String generateWord(double[][] proba, char[] alphabet, char startChar, Random random, int maxLength){
        StringBuilder word = new StringBuilder();

        int curInd = 0;
        for(int i = 0; i<alphabet.length; i++){
            if(alphabet[i]==startChar){
                curInd = i;
                break;
            }
        }
        word.append(startChar);

        for(int i = 1; i<maxLength; i++){
            double rand = random.nextDouble();
            int nextIndex = -1;
            for (int j = 0; j < 26; j++) {
                if (rand <= proba[curInd][j]) {
                    nextIndex = j;
                    break;
                }
            }
            if (nextIndex == -1) break;
            word.append(alphabet[nextIndex]);
            curInd = nextIndex;

        }

        return word.toString();
    }
}