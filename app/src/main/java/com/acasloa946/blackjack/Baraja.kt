package com.acasloa946.blackjack

class Jugador() {

    /**
     * Companion objects de métodos y variables de la baraja
     */
        val mano = mutableListOf<Carta>()
        val listaDeResources = mutableListOf(
            R.drawable.as1,
            R.drawable.as2,
            R.drawable.as3,
            R.drawable.as4,
            R.drawable.c2,
            R.drawable.c15,
            R.drawable.c28,
            R.drawable.c41,
            R.drawable.c3,
            R.drawable.c16,
            R.drawable.c29,
            R.drawable.c42,
            R.drawable.c4,
            R.drawable.c17,
            R.drawable.c30,
            R.drawable.c43,
            R.drawable.c5,
            R.drawable.c18,
            R.drawable.c31,
            R.drawable.c44,
            R.drawable.c6,
            R.drawable.c19,
            R.drawable.c32,
            R.drawable.c45,
            R.drawable.c7,
            R.drawable.c20,
            R.drawable.c33,
            R.drawable.c46,
            R.drawable.c8,
            R.drawable.c21,
            R.drawable.c34,
            R.drawable.c47,
            R.drawable.c9,
            R.drawable.c22,
            R.drawable.c35,
            R.drawable.c48,
            R.drawable.c10,
            R.drawable.c23,
            R.drawable.c36,
            R.drawable.c49,
            R.drawable.c11,
            R.drawable.c24,
            R.drawable.c37,
            R.drawable.c50,
            R.drawable.c12,
            R.drawable.c25,
            R.drawable.c38,
            R.drawable.c51,
            R.drawable.c13,
            R.drawable.c26,
            R.drawable.c39,
            R.drawable.c52,
        )
        //variables necesarias
        lateinit var Carta : Carta
        var listaCartas = arrayListOf<Carta>()

        var ultimaCarta = false

        /**
         * Función para generar la baraja
         */
        fun crearBaraja() {
            var contFotos = 4
            for (i in Naipes.values()) {
                for (j in Palos.values()) {
                    //controlo si son ASES para añadirlos con sus puntuacions (1 y 11)
                    if (i == Naipes.AS) {
                        if (j == Palos.CORAZONES) {
                            listaCartas.add(Carta(i,j,1,11,listaDeResources[0]))
                        }
                        if (j == Palos.DIAMANTES) {
                            listaCartas.add(Carta(i,j,1,11,listaDeResources[1]))
                        }
                        if (j == Palos.TREBOLES) {
                            listaCartas.add(Carta(i,j,1,11,listaDeResources[3]))
                        }
                        if (j == Palos.PICAS) {
                            listaCartas.add(Carta(i,j,1,11,listaDeResources[4]))

                        }
                    }
                    else if(i == Naipes.ROI || i == Naipes.VALET || i == Naipes.DAMA) {
                        listaCartas.add(Carta(i,j,10,10,listaDeResources[contFotos]))
                        contFotos++
                    }
                    //sino, el valor es el mismo.
                    else {
                        listaCartas.add(Carta(i,j,i.ordinal+1,i.ordinal+1, listaDeResources[contFotos]))
                        contFotos++
                    }
                }
            }

        }

        /**
         * Función para barajar.
         */
        fun barajar() {
            listaCartas.shuffle()
        }

        /**
         * Función que devuelve la última carta de la baraja y la borra.
         * @return Carta -> Carta devuelta
         */
        fun dameCarta():Carta {
            if (listaCartas.isNotEmpty()){
                listaCartas.remove(listaCartas.last())
                Carta = listaCartas.last()
                if (listaCartas.size==1) {
                    ultimaCarta = true
                }
                return Carta
            }

            return Carta
        }

        /**
         * Función para borrar la baraja (solo se borra cuando se pulsa boton reiniciar).
         */
        fun borrarBaraja() {
            listaCartas.clear()
        }
    }
