package com.example.letslikingcats.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalStateException
import javax.inject.Inject

private const val REQUEST_PERMISSION = 313

class Permissions @Inject constructor(private val activity: Activity) {

    private val behaviorSubject = BehaviorSubject.create<Pair<String, State>>()

    fun request(permission: String) : Completable {
        val permissionCheck =
            ContextCompat.checkSelfPermission(activity, permission)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            behaviorSubject.onNext(Pair(permission, State.REQUEST))
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                REQUEST_PERMISSION
            )
        } else {
            behaviorSubject.onNext(Pair(permission, State.SUCCESS))
        }

        return behaviorSubject
            .subscribeOn(Schedulers.io())
            .filter { it.first == permission && (it.second == State.SUCCESS || it.second == State.FAIL) }
            .map { it.second }
            .firstElement()
            .flatMapCompletable {
                return@flatMapCompletable when(it) {
                    State.SUCCESS -> Completable.complete()
                    State.FAIL -> Completable.error(IllegalStateException("permission is not granted"))
                    State.REQUEST -> Completable.error(IllegalStateException("state error"))
                }
            }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != REQUEST_PERMISSION) {
            return
        }
        if (permissions.isEmpty() && permissions.size > 1) {
            throw IllegalStateException("unhandled state")
        }
        if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            behaviorSubject.onNext(Pair(permissions[0], State.SUCCESS))
        } else {
            behaviorSubject.onNext(Pair(permissions[0], State.FAIL))
        }

    }

    enum class State {
        REQUEST, SUCCESS, FAIL
    }
}