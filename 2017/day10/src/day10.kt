fun main(args: Array<String>) {
    val input = "106,16,254,226,55,2,1,166,177,247,93,0,255,228,60,36"

    print(elfHash(input, arrayListOf(17, 31, 73, 47, 23)))

}

private fun elfHash(input:String,seed: ArrayList<Int>): String {
    val lengths = convert(input,seed)
    val numSpace = ArrayList<Int>((0 until 256).toList())

    var skip = 0
    var cIdx = 0

    for (round in 0 until 64 ) {
        for (length in lengths){
            val view = indexList(cIdx,length,numSpace.size)
            readAndReverse(numSpace,view)
            cIdx=(cIdx+skip+length)%numSpace.size
            skip++
        }
    }

    return dense(numSpace)
}

fun convert(input: String,seed:ArrayList<Int>): ArrayList<Int>{
    val result = arrayListOf<Int>()
    for(v in input){
        result.add(v.toInt())
    }
    result.addAll(seed)
    return result
}

fun dense(input: ArrayList<Int>): String {
    return input
            .chunked(16)
            .map { it.reduce({acc, i -> acc xor i }) }
            .map { i -> String.format("%02x",i) }
            .reduce({acc, s -> acc.plus(s) })
}

fun readAndReverse(list:ArrayList<Int>,index:ArrayList<Int>){
    val tmp = arrayListOf<Int>()
    index.mapTo(tmp) { list[it] }
    tmp.reverse()
    for (idx in 0 until index.size){
        list[index[idx]] = tmp[idx]
    }
}
fun indexList(start:Int, step:Int, length:Int): ArrayList<Int> {
    var indexList = arrayListOf<Int>()
    var i = start
    for (x in 0 until step){
        indexList.add(i)
        i = (i+1)% length
    }
    return indexList
}