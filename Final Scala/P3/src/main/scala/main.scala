@main
def main(): Unit = {
  val objeto1 = Conjunto(( x:Int) => x<= -20 )
  val objeto2 = Conjunto( (x:Int) => x >= -63 && x <= 7 )
  val objeto3 = Conjunto.conjuntoUnElemento(26)
  println("Objeto 1 (x<=20): " + objeto1.toString)
  println("Objeto 2 (-63<=x<=7): " + objeto2.toString)
  println("Objeto 3 (un elemento): " + objeto3.toString+"\n")

  val union12 = Conjunto.union(objeto1,objeto2)
  val union23 = Conjunto.union(objeto2,objeto3)
  val interseccion12 = Conjunto.interseccion(objeto1,objeto2)
  val interseccion23 = Conjunto.interseccion(objeto2,objeto3)
  val diferencia = Conjunto.diferencia(objeto1,objeto2)
  println("Union de 1 y 2: " + union12.toString)
  println("Union de 2 y 3: " + union23.toString)
  println("Interseccion de 1 y 2: " + interseccion12.toString)
  println("Interseccion de 2 y 3: " + interseccion23.toString)
  println("Diferencia de 1-2: " + diferencia.toString + "\n")

  val filtrado = Conjunto.filtrar(objeto2,(x:Int)=>x>0)
  println("Filtrado de los numeros positivos del objeto 2: " + filtrado.toString)
  println("¿Son todos los elementos de 1 negativos? " + objeto1.paraTodo( (x: Int) => x<0 ))
  println("¿Son todos los elementos de 2 negativos? " + objeto2.paraTodo( (x: Int) => x<0 ))
  println("¿Existe algún positivo en el objeto 1? " + objeto1.existe((x: Int) => x > 0))
  println("¿Existe algún positivo en el objeto 3? " + objeto3.existe((x: Int) => x > 0))
  val map1 = objeto1.map( (x: Int) => 2*x )
  val map3 = objeto3.map( (x: Int) => 2*x )
  println("El objeto 1 contiene a los x menores o iguales a -20, es decir, x <= -20, si hacemos un map a su doble tal que x=>2x, \n" +
    "el nuevo conjunto contendrá a los elementos tal que 2x < -20, o lo que es lo mismo, los x < -10: " + map1.toString)
  print("De igual manera, con el objeto 3, 2x=26, x=13: " + map3.toString)
}