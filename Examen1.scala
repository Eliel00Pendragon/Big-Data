
//1
def Calcula(num:Int):Boolean ={return (num%2==0)}
var num = 7
println(Calcula(num))

//2
def encuentra(lista:List[Int])={
  for(i<-lista){
    println(Calcula(i))

  }}

val lista = List(1,4,3)
encuentra(lista)



//3
var sum = 0
val li = List(1,2,7)
var x = li.length
for(i<-0 to x){
  if(li(i)==7){
    sum = sum + 14
  }else{
    sum = sum + li(i)
  }
}
println(s"La suma es $sum")


//4
val lista2 = List(7,2,1)
var a = lista2.length
var b = (a%2)-1
var suma = 0
var z = b+1
for(z<-z to a){
  suma = suma + lista2(z)
}
if(lista2(b)==suma){
  (lista2(b)==suma)
}else{
  (lista2(b)==suma)
}
//5
def palin(palabra:String):Boolean ={
  return (palabra==palabra.reverse)
}
 val palabra = "oso"
 val palabra2 = "casa"

 println(palin(palabra))
 println(palin(palabra2))
