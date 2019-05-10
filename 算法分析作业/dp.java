public class dp {

    static void lcs(int i,int j,String x,int [][] b)
    {
        if (i ==0 || j==0) return;
        if (b[i][j]== 1){
            lcs(i-1,j-1,x,b);
            System.out.print(x.charAt(i-1));
        }
        else if (b[i][j]== 2) lcs(i-1,j,x,b);
        else lcs(i,j-1,x,b);
    }

    public static void main(String[] args) {

//        String x = "REDIVIDE";
//        String y = "EDIVIDER";
        String x="RCURSION";
        String y = new StringBuffer(x).reverse().toString();
        int m = x.length() ;
        int n = y.length() ;
        int c[][] =new int[m+1][n+1];
        int b[][]=new int[m+1][n+1];
        for (int i =0;i<=m;i++){
            c[i][0]=0;

        }
        for (int i =0;i<=n;i++){
            c[0][i]=0;
        }


        for (int i = 1; i <=m; i++) {
            for (int j = 1; j <= n; j++) {
                if (j + i > x.length()) {
                    break;
                }
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = 1;
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = 2;
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = 3;
                }
            }
        }
        //            for (int i = 0; i <= m; i++) {
//                for (int j = 0; j <= n; j++) {
//                    System.out.print(c[i][j]);
//
//                }
//                System.out.println();
//            }
        System.out.println("测试用例："+x);
        System.out.println("运行结果：");
            for(int i=0;i<=m;i++){
                for (int j = 0; j <= n; j++) {
                    if(b[i][j]==1)
                    System.out.print(x.charAt(i-1));
                }
            }
        }
}
