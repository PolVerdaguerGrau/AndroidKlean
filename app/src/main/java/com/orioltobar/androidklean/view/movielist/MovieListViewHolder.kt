package com.orioltobar.androidklean.view.movielist

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseViewHolder
import com.orioltobar.androidklean.extensions.getDominantColor
import com.orioltobar.domain.models.movie.MovieModel
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListViewHolder(parent: ViewGroup) :
    BaseViewHolder<MovieModel>(parent, R.layout.movie_list_item) {

    override fun update(model: MovieModel) {

        itemView.movieViewHolderTitle.text = model.tittle
        itemView.movieViewHolderVotes.text =
            itemView.resources.getString(R.string.rate, model.voteAverage.toString())
        itemView.movieViewHolderCountry.text = model.originalLanguage
        itemView.movieViewHolderGenre.text = model.genres?.genre?.let {
            it.getOrNull(0)?.name ?: ""
        } ?: ""
        itemView.movieViewHolderDate.text = model.releaseDate

        // Image
        Glide.with(itemView.context)
            .load(model.frontImageUrl)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        itemView.movieListItemConstraint.setBackgroundColor(resource.getDominantColor())
                        itemView.movieListItemConstraint.background.alpha = 80
                    }
                    return false
                }
            })
            .into(itemView.movieViewHolderCover)
    }
}