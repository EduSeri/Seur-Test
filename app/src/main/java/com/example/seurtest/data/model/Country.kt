package com.example.seurtest.data.model

import android.util.Log
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.text.Collator
import java.util.Locale

fun flagEmoji(iso: String) =
    iso.uppercase().map { String(Character.toChars(0x1F1E6 + (it - 'A'))) }.joinToString("")

data class Country(
    val name: String,
    val isoCode: String,
    val phonePrefix: String
) {
    val flag: String
        get() = flagEmoji(isoCode)
}

object CountryRepository {
    // Si en algún momento se quieren limitar los países a los que opera SEUR, definir aquí los códigos ISO
    val paisesDisponibles: Set<String>? = null // null = todos los países del mundo

    val countries: List<Country> by lazy {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val spanishCollator = Collator.getInstance(Locale("es")).apply {
            strength = Collator.PRIMARY
        }

        val allCountries = Locale.getISOCountries().mapNotNull { code ->
            if (paisesDisponibles != null && code !in paisesDisponibles) return@mapNotNull null

            val name = Locale("", code).getDisplayCountry(Locale("es"))
            if (name.isBlank() || name.equals(code, ignoreCase = true)) return@mapNotNull null

            val phoneCode = phoneUtil.getCountryCodeForRegion(code)
            if (phoneCode <= 0) return@mapNotNull null

            Country(
                name = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("es")) else it.toString() },
                isoCode = code,
                phonePrefix = "+$phoneCode"
            )
        }.sortedWith { c1, c2 ->
            spanishCollator.compare(c1.name, c2.name)
        }

        // Pin Spain ("ES") to the top if present
        val spain = allCountries.find { it.isoCode.equals("ES", ignoreCase = true) }
        val sortedList = if (spain != null) {
            listOf(spain) + allCountries.filter { !it.isoCode.equals("ES", ignoreCase = true) }
        } else {
            allCountries
        }

        Log.d("CountryRepository", "Loaded ${sortedList.size} countries successfully.")
        sortedList
    }
}

val commonCountries: List<Country>
    get() = CountryRepository.countries
