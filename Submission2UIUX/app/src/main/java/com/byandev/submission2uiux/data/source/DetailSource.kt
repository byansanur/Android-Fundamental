package com.byandev.submission2uiux.data.source

//@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
//class DetailSource (private val apiService: ApiEndpoint, private val compositeDisposable: CompositeDisposable) {
//
//    private val _networkState = MutableLiveData<NetworkState>()
//    val networkState: LiveData<NetworkState> get() = _networkState
//
//    private val _downloadMovieDetailResponse = MutableLiveData<DetailUser>()
//    val downloadMovieDetailResponse: LiveData<DetailUser> get() = _downloadMovieDetailResponse
//
//    suspend fun fetchMovieDetail(username: String) {
//        _networkState.postValue(NetworkState.LOADING)
//
//        try {
//            compositeDisposable.add(
//                apiService.detailUser(username)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(
//                        {
//                            _downloadMovieDetailResponse.postValue(it)
//                            _networkState.postValue(NetworkState.LOADED)
//                        },
//                        {
//                            _networkState.postValue(NetworkState.ERROR)
//                            Log.d("movieDetail", it.message)
//                        }
//                    )
//            )
//        } catch (e: Exception) {
//            Log.d("tryCatch movie detail", e.message)
//        }
//    }
//
//}
//}
