package com.crazymin2.retailstore.framework;

/**
 * Created by admin on 2/15/2018.
 */

public interface FavInteractor {

    void saveItemInFav(Object item, OnFavResponseListener listener);

    void removeItemFromFav(Object item, OnFavResponseListener listener);

    void getTotalItemsInFav(OnFavResponseListener listener);

    void getFavItems(OnFavResponseListener listener, int categoryId);

    void countTotalFavs(OnFavResponseListener listener);
}
