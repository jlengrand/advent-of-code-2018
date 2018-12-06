class Collector(private val twos: Int =0, private val threes : Int = 0){
    constructor(value: Map<Char, Int>) : this(if (countsForTwo(value)) 1 else 0, if (countsForThree(value)) 1 else 0)

    operator fun plus(toAdd: Collector) : Collector {
        return Collector(toAdd.twos + this.twos, toAdd.threes + this.threes)
    }

    fun combine(): Int{
        return this.twos * this.threes
    }
}

fun toNumberedSet(string: String): Map<Char, Int>{
    return string.toList().groupingBy { it }.eachCount()
}

fun countsForTwo(value: Map<Char, Int>): Boolean{
    return value.filter { it.value == 2 }.isNotEmpty()
}

fun countsForThree(value: Map<Char, Int>): Boolean{
    return value.filter { it.value == 3 }.isNotEmpty()
}

fun compare(s1: String, s2: String): Int{
    return (s1.toList() zip s2.toList()).foldRight(0) {x, acc -> if (x.first == x.second) acc else acc+1}
}

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input2.txt").file)
    println(res.map { x -> toNumberedSet(x) }.foldRight(Collector()) { x, acc -> acc + Collector(x)}.combine())

    for(x in res){
        for(y in res){
            if(compare(x, y) == 1) println("$x $y")
        }
    }
}

