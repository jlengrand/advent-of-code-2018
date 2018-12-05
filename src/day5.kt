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

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input51.txt").file)[0]
    var t2 = res.toMutableList()

    var nextPair = findFirstPair(t2)
    while(nextPair != -1){
        t2.removeAt(nextPair)
        t2.removeAt(nextPair)
        nextPair = findFirstPair(t2)
    }
    println(t2.size)
}