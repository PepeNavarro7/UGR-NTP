class Conjunto (funcion: Int => Boolean){
  private val LIMITE = 1000000
  // metodo apply que se puede llamar sin nombre con "objeto(x)" y que devuelve la pertenencia o no de un entero al conjunto
  def apply(x: Int): Boolean = funcion(x)

  override def toString: String = {
    val aux: String = contenido()
    if aux.isEmpty then
      "El conjunto es vacío"
    else
      "Conjunto -> "+aux
  }

  /* "Comprueba si un determinado predicado se cumple para todos los elementos del conjunto." 
  * Es decir, comprobar si todos los elementos de un conjunto cualquiera cumplen el ser negativos, por ejemplo.
  * Una forma de ver esto es calcular si el conjunto inicial es subconjunto del que forma la funcion
  * Esto lo calculamos con la diferencia 
  * Si al conjunto inicial le restamos el que forma la función, el resultado será el vacío sí y sólo sí se 
  * cumple el predicado para todos los elementos */
  def paraTodo(f: Int => Boolean): Boolean = {
    val aux = Conjunto(f)
    Conjunto.diferencia(this, aux).contenido().isEmpty
  }

  // "Determina si un conjunto contiene al menos un elemento para el que se cumple un cierto predicado"
  // Si en un conjunto existe un elemento que cumple el predicado, la interseccion de ambos no debe ser vacía
  def existe(f: Int => Boolean): Boolean = {
    val aux = Conjunto(f)
    Conjunto.interseccion(this, aux).contenido().nonEmpty
  }

  // "Transforma un conjunto en otro aplicando una cierta funcion."
  // Buscamos crear un conjunto basado en una función en la que se componen la que se nos da como argumento, y la que forma el conjunto original
  def map(f: Int => Int): Conjunto = {
    val funcion: Int => Boolean = (x: Int) => this.apply(f(x))
    Conjunto(funcion)
  }

  /* Funcion auxiliar que usaremos para calcular de forma recursiva el contenido de un conjunto recorriendolo
  * La llamamos sin argumentos, pero para la funcion interna recursiva (go) tendremos 4 argumentos
  * st -> String con el contenido del conjunto, que se acabará devolviendo
  * desde -> entero desde el que se empieza a mirar en cada iteracion
  * izq -> booleano que nos indica si estamos buscando el principio o el final de un intervalo
  * primero -> booleando para el formatero de las uniones
  * Recorreremos el conjunto desde "desde" hasta LIMITE formando intervalos que uniremos
  * de la forma [-100,-50] U [-10,10] U {17}
  * Si izq==true buscamos el primer numero que esté en el conjunto (abrir el intervalo), o acabar el for;
  * y si izq==false buscamos el primer numero que NO esté en el conjunto (cerrar el intervalo),
  * o llegar a LIMITE, acabar el for, y cerrar ahí el intervalo */
  private def contenido(): String = {
    def go(st: String, desde: Int, izq: Boolean, primero: Boolean): String = {
      for i <- desde to LIMITE do // buscamos intervalos de numeros que estan, y de numeros que no estan
        if (this.apply(i) == izq) {
          if izq then // Abrimos un intervalo
            if this.apply(i + 1) then // es mas de un numero (es un intervalo realmente)
              if primero then return go(st + "[" + i + ",", i, false, false)
              else return go(st + " U [" + i + ",", i, false, false)
            else // es un numero suelto que pondremos {x}
              if primero then return go(st + "{" + i + "}", i + 1, true, false)
              else return go(st + " U {" + i + "}", i + 1, true, false)
          else // Cerramos el intervalo
            return go(st + (i - 1) + "]", i, true, false)
        }
      end for
      // Condicion de salida, cuando acabamos de recorrer todos los numeros
      if izq then st
      else st + LIMITE + "]"
    }
    go("",-LIMITE,true,true)
  }
}
// Tenemos un objeto compañero de la clase en el que definiremos los metodos que no dependen
// del objeto, los que en java serían métodos estáticos
object Conjunto {
  // Definimos la funcion que tendrá la instancia y llamamos al constructor con ella
  def conjuntoUnElemento(el: Int): Conjunto = {
    //val funcion = (_: Int) == x
    val funcion: Int => Boolean = (x: Int) => x == el
    this(funcion)
  }
  def union(c1: Conjunto, c2: Conjunto ): Conjunto = {
    val funcion: Int => Boolean = (x: Int) => c1(x) || c2(x)
    this(funcion)
  }

  def interseccion(c1: Conjunto, c2: Conjunto ): Conjunto = {
    val funcion: Int => Boolean = (x: Int) => c1(x) && c2(x)
    this(funcion)
  }

  def diferencia(c1: Conjunto, c2: Conjunto ): Conjunto = {
    val funcion: Int => Boolean = (x: Int) => c1(x) && !c2(x)
    this(funcion)
  }

  /* "Dado un conjunto y una funcion tipo Int => Boolean, devuelve como resultado un conjunto con los elementos que cumplen la condicion indicada."
  Entiendo que se pide un conjunto con los elementos del conjunto original que cumplen la condicion, vaya, la interseccion */
  def filtrar(c: Conjunto, f: Int => Boolean ): Conjunto = {
    val funcion: Int => Boolean = (x: Int) => c(x) && f(x)
    this(funcion)
  }
}
