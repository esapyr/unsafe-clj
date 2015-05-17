(ns unsafe.util
  (:require [unsafe.core :as unsafe]))

(defn all-nonstatic-fields 
  [^java.lang.Class c]
  (let [is-static?      (filter #(not (java.lang.reflect.Modifier/isStatic (.getModifiers %))))
        declared-fields (mapcat #(.getDeclaredFields %))
        not-nil         (take-while #(not (nil? %)))
        xf (comp not-nil declared-fields is-static?)]
     (sequence xf (iterate #(.getSuperclass %) c))))

(defn ^long sizeof
  "returns the shallow (doesn't follow pointers & ignores static fields) size 
  of a javaobject."
  [obj]
  (let [max-size (apply max (map #(unsafe/object-field-offset %)
                                 (all-nonstatic-fields (.getClass obj))))]
  (long (* 8 (+ 1 (/ max-size 8))))))

(defmulti compare-and-swap (fn [obj offset expected x] 
                             (mapv class [obj offset expected x])))
(defmethod compare-and-swap 
  [java.lang.Object long int int] [obj offset expected x]
  (unsafe/CAS-int obj offset expected x))
(defmethod compare-and-swap 
  [java.lang.Object long long long] [obj offset expected x]
  (unsafe/CAS-long obj offset expected x))
(defmethod compare-and-swap 
  [java.lang.Object long java.lang.Object java.lang.Object] [obj offset expected x]
  (unsafe/CAS-obj obj offset expected x))

; Getters and Setters
;{:type int, :volatile? true, :args [obj offset]}
; (unsafe-get {:type int, :volatile? true, :args [obj offset]}) 
; ---
; normal get: (get map key),   i.e. (get from with)
; (unsafe/get obj offset int), i.e. (get from with as)
; (unsafe/get address int),    i.e. (get from as)
; (unsafe/get-volatilely obj offset int)
(defmulti unsafe-get :type)
(defmulti unsafe-put :type)

(defmethod unsafe-get java.lang.Boolean
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-boolean-volatile args)
    (apply unsafe/get-boolean args)))
(defmethod unsafe-put java.lang.Boolean
  [{args :args volatile :volatile?}] 
  (if volatile
    (apply unsafe/put-boolean-volatile args)
    (apply unsafe/put-boolean args)))

(defmethod unsafe-get java.lang.Byte
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-byte-volatile args)
    (apply unsafe/get-byte args)))
(defmethod unsafe-put java.lang.Byte
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-byte args)
    (apply unsafe/put-byte-volatile args)))

(defmethod unsafe-get java.lang.Character
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-char-volatile args)
    (apply unsafe/get-char args)))
(defmethod unsafe-put java.lang.Character
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-char-volatile args)
    (apply unsafe/put-char args)))

(defmethod unsafe-get java.lang.Double
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-double-volatile args)
    (apply unsafe/get-double args)))
(defmethod unsafe-put java.lang.Double
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-double-volatile args)
    (apply unsafe/put-double args)))

(defmethod unsafe-get java.lang.Float 
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-float-volatile args)
    (apply unsafe/get-float args)))
(defmethod unsafe-put java.lang.Float
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-float-volatile args)
    (apply unsafe/put-float args)))

(defmethod unsafe-get java.lang.Integer
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-int-volatile args)
    (apply unsafe/get-int args)))
(defmethod unsafe-put java.lang.Integer
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-int-volatile args)
    (apply unsafe/put-int args)))

(defmethod unsafe-get java.lang.Long
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-long-volatile args)
    (apply unsafe/get-long args)))
(defmethod unsafe-put java.lang.Long
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-long-volatile args)
    (apply unsafe/put-long args)))

(defmethod unsafe-get java.lang.Object 
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-obj-volatile args)
    (apply unsafe/get-obj args)))
(defmethod unsafe-put java.lang.Object
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-obj-volatile args)
    (apply unsafe/put-obj args)))

(defmethod unsafe-get java.lang.Short
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/get-short-volatile args)
    (apply unsafe/get-short args)))
(defmethod unsafe-put java.lang.Short
  [{args :args volatile :volatile?}]
  (if volatile
    (apply unsafe/put-short-volatile args)
    (apply unsafe/put-short args)))
