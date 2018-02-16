package com.crazymin2.retailstore.framework;

/**
 * Created by admin on 2/15/2018.
 */

public interface FavPresenter {

    void onDestroy();

    void addItem(Object item);

    void removeItem(Object item);

    void showMeCategoryItemsFav(int id);

    void showMeItemsInCartFav();

    void countCartItems();

}
