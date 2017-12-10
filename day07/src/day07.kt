import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

data class Node(val name:String, val wight:Int,val nodes:ArrayList<Node> = ArrayList(),var root:Node?=null){
    override fun equals(other: Any?): Boolean {
        if(other is String){
            return name.equals(other)
        }
        return super.equals(other)
    }

    override fun toString(): String {
        return "$name $wight -> $nodes"
    }
}

fun main(args: Array<String>) {
    val edges: HashMap<String,HashSet<String>> = HashMap()
    val nodes: LinkedList<Node> = LinkedList()
    val done:Queue<Node> = LinkedList<Node>()
    Files.lines(Paths.get("input.txt"))
    .forEach {
        val lineData = it.split("->", ",")
        var (name, wight) = lineData[0].split(" ")
        wight = wight.substring(1, wight.length - 1)
        val node = Node(name,wight.toInt())

        if (lineData.size > 1) {
            val list = edges.getOrDefault(name,HashSet())
            (1 until lineData.size).mapTo(list) { lineData[it].trim() }
            edges.put(name,list)
            nodes.add(node)
        } else{
            done.add(node)
        }

    }



    while (!nodes.isEmpty()){
        val node = nodes.pop()
        if(!edges.contains(node.name)){
            done.offer(node)
        } else {
            val x = edges.getOrDefault(node.name,HashSet())
            for (d in done){
                if(x.contains(d.name)){
                    x.remove(d.name)
                    node.nodes.add(d)
                    d.root = node
                }
            }
            if(x.isEmpty()){
                done.offer(node)
            } else{
                nodes.offer(node)
            }

        }
    }

    val tree:Node = done.last()

    print(" task 01: ${tree.name} ${tree.nodes.map { it.name }}\n")

    val evilNode = detect(tree)

    print(" task 02 (manual): ${weight(evilNode.root!!).flatMap { it.value }.map { it.second }} ${evilNode.wight} " +
            "${weight(evilNode).flatMap { it.value }.map { it.first.wight }.sum() }}\n")

    print(" task 02: ${calcDifference(evilNode)}")
}

fun calcDifference(n:Node): Int {
    val rootWights = weight(n.root!!).map { it.value.first().second }

    assert(rootWights.size == 2)

    val difference = rootWights.first() - rootWights.last()

    return n.wight+difference
}

fun detect(n:Node):Node{
    val wights = weight(n)
    if(wights.keys.size > 1){
        val diff = wights.minBy { it.value.size }
        return detect(diff?.value?.first()?.first!!)
    } else {
        return n
    }
}

private fun weight(n: Node) = n.nodes.map { Pair(it, weighIn(it)) }.groupBy { it.second }

fun weighIn(n:Node):Int{

    val wight = n.nodes.sumBy { weighIn(it) }

    return wight+n.wight
}