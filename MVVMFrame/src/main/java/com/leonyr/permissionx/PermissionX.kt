package com.leonyr.permissionx

import kotlinx.coroutines.suspendCancellableCoroutine
import com.leonyr.permissionx.RequestPool.add
import com.leonyr.permissionx.RequestPool.remove

fun hasPermission(permission: String): Boolean {
    return checkPermission(permission)
}

fun hasPermissions(vararg permission: String): Boolean {
    return checkPermissions(permission)
}

suspend fun request(vararg permission: String) = suspendCancellableCoroutine<Result> {
    val request = Request(permission)
    add(request, it)
    it.invokeOnCancellation { remove(request) }
    exec(request)
}

