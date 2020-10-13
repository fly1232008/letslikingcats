package com.example.letslikingcats.features.snippets.cat

import android.view.*
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.bumptech.glide.Glide
import com.example.letslikingcats.R
import com.example.letslikingcats.business.models.Cat
import com.example.letslikingcats.business.cats.FavoriteToggleUseCase
import com.example.letslikingcats.features.downloadfiles.FileManager
import com.example.letslikingcats.utils.Permissions
import com.example.letslikingcats.utils.compositeadapter.CompositeAdapter
import com.example.letslikingcats.utils.compositeadapter.DelegateAdapter
import kotlinx.android.synthetic.main.item_cat.view.*
import moxy.MvpDelegate
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import moxy.presenter.ProvidePresenterTag
import javax.inject.Inject

class CatDelegateAdapter @Inject constructor(private val useCase: FavoriteToggleUseCase,
                                             private var fileManager: FileManager
): DelegateAdapter<Cat>() {

    lateinit var permissions: Permissions

    override fun onCreateViewHolder(
        parent: ViewGroup,
        delegate: MvpDelegate<*>
    ): CompositeAdapter.BaseViewHolder<Cat> =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false),
            delegate
        )

    override fun isForViewType(data: List<Any>, position: Int): Boolean = data[position] is Cat

    inner class ViewHolder(
        val view: View,
        delegate: MvpDelegate<*>
    ) : CompositeAdapter.BaseViewHolder<Cat>(view, delegate), CatItemView {

        @InjectPresenter
        lateinit var catItemPresenter: CatItemPresenter

        @ProvidePresenter
        fun providePresenter(): CatItemPresenter {
            catItemPresenter = CatItemPresenter(useCase, fileManager, permissions)
            return catItemPresenter
        }

        private val gestureDetector = GestureDetector(itemView.context, GestureListener())

        @ProvidePresenterTag(presenterClass = CatItemPresenter::class)
        fun providePresenterTag() : String = tag

        override fun bind(item: Cat) {
            catItemPresenter.item(item)
            Glide.with(itemView.image).load(item.url).into(itemView.image)
            itemView.image.setOnTouchListener { v, event ->  gestureDetector.onTouchEvent(event) }
        }

        override fun markFavorite() {
            itemView.star.visibility = View.VISIBLE
        }

        override fun markUnFavorite() {
            itemView.star.visibility = View.GONE
        }

        override fun showSavingImage() {
            Toast.makeText(itemView.context, "saving image...", LENGTH_SHORT).show()
        }

        override fun showImageSaved() {
            Toast.makeText(itemView.context, "image saved", LENGTH_SHORT).show()
        }

        private inner class GestureListener: GestureDetector.SimpleOnGestureListener() {

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                catItemPresenter.onDoubleTap()
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                super.onLongPress(e)
                catItemPresenter.onLongPress()
            }
        }
    }
}