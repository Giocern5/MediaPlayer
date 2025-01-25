package com.suno.android.sunointerview.data

import com.google.gson.annotations.SerializedName


data class ApiResponse(
    @SerializedName("end") val end: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("per_page") val perPage: Int?,
    @SerializedName("songs") val songs: List<Song?>?,
    @SerializedName("start") val start: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_songs") val totalSongs: Int?
)


data class Song(
    @SerializedName("audio_url") val audioUrl: String?,
    @SerializedName("avatar_image_url") val avatarImageUrl: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("display_name") val displayName: String?,
    @SerializedName("handle") val handle: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("image_large_url") val imageLargeUrl: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_handle_updated") val isHandleUpdated: Boolean?,
    @SerializedName("is_liked") val isLiked: Boolean?,
    @SerializedName("is_public") val isPublic: Boolean?,
    @SerializedName("is_trashed") val isTrashed: Boolean?,
    @SerializedName("is_video_pending") val isVideoPending: Boolean?,
    @SerializedName("major_model_version") val majorModelVersion: String?,
    @SerializedName("metadata") val metadata: Metadata?,
    @SerializedName("model_name") val modelName: String?,
    @SerializedName("play_count") val playCount: Int?,
    @SerializedName("reaction") val reaction: Reaction?,
    @SerializedName("status") val status: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("upvote_count") val upvoteCount: Int?,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("video_url") val videoUrl: String?
)

data class SongFeedData(
    val id: String,
    val userId: String,
    val audioUrl: String,
    val title: String,
    val imageLargeUrl: String,
    val imageUrl: String
)

// defaults. will change after seeing what i want to use
fun Song.toFeedSong(): SongFeedData {
    return SongFeedData(
        audioUrl = audioUrl ?: "",
        id = id ?: "",
        imageLargeUrl = imageLargeUrl ?: "",
        imageUrl = imageUrl ?: "",
        title = title ?: "",
        userId = userId ?: "",
    )
}

data class Metadata(
    @SerializedName("concat_history") val concatHistory: List<ConcatHistory?>?,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("prompt") val prompt: String?,
    @SerializedName("tags") val tags: String?,
    @SerializedName("type") val type: String?
)

data class Reaction(
    @SerializedName("clip") val clip: String?,
    @SerializedName("flagged") val flagged: Boolean?,
    @SerializedName("play_count") val playCount: Int?,
    @SerializedName("reaction_type") val reactionType: String?,
    @SerializedName("skip_count") val skipCount: Int?,
    @SerializedName("updated_at") val updatedAt: String?
)

data class ConcatHistory(
    @SerializedName("continue_at") val continueAt: Double?,
    @SerializedName("id") val id: String?
)

