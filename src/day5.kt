fun isPair(a : Char,b: Char) : Boolean{
    return a.equals(b, true) && !(a.equals(b, false))
}

fun findFirstPair(list: List<Char>): Int{
    for(i in 0..list.size-2){
        if(isPair(list[i], list[i+1])){
            return i
        }
    }
    return -1
}

fun react(poly: MutableList<Char>): Int{
    var nextPair = findFirstPair(poly)
    while(nextPair != -1){
        poly.removeAt(nextPair)
        poly.removeAt(nextPair)
        nextPair = findFirstPair(poly)
    }
    return poly.size
}

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input51.txt").file)[0]
    var t2 = res.toMutableList()
    println(react(t2))

    // Part 2
    for(comp in 'a'.. 'z'){
        var t3 = t2.filter { x -> !x.equals(comp, true) }.toMutableList()
        println(comp + " " + react(t3))
    }
}