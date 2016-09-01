public class stringToInt{
    int conversion = 0;
    public stringToInt(String input){
        int factor = 1;
        for(int i = input.length()-1; i >= 0; i--) {
            //check if the character is an integer 0-9
            if(Character.getNumericValue(input.charAt(i)) > 9)
            {
               System.out.println("Not an Integer");
               conversion = 0;
               return;
            }
            conversion += (input.charAt(i) - '0') * factor;
            factor *= 10;
        }
    }
    public Integer returnInt(){
        return conversion;
    }
}
