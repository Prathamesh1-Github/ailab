package CSP;

import java.util.*;

public class c_m_bfs {
    
    static boolean isGoal(int[] a) {
        return a[2] == 3 && a[3] == 3;
    }
    
    static boolean notValid(int[] a) {
        return (a[1] > a[0] && a[0] != 0) || ((a[3] > a[2]) && a[2] != 0);
    }

    public static void main(String[] args) {
        int[] a = {3, 3, 0, 0, 0};
        int ch = 0;
        Queue<Pair<List<Integer>, List<Pair<Character, Character>>>> q = new LinkedList<>();
        List<Pair<Character, Character>> ans = new ArrayList<>();
        q.add(new Pair<>(Arrays.asList(a[0], a[1], a[2], a[3], a[4]), ans));
        while (!q.isEmpty()) {
            List<Integer> curr = q.peek().getKey();
            List<Pair<Character, Character>> ans2 = q.peek().getValue();
            q.poll();
            if (!notValid(curr)) {
                continue;
            }

            if (isGoal(curr.toArray(new Integer[0]))) {
                for (Pair<Character, Character> c : ans2) {
                    System.out.println(c.getKey() + " " + c.getValue());
                }
                break;
            }
            for (int i = 0; i <= 2; i++) {
                for (int j = 0; j <= 2; j++) {
                    if (i + j <= 2 && i + j > 0) {
                        List<Integer> b = new ArrayList<>(curr);
                        if (curr.get(4) == 0 && b.get(0) >= i && b.get(1) >= j) {
                            b.set(0, b.get(0) - i);
                            b.set(1, b.get(1) - j);
                            b.set(2, b.get(2) + i);
                            b.set(3, b.get(3) + j);
                            Pair<Character, Character> boat = new Pair<>('#', '#');
                            if (i == 2) {
                                boat = new Pair<>('M', 'M');
                            }
                            if (j == 2) {
                                boat = new Pair<>('C', 'C');
                            }
                            if (i == 1) {
                                boat = new Pair<>('M', '#');
                            }
                            if (j == 1) {
                                boat = new Pair<>('#', 'C');
                            }
                            b.set(4, 1 - b.get(4));
                            ans2.add(boat);
                            q.add(new Pair<>(b, new ArrayList<>(ans2)));
                            ans2.remove(ans2.size() - 1);
                        } else if (curr.get(4) == 1 && b.get(2) >= i && b.get(3) >= j) {
                            b.set(0, b.get(0) + i);
                            b.set(1, b.get(1) + j);
                            b.set(2, b.get(2) - i);
                            b.set(3, b.get(3) - j);
                            Pair<Character, Character> boat = new Pair<>('#', '#');
                            if (i == 2) {
                                boat = new Pair<>('M', 'M');
                            }
                            if (j == 2) {
                                boat = new Pair<>('C', 'C');
                            }
                            if (i == 1) {
                                boat = new Pair<>('M', '#');
                            }
                            if (j == 1) {
                                boat = new Pair<>('#', 'C');
                            }
                            b.set(4, 1 - b.get(4));
                            ans2.add(boat);
                            q.add(new Pair<>(b, new ArrayList<>(ans2)));
                            ans2.remove(ans2.size() - 1);
                        }
                    }
                }
            }
        }
    }
}

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
