import java.util.*;

class Vertex {
    public int Value;
    public boolean Hit;

    public Vertex(int val) {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph {
    Vertex[] vertex;
    int[][] m_adjacency;
    int max_vertex;

    public SimpleGraph(int size) {
        max_vertex = size;
        m_adjacency = new int[size][size];
        vertex = new Vertex[size];
    }

    public void AddVertex(int value) {
        // ваш код добавления новой вершины
        // с значением value
        // в незанятую позицию vertex
        Vertex vertex = new Vertex(value);
        for (int i = 0; i < max_vertex; i++) {
            if (this.vertex[i] == null) {
                this.vertex[i] = vertex;
                break;
            }
        }
    }

    // здесь и далее, параметры v -- индекс вершины
    // в списке  vertex
    public void RemoveVertex(int v) {
        // ваш код удаления вершины со всеми её рёбрами
        vertex[v] = null;
        for (int i = 0; i < max_vertex; i++) {
            m_adjacency[v][i] = 0;
            m_adjacency[i][v] = 0;
        }
    }

    public boolean IsEdge(int v1, int v2) {
        // true если есть ребро между вершинами v1 и v2
        if (v1 >= max_vertex || v2 >= max_vertex || vertex[v1] == null || vertex[v2] == null) return false;
        return m_adjacency[v1][v2] == 1;
    }

    public void AddEdge(int v1, int v2) {
        // добавление ребра между вершинами v1 и v2
        if (vertex[v1] == null || vertex[v2] == null) return;
        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2) {
        // удаление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo) {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов - путь из VFrom в VTo.
        // Список пустой, если пути нету.

        ArrayList<Vertex> resultList = new ArrayList<>(); // результирующий список
        if (VFrom >= max_vertex || VTo >= max_vertex || vertex[VFrom] == null || vertex[VTo] == null) return resultList; // если нет переданных узлов
        Stack<Integer> indexStack = new Stack<>(); // стэк для поиска
        for (int i = 0; i < max_vertex; i++) vertex[i].Hit = false; // обнуление

        while (!IsEdge(VFrom, VTo)) { // метод IsEdge возвращает истину если ребро между узлами есть
            indexStack.push(VFrom);
            vertex[VFrom].Hit = true;
            for (int i = 0; i < max_vertex; i++) {
                if (IsEdge(VFrom, i) && !vertex[i].Hit) {
                    VFrom = i;
                    break;
                }
                if (i == max_vertex - 1) {
                    indexStack.pop();
                    if (!indexStack.empty()) VFrom = indexStack.pop();
                    else return resultList;
                }
            }
        }
        indexStack.push(VFrom);
        indexStack.push(VTo);
        while (!indexStack.empty()) resultList.add(0, vertex[indexStack.pop()]);
        return resultList;
    }
}