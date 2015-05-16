(ns unsafe.core)

(defn- make-unsafe []
  (let [constructor (.getDeclaredConstructor sun.misc.Unsafe nil)]
    (.setAccessible constructor true)
    (.newInstance constructor nil)))

;todo secure this
(def the-unsafe (make-unsafe))

(defn sizeof [^java.lang.Object obj] 
  "returns the shallow (doesn't follow pointers & ignores static fields) size 
  of a java object."
  (let [max-size (apply max (map #(.objectFieldOffset the-unsafe %) 
                                 (all-nonstatic-fields (.getClass obj))))]
  (* 8 (+ 1 (/ max-size 8)))))

(defn- all-nonstatic-fields [^java.lang.Class c]
  (let [is-static? (filter #(not (java.lang.reflect.Modifier/isStatic (.getModifiers %))))
        dec-fields (mapcat #(.getDeclaredFields %))
        not-nil    (take-while #(not (nil? %)))
        xf (comp not-nil dec-fields is-static?)] 
     (sequence xf (iterate #(.getSuperclass %) c))))
