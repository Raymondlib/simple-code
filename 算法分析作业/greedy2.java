import java.io.*;
import java.util.*;

public class greedy2 {
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
        int num =1;
        for (int i=0;i<num;i++){
            s+=f();
        }
        System.out.println("在总共"+num+"次实验中，"+s+"次实验表明贪心算法结果与暴力搜索结果相同");

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
        int num =6;
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
            map.put(i,d[i]);
        }
        List<Map.Entry<Integer,Integer>> entry_list = new ArrayList<>(map.entrySet());
        Collections.sort(entry_list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for(Map.Entry<Integer,Integer> i : entry_list){
            order.add(i.getKey());
        }
        System.out.println(order);
        //用数组fine表示任务是否有收益
        int fine [] =new int[num];
        for(Integer i:order){
            if (d[i]<order.indexOf(i)+1){
                fine[i]=-1;
            }else {
                fine[i]=1;
            }
//            System.out.println(order.indexOf(i));
        }
        for(int k=0;k<order.size();k++){
            Integer i=order.get(k);
            if (d[i]<order.indexOf(i)+1){
                for(int index=0;index<=d[i]-1;index++){
                    if(d[order.get(index)]<index+1&& d[i]>=index+1){
                        //找到了最先的一个无收益任务，并且此任务替换后，有收益,交换两个任务次序
                        order.set(order.indexOf(i),order.get(index));
                        order.set(index,i);
                        System.out.println("change");
//                        System.out.println(order);
//                        System.out.println(order.get(k+1));

//                        System.out.println(order.get(k));
//                        System.out.println(order.get(k+1));
                        break;
                    }

                }
                //没有无收益任务在前面，就找一个value小的任务替换,且这个value是前面可行任务中value最小的，且要求替换之后产生收益
                int min_value=p[order.get(0)];
                int min_index=0;
                for(int index=1;index<order.indexOf(i);index++){

//                    if(d[order.get(index)]>=)
                    if(p[order.get(index)]<min_value){
                        min_value=p[order.get(index)];
                        min_index=index;
                    }
//                    if(p[order.get(index)]<p[i] && d[i]>=index+1){
//
//                        break;
//                    }

                }
                if(p[order.get(min_index)]<p[i]){
                    order.set(order.indexOf(i),order.get(min_index));
                    order.set(min_index,i);
                    break;
                }

            }
//            System.out.println("----------");
//            System.out.println(order.get(k));
//            System.out.println(order);
        }
        System.out.println("fine");
        System.out.println(order);

        //计算sum_value
        for(int k=0;k<num;k++){
            int index =order.get(k);
            if(d[index]>=k+1){
                sum_value+=p[index];
            }
        }
//        if (sum_value>value_max){
//            value_max=sum_value;
//        }


//        for(int i=0;i<num;i++){
//            value_max=0;
//            for(int j=0;j<num;j++){
//                if (d[j]>=(i+1)&&selected[j]==0){
//                    if (p[j]>value_max){
//                        value_max=p[j];
//                        value_max_index=j;
//                    }
//                }
//            }
//            if (value_max==0){
//                //表示当前时间片，无法获得收益
//                seq[i]=i;
//                break;
//            }
//            else {
//                selected[value_max_index]=1;
//                seq[i]=value_max_index;
//                sum_value+=p[value_max_index];
//            }
//
//        }
//        for()
        for(int i=0;i<num;i++){
            System.out.print(seq[i]+" ");
        }
        System.out.println();
        for(int i=0;i<num;i++){
            System.out.print(d[i]+" ");
        }
        System.out.println();
        for(int i=0;i<num;i++){
            System.out.print(p[i]+" ");
        }
        System.out.println();
        System.out.println("采用贪心策略获得的最大收益为：");
        System.out.println(sum_value);


        List<Integer> s=new ArrayList<Integer>();
        List<Integer> rs=new ArrayList<Integer>();
        for(int m=0;m<num;m++)
            s.add(m);
        List<List<Integer> > l=null;
        int ii=0;

        System.out.println("采用暴力搜索，不同选择的收益分别为：");
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

            //close
            inputStream.close();
            bufferedReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(max_value);
//            System.out.println(pl(s,rs).get(0).get(0));
        if (max_value==sum_value){
            return 1;
        }else {
            return 0;
        }
    }

    public static void pl(List<Integer> s,List<Integer> rs,int [] d,int [] p,int num,int value_max){
//        List<List<Integer> > list =new ArrayList<List<Integer>>();

//        int value_max=0;
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
//                System.out.print(value_max+" ");

                writeLog("a.txt",value_max+"\n");
            }


//            System.out.println(rs.toString());
//            list.add(rs);
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
