package com.pasha.mukminapp.data.remote


import com.google.gson.annotations.SerializedName

data class HijriahRequest(
    @SerializedName("tanggal_hijriyah")
    val tanggal_hijriyah: String? = "",
    @SerializedName("hijri_tanggal")
    val hijri_tanggal: Int? = null,
    @SerializedName("hijri_bulan")
    val hijri_bulan: Int? = null,
    @SerializedName("hijri_tahun")
    val hijri_tahun: Int? = null,
)