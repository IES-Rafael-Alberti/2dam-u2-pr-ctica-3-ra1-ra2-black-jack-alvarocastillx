package com.acasloa946.blackjack

class Carta(nombre:Naipes,palo:Palos,puntosMin:Int,puntosMax:Int,idDrawable:Int) {
    //variables que necesito
    var IdDrawable = idDrawable
    var Nombre = nombre
    var Palo = palo

    var PuntosMin = puntosMin
    var PuntosMax = puntosMax
    //constructor vacío para inicializar una Carta
    constructor():this(Naipes.AS,Palos.PICAS,0,0,0)
}