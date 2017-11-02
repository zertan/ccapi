(ns ccapi.core
  (:require [ccapi.lib :refer :all]
            [ccapi.api :refer :all])
  (:gen-class))

;; (def res (send-query ethscan-url {:type "api"
;;                                   :params {"module" "account"
;;                                            "action" "txlist"
;;                                            "startblock" "0"
;;                                            "endblock"   "99999999"
;;                                            "address" eth-adress
;;                                            "sort" "asc"
;;                                            "apikey" ethscan-key}}))

(defn get-total
  [balances & {:keys [symbol] :or {symbol :USD}}]
  (let [tsyms (str/join "," (map name (keys balances)))
        price-query {:type "price"
                     :params {:fsym  (name symbol)
                              :tsyms tsyms}}
        rates (:body (send-query cc-url price-query))]
    (apply + (vals (merge-with / balances rates)))))

(def ethscan-key "99WUGU8FB363KIUZTXHFSQ1VCSQB41PGNF")
(def eth-adress "e9b568c323a3c58e13b333a4e73b089aada171f6")

(def mco-contract "0xb63b606ac810a52cca15e44Bb630fd42d8d1d83d")

(def cc-url "https://min-api.cryptocompare.com/data/")
;(def ethscan-url "https://api.etherscan.io/")
(def fixer-url "https://api.fixer.io/")

(fn []
  (let [x 0]
    (fn [] (inc x))))

(let [x 0]
  (defn inc-obj []
    (fn [] (inc x))))

(def counter
  ^{:doc "A simple counter object"}
  (let [x (atom 0)]
    (swap! x inc)))



(def x (atom 0))

(swap! x inc)

(def ethscan-base-query
  {:type "api"
   :params {"module" "account"
            "action" "balance"
            "address" :adress
            "tag" "latest"
            "apikey" :apikey}})

(def fixerio-base-query
  {:type "latest"
   :params {"base" "USD"
            "symbols" "SEK"}})


;(defrecord Api [url base-query other])

;(defrecord Asset [name api])

{:ethscan {:url "https://api.etherscan.io/"

           :base-query {:type "api"
                        :params {"module" "account"
                                 "action" "balance"
                                 "address" `adress
                                 "tag" "latest"
                                 "apikey" `apikey}}

           :handlers [{:asset :ETH
                       :method `get-balance
                       :body `(let [res (send-query url (web-query base-query))]
                                (/ (get-result res) 1E18))}

                      {:asset :MCO
                       :method `get-balance
                       :mco-contract "0xb63b606ac810a52cca15e44bb630fd42d8d1d83d"
                       :body `(sub-get :url
                                       :base-query
                                       1E8
                                       {"action" "tokenbalance"
                                        "contractaddress" :mco-contract})}]}}


(def assets {:ETH :ethscan
             :MCO :ethscan})

(defmulti get-balance (fn [x] x))

(get-balance :ETH)

(defmethod get-balance :MCO []
  (sub-get ethscan-url
           ethscan-base-query
           1E8
           {"action" "tokenbalance" "contractaddress" mco-contract}))

(defmethod get-balance :ETH []
  (sub-get ethscan-url
           ethscan-base-query
           1E18))

(def balances {:BTC 0
               :ETH eth
               :MCO mco
               :USDT 0
               :XRP 28005})

(get-balances )

(send-query fixer-url fixerio-base-query)

(get-total balances :symbol :USD)

(* 6895 8.3)

(get-total {:XRP 1} :symbol :USD)
(get-total {:MCO 1} :symbol :USD)
(get-total {:USD 1} :symbol :SEK)

(web-query ethscan-base-query {"action" "tokenbalance"
                               "contractaddress" mco-contract})
