package com.enesgemci.mamasandpapas.data.base

import java.io.Serializable

/**
 * Created by enesgemci on 21/11/2016.
 */
abstract class JsonData : Serializable {

//    override fun toString(): String {
//        return Utils.serializeObject(this)
//    }

    fun containsStringValue(filterText: String): Boolean {
        val fields = javaClass.declaredFields

        try {
            for (field in fields) {
                try {
                    field.isAccessible = true
                } catch (e: Exception) {
                }

                try {
                    if (field.type.superclass == JsonData::class.java) {
                        return (field.get(this) as JsonData).containsStringValue(filterText)
                    } else {
                        if (field.type == String::class.java || field.type == Int::class.java || field.type == Double::class.java) {
                            val value = "" + field.get(this)

                            if (value.toLowerCase().contains(filterText.toLowerCase()) ||
                                    value.toLowerCase().contains(filterText.toLowerCase()) ||
                                    value.toLowerCase().contains(filterText.toLowerCase()) ||
                                    value.toLowerCase().contains(filterText.toLowerCase())) {
                                return true
                            }
                        }
                    }
                } catch (e: Exception) {
                }

            }
        } catch (e: Exception) {
        }

        return false
    }
}