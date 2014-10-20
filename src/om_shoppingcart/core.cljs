(ns om-shoppingcart.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [goog.dom :as gdom]))

(enable-console-print!)

(def app-state
  (atom
    {:products
     [{:title "test product 1" :price "99"}
      {:title "test product 2" :price "399"}]}))

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
        (dom/h4 nil (display-price product))))))

(defn products-view [app owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div #js {:className "shoppingMain"}
        (dom/h2 nil "Products")
        (om/build-all product-view (:products app))))))

(om/root products-view app-state
  {:target (gdom/getElement "app")})
