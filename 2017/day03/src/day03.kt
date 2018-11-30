import java.lang.Math.*
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    print (star5(325489))
    print("\n")
    print (star6(325489))
}

fun star6(input: Int):Int{
    val grid = LinkedList<Point>()
    var index = HashMap<Int,HashMap<Int,Point>>()
    //star6 only generative approach comes to mind, and that is to much work :)
    var ring = 1 //level in the grid
    var x = 0
    var y = 0
    var state:segments = segments.right

    var i = 2 //runtime value
    var w: Boolean //flag if we write

    val seed = Point(1,0,0)
    grid.add(seed)
    index.put(0, hashMapOf(Pair(0,seed)))

    //ugly generator
    while(i < input){
        w =false;
        if(state == segments.right){
            if(x < ring){
                //this is ugly as .. but works fine :)
                x+=1
                w = true
            } else {
                state = segments.up
            }
        } else if(state == segments.up){
            if(y < ring){
                y+=1
                w = true
            } else {
                state = segments.left
            }
        } else if(state == segments.left){
            if(-x<ring){
                x-=1
                w = true
            } else {
                state = segments.down
            }
        } else {
            if(-y < ring){
                y-=1
                w = true
            } else {
                ring+=1
                state = segments.right
            }
        }
        //put a value into the grid
        if(w){
            //get all available sounding points using the hashIndex
            val neighbor = getNeighbor(x,y,index)

            val v = neighbor.sumBy { it.value }

            val p = Point(v,x,y)
            grid.add(p)
            val idx = index.getOrDefault(x, HashMap());
            idx[y]=p

            index.put(x,idx)

            i=v
        }
    }


    //plot(toArray(grid,ring))
    return i
}

fun getNeighbor(x: Int, y: Int, index: HashMap<Int, HashMap<Int, Point>>): LinkedList<Point> {
    val result = LinkedList<Point>()
    for(i in -1..1){
        for(j in -1..1){
            index[x+i]?.get(y+j)?.let { result.add(it) }
        }
    }
    return result
}

fun plot(iGrid: Array<IntArray>) {
    for(y in iGrid){
        for(i in y){
            print(String.format("%10d\t",i))
        }
        print("\n");
    }
}

fun toArray(grid:LinkedList<Point>,ring:Int): Array<IntArray> {
    val data = Array(ring*2+1, {IntArray(ring*2+1)})
    data[ring][ring] = 1
    for(p in grid){
        data[ring-p.y][ring+p.x] = p.value
    }

    return data

}

enum class segments{
    right,left,up,down;
}

data class Point(val value:Int,val x:Int,val y:Int);

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

