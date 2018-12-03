
class PlacementDetector(val size:Int){
    val rects:MutableMap<Int,Rect> = mutableMapOf<Int, Rect>();

    val xList = arrayOfNulls<MutableSet<Rect>>(size)
    val yList = arrayOfNulls<MutableSet<Rect>>(size)



    private fun insertInto( rect:Rect, i:Int,list:Array<MutableSet<Rect>?>){
        if(list[i] == null){
            list[i] = mutableSetOf()
        }

        list[i]!!.add(rect)
    }

    /*
    add rect to interal data structures
     */
    fun insert( rect:Rect ){
        if(rect.x > size || rect.y > size){
            error("rect out of bounds")
            return
        }

        this.rects[rect.id] = rect

        for (i in rect.x..(rect.x+rect.widht-1)){
            insertInto(rect,i,xList)
        }

        for (i in rect.y..(rect.y+rect.hight-1)){
            insertInto(rect,i,yList)
        }
    }

    override fun toString(): String {
        return "$rects - ${xList.asList()}"
    }


    //uses a line scanning approach to only check rects that might overlap in one dimension
    fun calcOverlap() : List<Rect> {
        val overlapping = mutableSetOf<Rect>()
        for (i in 0 until xList.size){
            if (xList[i] == null){
                continue
            } else {
                if(xList[i]!!.size > 1){
                    for (rect in xList[i]!!){
                        if (overlapping.contains(rect)){
                            continue
                        }
                        for (xRect in xList[i]!!){
                            if (rect != xRect){
                                if (rect.isOVerlapping(xRect)){
                                    overlapping.add(rect)
                                    overlapping.add(xRect)
                                }
                            }
                        }
                    }
                }
            }

        }

        return overlapping.toList()
    }

    /*
        create a binary grid representation of the hole layout each rect incrementes the numner in the binay grid
     */
    fun createGrid() : UByteArray {
        val grid = UByteArray(size*size,  {0u})


        for (rect in rects.values){
            for (x in rect.x..(rect.x+rect.widht-1)){
                for (y in rect.y..(rect.y+rect.hight-1)){
                    grid[x+y*size] = grid[x+y*size].inc()
                }
            }
        }

        return grid
    }

    /**
     * counts all n
     */
    fun calculateOverlappingArea() : Int{
        val grid =createGrid()

        var area = 0
        for (i in grid){
            if(i > 1u){
                area++
            }
        }
        return area
    }

    fun printGrid() {
        val grid =createGrid()

        for(x in 0 until size){
            for (y in 0 until size){
                when {
                    grid[x+y*size] <= 0u -> print(".")
                    grid[x+y*size] <= 1u -> print("#")
                    else -> print("X")
                }
            }
            print("\n")
        }
    }

}