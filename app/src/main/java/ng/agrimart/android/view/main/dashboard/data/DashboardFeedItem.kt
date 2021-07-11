package ng.agrimart.android.view.main.dashboard.data

data class DashboardFeedItem(val data: List<Any>, val type: Type)  {

    enum class Type {
        CATEGORY_LIST,
        PRODUCT
    }

}
