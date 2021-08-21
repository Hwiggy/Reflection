@file:Suppress("UNUSED", "UNCHECKED_CAST")
package me.hwiggy.reflection

import java.lang.reflect.Method

/**
 * Extracts the Method with the specified name and returns the result of the invocation
 * @param[extractor] The extractor responsible for obtaining a Method from a Class
 * @param[name] The name of the Method to extract
 * @param[source] The desired caller of the Method, if applicable
 * @param[paramTypes] The signature of the Method
 * @param[params] The parameters to supply to this Method
 */
private fun <R : Any?> invokeMethod(
    extractor: (String, Array<out Class<*>>) -> Method,
    name: String,
    source: Any? = null,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): R = extractor(name, paramTypes).also { it.isAccessible = true }.invoke(source, params) as R

/**
 * Invokes the Declared instance Method with the specified name from this type
 * @param[name] The name of the Method to invoke
 * @param[obj] The object providing this method
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> Class<out T>.declaredInstanceMethod(
    name: String, obj: Any, paramTypes: Array<out Class<*>>, vararg params: Any?
): U = invokeMethod(this::getDeclaredMethod, name, obj, paramTypes, params)


/**
 * Invokes the Declared static Method with the specified name from this type
 * @param[name] The name of the Method to invoke
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> Class<out T>.declaredStaticMethod(
    name: String, paramTypes: Array<out Class<*>>, vararg params: Any?
): U = invokeMethod(this::getDeclaredMethod, name, null, paramTypes, params)

/**
 * Invokes the inherited instance Method with the specified name from this type
 * @param[name] The name of the Method to invoke
 * @param[obj] The object providing this method
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> Class<out T>.instanceMethod(
    name: String, obj: Any, paramTypes: Array<out Class<*>>, vararg params: Any?
): U = invokeMethod(this::getMethod, name, obj, paramTypes, params)

/**
 * Invokes the inherited static Method with the specified name from this type
 * @param[name] The name of the Method to invoke
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> Class<out T>.staticMethod(
    name: String, paramTypes: Array<out Class<*>>, vararg params: Any?
): U = invokeMethod(this::getMethod, name, null, paramTypes, params)

/**
 * Invokes the Declared instance Method with the specified name from this receiver parameter
 * @param[name] The name of the Method to invoke
 * @param[obj] The object providing this method, default 'this'
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> T.declaredInstanceMethod(
    name: String,
    obj: Any = this,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): U = this::javaClass.declaredInstanceMethod(name, obj, paramTypes, params)

/**
 * Invokes the Declared static Method with the specified name from this receiver parameter
 * @param[name] The name of the Method to invoke
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> T.declaredStaticMethod(
    name: String,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): U = this::javaClass.declaredStaticMethod(name, paramTypes, params)

/**
 * Invokes the inherited instance Method with the specified name from this receiver parameter
 * @param[name] The name of the Method to invoke
 * @param[obj] The object providing this method, default 'this'
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> T.instanceMethod(
    name: String,
    obj: Any = this,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): U = this::javaClass.instanceMethod(name, obj, paramTypes, params)

/**
 * Invokes the inherited static Method with the specified name from this receiver parameter
 * @param[name] The name of the Method to invoke
 * @param[paramTypes] Types for the signature of the desired method
 * @param[params] Parameters to be supplied to the desired method invocation
 */
fun <T : Any, U> T.staticMethod(
    name: String,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): U = this::javaClass.staticMethod(name, paramTypes, params)