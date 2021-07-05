@file:Suppress("UNUSED", "UNCHECKED_CAST")
package me.hwiggy.reflection

import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Extracts the Field with the specified name and returns the value of the field for the parameter object
 * @param[extractor] Either T::getDeclaredField or T::getField
 * @param[name] The name of the Field to extract
 * @param[param] The parameter object to use to retrieve the Field value; null for static fields.
 */
private fun <R : Any?> readField(
    extractor: (String) -> Field, name: String, param: Any? = null
): R = extractor(name).also { it.isAccessible = true }.let { it.get(param) as R }

private fun <R : Any?> invokeMethod(
    extractor: (String, Array<out Class<*>>) -> Method,
    name: String,
    source: Any? = null,
    paramTypes: Array<out Class<*>>,
    vararg params: Any?
): R = extractor(name, paramTypes).also { it.isAccessible = true }.invoke(source, params) as R

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