import java.io.*;
import java.util.*;
//思路：设想n个任务，对应数组中的空位，把任务按收益排序，每次取最大收益，放置在刚好ddl前的一个位置，如果没有符合的空位，就跳过这个任务，计算收益结束
public class greedy3 {
    public static void writeLog(String filepath,String s){
        try{
            FileWriter fw = new FileWriter(filepath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(s);
            bw.close();
            fw.close();
        }catch (Exception  e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int s=0;
        int error=0;
        int num =10;
        for (int i=0;i<num;i++){
            if(f()==1){
                s+=1;
            }else if(f()==-1){
                error+=1;
            }

        }
        System.out.println("在总共"+num+"次实验中，"+s+"次实验表明贪心算法结果与暴力搜索结果相同");
        System.out.println("在总共"+num+"次实验中，"+error+"次实验表明贪心算法结果优于暴力搜索结果");

    }
    public static int f(){

        try {
            File file = new File("a.txt");

            if(file.delete()) {
                System.out.println( file.getName() + " is deleted!");
            }else {
//                System.out.println("Delete operation is failed.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        Random random = new Random();
        int a=random.nextInt(100);
        //任务数量
        int num =20;
        int [] d=new int[num];
        int [] p=new int[num];
        for(int i=0;i<num;i++){
            d[i]=random.nextInt(num/2)+1;
            p[i]=random.nextInt(100)+1;
        }
        int value_max=0;
        int value_max_index=0;
        int seq[]=new int[num];
        int selected []=new int [num];
        int sum_value =0;

        List<Integer> order =new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<d.length;i++){
            map.put(i,p[i]);
        }
        List<Map.Entry<Integer,Integer>> entry_list = new ArrayList<>(map.entrySet());
        Collections.sort(entry_list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for(Map.Entry<Integer,Integer> i : entry_list){
            order.add(i.getKey());
        }

//        System.out.println(entry_list);

        int []v=new int[num];
        for(int j=0;j<v.length;j++){
            v[j]=0;
        }
        for(Integer i :order){
            for(int k=num-1;k>=0;k--){
                if(v[k]==0&&d[i]>=k+1){
                    v[k]=i;
                    sum_value+=p[i];
                    break;
                }
            }
        }
//        for (int j=0;j<v.length;j++){
//            System.out.println(v[j]);
//        }
//        System.out.println(sum_value);
//        for(int i=0;i<num;i++){
//            System.out.print(seq[i]+" ");
//        }
        System.out.println();
//        for(int i=0;i<num;i++){
//            System.out.print(d[i]+" ");
//        }
//        System.out.println();
//        for(int i=0;i<num;i++){
//            System.out.print(p[i]+" ");
//        }
        System.out.println();
        System.out.println("采用贪心策略获得的最大收益为：");
        System.out.println(sum_value);
        List<Integer> s=new ArrayList<Integer>();
        List<Integer> rs=new ArrayList<Integer>();
        for(int m=0;m<num;m++)
            s.add(m);
        List<List<Integer> > l=null;
        int ii=0;
//        System.out.println("采用暴力搜索，不同选择的收益分别为：");
        pl(s,rs,d,p,num,0);
        System.out.println();
        System.out.println("采用暴力搜索，最大的收益为：");
        int max_value=0;
        try {
            FileInputStream inputStream = new FileInputStream("a.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String str = null;
            while((str = bufferedReader.readLine()) != null)
            {
                if(Integer.parseInt(str)>max_value){
                    max_value =Integer.parseInt(str);
                }
            }
            inputStream.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(max_value);
//            System.out.println(pl(s,rs).get(0).get(0));
        if (max_value==sum_value){
            return 1;
        }else if(max_value<sum_value){
            return -1;
        }else {
            return 0;
        }
    }
    public static void pl(List<Integer> s,List<Integer> rs,int [] d,int [] p,int num,int value_max){
        int sum_value;
        if(s.size()==1)
        {
            rs.add(s.get(0));
            sum_value=0;
            //计算这个排列的value
            if (rs.size()==num){

                for(int k=0;k<num;k++){
                    int index =rs.get(k);
                    if(d[index]>=k+1){
                        sum_value+=p[index];
                    }
                }
                if (sum_value>value_max){
                    value_max=sum_value;
                }
                writeLog("a.txt",value_max+"\n");
            }
            rs.remove(rs.size()-1);
        }else{
            for(int i=0;i<s.size();i++){
                rs.add(s.get(i));
                List<Integer> tmp=new ArrayList<Integer>();
                for(Integer a:s)
                    tmp.add(a);
                tmp.remove(i);
                pl(tmp,rs,d,p,num,value_max);
                rs.remove(rs.size()-1);
            }
        }
    }
}
