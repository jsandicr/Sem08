package com.sem08.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lugar(
    /*@PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "correo")
    val correo: String?,
    @ColumnInfo(name = "telefono")
    val telefono: String?,
    @ColumnInfo(name = "web")
    val web: String?*/

    var id: String,
    val nombre: String,
    val correo: String?,
    val telefono: String?,
    val web: String?
) : Parcelable {
    constructor():
        this("", "", "", "", "")
}