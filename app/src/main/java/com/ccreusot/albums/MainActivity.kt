package com.ccreusot.albums

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ccreusot.albums.adapters.AlbumsAdapter
import com.ccreusot.albums.interactors.AlbumsInteractor
import com.ccreusot.albums.presentations.AlbumsPresenterImpl
import com.ccreusot.albums.presentations.AlbumsView
import com.ccreusot.albums.presentations.AlbumsViewDecorator
import com.ccreusot.albums.repositories.CacheAlbumsRepository
import com.ccreusot.albums.repositories.RetrofitAlbumsRepository
import com.ccreusot.albums.viewmodels.AlbumViewModel
import kotlinx.android.synthetic.main.activity_layout_list.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity(), AlbumsView {

    private val decorator = AlbumsViewDecorator(this)
    private val interactor = AlbumsInteractor(
            AlbumsPresenterImpl(decorator),
            CacheAlbumsRepository(this, RetrofitAlbumsRepository())
    )

    private var fetchAlbumsJob: Job? = null

    companion object {
        private const val DISPLAY_LOADER = 0
        private const val DISPLAY_ERROR = 1
        private const val DISPLAY_LIST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_list)

        contentViewFlipper.displayedChild = DISPLAY_LOADER
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.adapter = AlbumsAdapter()

        fetchAlbumsJob = launch {
            interactor.fetchAlbums()
        }
    }

    override fun onDestroy() {
        // We're removing the job since the activity is destroy
        fetchAlbumsJob?.cancel()
        fetchAlbumsJob = null
        decorator.destroy()
        super.onDestroy()
    }

    override fun displayAlbumsError() {
        contentViewFlipper.displayedChild = DISPLAY_ERROR
        errorTextView.text = getString(R.string.generic_error_from_server)
    }

    override fun displayEmptyAlbumList() {
        contentViewFlipper.displayedChild = DISPLAY_ERROR
        errorTextView.text = getString(R.string.activity_main_error_empty_list)
    }

    override fun displayAlbums(albumViewModels: List<AlbumViewModel>) {
        contentViewFlipper.displayedChild = DISPLAY_LIST
        (itemRecyclerView.adapter as AlbumsAdapter).albumViewModelList = albumViewModels
        (itemRecyclerView.adapter as AlbumsAdapter).onClick = this::onClickItem
    }

    fun onClickItem(position: Int) {
        val albumId = (itemRecyclerView.adapter as AlbumsAdapter).albumViewModelList?.get(position)?.getId()
                ?: -1
        AlbumDetailActivity.startActivity(this, albumId)
    }
}
