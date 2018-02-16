package com.crazymin2.retailstore.framework;

import com.crazymin2.retailstore.ui.BaseActivity;

import java.util.List;

/**
 * Created by admin on 2/15/2018.
 */

public class FavPresenterImpl implements FavPresenter, OnFavResponseListener {

    private FavView favView;
    private FavInteractor favInteractor;


    public FavPresenterImpl(FavView favView) {
        this.favView = favView;
        this.favInteractor = new FavInteractorImpl();
    }


    @Override
    public void onFavSuccess() {
        if (favView != null) {
            favView.onFavActionResponse(true);
        }
    }
    @Override
    public void onSuccessFavRemoved(Object item) {
        if (favView != null) {
            favView.onSuccessFavDeletion(item);
        }
    }

    @Override
    public void onFavError() {
        if (favView != null) {
            favView.onFavActionResponse(false);
        }
    }

    @Override
    public void onDisplayItemsOnFav(List items) {
        if (favView != null && items != null) {
            favView.displayFavItems(items);
        }
    }

    @Override
    public void onDisplayFavItems(List items) {
        if (favView != null && items != null) {
            favView.displayFavCategoryItems(items);
        }
    }

    @Override
    public void onDisplayTotalFav(int size) {
        if (favView != null) {
            favView.displayCounterFav(size);
        }
    }

    @Override
    public void addItem(Object item) {
        favInteractor.saveItemInFav(item, this);
    }

    @Override
    public void removeItem(Object item) {
        favInteractor.removeItemFromFav(item, this);
    }

    @Override
    public void showMeCategoryItemsFav(int categoryId) {
        favInteractor.getFavItems(this, categoryId);
    }


    @Override
    public void onDestroy() {
        favView = null;
    }

    @Override
    public void showMeItemsInCartFav() {
        favInteractor.getTotalItemsInFav(this);
    }

    @Override
    public void countCartItems() {
        favInteractor.countTotalFavs(this);
    }
}
