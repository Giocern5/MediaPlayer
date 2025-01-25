package com.suno.android.sunointerview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import android.util.Log
import com.suno.android.sunointerview.data.Song
import com.suno.android.sunointerview.data.toFeedSong
import com.suno.android.sunointerview.network.MediaService
import javax.inject.Inject

class MediaFeedPagingSource @Inject constructor (
    private val mediaService: MediaService,
): PagingSource<Int, Song>()  {

    companion object {
        const val TAG = "MediaFeedPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, Song>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Song> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = mediaService.getSongs(page = page, pageSize = pageSize)

            if (response.isSuccessful) {
                val songs = response.body()?.songs.orEmpty()
                    .filterNotNull()
                    .map { it.toFeedSong() }

                LoadResult.Page(
                    data = songs,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (songs.isEmpty()) null else page.plus(1),
                )
            } else {
                Log.e(TAG, response.message().toString())
                LoadResult.Error(Exception(response.message().toString()))
            }
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
            LoadResult.Error(e)
        }
    }

}