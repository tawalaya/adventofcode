

fun main(args: Array<String>) {
    if (args.size <= 1) {
        print("missing filename and option")
        System.exit(-1)
    }


    val placementDetector = PlacementDetector(args[1].toInt())


    for( rect in readFile(args[0])){
        placementDetector.insert(rect)
    }

    val overlapping = placementDetector.calcOverlap()

    print("#overlap ${overlapping.size}\n")

    print("Area ${placementDetector.calculateOverlappingArea()} \n")

    val rects = mutableSetOf<Rect>()

    rects.addAll(placementDetector.rects.values)
    rects.removeAll(overlapping)

    print(rects)

    placementDetector.printGrid()


}



