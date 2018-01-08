import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.stream.Collectors
import java.util.stream.Stream

fun main(args: Array<String>) {
    val input = toDirList(Files.lines(Paths.get("input.txt")))
    val path = reduce(input)
    print("part 1: ${path.size}\n")

    val threads = Executors.newCachedThreadPool()
    val jobs = (1 until input.size).map { threads.submit(Callable {  reduce(LinkedList(input.subList(0, it))).size }) }
    threads.shutdown()
    print("${jobs.maxBy { it.get() }!!.get()}")

}

fun toDirList(stream: Stream<String>):LinkedList<Direction>{
    return stream.flatMap { t -> t.split(",").stream() }.map { Direction.valueOf(it)  }.collect(Collectors.toCollection { LinkedList<Direction>() })
}

fun reduce(path:LinkedList<Direction>):List<Direction>{
    var i = path.size-1
    while (i > 0){
        val me = path[i]
        for (j in 0 until i){
            if(me == path[j].inverse()){
                path.removeAt(i)
                path.removeAt(j)
                i--
                break
            } else if (path[j].canReduce(me)) {
                path[j] = path[j].reduce(me)
                path.removeAt(i)
                break
            }
        }
        i--
    }
    return path
}

enum class Direction {
    n,ne,se,s,sw,nw;

    fun inverse():Direction{
        return when(this){
            n  -> s
            s  -> n
            nw -> se
            ne -> sw
            sw -> ne
            se -> nw
        }
    }

    fun counterclockwise():Direction{
        return when(this){
            n -> nw
            s -> se
            nw -> sw
            sw -> s
            ne -> n
            se -> ne
        }
    }

    fun clockwise():Direction{
        return when(this){
            n -> ne
            ne -> se
            se -> s
            s -> sw
            sw -> nw
            nw -> n
        }
    }

    fun reduce(direction: Direction): Direction {
        return when {
            counterclockwise().inverse() == direction -> clockwise()
            clockwise().inverse() == direction -> counterclockwise()
            else -> this
        }
    }

    fun canReduce(direction: Direction): Boolean {
        return when (direction) {
            counterclockwise().inverse() -> true
            clockwise().inverse() -> true
            else -> false
        }
    }
}