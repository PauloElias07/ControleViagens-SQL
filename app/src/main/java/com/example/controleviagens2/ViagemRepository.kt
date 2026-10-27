package com.example.controleviagens2

import android.content.ContentValues
import android.content.Context

data class Viagem(
    val id: Int = 0,
    val data: String,
    val kmInicial: Double,
    val kmFinal: Double,
    val litros: Double,
    val valorCombustivel: Double,
    val pedagio: Double
) {
    fun calcularKm(): Double = kmFinal - kmInicial
    fun custoCombustivel(): Double = litros * valorCombustivel
    fun custoTotal(): Double = custoCombustivel() + pedagio
    fun mediaKmLitro(): Double = if (litros > 0) calcularKm() / litros else 0.0
}

class ViagemRepository(context: Context) {

    private val helper = BancoHelper(context.applicationContext)

    fun inserir(
        data: String,
        kmInicial: Double,
        kmFinal: Double,
        litros: Double,
        valorCombustivel: Double,
        pedagio: Double
    ): Boolean {
        val db = helper.writableDatabase

        val valores = ContentValues().apply {
            put("data", data)
            put("km_inicial", kmInicial)
            put("km_final", kmFinal)
            put("litros", litros)
            put("valor_combustivel", valorCombustivel)
            put("pedagio", pedagio)
        }

        val resultado = db.insert(
            "viagem",
            null,
            valores
        )

        return resultado != -1L
    }

    fun listar(): List<Viagem> {
        val db = helper.readableDatabase
        val viagens = mutableListOf<Viagem>()

        val cursor = db.rawQuery(
            "SELECT * FROM viagem",
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(
                    it.getColumnIndexOrThrow("id")
                )
                val data = it.getString(
                    it.getColumnIndexOrThrow("data")
                )
                val kmInicial = it.getDouble(
                    it.getColumnIndexOrThrow("km_inicial")
                )
                val kmFinal = it.getDouble(
                    it.getColumnIndexOrThrow("km_final")
                )
                val litros = it.getDouble(
                    it.getColumnIndexOrThrow("litros")
                )
                val valorCombustivel = it.getDouble(
                    it.getColumnIndexOrThrow("valor_combustivel")
                )
                val pedagio = it.getDouble(
                    it.getColumnIndexOrThrow("pedagio")
                )

                viagens.add(
                    Viagem(
                        id = id,
                        data = data,
                        kmInicial = kmInicial,
                        kmFinal = kmFinal,
                        litros = litros,
                        valorCombustivel = valorCombustivel,
                        pedagio = pedagio
                    )
                )
            }
        }

        return viagens
    }
}
