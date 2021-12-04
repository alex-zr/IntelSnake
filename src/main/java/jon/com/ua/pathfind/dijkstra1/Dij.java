package jon.com.ua.pathfind.dijkstra1;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/21/13
 */
public class Dij {
    public static final long INF = Long.MAX_VALUE / 10;

    public static int dijkstra(Graph g, int s, int z, long[] prio, int[] pred) {
/*
        int z = 0;
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter an initial top: ");
            String line = buf.readLine();
            source = Integer.parseInt(line);
            System.out.print("Enter an eventual top: ");
            line = buf.readLine();
            z = Integer.parseInt(line);
            buf.close();
        } catch (Exception e) {
            System.err.println(e.toString());
            System.exit(0);
        }
*/

// begin
        Arrays.fill(pred, -1);
        Arrays.fill(prio, INF);
        prio[s] = 0;
        Queue<QItem> q = new PriorityQueue<QItem>();
        q.add(new QItem(0, s));
        while (!q.isEmpty()) {
            QItem curItem = q.poll();
            if (curItem.prio != prio[curItem.v]) {
                continue;
            }

            for (Graph.Edge edge : g.nodeEdges[curItem.v]) {
                long nprio = prio[curItem.v] + edge.cost;
                if (prio[edge.target] > nprio) {
                    prio[edge.target] = nprio;
                    pred[edge.target] = curItem.v;
                    q.add(new QItem(nprio, edge.target));
                }
            }
        }
        return z;
    }


    public static class QItem implements Comparable<QItem> {
        long prio;
        int v;

        public QItem(long prio, int v) {
            this.prio = prio;
            this.v = v;
        }

        public int compareTo(QItem q) {
            return prio < q.prio ? -1 : prio > q.prio ? 1 : 0;
        }
    }


// end

    public static void main(String[] args) {
        int[][] arr = Util.readFile();
        Graph g = Util.oldCreateGraph(arr, Util.size);
        long[] path = new long[g.n];
        int[] pred = new int[g.n];
        int z = dijkstra(g, 0, 1, path, pred);
        System.out.println("Shortest path: " + path[z]);
        Util.printPath(pred, z);
    }
}