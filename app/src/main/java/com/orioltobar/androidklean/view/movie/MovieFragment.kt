package com.orioltobar.androidklean.view.movie

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.orioltobar.androidklean.R
import com.orioltobar.androidklean.base.BaseFragment
import com.orioltobar.androidklean.extensions.getDominantColor
import com.orioltobar.domain.models.ErrorModel
import com.orioltobar.domain.models.movie.MovieModel
import com.orioltobar.features.UiStatus
import com.orioltobar.features.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.movie_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MovieFragment : BaseFragment() {

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.movie_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, vmFactory).get(MovieViewModel::class.java)
        viewModel.execute(args.id)

        viewModel.movieDataStream.observe(
            viewLifecycleOwner,
            Observer<UiStatus<MovieModel, ErrorModel>> { handleUiStates(it, ::processNewValue) })
    }

    private fun processNewValue(model: MovieModel) {
        movieFragmentTitle.text = model.tittle

        movieFragmentReleaseDate.text = model.releaseDate
        movieFragmentOverview.text = model.overview

        context?.let {
            movieFragmentRate.text =
                it.resources.getString(R.string.rate, model.voteAverage.toString())
            // Image
            Glide.with(it)
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
                            movieFragmentBackground.setBackgroundColor(resource.getDominantColor())
                            movieFragmentBackground.background.alpha = 80
                        }
                        return false
                    }
                })
                .into(movieFragmentImage)
        }

        progressBar.visibility = View.GONE
    }

    override fun onError(error: ErrorModel) {
        println("TRACK STATUS: ERROR!")
    }

    override fun onLoading() {
        progressBar.visibility = View.VISIBLE
        println("TRACK STATUS: LOADING...")
    }
}