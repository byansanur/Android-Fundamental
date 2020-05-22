package com.byandev.submission2uiux.ui.viewModel


//
//class MainActivityViewModel(private val searchRepository: SearchRepo) : ViewModel() {
//
//    private val compositeDisposable = CompositeDisposable()
//
//    val searchPageList : LiveData<PagedList<Item>> by lazy {
//        searchRepository.fetchLiveSearchPagedList(compositeDisposable)
//    }
//
//    val networkState : LiveData<NetworkState> by lazy {
//        searchRepository.getSearchNetworkState()
//    }
//
//    fun listItemEmpty(): Boolean {
//        return searchPageList.value?.isEmpty() ?: true
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        compositeDisposable.dispose()
//    }
//}
