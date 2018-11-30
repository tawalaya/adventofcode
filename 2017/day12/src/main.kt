import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Stream

fun main(args: Array<String>) {
    val graph = mutableMapOf<Int,Node>()

    parse(graph,Files.lines(Paths.get("input.txt")))

    print("${clicke(graph[0]!!).size}\n")
    var groups = 0
    val open = LinkedList<Node>(graph.values)
    while (!open.isEmpty()){
        val clicke = clicke(node = open.pop())
        open.removeAll(clicke)
        groups++
    }
    print("$groups\n")

}

fun clicke(node: Node): MutableSet<Node> {
    val visited:MutableSet<Node> = mutableSetOf()
    val open:Stack<Node> = Stack<Node>()
    open.push(node)
    while (!open.empty()){
        val now = open.pop()
        now.edges.filter { !visited.contains(it) }.forEach { open.push(it) }
        visited.add(now)
    }
    return visited
}

fun parse(graph: MutableMap<Int,Node>, lines: Stream<String>) {
    lines.forEach {
        val adjecenyList = it.split(" <-> ")
        val nodeID = adjecenyList[0].toInt()
        val edges = adjecenyList[1].split(",").map { it.trim().toInt() }


        val node = if (graph.containsKey(nodeID)){
            graph[nodeID]!!
        } else{
            Node(nodeID)
        }

        edges.forEach {
            if (graph.containsKey(it)){
                node.edges.add(graph[it]!!)
            } else {
                val edge = Node(it)
                node.edges.add(edge)
                graph.put(it,edge)
            }
        }

        graph[nodeID] = node
    }
}

class Node(val id:Int,val edges:MutableSet<Node> = mutableSetOf()){
    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return if(other is Node){
            id == other.id
        } else {
            false
        }
    }

    override fun toString(): String {
        return "$id <-> ${edges.map { it.id }}"
    }
}