package mona;

/**
 * @Author jambestwick
 * @create 2021/11/30 0030  0:38
 * @email jambestwick@126.com
 */
public class RandomUtil {


    public static String randomNumDigest() {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                double rand = Math.random();
                double randTri = Math.random() * 3;
                if (randTri >= 0 && randTri < 1) {
                    System.out.print((char) (rand * ('9' - '0') + '0'));
                    stringBuilder.append((char) (rand * ('9' - '0') + '0'));
                } else if (randTri >= 1 && randTri < 2) {
                    System.out.print((char) (rand * ('Z' - 'A') + 'A'));
                    stringBuilder.append((char) (rand * ('Z' - 'A') + 'A'));
                } else {
                    System.out.print((char) (rand * ('z' - 'a') + 'a'));
                    stringBuilder.append((char) (rand * ('z' - 'a') + 'a'));
                }
            }
            System.out.println();
        }
        return stringBuilder.toString();
    }
}
