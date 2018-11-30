import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


fun main(args: Array<String>) {
    val line:String = Files.readAllLines(Paths.get("input.txt")).first()
    //val line = "<<<<>"
    val token:Stack<Char> = Stack()

    var i = 0
    var groups = 0
    var totalScore = 0
    var groupScore = 0
    var garbageCount = 0
    var garbage = false
    while (i<line.length){
        val c = line[i]

        when(c){
            '!' -> {i+=1; garbageCount-=1;}  // skip next
            '<' -> if(token.empty() || token.peek() != '>') {
                token.push('>')
                garbageCount-=1 //thats an ugly fix :P
                garbage = true
            }
            '{' -> if (token.empty() || token.peek() != '>') {
                token.push('}')
                groupScore += 1
            }
            '}' -> if (token.peek() == '}') {
                token.pop()
                totalScore += groupScore
                groupScore -= 1
                groups++
            }
            '>' -> if(token.peek() == '>') {
                token.pop()
                garbage = false
            }
        }
        if(garbage){
            garbageCount++
        }
        i++
    }
    print(" stack:$token groups:$groups score:$totalScore garbageCount:$garbageCount\n")
}