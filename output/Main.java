import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author saikat021
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        TaskC solver = new TaskC();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskC {
        public int count = 0;

        public void solve(int testNumber, InputReader in, OutputWriter out) {
            String[] strings = in.readLine().trim().split("\\s+");
            int n = Integer.parseInt(strings[0]);//number of vertices
            int m = Integer.parseInt(strings[1]);//Maximum number of cats allowed
            strings = in.readLine().trim().split("\\s+");
            TreeNode[] nodes = new TreeNode[n];
            boolean[] visited = new boolean[n];
            for (int i = 0; i < strings.length; i++) {
                nodes[i] = (Integer.parseInt(strings[i]) == 1) ? new TreeNode(i, true) : new TreeNode(i, false);
            }
            //TreeNode root=null; Root is the nodes[0]..
            for (int i = 0; i < n - 1; i++) {
                //Going through and making all edges
                strings = in.readLine().trim().split("\\s+");
                int parent = Integer.parseInt(strings[0]);
                int child = Integer.parseInt(strings[1]);
                //take first parent as root
                nodes[parent - 1].addChildren(nodes[child - 1]);
                nodes[child - 1].addChildren(nodes[parent - 1]);
            }
            //Tree created
            numCats(nodes[0], m, m, visited);
            out.println(count);
        }

        public void numCats(TreeNode root, int m, int curr_m, boolean[] visited) {
            curr_m = root.isCat ? curr_m - 1 : m;
            visited[root.val] = true;
            if (curr_m == -1) return;//more than m adjacent nodes
            int count_children = 0;
            for (TreeNode child : root.children) {
                if (visited[child.val] == false) {//not yet visited
                    numCats(child, m, curr_m, visited);
                    count_children++;
                }
            }
            if (count_children == 0) {
                count++;
                //Is a leaf node and could be
                // reached so should be included in result
            }
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        private String readLine0() {
            StringBuilder buf = new StringBuilder();
            int c = read();
            while (c != '\n' && c != -1) {
                if (c != '\r') {
                    buf.appendCodePoint(c);
                }
                c = read();
            }
            return buf.toString();
        }

        public String readLine() {
            String s = readLine0();
            while (s.trim().length() == 0) {
                s = readLine0();
            }
            return s;
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void close() {
            writer.close();
        }

        public void println(int i) {
            writer.println(i);
        }

    }

    static class TreeNode {
        int val;
        boolean isCat;
        ArrayList<TreeNode> children;

        TreeNode(int value, boolean hasCat) {
            this.children = new ArrayList<>();
            this.isCat = hasCat;
            this.val = value;

        }

        public void addChildren(TreeNode child) {
            this.children.add(child);
        }

    }
}

