package com.example.letslikingcats.ui.common

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BindingPresenter<View : MvpView>: MvpPresenter<View>() {

    private val viewDisposable = CompositeDisposable()
    private val uiDisposable = CompositeDisposable()
    private val presenterDisposable = CompositeDisposable()

    protected open fun bind(disposable: Disposable, level: LifeLevel = LifeLevel.PER_UI) {
        when (level) {
            LifeLevel.PER_VIEW -> viewDisposable.add(disposable)
            LifeLevel.PER_UI -> uiDisposable.add(disposable)
            LifeLevel.PER_PRESENTER -> presenterDisposable.add(disposable)
        }
    }

    override fun detachView(view: View) {
        super.detachView(view)
        viewDisposable.clear()
    }

    override fun destroyView(view: View) {
        super.destroyView(view)
        uiDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterDisposable.clear()
    }

    protected open fun <T> onUI(single: Single<T>): Single<T> {
        return single.observeOn(AndroidSchedulers.mainThread())
    }

    protected open fun <T> onUI(maybe: Maybe<T>): Maybe<T> {
        return maybe.observeOn(AndroidSchedulers.mainThread())
    }

    protected open fun <T> onUI(observable: Observable<T>): Observable<T> {
        return observable.observeOn(AndroidSchedulers.mainThread())
    }

    protected open fun onUI(completable: Completable): Completable {
        return completable.observeOn(AndroidSchedulers.mainThread())
    }

    protected open fun <T> onUI(flowable: Flowable<T>): Flowable<T> {
        return flowable.observeOn(AndroidSchedulers.mainThread())
    }

    enum class LifeLevel {
        PER_VIEW, PER_PRESENTER, PER_UI
    }
}