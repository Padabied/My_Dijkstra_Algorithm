import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {

    static class Node {
        static int count = 0;
        private int number;
        int mark = Integer.MAX_VALUE;           //Длина кратчайшего пути в эту вершину
        boolean visited = false;
        private ArrayList<Node> nodes = new ArrayList<>();
        public Node() {
            this.number = count++;
        }
    }

    static class Arc {
        private Node start;
        private Node end;
        private int dist;
        public Arc(Node st, Node end, int dist) {
            this.start=st;
            this.end = end;
            this.dist = dist;
        }

        public Node getStart() {
            return start;
        }
        public Node getEnd() {
            return end;
        }
        public int getDist() {
            return dist;
        }
    }

    static class Graph {
        public Map<Integer, Node> nodes = new HashMap<>();
        int[][] matrix;
        Node start;   //Вершина-источник
        ArrayList<Arc> arcList = new ArrayList<>();

        //Сгенерировать граф на 7 вершин
        public static Graph getGraph() {
            Graph result = new Graph();
            for (int i = 0; i < 7; i++) {
                result.nodes.put(i, new Node());
            }
            //Создается граф
            result.nodes.get(0).nodes.add(result.nodes.get(2));
            result.nodes.get(1).nodes.add(result.nodes.get(0));
            result.nodes.get(1).nodes.add(result.nodes.get(3));
            result.nodes.get(1).nodes.add(result.nodes.get(4));
            result.nodes.get(2).nodes.add(result.nodes.get(4));
            result.nodes.get(3).nodes.add(result.nodes.get(5));
            result.nodes.get(4).nodes.add(result.nodes.get(6));
            result.nodes.get(5).nodes.add(result.nodes.get(4));
            result.nodes.get(6).nodes.add(result.nodes.get(1));
            //Создаются дуги между вершинами графа
            result.arcList.add(new Arc(result.nodes.get(0), result.nodes.get(2), 5 ));
            result.arcList.add(new Arc(result.nodes.get(1), result.nodes.get(0), 10 ));
            result.arcList.add(new Arc(result.nodes.get(1), result.nodes.get(4), 11 ));
            result.arcList.add(new Arc(result.nodes.get(1), result.nodes.get(3), 15 ));
            result.arcList.add(new Arc(result.nodes.get(2), result.nodes.get(4), 7 ));
            result.arcList.add(new Arc(result.nodes.get(3), result.nodes.get(5), 6 ));
            result.arcList.add(new Arc(result.nodes.get(4), result.nodes.get(6), 2 ));
            result.arcList.add(new Arc(result.nodes.get(5), result.nodes.get(4), 3 ));
            result.arcList.add(new Arc(result.nodes.get(6), result.nodes.get(1), 8 ));

            result.start = result.nodes.get(0);
            result.start.mark = 0;
            getMatrix(result);
            return result;
        }
        //Инициализация матрицы смежности
        private static void getMatrix(Graph graph) {
            int[][] result = new int[graph.nodes.size()][graph.nodes.size()];

            for (int i = 0; i < graph.nodes.size(); i++) {
                for (int j = 0; j < graph.nodes.size(); j++) {
                    if (graph.nodes.get(i).nodes.contains(graph.nodes.get(j))) {
                        result[i][j] = 1;
                    }
                    else {
                        result[i][j] = 0;
                    }
                }
            }
            graph.matrix = result;
        }
        //Печать матрицы смежности
        public void printMatrix () {
            for (int[] ints : matrix) {
                for (int anInt : ints) {
                    System.out.print(anInt + " ");
                }
                System.out.print("\n");
            }
        }
    }

    public static void dijkstra(Graph graph) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.push(graph.start);

        while (queue.peek() != null) {
            Node curr = queue.poll();
            ArrayList<Node> connections = curr.nodes;
            for (int i = 0; i < connections.size(); i++) {
                for (int j = 0; j < graph.arcList.size(); j++) {
                    Arc arc = graph.arcList.get(j);
                    if (arc.start.number == curr.number && arc.end.number == connections.get(i).number && !arc.end.visited) {
                        queue.push(arc.end);
                        if (arc.dist + arc.start.mark < arc.end.mark) {
                            arc.end.mark = arc.dist + arc.start.mark;
                        }
                    }
                }
            }
            curr.visited = true;
        }

        for (int i = 0; i < graph.nodes.size(); i++) {
            System.out.println(graph.nodes.get(i).number + "---" + graph.nodes.get(i).mark);
        }
    }

    public static void main(String[] args) {
        Graph graph = Graph.getGraph();
        graph.printMatrix();
        dijkstra(graph);
    }
}
