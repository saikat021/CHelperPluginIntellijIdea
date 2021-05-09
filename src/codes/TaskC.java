package codes;

import FastIO.InputReader;
import FastIO.OutputWriter;

import java.util.ArrayList;

public class TaskC {
    public int count=0;
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String [] strings=in.readLine().trim().split("\\s+");
        int n=Integer.parseInt(strings[0]);//number of vertices
        int m=Integer.parseInt(strings[1]);//Maximum number of cats allowed
        strings=in.readLine().trim().split("\\s+");
        TreeNode [] nodes = new TreeNode[n];
        boolean [] visited =new boolean[n];
        for(int i=0;i<strings.length;i++){
            nodes[i]=(Integer.parseInt(strings[i])==1)?new TreeNode(i,true):new TreeNode(i,false);
        }
        //TreeNode root=null; Root is the nodes[0]..
        for(int i=0;i<n-1;i++){
            //Going through and making all edges
            strings=in.readLine().trim().split("\\s+");
            int parent=Integer.parseInt(strings[0]);
            int child=Integer.parseInt(strings[1]);
            //take first parent as root
            nodes[parent-1].addChildren(nodes[child-1]);
            nodes[child-1].addChildren(nodes[parent-1]);
        }
        //Tree created
        numCats(nodes[0],m,m,visited);
        out.println(count);
    }

    public void numCats(TreeNode root,int m,int curr_m,boolean[] visited) {
        curr_m = root.isCat ? curr_m - 1 : m;
        visited[root.val]=true;
        if (curr_m == -1) return;//more than m adjacent nodes
        int count_children=0;
        for (TreeNode child : root.children) {
            if(visited[child.val]==false){//not yet visited
                numCats(child,m,curr_m,visited);
                count_children++;
            }
        }
        if(count_children==0){
            count++;
            //Is a leaf node and could be
            // reached so should be included in result
        }
    }
}
class TreeNode{
    int val;
    boolean isCat;
    ArrayList<TreeNode> children;
    TreeNode(int value,boolean hasCat){
        this.children=new ArrayList<>();
        this.isCat=hasCat;
        this.val=value;

    }
    public void addChildren(TreeNode child){
        this.children.add(child);
    }

}
