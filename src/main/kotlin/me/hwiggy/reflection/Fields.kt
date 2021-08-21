@file:Suppress("UNUSED", "UNCHECKED_CAST")
package me.hwiggy.reflection

import java.lang.reflect.Field

/**
 * Extracts the Field with the specified name and returns the value of the field for the parameter object
 * @param[extractor] Either T::getDeclaredField or T::getField
 * @param[name] The name of the Field to extract
 * @param[param] The parameter object to use to retrieve the Field value; null for static fields.
 */
private fun <R : Any?> readField(
    extractor: (String) -> Field, name: String, param: Any? = null
): R = extractor(name).also { it.isAccessible = true }.let { it.get(param) as R }

/**
 * Reads the inherited instance Field with the specified name from this type
 * @param[name] The name of the Field to read
 * @param[obj] The parameter object to use to read the Field value
 */
fun <T : Any, U> Class<out T>.instanceField(
    name: String, obj: Any
): U = readField(this::getField, name, obj)

/**
 * Reads the inherited static Field with the specified name from this type
 * @param[name] The name of the Field to read
 */
fun <T : Any, U> Class<out T>.staticField(
    name: String
): U = readField(this::getField, name)

/**
 * Reads the Declared instance Field with the specified name from this type
 * @param[name] The name of the Field to read
 * @param[obj] The parameter object to use to read the Field value
 */
fun <T : Any, U> Class<out T>.declaredInstanceField(
    name: String, obj: Any
): U = readField(this::getDeclaredField, name, obj)

/**
 * Reads the Declared static Field with the specified name from this type
 * @param[name] The name of the Field to read
 */
fun <T : Any, U> Class<out T>.declaredStaticField(
    name: String
): U = readField(this::getDeclaredField, name)

/**
 * Reads the inherited instance Field with the specified name from the receiver type
 * @param[name] The name of the Field to read
 * @param[obj] The parameter object to use to retrieve the Field value; defaults to `this`
 */
fun <T : Any, U> T.instanceField(
    name: String, obj: Any = this
): U = this::javaClass.instanceField(name, obj)

/**
 * Reads the inherited static Field with the specified name from the receiver type
 * @param[name] The name of the Field to read
 */
fun <T : Any, U> T.staticField(name: String): U = this::javaClass.staticField(name)

/**
 * Reads the Declared instance Field with the specified name from the receiver type
 * @param[name] The name of the Field to read
 * @param[obj] The parameter object to use to retrieve the Field value; defaults to `this`
 */
fun <T : Any, U> T.declaredInstanceField(
    name: String, obj: Any = this
): U = this::javaClass.declaredInstanceField(name, obj)

/**
 * Reads the Declared static Field with the specified name from the receiver type
 * @param[name] The name of the Field to read
 */
fun <T : Any, U> T.declaredStaticField(name: String): U = this::javaClass.declaredStaticField(name)