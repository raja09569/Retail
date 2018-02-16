package com.crazymin2.retailstore.framework;

import java.util.List;

/**
 * Created by admin on 2/15/2018.
 */

public interface FavView<T> {
    /*
    * update total items in cart
    */
    void displayCounterFav(int size);

    /*
    * load all items present in cart
    */
    void displayFavItems(List<T> items);

    /*
    * this method will help to load categories items for items inside
    * e.g furniture and electronic category
    */
    void displayFavCategoryItems(List<T> items);

    void onFavActionResponse(boolean isActionSuccessful);

    /*
    * On successful deletion of an item from cart
    */
    void onSuccessFavDeletion(Object item);


}
