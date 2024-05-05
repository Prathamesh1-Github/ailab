import java.util.*;

class Node {
    String city;
    int g; // Cost from start to current node
    int h; // Heuristic
    Node parent;

    Node(String city, int g, int h, Node parent) {
        this.city = city;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    int f() {
        return g + h;
    }
}

class AStarComparator implements Comparator<Node> {
    public int compare(Node a, Node b) {
        return Integer.compare(a.f(), b.f());
    }
}

public class back_to_romania_astar {

    static Map<String, Map<String, Integer>> graph = new HashMap<>();
    static Map<String, Integer> heuristic = new HashMap<>();

    static {
        graph.put("Arad", Map.of("Zerind", 75, "Sibiu", 140, "Timisoara", 118));
        graph.put("Zerind", Map.of("Arad", 75, "Oradea", 71));
        graph.put("Oradea", Map.of("Zerind", 71, "Sibiu", 151));
        graph.put("Sibiu", Map.of("Arad", 140, "Oradea", 151, "Fagaras", 99, "Rimnicu Vilcea", 80));
        graph.put("Timisoara", Map.of("Arad", 118, "Lugoj", 111));
        graph.put("Lugoj", Map.of("Timisoara", 111, "Mehadia", 70));
        graph.put("Mehadia", Map.of("Lugoj", 70, "Drobeta", 75));
        graph.put("Drobeta", Map.of("Mehadia", 75, "Craiova", 120));
        graph.put("Craiova", Map.of("Drobeta", 120, "Rimnicu Vilcea", 146, "Pitesti", 138));
        graph.put("Rimnicu Vilcea", Map.of("Sibiu", 80, "Craiova", 146, "Pitesti", 97));
        graph.put("Fagaras", Map.of("Sibiu", 99, "Bucharest", 211));
        graph.put("Pitesti", Map.of("Rimnicu Vilcea", 97, "Craiova", 138, "Bucharest", 101));
        graph.put("Bucharest", Map.of("Fagaras", 211, "Pitesti", 101, "Giurgiu", 90, "Urziceni", 85));
        graph.put("Giurgiu", Map.of("Bucharest", 90));
        graph.put("Urziceni", Map.of("Bucharest", 85, "Vaslui", 142, "Hirsova", 98));
        graph.put("Vaslui", Map.of("Urziceni", 142, "Iasi", 92));
        graph.put("Iasi", Map.of("Vaslui", 92, "Neamt", 87));
        graph.put("Neamt", Map.of("Iasi", 87));
        graph.put("Hirsova", Map.of("Urziceni", 98, "Eforie", 86));
        graph.put("Eforie", Map.of("Hirsova", 86));

        heuristic.put("Arad", 366);
        heuristic.put("Zerind", 374);
        heuristic.put("Oradea", 380);
        heuristic.put("Sibiu", 253);
        heuristic.put("Timisoara", 329);
        heuristic.put("Lugoj", 244);
        heuristic.put("Mehadia", 241);
        heuristic.put("Drobeta", 242);
        heuristic.put("Craiova", 160);
        heuristic.put("Rimnicu Vilcea", 193);
        heuristic.put("Fagaras", 178);
        heuristic.put("Pitesti", 98);
        heuristic.put("Bucharest", 0);
        heuristic.put("Giurgiu", 77);
        heuristic.put("Urziceni", 80);
        heuristic.put("Vaslui", 199);
        heuristic.put("Iasi", 226);
        heuristic.put("Neamt", 234);
        heuristic.put("Hirsova", 151);
        heuristic.put("Eforie", 161);
    }

    public static Pair<List<String>, Integer> aStar(String start, String goal) {
        Map<String, Boolean> visited = new HashMap<>();
        PriorityQueue<Node> openSet = new PriorityQueue<>(new AStarComparator());
        openSet.offer(new Node(start, 0, heuristic.get(start), null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.city.equals(goal)) {
                List<String> path = new ArrayList<>();
                int totalDistance = current.g;
                while (current != null) {
                    path.add(current.city);
                    current = current.parent;
                }
                Collections.reverse(path);
                return new Pair<>(path, totalDistance);
            }

            visited.put(current.city, true);

            for (Map.Entry<String, Integer> neighbor : graph.getOrDefault(current.city, Collections.emptyMap()).entrySet()) {
                if (!visited.getOrDefault(neighbor.getKey(), false)) {
                    int g = current.g + neighbor.getValue();
                    int h = heuristic.get(neighbor.getKey());
                    openSet.offer(new Node(neighbor.getKey(), g, h, current));
                }
            }
        }

        return new Pair<>(Collections.emptyList(), 0);
    }

    public static void main(String[] args) {
        String start = "Arad";
        String goal = "Bucharest";

        Pair<List<String>, Integer> result = aStar(start, goal);
        List<String> path = result.getFirst();
        int totalDistance = result.getSecond();

        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i != path.size() - 1) {
                System.out.print("->");
            }
        }
        System.out.println();
        System.out.println("Total distance: " + totalDistance);
    }

    static class Pair<A, B> {
        private A first;
        private B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public B getSecond() {
            return second;
        }
    }
}
