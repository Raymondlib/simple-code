import java.util.ArrayList;
import java.util.List;
public class Fully_arranged {

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> s=new ArrayList<Integer>();
        List<Integer> rs=new ArrayList<Integer>();
        for(int i=1;i<=3;i++)
            s.add(i);
        List<List<Integer> > l=null;
//        l=pl(s,rs);
//        for (int i=0;i<l.size();i)
        System.out.println(pl(s,rs).get(0).get(0));
    }
    public static List<List<Integer> > pl(List<Integer> s,List<Integer> rs){
        List<List<Integer> > list =new ArrayList<List<Integer>>();
        if(s.size()==1)
        {
            rs.add(s.get(0));
            System.out.println(rs.toString());
            list.add(rs);
            rs.remove(rs.size()-1);
        }else{

            for(int i=0;i<s.size();i++){

                rs.add(s.get(i));
                List<Integer> tmp=new ArrayList<Integer>();
                for(Integer a:s)
                    tmp.add(a);
                tmp.remove(i);
                pl(tmp,rs);
                rs.remove(rs.size()-1);

            }
        }
        return list;
    }
}
