import java.util.*;
class Node{
    int layer;
    List<Integer> x=new ArrayList<>();
    @Override
    public String toString() {
        return layer+"--"+x.toString();
    }
}
public class BFS {
    public static void main(String[] args) {
        //随机生成数量不定的子集
        List<Integer> X = new ArrayList<>();
        Set<Integer> all_X = new HashSet<>();
        int size=10;
        for (int i = 1; i < size; i++) {
            X.add(i);
            all_X.add(i);
        }
        Random random =new Random();
        List<Set<Integer>> setList =new ArrayList<>();
        int subsetNum=6;
        for(int i=0;i<subsetNum;i++){//子集数量
            Set<Integer> temp=new HashSet<>();
            for(int j=0;j<random.nextInt(size);j++){//子集长度
                temp.add(random.nextInt(size));
            }
            setList.add(temp);
        }

        Collections.sort(setList, new Comparator<Set<Integer>>() {
            @Override
            public int compare(Set<Integer> o1, Set<Integer> o2) {
                return o2.size()-o1.size();
            }
        });
        System.out.println("原集合为");
        System.out.println(all_X);
        System.out.println("各个子集为");
        System.out.println(setList);
        int set_num = subsetNum-1;
        Queue<Node> queue = new LinkedList<Node>();
        Node sign = new Node();
        sign.layer = -1;
        int t = 1;
        Node cur = new Node();
        cur.layer = 0;
        cur.x.add(0);
        int times=0;
        ((LinkedList<Node>) queue).add(cur);
        while (times++<100) {
            if(queue.isEmpty()){
                break;
            }
            System.out.println(cur.toString());
            if(check(setList,cur,all_X)){
                System.out.println("找到了最小覆盖序列：");
                System.out.println(cur);
                System.out.println("各个子集分别为");
                for(Integer i:cur.x){
                    System.out.println(setList.get(i));
                }
                break;
            }
            for (int k = 1; k <= set_num; k++) {
                Node q = new Node();
                q.layer = cur.x.size();
                q.x.add(0);
                for (int i = 1; i < cur.x.size(); i++) {
                    q.x.add(cur.x.get(i));
                }
                if (!q.x.contains(k)) {
                    q.x.add(k);
                    ((LinkedList<Node>) queue).add(q);
                }
            }
            cur = queue.poll();
        }
    }
    static boolean check(List<Set<Integer>> subsets, Node node, Set<Integer> all_x){
        Set<Integer> set=new HashSet<>();
        for( Integer i: node.x){
            Set<Integer> item=subsets.get(i);
            set.addAll(item);
        }
        if(set.equals(all_x)){
            return true;
        }else return false;
    }
}