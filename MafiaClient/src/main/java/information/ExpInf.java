package information;

public class ExpInf {

	public static final int[] need_exp = {
            15, 
            34, 
            57, 
            92, 
            135,

            372,
            560, 
            840, 
            1242, 
            1242, 

            1242, 
            1242, 
            1242, 
            1242, 
            1242, 

            1490, 
            1788,
            2145, 
            2574, 
            3088,

            3705, 
            4446, 
            5335, 
            6402, 
            7682, 

            9218, 
            11061, 
            13273, 
            15927, 
            19112
    };
    public static final int MAX_LEVEL = 30;

    public static int needExp(int level) {
        return need_exp[level - 1];
    }
}
