import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs){
        DisjointSetUnion dsu = new DisjointSetUnion(s.length());
        for(List<Integer> pair: pairs){
            dsu.union(pair.get(0), pair.get(1));
        }

        Map<Integer, List<Character>> map = new HashMap<>();
        for(int i=0; i < s.length(); i++){
            int parent = dsu.find(i);
            if(!map.containsKey(parent)){
                map.put(parent, new ArrayList<Character>());
            }

            map.get(parent).add(s.charAt(i));
        }

        for(Map.Entry<Integer, List<Character>> entry: map.entrySet()){
            Collections.sort(entry.getValue(), new Comparator<Character>(){
                public int compare(Character c1, Character c2){
                    return c2 - c1;
                }
            });
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            int x = dsu.find(i);
            List<Character> list = map.get(x);
            sb.append(list.remove(list.size()-1));
        }

        return sb.toString();
    }
}
class DisjointSetUnion{
    int[] father;
    int n;

    public DisjointSetUnion(int n){
        this.n = n;
        father = new int[this.n];

        for(int i=0; i<n; i++){
            father[i] = i;
        }
    }

    public int find(int x){
        if(x == father[x]){
            return x;
        }
        return father[x] = find(father[x]);
    }

    public void union(int x, int y){
        int px = find(x);
        int py = find(y);

        if(px != py){
            father[px] = py;
        }
    }
}