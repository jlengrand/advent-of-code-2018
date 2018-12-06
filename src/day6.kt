import kotlin.math.abs
import kotlin.math.max

class Point(val x: Int, val y: Int, val id: Int = 0){
    companion object {
        private var id = 0
        fun fromString(s : String): Point{
            id += 1
            val coords = s.split(",")
            return Point(coords[0].trim().toInt(), coords[1].trim().toInt(), id)
        }
    }
}

class Grid(size: Point){
    private val width = size.x
    private val height = size.y
    private val gridPixel = IntArray(width * height)

    fun applyManhattan(points: List<Point>){
        for(i in 0..width*height-1){
            if(gridPixel[i] == 0) {
                val currPoint = Point(i % width, i / width)
                gridPixel[i] = findNearestPoint(currPoint, points)
            }
        }
    }

    fun applyManhattanSum(points: List<Point>){
        for(i in 0..width*height-1){
            if(gridPixel[i] == 0) {
                val currPoint = Point(i % width, i / width)
                gridPixel[i] = manhattanSumForPoint(currPoint, points)
            }
        }
    }

    fun drawPoints(points: List<Point>){
        for(point in points){
            gridPixel[point.x + width * point.y] = point.id
        }
    }

    private fun manhattanSumForPoint(p: Point, points: List<Point>) : Int {
        return if (points.map { x -> Pair(x.id, calculateManhattan(x, p)) }.sortedBy { x -> x.second }.sumBy { it.second } < 10000 ) 1 else 0
    }

    private fun findNearestPoint(p: Point, points: List<Point>) : Int{
        val manhs = points.map { x -> Pair(x.id, calculateManhattan(x, p)) }.sortedBy { x -> x.second }
        return if(manhs[0].second == manhs[1].second) -1 else manhs[0].first
    }

    private fun calculateManhattan(p1: Point, p2: Point): Int{
        return abs(p2.x - p1.x) + abs(p2.y - p1.y)
    }

    fun getInnerAreas() : Map<Int, Int>{
        val bordersBot = gridPixel.slice(IntRange(width * (height - 1), width * height - 1)).toList()

        val rightIndices = IntArray(height)
        for(i in 0..height-1) { rightIndices[i] = (width) * i + (width - 1) }
        val bordersRight = gridPixel.sliceArray(rightIndices.toList()).toList()

        val bordersTop = gridPixel.slice(IntRange(0, width - 1)).toList()

        val leftIndices = IntArray(height)
        for(i in 0..height-1) { leftIndices[i] = (width) * i }
        val bordersLeft = gridPixel.sliceArray(leftIndices.toList()).toList()

        val borders = (bordersBot + bordersRight + bordersTop + bordersLeft).distinct()
        return  gridPixel.toList().groupingBy { it }.eachCount().filter { x -> !borders.contains(x.key) }
    }

    fun getSum(): Int{
        return gridPixel.sum()
    }

    override fun toString(): String{
        var res = "";
        for(i in 1..gridPixel.size){
            res += gridPixel[i-1]
            if(i % width == 0) res += "\n"
        }
        return res
    }
}

fun findMax(points: List<Point>): Point{
    val max = points.foldRight(Point(0, 0), {point, finalPoint -> Point(max(point.x, finalPoint.x), max(point.y, finalPoint.y))})
    return Point(max.x + 1, max.y + 1)
}

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input6.txt").file)
    val coords = res.map { Point.fromString(it) }

    val maxPoint = findMax(coords)
    val grid = Grid(maxPoint)
//    grid.drawPoints(coords)
    grid.applyManhattan(coords)

    val areas = grid.getInnerAreas()
    println(areas.maxBy { x -> x.value })

     //Part 2
    val grid2 = Grid(maxPoint)
    grid2.applyManhattanSum(coords)
    println(grid2.getSum())
}