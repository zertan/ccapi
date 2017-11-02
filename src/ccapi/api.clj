(ns ccapi.api
  (:require [ccapi.lib :refer :all]))

(defn new-api [api]
  (let []))

;(map define-method handlers)

(defn define-method
  [name dispatch-value body]
  `(defmethod name dispatch-value [] body))

;(new-api :ethscan )
