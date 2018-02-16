package com.crazymin2.retailstore.framework;

import android.os.Handler;

import com.crazymin2.retailstore.database.DatabaseManager;
import com.crazymin2.retailstore.home.data.Product;

import java.util.ArrayList;

import static com.crazymin2.retailstore.util.LogUtils.LOGD;

/**
 * Created by admin on 2/15/2018.
 */

public class FavInteractorImpl implements FavInteractor {

    private static final String TAG = FavInteractorImpl.class.getSimpleName();
    @Override
    public void saveItemInFav(Object item, OnFavResponseListener listener) {
        boolean persistProduct = DatabaseManager.getInstance().saveFavProduct((Product) item);
        if (persistProduct) {
            LOGD(TAG, "Item added into local database");
            listener.onFavSuccess();
        } else {
            LOGD(TAG, "Item not added into local database");
            listener.onFavError();
        }
    }

    @Override
    public void removeItemFromFav(Object item, OnFavResponseListener listener) {
        boolean deleteCartProduct = DatabaseManager.getInstance().deleteFavProduct((Product) item);
        if (deleteCartProduct) {
            LOGD(TAG, "Item successfully deleted from local database");
            listener.onSuccessFavRemoved((Product) item);
        } else {
            LOGD(TAG, "Item not deleted from local database");
            listener.onFavError();
        }
    }

    @Override
    public void getTotalItemsInFav(final OnFavResponseListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<Product> cartProducts = DatabaseManager.getInstance().getFavProducts();
                listener.onDisplayItemsOnFav(cartProducts);
            }
        });
    }

    @Override
    public void getFavItems(final OnFavResponseListener listener, final int categoryId) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArrayList<Product> cartProducts = DatabaseManager.getInstance().getFavProducts();
                ArrayList<Product> productsByCategory = DatabaseManager.getInstance().getProductsByCategory(categoryId);
                int position = 0;
                for (Product product : productsByCategory) {
                    if (!cartProducts.contains(product)) {
                        productsByCategory.get(position).isInFav = false;
                        LOGD(TAG, "item not matched");
                    } else {
                        productsByCategory.get(position).isInFav = true;
                        LOGD(TAG, "item matched");
                    }
                    position++;
                }
                listener.onDisplayFavItems(productsByCategory);
            }
        });
    }

    @Override
    public void countTotalFavs(OnFavResponseListener listener) {
        int productCount = DatabaseManager.getInstance().getFavCount();
        listener.onDisplayTotalFav(productCount);
    }
}
