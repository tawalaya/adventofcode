import java.lang.Math.*

fun main(args: Array<String>) {
    print (star5(325489))

    //star6 only generative approach comes to mind, and that is to much work :)
}

fun star5(input:Int):Int{
    var ringEdge = 1
    var ring = 1

    //use the odd squres to find ring location of the anser
    while(ringEdge < input){
        ring += 2
        ringEdge = pow(ring.toDouble(), 2.0).toInt()
    }

    val offset = ringEdge - input
    val steps = offset % (ring - 1)

    return (ring - 1) / 2 + abs((ring / 2) - steps);
}

