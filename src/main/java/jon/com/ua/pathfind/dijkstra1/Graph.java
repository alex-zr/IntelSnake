package jon.com.ua.pathfind.dijkstra1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: al1
 * Date: 8/21/13
 */
public class Graph {
    public final int n;
    public List<Edge>[] nodeEdges;

    public static class Edge {
        public int source, target, cost;

        public Edge(int s, int t, int cost) {
            this.source = s;
            this.target = t;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "source=" + source +
                    ", target=" + target +
                    ", cost=" + cost +
                    '}';
        }
    }

    public Graph(int n) {
        this.n = n;
        nodeEdges = new List[n];
        for (int i = 0; i < n; i++) {
            nodeEdges[i] = new ArrayList<Edge>();
        }
    }

    void addEdge(int s, int t, int cost) {
        nodeEdges[s].add(new Edge(s, t, cost));
    }
}
