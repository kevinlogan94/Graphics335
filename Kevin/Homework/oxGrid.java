/*Basic Program for the 10x10 OX Grid
  Final Result:
    OOOOOOOOOX
    OOOOOOOOXX
    OOOOOOOXXX
    OOOOOOXXXX
    OOOOOXXXXX
    OOOOXXXXXX
    OOOXXXXXXX
    OOXXXXXXXX
    OXXXXXXXXX                                                                                                      
    XXXXXXXXXX
*/
public class oxGrid {

    public static void main(String []args) {
        int o_num = 9;
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 10; k++) {
                if (k < o_num) {
                    System.out.print('O');
                }
                else {
                    System.out.print('X');
                }
            }
            o_num--;
            System.out.print('\n');
        }
    }
}
