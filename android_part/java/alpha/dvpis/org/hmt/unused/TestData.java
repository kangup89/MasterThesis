package alpha.dvpis.org.hmt.unused;

/**
 * Created by kangup on 2016-09-04.
 */
public class TestData {
    public static String testUsername = "test";
    public static String testPassword = "test";

    public static String[] puNames = {"Francisco Juliano", "Maria Perez", "Luisa Jimenez"};

    public int[] getBlood(int name){
        int[] blood;

        /*if(name.equals(puNames[0])){
            blood = new int[]{75,120,100,70,115,100,80,125,120,82,130,110};
        }else if(name.equals(puNames[1])){
            blood = new int[]{85,110,90,70,120,110,75,115,120,82,120,90};
        }else if(name.equals(puNames[2])){
            blood = new int[]{90,115,85,100,120,115,85,90,110,85,110,90};
        }else{
            return null;
        }*/

        if(name == 0){
            blood = new int[]{75,120,100,70,115,100,80,125,120,82,130,110};
        }else if(name == 1){
            blood = new int[]{85,110,90,70,120,110,75,115,120,82,120,90};
        }else if(name == 2){
            blood = new int[]{90,115,85,100,120,115,85,90,110,85,110,90};
        }else{
            return null;
        }

        return blood;
    }

    public int[] getGlu(int name){
        int[] blood;

        /*if(name.equals(puNames[0])){
            blood = new int[]{75,120,100,70,115,100,80,125,120,82,130,110};
        }else if(name.equals(puNames[1])){
            blood = new int[]{85,110,90,70,120,110,75,115,120,82,120,90};
        }else if(name.equals(puNames[2])){
            blood = new int[]{90,115,85,100,120,115,85,90,110,85,110,90};
        }else{
            return null;
        }*/

        if(name == 0){
            blood = new int[]{100,120,100,130,115,100,140,125,120,90,130,110};
        }else if(name == 1){
            blood = new int[]{110,110,90,130,120,110,120,115,120,95,120,90};
        }else if(name == 2){
            blood = new int[]{130,115,120,100,120,115,100,150,110,120,110,90};
        }else{
            return null;
        }

        return blood;
    }

}
