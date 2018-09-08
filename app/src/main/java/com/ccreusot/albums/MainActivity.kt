package com.ccreusot.albums

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ccreusot.albums.adapters.AlbumsAdapter
import com.ccreusot.albums.interactors.AlbumsInteractor
import com.ccreusot.albums.presentations.AlbumsPresenterImpl
import com.ccreusot.albums.presentations.AlbumsView
import com.ccreusot.albums.presentations.AlbumsViewDecorator
import com.ccreusot.albums.repositories.RetrofitAlbumsRepository
import com.ccreusot.albums.viewmodels.AlbumViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity(), AlbumsView {

    private val decorator = AlbumsViewDecorator(this)
    private val interactor = AlbumsInteractor(AlbumsPresenterImpl(decorator), RetrofitAlbumsRepository())
    private var fetchAlbumsJob: Job? = null

    companion object {
        private const val DISPLAY_LOADER = 0
        private const val DISPLAY_ERROR = 1
        private const val DISPLAY_LIST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainContentViewFlipper.displayedChild = DISPLAY_LOADER
        activityMainAlbumRecyclerView.layoutManager = LinearLayoutManager(this)
        activityMainAlbumRecyclerView.adapter = AlbumsAdapter()

        fetchAlbumsJob = launch {
            interactor.fetchAlbums()
        }
    }

    override fun onDestroy() {
        fetchAlbumsJob?.cancel()
        fetchAlbumsJob = null
        decorator.destroy()
        super.onDestroy()
    }

    override fun displayAlbumsError() {
        activityMainContentViewFlipper.displayedChild = DISPLAY_ERROR
        activityMainErrorTextView.text = getString(R.string.activity_main_error_from_server)
    }

    override fun displayEmptyAlbumList() {
        activityMainContentViewFlipper.displayedChild = DISPLAY_ERROR
        activityMainErrorTextView.text = getString(R.string.activity_main_error_empty_list)
    }

    override fun displayAlbums(albumViewModels: List<AlbumViewModel>) {
        activityMainContentViewFlipper.displayedChild = DISPLAY_LIST
        (activityMainAlbumRecyclerView.adapter as AlbumsAdapter).albumViewModelList = albumViewModels
        (activityMainAlbumRecyclerView.adapter as AlbumsAdapter).onClick = this::onClickItem
    }

    fun onClickItem(position : Int) {
        // TODO
    }
}
