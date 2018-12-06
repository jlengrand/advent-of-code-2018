fun fabricClaimFromString(string: String): FabricClaim{

    val idAndRest = string.split("@")
    val id = idAndRest[0].trim().replace("#", "")

    val originAndRest = idAndRest[1].split(":")
    val xAndYOrigin = originAndRest[0].trim().split(",")

    val widthAndHeight = originAndRest[1].trim().split("x")

    return FabricClaim(id.toInt(), xAndYOrigin[0].toInt(), xAndYOrigin[1].toInt(), widthAndHeight[0].toInt(), widthAndHeight[1].toInt())
}

class Cloth(private val width: Int = 1000, height: Int = 1000){
    val clothPixel = IntArray(width * height)

    fun claim(claim: FabricClaim){
        val startPixel = claim.xOrigin + claim.yOrigin * width
        for(h in 0 until claim.height){
            for(w in 0 until claim.width){
                val pixel = startPixel + width*h + w
                clothPixel[pixel] += 1
            }
        }
    }

    fun isSingleClaim(claim: FabricClaim): Boolean{
        val startPixel = claim.xOrigin + claim.yOrigin * width
        for(h in 0 until claim.height){
            for(w in 0 until claim.width){
                val pixel = startPixel + width*h + w
                if (clothPixel[pixel] != 1) return false
            }
        }

        return true
    }

    override fun toString(): String{
        var res = ""
        for(i in 1 until clothPixel.size){
            res += clothPixel[i-1]
            if(i % width == 0) res += "\n"
        }
        return res
    }
}

class FabricClaim(val id: Int, val xOrigin: Int, val yOrigin: Int, val width: Int, val height: Int)

fun main(args : Array<String>) {
    val res = readFileAsLinesUsingUseLines(ClassLoader.getSystemResource("input3.txt").file)
    val claims = res.map { x -> fabricClaimFromString(x) }

    val cloth = Cloth()
    for(claim in claims) cloth.claim(claim)
    println(cloth.clothPixel.filter { it > 1 }.size)

    val resClaim = claims.map { x -> Pair(x, cloth.isSingleClaim(x)) }.filter { x -> x.second }
    println(resClaim[0].first.id)
}

