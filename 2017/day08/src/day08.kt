import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.max

fun main(args: Array<String>) {
    val parser:Regex = Regex("([a-z]+) (inc|dec) (-?[0-9]*) (if) ([a-z]+) (!=|==|>=|<=|>|<) (-?[0-9]*)")

    val lines = Files.lines(Paths.get("input.txt"))

    val code = lines.map { parser.find(it)?.groupValues }

    val registers:HashMap<String,Int> = HashMap()

    var totalMax =  0
    code.forEach {
        //firt eval condition
        val data = registers.getOrDefault(it!![5],0)
        val test = it!![7].toInt()
        val conditon = when(it[6]){
            "==" -> data == test
            ">=" -> data >= test
            "<=" -> data <= test
            ">" -> data > test
            "<" -> data < test
            "!=" -> data != test
            else -> false
        }
        var register = registers.getOrDefault(it[1],0)
        if(conditon){
            when(it[2]){
                "inc" -> register += it[3].toInt()
                "dec" -> register -= it[3].toInt()
            }
            registers.put(it[1],register)
        }
        totalMax = max(totalMax,registers.values.max()!!)

    }
    print("registers: $registers\n")
    print("maxValue: ${registers.values.max()}\n")
    print("totalMax: $totalMax\n")
}