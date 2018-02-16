package com.crazymin2.retailstore.framework;

import java.util.List;

/**
 * Created by admin on 2/15/2018.
 */

public interface OnFavResponseListener {

    void onFavSuccess();

    void onFavError();

    void onSuccessFavRemoved(Object item);

    void onDisplayItemsOnFav(List items);

    void onDisplayFavItems(List items);

    void onDisplayTotalFav(int size);

}
