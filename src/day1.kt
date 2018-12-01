import java.io.File
import kotlin.system.exitProcess

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
        = File(fileName).useLines { it.toList() }

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input.txt").file)

    val final = res.foldRight(0, {x: String, acc: Long -> acc + x.toLong() })
    println(final)

    val res2 = mutableListOf<Long>()
    res.forEach { x -> if(res2.isEmpty()) res2.add(x.toLong()) else res2.add(res2.last() + x.toLong()) }


    // This is horrible crap
    val frequencies = mutableListOf<Long>()
    var currentFreq : Long = 0
    while(true){
        for(x in res){
            val addFreq = x.toLong()
            currentFreq += addFreq

            if(frequencies.isNotEmpty() && frequencies.contains(currentFreq)){
                println(currentFreq)
                exitProcess(-1)
            }
            else{
                frequencies.add(currentFreq);
            }
        }
    }
}

