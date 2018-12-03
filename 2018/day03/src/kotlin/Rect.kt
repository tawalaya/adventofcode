import java.io.File
import java.util.stream.Stream

data class Rect(val id:Int, val x:Int,val y:Int,val widht:Int,val hight:Int){
    val left = x
    val right = x+widht;
    val top = y
    val bottom = y+hight

    fun isOVerlapping(rect:Rect) : Boolean{
        return !(left >= rect.right || right <= rect.left || top >= rect.bottom || bottom <= rect.top);

    }
}

fun readFile(filename:String) : Stream<Rect> {
    return File(filename).readLines().parallelStream().map {
        val values = it.split(" ")

        val id = values[0].substring(1)

        val cords = values[2].substring(0,values[2].length-1).split(",")

        val size = values[3].split("x")

        Rect(id.toInt(),cords[0].toInt(),cords[1].toInt(),size[0].toInt(),size[1].toInt())
    }

}

