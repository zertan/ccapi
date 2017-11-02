(ns ccapi.lib
  (:require [clj-http.client :as client]
            [clojure.string :as str]))

(defn send-query [base-url query]
  (client/get (str/join [base-url (:type query)])
              {:query-params (:params query)
               :as :json}))

(defn web-query [base-query & params]
  (let [params (or params {})]
    (update-in base-query [:params] conj params)))

(defn get-result
  [response]
  (read-string (get-in response [:body :result])))

(defn sub-get [url base-query norm & params]
  (let [res (send-query url (web-query base-query params))]
    (/ (get-result res) norm)))
