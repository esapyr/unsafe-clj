(ns unsafe.core)

(defn getUnsafe [] 
  (let [f (.getDeclaredField sun.misc.Unsafe "theUnsafe")]
    (.setAccessible f true)
    (.get f nil)))

(defn sizeof [^java.lang.Object obj] 
  "returns the shallow (doesn't follow pointers) size of a java object."
  (let [theUnsafe (getUnsafe)]
    ))
