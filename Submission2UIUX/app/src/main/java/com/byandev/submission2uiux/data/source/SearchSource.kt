package com.byandev.submission2uiux.data.source

//class SearchSource (private val apiEndpoint: ApiEndpoint, private val compositeDisposable: CompositeDisposable) {
    
//    private val _networkState = MutableLiveData<NetworkState>()
//    val networkState: LiveData<NetworkState> get() = _networkState
//
//    private val downloadSearch = MutableLiveData<SearchModel>()
//
//    val downloadSearchResponse: LiveData<SearchModel> get() = downloadSearch
//
//    fun fetchSearchUser(query: String) {
//
//        _networkState.postValue(NetworkState.LOADING)
//        try {
//            compositeDisposable.add(
//                apiEndpoint.searchUsers(query)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(
//                        {
//                            downloadSearch.postValue(it)
//                            _networkState.postValue(NetworkState.LOADED)
//                        },
//                        {
//                            _networkState.postValue(NetworkState.ERROR)
//                        }
//                    )
//            )
//        } catch (e: Exception) {
//            Log.e("TAG search source", "fetchSearchUser: ${e.message}" )
//        }
//    }


//}
