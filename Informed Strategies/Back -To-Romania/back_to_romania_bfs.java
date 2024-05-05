import java.util.*;

class Edge {
    String source, dest;
    int weight;

    Edge(String source, String dest, int weight) {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

class PQNode implements Comparable<PQNode> {
    String node;
    int priority;
    int distance;

    PQNode(String node, int priority, int distance) {
        this.node = node;
        this.priority = priority;
        this.distance = distance;
    }

    @Override
    public int compareTo(PQNode other) {
        return Integer.compare(this.priority, other.priority);
    }
}

class Graph {
    Map<String, List<Edge>> adjList;

    Graph(List<Edge> edges) {
        adjList = new HashMap<>();
        for (Edge edge : edges) {
            adjList.computeIfAbsent(edge.source, k -> new ArrayList<>()).add(edge);
        }
    }
}

public class back_to_romania_bfs {

    static Map<String, Integer> heuristic = new HashMap<>();
    
    static {
        heuristic.put("Arad", 366);
        heuristic.put("Bucharest", 0);
        heuristic.put("Craiova", 160);
        heuristic.put("Dobreta", 242);
        heuristic.put("Eforie", 161);
        heuristic.put("Fagaras", 176);
        heuristic.put("Giurgiu", 77);
        heuristic.put("Hirsova", 151);
        heuristic.put("Iasi", 226);
        heuristic.put("Lugoj", 244);
        heuristic.put("Mehadia", 241);
        heuristic.put("Neamt", 234);
        heuristic.put("Oradea", 380);
        heuristic.put("Pitesti", 100);
        heuristic.put("Rimnicu Vilcea", 193);
        heuristic.put("Sibiu", 253);
        heuristic.put("Timisoara", 329);
        heuristic.put("Urziceni", 80);
        heuristic.put("Vaslui", 199);
        heuristic.put("Zerind", 374);
    }

    static void bestFirstSearch(Graph graph, String src, String dest) {
        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> path = new HashMap<>();

        pq.offer(new PQNode(src, 0, 0));

        while (!pq.isEmpty()) {
            PQNode currentNode = pq.poll();

            String node = currentNode.node;
            int currentPriority = currentNode.priority;
            int currentDistance = currentNode.distance;

            visited.add(node);

            if (node.equals(dest)) {
                System.out.print("Path: ");
                List<String> reversePath = new ArrayList<>();
                while (!node.equals(src)) {
                    reversePath.add(node);
                    node = path.get(node);
                }
                reversePath.add(src);
                for (int i = reversePath.size() - 1; i > 0; i--) {
                    System.out.print(reversePath.get(i) + " -> ");
                }
                System.out.println(reversePath.get(0));
                System.out.println("Total Distance: " + currentDistance + " km");
                break;
            }

            for (Edge edge : graph.adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited.contains(edge.dest)) {
                    int priority = heuristic.getOrDefault(edge.dest, 0);
                    // int priority = edge.weight + heuristic.getOrDefault(edge.dest, 0);
                    int newDistance = currentDistance + edge.weight;
                    pq.offer(new PQNode(edge.dest, priority, newDistance));
                    path.put(edge.dest, node);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<Edge> edges = List.of(
            new Edge("Arad", "Zerind", 75), new Edge("Arad", "Sibiu", 140), new Edge("Arad", "Timisoara", 118),
            new Edge("Zerind", "Oradea", 71), new Edge("Oradea", "Sibiu", 151), new Edge("Timisoara", "Lugoj", 111),
            new Edge("Lugoj", "Mehadia", 70), new Edge("Mehadia", "Dobreta", 75), new Edge("Dobreta", "Craiova", 120),
            new Edge("Craiova", "Rimnicu Vilcea", 146), new Edge("Craiova", "Pitesti", 138),
            new Edge("Rimnicu Vilcea", "Sibiu", 80), new Edge("Rimnicu Vilcea", "Pitesti", 97),
            new Edge("Sibiu", "Fagaras", 99), new Edge("Fagaras", "Bucharest", 211),
            new Edge("Pitesti", "Bucharest", 101), new Edge("Bucharest", "Giurgiu", 90),
            new Edge("Bucharest", "Urziceni", 85), new Edge("Urziceni", "Vaslui", 142),
            new Edge("Vaslui", "Iasi", 92), new Edge("Iasi", "Neamt", 87), new Edge("Urziceni", "Hirsova", 98),
            new Edge("Hirsova", "Eforie", 86)
        );

        Graph graph = new Graph(edges);

        String src = "Arad";
        String dest = "Bucharest";

        bestFirstSearch(graph, src, dest);
    }
}
