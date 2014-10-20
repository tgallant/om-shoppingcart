(ns om-shoppingcart.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]))

(enable-console-print!)

(def app-state
  (atom
    {:products
     [{:title "test product 1" :price "99"}
      {:title "test product 2" :price "399"}]
     :selected []}))

(defn display-title [{:keys [title] :as product}]
  (str title))

(defn display-price [{:keys [price] :as product}]
  (str "$" price))

(defn product-view [product owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "pure-u-1 pure-u-md-1-2"}
        (dom/h2 nil (display-title product))
        (dom/h4 nil (display-price product))
        (dom/button #js {:className "pure-button"} "Add to Cart")))))

(defn cart-menu [selected owner]
  (if (= (count selected) 0)
    (reify
      om/IRender
      (render [this]
        (dom/div #js {:className "pure-menu pure-menu-open pure-menu-horizontal pure-menu-pull-right"}
          (dom/ul nil
            (dom/li nil (dom/a #js {:href "#"} "Home"))
            (dom/li nil (dom/a #js {:href "#"} (dom/i #js {:className "fa fa-shopping-cart fa-2x"})))))))
    (reify
      om/IRender
      (render [this]
        (dom/div #js {:className "pure-menu pure-menu-open pure-menu-horizontal pure-menu-pull-right"}
          (dom/ul nil
            (dom/li nil (dom/a #js {:href "#"} "Home"))
            (dom/li nil (dom/a #js {:href "#" :data-dropdown "#dropdown-1" :data-horizontal-offset "-50"}
                          (dom/i #js {:data-count (count selected) :className "fa fa-shopping-cart fa-2x badge"})))))))))

(defn products-view [app owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div #js {:className "shoppingMain"}
        (om/build cart-menu (:selected app))
        (dom/h2 nil "Products")
        (om/build-all product-view (:products app))))))

(om/root products-view app-state
  {:target (gdom/getElement "app")})
