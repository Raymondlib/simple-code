package greedy3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

//思路：设想n个任务，对应数组中的空位，把任务按收益排序，每次取最大收益，放置在刚好ddl前的一个位置，如果没有符合的空位，就跳过这个任务，计算收益结束

class Task{
    int d;
    int p;
    int i;
    public Task(int d,int p,int i){
        this.d=d;
        this.p=p;
        this.i=i;
    }

}

public class greedy4 {
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
        int a=0;
        int b=0;
        int num =100;

        for (int i=0;i<num;i++){
            List<Integer> l=f();
            s+=l.get(0);
            a+=l.get(1);
            b+=l.get(2);

        }
        System.out.println("任务数量为50");
        System.out.println("在总共"+num+"次实验中，"+s+"次实验表明贪心算法1结果与贪心算法2结果相同");
        System.out.println("在总共"+num+"次实验中，"+a+"次实验表明贪心算法1结果与贪心算法2得到的序列长度相同");
        System.out.println("在总共"+num+"次实验中，"+b+"次实验表明贪心算法1结果与贪心算法2得到的序列不完全相同");

    }

    public static List<Integer> f(){
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
        int num =50;
        int [] d=new int[num];
        int [] p=new int[num];
        Task[] tasks =new Task[num];

        for(int i=0;i<num;i++){

            d[i]=random.nextInt(num)+1;
            p[i]=random.nextInt(100)+1;
            tasks[i]=new Task(d[i],p[i],i);
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
        for(Map.Entry<Integer,Integer> i : entry_list) {
            order.add(i.getKey());
        }
        int []v=new int[num];
        for(int j=0;j<v.length;j++){
            v[j]=-1;
        }
        for(Integer i :order) {
            for (int k = num - 1; k >= 0; k--) {
                if (v[k] == -1 && d[i] >= k + 1) {
                    v[k] = i;
                    sum_value += p[i];
                    break;
                }
            }
        }
        System.out.println();
        System.out.println();
        //已安排列表
        List<Task> arrange_order =new ArrayList<>();

        int greedy_sum_value2=0;
        for(Integer i :order){
            if(arrange_order.isEmpty()){
                arrange_order.add(new Task(d[i],p[i],i));
                greedy_sum_value2+=p[i];
            }else {
                Task temp=new Task(d[i],p[i],i);
                arrange_order.add(temp);
                int fl=0;
                Collections.sort(arrange_order, new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return o1.d-o2.d;
                    }
                });
                for(Task task:arrange_order){
                    if(task.d<arrange_order.indexOf(task)+1){
                        fl=1;
                        break;
                    }
                }
                if(fl==1){
                    arrange_order.remove(temp);
                }else {
                    greedy_sum_value2+=p[i];
                }
            }
        }
        //把算法2中未排列的任务，加上，并计算收益
        int pre =arrange_order.size();
        List<Task> unarrange_task =new ArrayList<>();
        List<Integer> arrange_task =new ArrayList<>();
        for(Task t:arrange_order){
            arrange_task.add(t.i);
        }
        for(int i=0;i<num;i++){
            if(!arrange_task.contains(i)){
                unarrange_task.add(new Task(d[i],p[i],i));
            }
        }
        Collections.sort(unarrange_task, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.d-o2.d;
            }
        });
        int left_value=0;
        for(int i=0;i<unarrange_task.size();i++){
            if(unarrange_task.get(i).d>=pre+i+1){
                left_value+=unarrange_task.get(i).p;
            }
        }

        System.out.println("采用方法2的结果为");
        System.out.println(greedy_sum_value2);
        System.out.println("采用方法2的最终结果为");
        System.out.println(greedy_sum_value2+left_value);
        for(int i=0;i<arrange_order.size();i++){
            System.out.print(arrange_order.get(i).i+" ");
        }
        System.out.println();
        System.out.println("left_value"+left_value);
        System.out.println("长度为"+arrange_order.size());
//        System.out.println();
        System.out.println("采用贪心策略获得的最大收益为：");
        System.out.println(sum_value);
        System.out.println("采用贪心策略序列为：");
        int v_length=0;
        for(int i=0;i<v.length;i++){

            if(v[i]!=-1){
                System.out.print(v[i]+" ");
                v_length+=1;
            }
        }
        System.out.println();
        System.out.println("长度为"+v_length);

        List<Integer> s=new ArrayList<Integer>();
        List<Integer> rs=new ArrayList<Integer>();
        for(int m=0;m<num;m++)
            s.add(m);
        List<List<Integer> > l=null;
        int ii=0;
        System.out.println();
        int max_value=0;
//        System.out.println(max_value);
        List<Integer> result=new ArrayList<>();

        if ( greedy_sum_value2==sum_value){
            result.add(1);
        }else if(greedy_sum_value2<sum_value){
            result.add(-1);
        }else {
            result.add(0);
        }
        if(v_length==arrange_order.size()){
            result.add(1);
        }else {
            result.add(0);
        }
        int index_task_0=0;
        int equal =1;

        for(int i=0;i<v.length;i++){
            if(v[i]!=-1){

                if(v[i]!=arrange_order.get(index_task_0).i){
                    equal=0;
                    break;
                }
                index_task_0+=1;
            }
        }
        if(equal==1){
            result.add(0);
        }else {
            result.add(1);
        }
        return result;
    }

}
