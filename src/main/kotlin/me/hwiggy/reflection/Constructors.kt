@file:Suppress("UNUSED", "UNCHECKED_CAST")
package me.hwiggy.reflection

import java.lang.reflect.Constructor

/**
 * Extracts the Constructor with the specified signature and returns the result of the invocation
 * @param[extractor] The extractor responsible for obtaining a Constructor from a Class
 * @param[paramTypes] The signature of the Constructor, default empty array
 * @param[params] The parameters to supply to this Constructor
 */
private fun <T : Any> invokeConstructor(
    extractor: (Array<out Class<*>>) -> Constructor<T>,
    paramTypes: Array<out Class<*>> = emptyArray(),
    vararg params: Any?
): T = extractor(paramTypes).also { it.isAccessible = true }.newInstance(*params)

/**
 * Extracts and invokes the Declared Constructor with the specified signature
 * @param[paramTypes] The signature of the Constructor, default empty array
 * @param[params] The parameters to supply to this Constructor
 */
fun <T : Any> Class<out T>.declaredConstructor(
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): T = invokeConstructor({ this.getDeclaredConstructor(*it) }, paramTypes, params)

/**
 * Extracts and invokes the inherited Constructor with the specified signature
 * @param[paramTypes] The signature of the Constructor, default empty array
 * @param[params] The parameters to supply to this Constructor
 */
fun <T : Any> Class<out T>.constructor(
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): T = invokeConstructor({ this.getConstructor(*it) }, paramTypes, params)

/**
 * Extracts and invokes the Declared Constructor with the specified signature
 * @param[paramTypes] The signature of the Constructor, default empty array
 * @param[params] The parameters to supply to this Constructor
 */
fun <T : Any> T.declaredConstructor(
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): T = this.javaClass.declaredConstructor(paramTypes, params)

/**
 * Extracts and invokes the inherited Constructor with the specified signature
 * @param[paramTypes] The signature of the Constructor, default empty array
 * @param[params] The parameters to supply to this Constructor
 */
fun <T : Any> T.constructor(
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): T = this.javaClass.constructor(paramTypes, params)