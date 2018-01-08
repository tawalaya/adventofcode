import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

fun main(args: Array<String>) {
    val firewall = parse(Files.lines(Paths.get("input.txt")))


    var score = 0
    var pos = 0
    val maxDepth = firewall.keys.max()!!
    while (pos < maxDepth){
        val scanner = updateScanner(firewall,pos)
        if(scanner.getOrDefault(pos,-1) == 0){
            score += firewall.getOrDefault(pos,0)*pos
        }

        pos++
    }
    print("$score\n")
}

fun updateScanner(firewall: Map<Int, Int>,pos:Int = 0): Map<Int, Int> {
    val scanner = mutableMapOf<Int,Int>()
    for (e in firewall) {

    }
    return  scanner
}

fun parse(lines: Stream<String>): Map<Int,Int> {
    val data = mutableMapOf<Int,Int>()
    lines.map { it.split(": ") }.forEach { data[it[0].toInt()] = it[1].toInt() }
    return data
}
