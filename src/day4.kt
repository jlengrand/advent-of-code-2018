import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap
import java.time.temporal.ChronoUnit

fun getId(string: String) : Int{
    return string.split("#")[1].split(" ")[0].toInt()
}

fun getDatePart(stringEntry: String) : String{
    return stringEntry.substring(1).split("]")[0]
}

fun toDate(string: String): LocalDateTime{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    return LocalDateTime.parse(string, formatter)
}

fun toDatedEntry(entry: String): DatedEntry{
    return DatedEntry(toDate(getDatePart(entry)), entry)
}

class Guard(){
    val minutesSlept = IntArray(60)

    fun addSleep(start: LocalDateTime, end: LocalDateTime){
        val minutes = ChronoUnit.MINUTES.between(start, end)
        for(i in 0..minutes-1){
            minutesSlept[start.plusMinutes(i).minute] += 1
        }
    }
}

class GuardPost{
    val guards = HashMap<Int, Guard>()
}

data class DatedEntry(val date: LocalDateTime, val entry: String)

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input4.txt").file)
    val datedEntries = res.map { x -> toDatedEntry(x) }.sortedBy { x -> x.date }

    val guardPost = GuardPost()
    var currentGuardId = 0
    var sleep = LocalDateTime.now()

    val iterator = datedEntries.listIterator()
    while(iterator.hasNext()){
        val line = iterator.next()
        if(line.entry.contains("Guard")) {
            currentGuardId = getId(line.entry)
        }
        else if(line.entry.contains("asleep")){
            sleep = line.date
        }
        else if(line.entry.contains("wakes")){
            var guard = guardPost.guards.get(currentGuardId)
            if(guard == null) {
                guard = Guard()
            }
            val wakeup = line.date
            guard.addSleep(sleep, wakeup)

            guardPost.guards.set(currentGuardId, guard)
        }
    }

    val maxSlept = guardPost.guards.mapValues { it.value.minutesSlept.sum() }
    val maxId= maxSlept.maxBy { it.value }

    // This is so ugly - Read https://kotlinlang.org/docs/reference/null-safety.html#checking-for-null-keyword--in-conditions
    println(maxId)
    val sleepingSchedule = guardPost.guards.get(2657)!!.minutesSlept
    val maxValue: Int = sleepingSchedule!!.max()!!
    val value = sleepingSchedule!!.indexOf(maxValue)
    println(value)


    val biggestMinute = guardPost.guards.mapValues { it.value.minutesSlept.max() }
    val biggestMinuteAndGuard = biggestMinute
    // Calculated the rest by hand to avoid having to play with nulls
}
